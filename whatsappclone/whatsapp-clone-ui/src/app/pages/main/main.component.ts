import { Component, OnInit } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import {ChatResponse, MessageRequest, MessageResponse} from '../../services/models';
import {ChatService, MassageService} from '../../services/services';
import {KeycloakService} from '../../utils/keycloak/keycloak.service';
import {DatePipe} from '@angular/common';
import {PickerComponent} from '@ctrl/ngx-emoji-mart';
import {FormsModule} from '@angular/forms';
import {EmojiData} from '@ctrl/ngx-emoji-mart/ngx-emoji';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';

@Component({
  selector: 'app-main',
  imports: [ChatListComponent, DatePipe, PickerComponent, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent implements OnInit {
  chats: Array<ChatResponse> = [];
  selectedChat: ChatResponse = {};
  chatMessages: MessageResponse[] = [];
  showEmojis: boolean = false;
  messageContent:string = '';
  socketClient: any = null;
  private notificationSubscription: any;

  constructor(private chatService: ChatService,
              private keyCloakService: KeycloakService,
              private messageService: MassageService) {}

  ngOnInit(): void {
    this.initWebSocket();
    this.getAllChats();
  }

  private getAllChats() {
    this.chatService.getChat().subscribe({
      next: (res) => {
        this.chats = res;
      },
    });
  }

  userProfile() {
    this.keyCloakService.accoutManagement();
  }

  logout() {
  this.keyCloakService.logout();
  }

  chatSelected(chatResponse: ChatResponse) {
      this.selectedChat = chatResponse;
      this.getAllChatMessages(chatResponse.id as string);
      this.setMessagesToSeen();
      this.selectedChat.unreadCount = 0;
  }

  private getAllChatMessages(chatId: string) {
        this.messageService.getMessages({
          'chat-id': chatId
        }).subscribe({
          next: (messages) => {
            this.chatMessages = messages;
          }
        })
  }

  private setMessagesToSeen() {
      this.messageService.setMessagesToSeen({
        'chat-id': this.selectedChat.id as string
      }).subscribe({
        next: () => {}
      });
  }
  isSelfMessage(message: MessageResponse) {
      return message.senderId === this.keyCloakService.userId;
  }

  uploadMediaFile(target: EventTarget | null) {

  }

  onSelectEmojis(emojiSelected: any) {
        const emoji:EmojiData = emojiSelected.emoji;
        this.messageContent += emoji.native;
  }

  keyDown(event: KeyboardEvent) {
      if (event.key === 'Enter') {
        this.sendMessage();
      }
  }

  onClick() {
    this.setMessagesToSeen();
  }

  sendMessage() {
      if (this.messageContent) {
        const messageRequest: MessageRequest = {
          chatId: this.selectedChat.id,
          senderId: this.getSenderId(),
          receiverId: this.getReceiverId(),
          content: this.messageContent,
          type: 'TEXT'
        };
        this.messageService.saveMessage({
          body: messageRequest
        }).subscribe({
          next: () => {
           const message: MessageResponse = {
             senderId: this.getSenderId(),
             receiverId: this.getReceiverId(),
             content: this.messageContent,
             type: 'TEXT',
             state: 'SENT',
             createdAt: new Date().toString()
           };
           this.selectedChat.lastMessage = this.messageContent;
           this.chatMessages.push(message);
           this.messageContent = '';
           this.showEmojis = false;
          }
        });
      }
  }

  private getSenderId(): string {
    if (this.selectedChat.senderId === this.keyCloakService.userId) {
      return this.selectedChat.senderId as string;
    }
    return this.selectedChat.receiverId as string;

  }

  private getReceiverId():string {
    if (this.selectedChat.senderId === this.keyCloakService.userId) {
      return this.selectedChat.receiverId as string;
    }
    return this.selectedChat.senderId as string;
  }

  private initWebSocket() {
    if (this.keyCloakService.keycloak.tokenParsed?.sub) {
      let ws = new SockJS('http://localhost:8080/ws');
      this.socketClient = Stomp.over(ws);
      const subUrl =  `/user/${this.keyCloakService.keycloak.tokenParsed?.sub}/chat`;
      this.socketClient.connect({'Authorization': 'Bearer ' + this.keyCloakService.keycloak.token},
        () => {
          this.notificationSubscription = this.socketClient.subscribe(subUrl,
            (message: any) => {
                const notification = Notification = JSON.parse(message.body);
            },
            () => console.error("Error while connection to WebSocket"));
        }
        );
    }
  }
}
