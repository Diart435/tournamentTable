import { Component, signal } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('front');
  ngOnInit() {
    axios.defaults.baseURL = 'http://localhost:8080/api';
    axios.defaults.headers.common['Content-Type'] = 'application/json';
  }
}
