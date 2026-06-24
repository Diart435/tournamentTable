import { Component, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TeamDetail } from './teamDetail';
import axios from 'axios';

@Component({
  selector: 'app-team',
  standalone: false,
  templateUrl: './team.html',
  styleUrl: './team.css',
})
export class Team {
  team = signal<TeamDetail | null>(null);
  loading = signal<boolean>(true);
  error = signal<string | null>(null);
  teamId: number | null = null;

  constructor(
    private route: ActivatedRoute,  
    private router: Router           
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.teamId = params['id']; 
      console.log('ID команды:', this.teamId);
      
      if (this.teamId) {
        this.loadTeamData(this.teamId);
      }
    });
  }

  loadTeamData(id: number): void {
    this.loading.set(true);
    this.error.set(null);

    axios.get<TeamDetail>(`/public/get/table/${id}`)
      .then(response => {
        this.team.set(response.data);
      })
      .catch(error => {
        this.team.set(null);
      })
      .finally(() => {
        this.loading.set(false);
      });
  }

  goBack(): void {
    this.router.navigate(['/teams']);
  }

  goToMatches(): void {
    if (this.teamId) {
      this.router.navigate(['/team', this.teamId, 'matches']);
    }
  }
}
