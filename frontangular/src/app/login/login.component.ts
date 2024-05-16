import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ChatService } from '../chat.service';
import { SafeScript } from '@angular/platform-browser';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  formulaire = new FormGroup({
    username: new FormControl('')
  });
  
  constructor(private router : Router, private chat : ChatService) {}

  onSubmit() {
    console.log('Vous avez choisi la réponse : ' + this.formulaire.value.username);
    console.log(this.chat.connect(this.formulaire.value.username as String));
    this.router.navigate(['/chatroom']);
  }
  
}
