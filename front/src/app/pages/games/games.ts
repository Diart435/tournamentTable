import { Component, signal, OnInit } from '@angular/core';
import { Game } from './game';
import axios from 'axios';

@Component({
  selector: 'app-games',
  standalone: false,
  templateUrl: './games.html',
  styleUrl: './games.css',
})
export class Games implements OnInit {
  games = signal<Game[]>([]);
  loading = signal<boolean>(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading.set(true);
    this.error.set(null);

    axios.get<Game[]>('/public/get/games') 
      .then(response => {
        this.games.set(response.data);
        console.log('✅ Игры загружены:', this.games());
      })
      .catch(error => {
        console.error('❌ Ошибка загрузки игр:', error);
        this.error.set('Не удалось загрузить данные');
        this.games.set([]);
      })
      .finally(() => {
        this.loading.set(false);
      });
  }

  refresh(): void {
    this.loadData();
  }
}