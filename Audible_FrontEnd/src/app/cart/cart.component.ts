import { Component, OnInit } from '@angular/core';
// import * as Razorpay from 'razorpay';
import { CartService } from '../cart.service';
import { StorageService } from '../storage.service';

declare var Razorpay: any;

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  details1: any = [];
  totalprice = 0;
  allCartData: any = [];
  allAudioBookData: any = [];
  allAudioBookDataOfCart: any = [];
  transactionId: any;
  custId: any;
  allCartDataOfUser: any = [];
  checkCartPayment: any = {};
  userName!:any;

  constructor(private service: CartService, private storage: StorageService) {}

  ngOnInit(): void {
    //this.getCarts();
    console.log(JSON.parse(this.storage.getUser()).username);
    this.userName = JSON.parse(this.storage.getUser()).username;
    this.getCustomerByUserName(this.userName);
  }

  LogOut(){
    this.storage.clean();
    window.location.href="/";
  }

  getCustomerByUserName(userName: any) {
    this.service.getCustomerId(userName).subscribe({
      next: (res) => {
        this.custId = res.userId;
      },
      error: (err) => {
        console.log(err);
      },
      complete: () => {
        this.getCarts();
      },
    });
  }

  getCarts() {
    this.allCartData = [];
    this.service.getCartDetails(this.custId, 'w').subscribe({
      next: (res) => {
        this.allCartData = res;
        console.log(this.allCartData);
      },
      complete: () => {
        this.getAudioBookData();
      },
    });
  }

  getAudioBookData() {
    // this.getCarts();
    this.service.getAllAudioBook().subscribe({
      next: (res) => {
        this.allAudioBookData = res;
        this.getAudioBookDataOfCart();
      },
      error: (err) => {},
    });
  }

  getAudioBookDataOfCart() {
    this.allAudioBookDataOfCart = [];
    this.totalprice = 0;
    console.log('inside get audio bookdata of cart');
    // this.getAudioBookData();
    for (let i = 0; i < this.allCartData.length; i++) {
      for (let b of this.allAudioBookData) {
        if (
          this.allCartData[i].audioBookId == b.audioBookId &&
          this.allCartData[i].type == 'w'
        ) {
          this.allAudioBookDataOfCart.push(b);
          break;
        }
      }
    }
    for (let a of this.allAudioBookDataOfCart) {
      this.totalprice += a.price;
    }
  }
  cartId = 0;
  delete(a: any) {
    this.totalprice = this.totalprice - a.price;
    for (let i of this.allCartData) {
      if (i.audioBookId == a.audioBookId) {
        this.cartId = i.cartId;
        break;
      }
    }
    this.service.deleteCarts(this.cartId).subscribe((res) => {
      alert('successfully deleted');
      window.location.href = '/cart';
    });
  }

  createTransactionAndPlaceOrder() {
    let amount = this.totalprice;
    console.log(amount);
    this.service.createTransaction(amount).subscribe({
      next: (response) => {
        this.openTransactioModal(response);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  openTransactioModal(response: any) {
    var options = {
      order_id: response.orderId,
      key: response.key,
      amount: response.amount,
      currency: response.currency,
      name: 'Pay For Your AudioBook',
      description: 'Payment of online shopping',
      image:
        'https://cdn.pixabay.com/photo/2023/01/22/13/46/swans-7736415_640.jpg',
      handler: (response: any) => {
        if (response != null && response.razorpay_payment_id != null) {
          this.processResponse(response);
        } else {
          alert('Payment failed..');
        }
      },
      prefill: {
        name: 'LPY',
        email: 'LPY@GMAIL.COM',
        contact: '90909090',
      },
      notes: {
        address: 'Online Shopping',
      },
      theme: {
        color: '#F37254',
      },
    };

    var razorPayObject = new Razorpay(options);
    razorPayObject.open();
  }
  processResponse(resp: any) {
    console.log(resp);
    for (let a of this.allCartData) {
      // a.transactionId = resp.razorpay_payment_id;
      this.service
        .updateCartTransaction(a.cartId, resp.razorpay_payment_id)
        .subscribe({
          next: (res) => {
            console.log(res);
          },
          error: (err) => {
            console.log(err);
          },
          complete: () => {
            this.checkInCart();
          },
        });
    }
  }

  checkInCart() {
    window.location.href = '/cart';
  }

  onBackToHome(){
    window.location.href = '/home';
  }
}
