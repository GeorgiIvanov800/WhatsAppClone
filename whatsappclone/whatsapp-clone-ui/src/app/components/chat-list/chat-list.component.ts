import { Component, input, InputSignal } from '@angular/core';
import { ChatResponse } from '../../services/models';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-chat-list',
  imports: [
    DatePipe
  ],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss',
})
export class ChatListComponent {
  chats: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);

  searchNewContact: boolean = false;

  searchContact() {

  }

  chatClicked(chat: ChatResponse) {

  }

  wrapMessage(lastMessage: string | undefined): string {
    if (lastMessage && lastMessage.length <= 20) {
      return lastMessage;
    }
    return lastMessage?.substring(0, 17) + '....';
  }
}
