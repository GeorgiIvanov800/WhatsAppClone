import { Component, OnInit } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import {ChatResponse, MessageResponse} from '../../services/models';
import {ChatService, MassageService} from '../../services/services';
import {KeycloakService} from '../../utils/keycloak/keycloak.service';
import {DatePipe} from '@angular/common';
import {PickerComponent} from '@ctrl/ngx-emoji-mart';
import {FormsModule} from '@angular/forms';

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


  constructor(private chatService: ChatService,
              private keyCloakService: KeycloakService,
              private messageService: MassageService) {}

  ngOnInit(): void {
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
      // this.selectedChat.unreadCount = 0;
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

  }
  isSelfMessage(message: MessageResponse) {
      return message.senderId === this.keyCloakService.userId;
  }

  uploadMediaFile(target: EventTarget | null) {

  }

  onSelectEmojis(emojiSelected: any) {

  }

  keyDown(event: KeyboardEvent) {

  }

  onClick() {

  }

  sendMessage() {

  }
}
