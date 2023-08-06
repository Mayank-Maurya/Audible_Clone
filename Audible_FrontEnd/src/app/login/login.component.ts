import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms'
import {Router} from '@angular/router'
import { AuthServiceService } from '../auth-service.service';
import { StorageService } from '../storage.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  LoginForm!:FormGroup;


  constructor(
    private router:Router,
    private fb:FormBuilder,
    private ser:AuthServiceService,
    private storagesSer:StorageService){}

  ngOnInit(): void {
    this.LoginForm=this.fb.group({
      username:[''],
      password:['']
    })
    
  }

  Login(){
    this.ser.loginUser(this.LoginForm.value).subscribe({
      next:(res)=>{
        console.log(res);
        this.storagesSer.saveUser(res);
      },
      error:(msg)=>{console.log(msg)},
      complete:()=>{
        console.log("login Successfull");
        this.router.navigate(['/home'])}
    })
  }

  NavigateToRegister(){
    this.router.navigate(['/register'])
  }



}
