import { Component, OnInit } from '@angular/core';
import {Http, HttpModule} from '@angular/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  public animeList: Array<any>;

  constructor(public http: Http){

  }

  ngOnInit() {
    console.log('App Component ngOnInit()');
    this.http.get('/api/anime').subscribe( response => {
      this.animeList = response.json();

      console.log(this.animeList)
    });
  }


}
