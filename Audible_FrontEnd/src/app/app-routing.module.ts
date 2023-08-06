import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AudioPlayerComponent } from './audio-player/audio-player.component';
import { authenticate } from './authenticate.guard';
import { CartComponent } from './cart/cart.component';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { toHomeGuardGuard } from './to-home-guard.guard';

const routes: Routes = [
  {path:'register',component:RegisterComponent},
  {path:'',component:LoginComponent,canActivate:[toHomeGuardGuard]},
  {path:'home',component:HomePageComponent,canActivate:[authenticate]},
  {path:'audio-player',component:AudioPlayerComponent,canActivate:[authenticate]},
  {path:'cart',component:CartComponent,canActivate:[authenticate]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
