import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChatService } from '../chat.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chat-room',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './chatRoom.component.html',
  styleUrl: './chatRoom.component.css'
})
export class ChatRoomComponent {
  formulaire = new FormGroup({
    message: new FormControl('')
  });
  messages: {username: String, message: String}[] = [];
  username: String = "";

  constructor(private chat : ChatService, private route : Router) {
    this.chat.getSocket().on('message', (message) => {
      console.log(message.username, message.content);
      this.messages.unshift({username: message.username, message: message.content});
    });
    this.username = chat.username;
  }

  onSubmit() {
    this.chat.sendMessage(this.formulaire.value.message||"");
    this.formulaire.reset();
  }

  loggout () {
    this.chat.getSocket().disconnect();
    this.chat.username = "";
    this.route.navigate(['/']);
  }

}
