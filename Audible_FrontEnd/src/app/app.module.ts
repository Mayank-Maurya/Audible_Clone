import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

import {MatMenuModule} from '@angular/material/menu';
import {MatSliderModule} from '@angular/material/slider';
import {MatToolbarModule} from '@angular/material/toolbar'
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterComponent } from './register/register.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthServiceService } from './auth-service.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import {MatDividerModule} from '@angular/material/divider';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { AudioPlayerComponent } from './audio-player/audio-player.component';
import { ToHoursPipe } from './to-hours.pipe';
import { LibraryComponent } from './library/library.component';
import{MatListModule} from '@angular/material/list';
import{MatCardModule} from '@angular/material/card';
import{MatButtonModule} from '@angular/material/button';
import { CartComponent } from './cart/cart.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomePageComponent,
    AudioPlayerComponent,
    ToHoursPipe,
    LibraryComponent,
    CartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatIconModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDividerModule,
    MatCheckboxModule,
    MatSliderModule,
    MatButtonModule,
    MatListModule,
    MatCardModule,
    MatSliderModule,
    MatMenuModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
