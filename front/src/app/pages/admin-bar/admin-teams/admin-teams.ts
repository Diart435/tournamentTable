import { Component, signal, OnInit } from '@angular/core';
import { TeamDTO } from '../../../dtos/TeamDTO';
import { ApiService } from '../../../services/ApiService';

@Component({
    selector: 'app-admin-teams',
    templateUrl: './admin-teams.html',
    standalone: false,
    styleUrls: ['./admin-teams.css']
})
export class AdminTeams implements OnInit {
    teams = signal<any[]>([]);
    loading = signal(false);
    newTeam = signal('');
    error = signal<string | null>(null);

    constructor(private apiService: ApiService) {}

    ngOnInit(): void {
        this.loadTeams();
    }

    async loadTeams(): Promise<void> {
        this.loading.set(true);
        try {
            const data = await this.apiService.get<any[]>('/public/get/table');
            this.teams.set(data);
        } catch (error) {
            console.error('Ошибка загрузки:', error);
        } finally {
            this.loading.set(false);
        }
    }

    async createTeam(): Promise<void> {
        const title = this.newTeam().trim();
        if (!title) return;

        const payload: TeamDTO = { title };

        this.loading.set(true);
        try {
            await this.apiService.post('/private/add/team', payload);
            this.newTeam.set('');
            await this.loadTeams();
        } catch (error) {
            console.error('Ошибка создания:', error);
        } finally {
            this.loading.set(false);
        }
    }

    async deleteTeam(title: string): Promise<void> {
        if (!confirm(`Удалить команду "${title}"?`)) return;

        const payload: TeamDTO = { title };

        this.loading.set(true);
        try {
            await this.apiService.delete('/private/delete/team', payload);
            await this.loadTeams();
        } catch (error) {
            console.error('Ошибка удаления:', error);
        } finally {
            this.loading.set(false);
        }
    }
}