import { AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormGroup, NumberValueAccessor } from '@angular/forms';
import { AudiobookService } from '../audiobook.service';
import { LengthHelper } from '../utils/LengthHelper';
import { LibraryComponent } from '../library/library.component';
import { AudioService } from '../audio.service';
import {StorageService} from '../storage.service'
import { CartService } from '../cart.service';
import { JwtHelper } from '../utils/JwtHelper';
import { JsonpClientBackend } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit,AfterViewInit,OnDestroy {
  
  //audio player Data
  url: string = "";
  volumeButton: number = -1;
  sizeOfSongs: number = 0;
  isFirstPlaying: boolean = true;
  isLastPlaying: boolean = true;
  isPlaying:boolean = false;
  isFirstTimePlay:boolean=true;
  curSongInd:number = 0;
  audioBookPurchased: any = [];
  

  @ViewChild(LibraryComponent)
  libraryChild:any;


  baseUrl:string="http://localhost:8081/audiobook/audio/";
  customerId!:number;
  userName!:any;
  isLibrary!:boolean;
	form!:FormGroup;
	searchTerm!:string;
  sortBy!:string;
  pageSize!:number;
  maxPageSize!:number;
  maxPages!:number;
  pageNum!:number;
  isAsc!:boolean;
  rating!:number;
	audioBookList:any=[];
  genreList:any=[];
  lenthList:any=[];
  releasesList:any=[];
  filterList:any=[];
  genreListToSend:any=[];
  lengthListToSend:any=[];
  releasesListToSend:any=[];
  
	checkList=[{name:'title'},{name:'author'},{name:'avgRating'},{name:'author'}]
  
  audio = new Audio();

  constructor(private ABService:AudiobookService,
    private audioSer:AudioService,
    private storageService:StorageService,
    private cartService:CartService,
    private router:Router){}
  
    ngOnDestroy(): void {
      this.audio.load();
      this.audio.src="";
      this.isPlaying=false;
      this.isFirstPlaying=false;
      this.isLastPlaying=false;


  }
  ngAfterViewInit(): void {
    console.log("debug")
    this.handler();
  }

  ngOnInit(): void {
    this.setFilterValues();
    this.ABService.getAudioBookListSize().subscribe({
      next:(res)=>{
        this.maxPageSize = res;
      },
      error:(msg)=>{
        console.log(msg);
      },
      complete:()=>{
        this.setInitialValues();
        this.search();
        this.userName = this.storageService.getUser().username;
        this.getCustomerByUserName(this.userName);
      }
    })
    
  }

  getCustomerByUserName(userName: any) {
    this.cartService.getCustomerId(userName).subscribe({
      next: (res) => {
        this.customerId = res.userId;
      },
      error: (err) => {
        console.log(err);
      },
      complete: () => {
      },
    });
  }

  recievedMsg($event:any){
    this.isFirstTimePlay = false;
    console.log("happened");
    console.log($event);
    this.sizeOfSongs = this.libraryChild.audioBookList.length;
    console.log( this.libraryChild.audioBookList);
    this.play($event);
  }

  setInitialSong($event:any){
    this.sizeOfSongs = this.libraryChild.audioBookList.length;
    this.audio.src = `${this.baseUrl}${$event}_main`;
    this.audio.load();
    this.setNextorPreviousbtn();
    console.log( this.libraryChild.audioBookList);
  }

  stopMusicPlayback($event:any){
    this.audio.src="";
    this.audio.load();
    this.isPlaying=false;
    this.isFirstPlaying=false;
    this.isLastPlaying=false;
    this.curSongInd=0;
    this.sizeOfSongs=0;
    this.setNextorPreviousbtn();
  }


  setInitialValues(){
    this.isLibrary = false;
    this.searchTerm = "";
    this.sortBy = "avgRating";
    this.pageNum = 1;
    this.pageSize = 5;
    this.maxPages = Math.ceil(this.maxPageSize / this.pageSize);
    this.isAsc = true;
    this.rating = 5;
    this.audio.src="";
  }

  setFilterValues(){
    this.ABService.getGenreFilterList().subscribe({
      next:(res)=>{
        this.addIsSelectedFlag(this.genreList,res);
      },
      error:(msg)=>{
        console.log(msg);
      }
    })

    this.ABService.getLengthFilterList().subscribe({
      next:(res)=>{
        this.addIsSelectedFlag(this.lenthList,res);
      },
      error:(msg)=>{
        console.log(msg);
      }
    })

    this.ABService.getReleasesFilterList().subscribe({
      next:(res)=>{
        this.addIsSelectedFlag(this.releasesList,res);
      },
      error:(msg)=>{
        console.log(msg);
      }
    })

  }

  addIsSelectedFlag(originalList:any[],res:any){
    for(let item of res){
      originalList.push({data:item,isSelected:false});
    }
  }

  onCartClicked(){
    this.router.navigate(['/cart']);
  }
  onExplore(){
    console.log("explore clicked")
    this.isLibrary=false;
  }

  onLibrary(){
    console.log("Library clicked")
    this.isLibrary=true;
    this.audio.load();
    this.audio.src="";
    this.isPlaying=false;
    this.isFirstPlaying=false;
    this.isLastPlaying=false;
  }

  imgClicked(){
	console.log("img clicked");
  }

  search(){
    this.audioBookList=[];
    this.prepareDataForSearch();
    console.log(this.filterList);
    if(this.searchTerm==""){
      this.makeSearchRequest("All");
    }else{
      this.makeSearchRequest(this.searchTerm);
    }
    
  }

  prepareDataForSearch(){
    
    this.genreListToSend=[];
    this.lengthListToSend=[];
    this.releasesListToSend=[];

    for(let genre of this.genreList){
      if(genre.isSelected == true){
        this.genreListToSend.push(genre);
      }
    }

    for(let len of this.lenthList){
      if(len.isSelected == true){
        this.lengthListToSend.push(len);
      }
    }

    for(let release of this.releasesList){
      if(release.isSelected == true){
        this.releasesListToSend.push(release);
      }
    }


    this.filterList = {
      genreList:this.genreListToSend,
      lengthList:this.lengthListToSend,
      releasesList:this.releasesListToSend,
      rating:this.rating
    }
  }

  makeSearchRequest(term:string){
    this.ABService.getAudioBookList(term,this.pageSize,this.pageNum,this.sortBy,this.isAsc,this.filterList).subscribe({
      next:(res)=>{
        for(let AB of res){
          this.audioBookList.push({AB:AB,isPlaying:false});
        }
      },
      error:(msg)=>{
        console.log(msg);
      },
      complete:()=>{
        console.log(this.audioBookList);
        this.makeSearchListSizeRequest(term);
      }
    })
  }

  makeSearchListSizeRequest(term:string){
    this.ABService.getAudioBookSearchListSize(term).subscribe({
      next:(res)=>{
        this.maxPageSize = res;
      },
      error:(msg)=>{
        console.log(msg);
      },
      complete:()=>{
        this.maxPages = Math.ceil(this.maxPageSize / this.pageSize);
      }
    })
  }


  onProductClicked(){

  }

  onAddToCart(audioBookId:number){
    const cartDetails = {
      audioBookId:audioBookId,
      customerId:this.customerId,
      type:"w",
      transactionId:null
    }

    this.cartService.addToCart(cartDetails).subscribe({
      next:(res)=>{
        console.log(res);
      },
      error:(msg)=>{

      },
      complete:()=>{
        alert("Item Added To Cart");
      }
    })
  }
  
  onAddToWL(){

  }

  onSamplePlay(audioBookId:number){
    this.audio.src = `${this.baseUrl}${audioBookId}_sample`;
    for(let a of this.audioBookList)
    {
      if(a.AB.audioBookId!=audioBookId)
      {
        a.isSelected=false;
      }
    }
    this.audio.load();
    for(let a of this.audioBookList)
    {
      if(a.AB.audioBookId==audioBookId)
      {
        if(!a.isSelected)
        {
          this.audio.play();
          a.isSelected = true;
        }
        else{
          this.audio.pause();
          a.isSelected = false;
        }
        break;
      }
    }  
  }

  onPageNumChange(){
    console.log("Debug page num : " + this.pageNum + " max pages : " + this.maxPages);
    if(this.pageNum>this.maxPages){
      this.pageNum=this.maxPages;
    }
    if(this.pageNum<=0){
      this.pageNum=1;
    }
    this.search();
  }
  onPageSizeChange(){
    if(this.pageSize>this.maxPageSize)
    {
      this.pageSize=this.maxPageSize;
    }
    if(this.pageSize<=0){
      this.pageSize=1;
    }
    this.search();
  }

  onOrderChange(){
    this.search();
  }

  onSortyByChange(){
    this.search();
  }

  onFilterSelected(){
    this.search();
  }

  
  play(audioBookId:number) {
    console.log(this.audio.src)
    console.log(`${this.baseUrl}${audioBookId}_main`)
    if(this.audio.src === `${this.baseUrl}${audioBookId}_main`){
      this.audio.load();
      if (this.isPlaying == false) {
        if (this.libraryChild.audioBookList[this.curSongInd].isSelected == false) {
          this.libraryChild.audioBookList[this.curSongInd].isSelected = true;
          this.isPlaying = true;
          this.audio.play();
        } else {
          this.libraryChild.audioBookList[this.curSongInd].isSelected = false;
        }
      } else {
        this.libraryChild.audioBookList[this.curSongInd].isSelected = false;
        this.isPlaying = false;
      }
      this.setNextorPreviousbtn();
      return;
    }
    this.audio.src = `${this.baseUrl}${audioBookId}_main`;
    this.audio.load();
    this.curSongInd = -1;
    for(let a of this.libraryChild.audioBookList){
      this.curSongInd++;
      if(a.AB.audioBookId==audioBookId){
        break;
      }
    }
    console.log("Song Ind: " +this.curSongInd);
    for(let a of this.libraryChild.audioBookList)
    {
      if(a.AB.audioBookId!=audioBookId)
      {
        a.isSelected=false;
      }else{
        a.isSelected=true;
      }
    }
    this.isPlaying=false;
    this.setNextorPreviousbtn();
    this.pause();
  }

  pause() {

    // if(this.isFirstTimePlay){
    //   this.play(this.libraryChild.audioBookList[this.curSongInd].AB.audioBookId);
    //   this.isFirstTimePlay=false;
    //   return;
    // }

    if(this.isPlaying==true)
    {
      this.audio.pause();
      this.isPlaying=false;
    }else{
      this.audio.play();
      this.libraryChild.audioBookList[this.curSongInd].isSelected = true;
      this.isPlaying=true;
    }
    this.setNextorPreviousbtn();    
  }

 
  previous() {
    this.libraryChild.audioBookList[this.curSongInd].isSelected = false;
    this.curSongInd--;
    this.libraryChild.audioBookList[this.curSongInd].isSelected = true;
    this.setNextorPreviousbtn();
    this.audio.src = `${this.baseUrl}${this.libraryChild.audioBookList[this.curSongInd].AB.audioBookId}_main`;
    this.audio.load();
    this.audio.play();
    this.isPlaying = true;
  }

  
  next() {
    this.libraryChild.audioBookList[this.curSongInd].isSelected = false;
    this.curSongInd++;
    this.libraryChild.audioBookList[this.curSongInd].isSelected = true;
    this.setNextorPreviousbtn();
    this.audio.src = `${this.baseUrl}${this.libraryChild.audioBookList[this.curSongInd].AB.audioBookId}_main`;
    this.audio.load();
    this.audio.play();
    this.isPlaying = true;
  }


  setNextorPreviousbtn(){
    this.isLastPlaying = false;
    this.isFirstPlaying = false;
    if(this.curSongInd==0){
      this.isFirstPlaying=true;
    }
    if(this.curSongInd==this.sizeOfSongs-1){
      this.isLastPlaying=true;
    }
  }

 


  // ***********************************************************************************************

  currentTime: any = "0:0:0";
  duration: any = "0:0:0";
  duration2: any = 0;
  seek: any = 0;


  audioEvents = [
    "ended",
    "error",
    "playing",
    "play",
    "pause",
    "timeupdate",
    "canplay",
    "loadstart",
    "loadedmetadata"
  ]


  handler() {
    const handler = (event: Event) => {
      this.seek = this.audio.currentTime;
      this.currentTime = this.timeFormat(this.audio.currentTime);
      this.duration = this.timeFormat(this.audio.duration);
      this.duration2 = Math.round(this.audio.duration);
    }

    this.addEvent(this.audio, this.audioEvents, handler);

    return () => {
      this.audio.pause();
      this.audio.currentTime = 0;
      this.removeEvent(this.audio, this.audioEvents, handler);
    }
  }

  setSeekTo(seek: any) {
    this.audio.currentTime = seek.target.value
  }
  timeFormat(seconds: any) {
    const h = Math.floor(seconds / 3600);
    const m = Math.floor((seconds % 3600) / 60);
    const s = Math.round(seconds % 60);
    return h + ":" + m + ":" + s
  }
  setVolume(ev: any) {
    this.audio.volume = ev.target.value;
  }

  addEvent(obj: any, events: any, handler: any) {
    events.forEach((event: any) => {
      obj.addEventListener(event, handler);
    });
  }

  removeEvent(obj: any, events: any, handler: any) {
    events.forEach((event: any) => {
      obj.removeEventListener(event, handler);
    });
  }

  LogOut(){
    this.storageService.clean();
    window.location.href="/home";
  }



}
