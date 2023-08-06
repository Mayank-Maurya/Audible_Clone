import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './User';
import { loginData } from './loginData';
@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  baseURL:string = "http://localhost:8000/user/";

  constructor(private http:HttpClient) { }

   registerUser(user:User){
    user.roles="USER"
    return this.http.post(this.baseURL + 'register', user,{responseType:'text'})
   }

   loginUser(data:loginData){
    console.log(data);
    return this.http.post(this.baseURL+'login',data);
   }



}
