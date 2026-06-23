import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { TeamCard } from './teamCard';
import axios from 'axios';

@Component({
  selector: 'app-teams',
  standalone: false,
  templateUrl: './teams.html',
  styleUrl: './teams.css',
})
export class Teams {
  constructor(private router: Router) {}
  teamCards = signal<TeamCard[]>([]);

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    axios.get<TeamCard[]>('/public/get/table')
      .then(response => {
        this.teamCards.set(response.data);
      })
      .catch(error => {
        this.teamCards.set([]);
      });
  }
  goToTeam(id: number): void {
    this.router.navigate(['/team', id]);
  }
}
