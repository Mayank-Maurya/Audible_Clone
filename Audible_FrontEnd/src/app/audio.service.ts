import { Injectable } from "@angular/core";
import { Observable, BehaviorSubject, Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
@Injectable({
  providedIn: 'root'
})
export class AudioService {

  constructor() { }

  songList:any=[{song_uri:"9_main",image_uri:"9"}];

  public songsListbyUserId(id:number){
    return this.songList;
  }

}
