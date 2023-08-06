import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder,FormGroup, Validators} from '@angular/forms'
import { Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  RegisterForm!:FormGroup
  errorMessage!:string;
  successMessage!:string;

  constructor(private router:Router, private fb:FormBuilder, private ser:AuthServiceService){}
  ngOnInit(): void {
    this.RegisterForm = this.fb.group({
      fullName:['',[Validators.required,this.validateFullName]],
      email:['',[ Validators.required,Validators.email]],
      userName:['',[ Validators.required,this.validateUserName]],
      password:['',[Validators.required,this.validatePassword]],
      confirmPassword:['']
    })
    this.RegisterForm.controls['confirmPassword'].setValidators([Validators.required,this.confirmPassword(this.RegisterForm.controls['password'])]);
  }

  Register(){
    
    this.errorMessage="";
    this.successMessage="";

    this.ser.registerUser(this.RegisterForm.value).subscribe({
      next:(res) =>{
        console.log(res);
        this.successMessage = res
      },
      error:(err)=>{
        console.log(err.error);
        this.errorMessage=err.error
      },
      complete:()=>{
        //this.NavigateToLogin();
      }
    });

  }

  NavigateToLogin(){
    this.router.navigate(['/'])
  }

  validateFullName(control: AbstractControl):any {
    let fullNameRegex = /^([A-Z][a-z]{2,})( [A-Z][a-z]*){0,2}/;
    let value = control.value;
    let matches: boolean = fullNameRegex.test(value);
    if (!matches) {
        return { "namePatternError": true }
    }
    return null;
  }

  validateUserName(control: AbstractControl): any {
    let namePattern1: RegExp = /^[A-Za-z0-9]+(?:[._-][A-Za-z0-9]+)*$/;
    let value = control.value;
    let matches: boolean = namePattern1.test(value);
    if (!matches) {
        return { "namePatternError": true }
    }
    return null;
  }

  validatePassword(control: AbstractControl): any {
    let pattern1: RegExp = /^.*[A-Z]+.*/;
    let pattern2: RegExp = /^.*[a-z]+.*/;
    let pattern3: RegExp = /.*[\d]+.*/;
    let pattern4: RegExp = /.*[@#$%&*^]+.*/;
    let value = control.value;
    let matches: boolean = pattern1.test(value) && pattern2.test(value) && pattern3.test(value)
        && pattern4.test(value);

    if (!matches) {
        return { "passwordPatternError": true }
    }
    return null;
  }

  confirmPassword(passwordControl: AbstractControl): any {
    return (confirmPasswordControl: AbstractControl)=>{
        if(passwordControl.value != confirmPasswordControl.value)
            return {'confirmPassword': true}
        return null;
    };
  }




}
