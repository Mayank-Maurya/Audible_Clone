import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http:HttpClient,private ser:StorageService) { }


  deleteAudioBook(cartId:any){
    return this.http.delete("http://localhost:8000/cart/"+cartId,{responseType: 'text'})
  }
  
  addToCart(cartDetails:any){
    console.log(cartDetails)
    return this.http.post("http://localhost:8000/cart",cartDetails,{responseType:'text'});
  }

  getAllAudioBook():Observable<any>{
    return this.http.get<any>("http://localhost:8000/audiobook/");
  }

  getCartDetails(customerId:any,type:any):Observable<any>{

    return this.http.get<any>("http://localhost:8000/cart/"+customerId+"/"+type);

  }

  deleteCarts(id:any){
    return this.http.delete("http://localhost:8000/cart/"+id,{responseType: 'text'})
  }

  public createTransaction(amount: any) {
    return this.http.get("http://localhost:8000/createTransaction/"+amount);
  }

  updateCartTransaction(cartId:any,transId:any){
    return this.http.put("http://localhost:8000/cart/"+cartId+"/"+transId,null);
  }

  getCustomerId(username:any):Observable<any>{
    let myToken =this.ser.getUser().jwttoken;
    console.log(myToken) 
    return this.http.get<any>("http://localhost:8000/user/username/"+username,{headers: new HttpHeaders(
      {
        'Authorization': `Bearer ${myToken}`,
        'Content-Type': 'application/json'
      })});
  }

  

}


