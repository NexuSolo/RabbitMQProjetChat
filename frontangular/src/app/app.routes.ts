import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ChatRoomComponent } from './chat-room/chatRoom.component';

export const routes: Routes = [
    { path: '', component: LoginComponent},
    { path: 'chatroom', component: ChatRoomComponent}
];
