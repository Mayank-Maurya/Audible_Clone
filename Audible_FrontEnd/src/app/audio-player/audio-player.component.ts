import { Component, OnInit } from '@angular/core';
import { AudioService } from '../audio.service';

@Component({
  selector: 'app-audio-player',
  templateUrl: './audio-player.component.html',
  styleUrls: ['./audio-player.component.css']
})
export class AudioPlayerComponent implements OnInit {

  files: any = [];
  currentFile: any = {};

  constructor(private audioService: AudioService) {
    this.files = this.audioService.songsListbyUserId(9);

  }
  ngOnInit(): void {
    
  }

  playStream(url:any) {
  }


  pause() {
  }

  play() {
  }

  stop() {
  }

  next() {

  }

  previous() {

  }

  isFirstPlaying() {
  }

  isLastPlaying() {
  }

  onSliderChangeEnd(change:any) {
    console.log(change);
  }

}
