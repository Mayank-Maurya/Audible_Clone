import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import { LengthHelper } from './utils/LengthHelper';

@Injectable({
  providedIn: 'root'
})
export class AudiobookService {


  baseURL:string = "http://localhost:8081/audiobook/";

  constructor(private http:HttpClient,private ser:StorageService) { }

  getAudioBookSongUrlList(){
    
  }

  getAudioBookList(searchKeyWord:string,pageSize:number,pageNum:number,sortBy:string,isAsc:boolean,filterList:any):Observable<any>{
    return this.http.post<any>(`${this.baseURL}list/search/${searchKeyWord}/${pageSize}/${pageNum}/${sortBy}/${isAsc}`,filterList);
  }

  getAudioBookListSize():Observable<any>{
    return this.http.get(`${this.baseURL}list/size`);
  }

  getAudioBookSearchListSize(searchKeyWord:string):Observable<any>{
    return this.http.get(`${this.baseURL}list/search/size/${searchKeyWord}`);
  }

  getGenreFilterList():Observable<any>{
    return this.http.get(`${this.baseURL}filters/genre/list`);
  }

  getLengthFilterList():Observable<any>{
    return this.http.get<any>(`${this.baseURL}filters/length/list`);
  }

  getReleasesFilterList():Observable<any>{
    return this.http.get(`${this.baseURL}filters/releases/list`);
  }

  downloadFile(name:number): any {
		return this.http.get(`http://localhost:8081/audiobook/audio/${name}_main`, {responseType: 'blob'});
  }

  getCustomerId(username:any):Observable<any>{
    let myToken =this.ser.getUser().jwttoken;
    console.log(myToken) 
    return this.http.get<any>("http://localhost:8080/user/username/"+username,{headers: new HttpHeaders(
      {
        'Authorization': `Bearer ${myToken}`,
        'Content-Type': 'application/json'
      })});
  }



}
