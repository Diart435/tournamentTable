import { Component, signal } from '@angular/core';
import { Player } from './player';
import axios from 'axios';

@Component({
  selector: 'app-players',
  standalone: false,
  templateUrl: './players.html',
  styleUrl: './players.css',
})
export class Players {
  players = signal<Player[]>([]);

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    axios.get<Player[]>('/public/get/players')
      .then(response => {
        this.players.set(response.data);
      })
      .catch(error => {
        this.players.set([]);
      });
  }
}
