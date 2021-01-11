import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignupRequestPayload } from './signup-request.payload';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  signuprequest: SignupRequestPayload;
  signupForm: FormGroup;

  constructor(
    private authService: AuthService,
    private router: Router // private toaster: ToasterService
  ) {
    this.signuprequest = {
      userName: '',
      userPassword: '',
      userEmail: '',
    };
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }
  signup() {
    this.signuprequest.userName = this.signupForm.get('username').value;
    this.signuprequest.userEmail = this.signupForm.get('email').value;
    this.signuprequest.userPassword = this.signupForm.get('password').value;
    // console.log('Signuprequest is ', this.signuprequest);
    this.authService.signup(this.signuprequest).subscribe((data) => {
      console.log(data);
    });
  }
}
