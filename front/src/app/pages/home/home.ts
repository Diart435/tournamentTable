import { Component, OnInit, signal } from '@angular/core';
import axios from 'axios';

export interface Stats {
  title: string;
  teamScore: number;
  matches: number;
  wins: number;
  losses: number;
  draws: number;
}

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  table = signal<Stats[]>([]);

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    axios.get<Stats[]>('/public/get/table')
      .then(response => {
        this.table.set(response.data);
      })
      .catch(error => {
        this.table.set([]);
      });
  }
}