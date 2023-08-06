import { AfterViewInit, Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { AudiobookService } from '../audiobook.service';
import { CartService } from '../cart.service';
import { StorageService } from '../storage.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {
  baseUrl:string="http://localhost:8081/audiobook/audio/";
  customerId!:number;
	audioBookList:any=[];
  allCartData:any = [];
  allAudioBookData:any=[];
  allAudioBookDataOfCart:any = [];
  @Output() messageEvent = new EventEmitter<string>();

  @Output() setInitialSong = new EventEmitter<string>();

  @Output() stopMusicPlayback = new EventEmitter<string>();

  constructor(private ABService:AudiobookService,
    private storageService:StorageService,
    private cartService:CartService){}
  ngOnDestroy(): void {
    this.stopMusicPlaybackFn();
  }

  ngAfterViewInit(): void {

  }

  ngOnInit(): void {
    let userName = JSON.parse(this.storageService.getUser()).username;
    this.getCustomerByUserName(userName);
  }


  getCustomerByUserName(userName: any) {
    this.ABService.getCustomerId(userName).subscribe({
      next: (res) => {
        this.customerId = res.userId;
        console.log(this.customerId)
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
    this.cartService.getCartDetails(this.customerId, 'p').subscribe({
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
    this.cartService.getAllAudioBook().subscribe({
      next: (res) => {
        this.allAudioBookData = res;
        console.log(this.allAudioBookData)
      },
      error: (err) => {},
      complete:()=>{
        this.getAudioBookDataOfCart();
      }
    });
  }

  getAudioBookDataOfCart() {
    this.allAudioBookDataOfCart = [];
    console.log('inside get audio bookdata of cart');
    for (let i = 0; i < this.allCartData.length; i++) {
      for (let b of this.allAudioBookData) {
        if (
          this.allCartData[i].audioBookId == b.audioBookId &&
          this.allCartData[i].type == 'p'
        ) {
          this.allAudioBookDataOfCart.push(b);
          break;
        }
      }
    }
    console.log("Debug ======");
    console.log(this.allAudioBookDataOfCart);
    this.addIsSelectedFlag();
    
  }

  
  emitMessage(audioBookId:number){
    this.messageEvent.emit(audioBookId+"");
  }
  emitMessage1(){
    this.setInitialSong.emit(this.audioBookList[0].AB.audioBookId+"");
  }

  stopMusicPlaybackFn(){
    this.stopMusicPlayback.emit("Close");
  }



  addIsSelectedFlag(){
    for(let item of this.allAudioBookDataOfCart){
      this.audioBookList.push({AB:item,isSelected:false});
    }
    this.emitMessage1();
  }

  imgClicked(){
	console.log("img clicked");
  }

  onDownloadAB(){
    
  }

  onProductClicked(){

  }
  
  onDeleteAB(b:any){
    for(let i of this.allCartData){
      if(i.audioBookId==b){
        console.log("i am in")
        this.cartService.deleteAudioBook(i.cartId).subscribe({
          next:(res)=>{
            alert("Item Deleted");
            //Swal.fire("item deleted successfully");
          },
          error:(err)=>{
            console.log(err)
          },
          complete:()=>{
            window.location.href="/home";
          }
        })
        break;
      }
    }
  }

}
