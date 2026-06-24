import { Component, signal, OnInit } from '@angular/core';
import { PlayerDTO } from '../../../dtos/PlayerDTO';
import { PlayerTeamDTO } from '../../../dtos/PlayerTeamDTO';
import { TransferDTO } from '../../../dtos/TransferDTO';
import { ApiService } from '../../../services/ApiService';

@Component({
    selector: 'app-admin-players',
    templateUrl: './admin-players.html',
    standalone: false,
    styleUrls: ['./admin-players.css']
})
export class AdminPlayers implements OnInit {
    players = signal<any[]>([]);
    teams = signal<any[]>([]);
    loading = signal(false);
    error = signal<string | null>(null);

    newPlayer = { name: '' };
    addToTeam = { name: '', title: '' };
    removeFromTeam = { name: '', title: '' };
    transfer = { name: '', team1: '', team2: '' };
    deletePlayer = { name: '', title: '' };

    constructor(private apiService: ApiService) {}

    ngOnInit(): void {
        this.loadData();
    }

    async loadData(): Promise<void> {
        this.loading.set(true);
        try {
            const [players, teams] = await Promise.all([
                this.apiService.get<any[]>('/public/get/players'),
                this.apiService.get<any[]>('/public/get/table')
            ]);
            this.players.set(players);
            this.teams.set(teams);
        } catch (error) {
            console.error('Ошибка загрузки:', error);
            this.error.set('Не удалось загрузить данные');
        } finally {
            this.loading.set(false);
        }
    }

    async createPlayer(): Promise<void> {
        if (!this.newPlayer.name.trim()) return;

        const payload: PlayerDTO = { name: this.newPlayer.name };

        this.loading.set(true);
        this.error.set(null);

        try {
            await this.apiService.post('/private/add/player', payload);
            this.newPlayer = { name: '' };
            await this.loadData();
        } catch (error: any) {
            console.error('Ошибка создания игрока:', error);
            if (error.response?.status === 401) {
                this.error.set('Сессия истекла, войдите заново');
            } else {
                this.error.set('Ошибка создания игрока');
            }
        } finally {
            this.loading.set(false);
        }
    }

    async addToTeamAction(): Promise<void> {
        if (!this.addToTeam.name || !this.addToTeam.title) return;

        const payload: PlayerTeamDTO = {
            name: this.addToTeam.name,
            title: this.addToTeam.title
        };

        this.loading.set(true);
        this.error.set(null);

        try {
            await this.apiService.post('/private/add/toteam', payload);
            this.addToTeam = { name: '', title: '' };
            await this.loadData();
        } catch (error) {
            console.error('Ошибка добавления в команду:', error);
            this.error.set('Ошибка добавления игрока в команду');
        } finally {
            this.loading.set(false);
        }
    }

    async removeFromTeamAction(): Promise<void> {
        if (!this.removeFromTeam.name || !this.removeFromTeam.title) return;

        const payload: PlayerTeamDTO = {
            name: this.removeFromTeam.name,
            title: this.removeFromTeam.title
        };

        this.loading.set(true);
        this.error.set(null);

        try {
            await this.apiService.patch('/private/delete/fromteam', payload);
            this.removeFromTeam = { name: '', title: '' };
            await this.loadData();
        } catch (error) {
            console.error('Ошибка удаления из команды:', error);
            this.error.set('Ошибка удаления игрока из команды');
        } finally {
            this.loading.set(false);
        }
    }

    async transferPlayer(): Promise<void> {
        if (!this.transfer.name || !this.transfer.team1 || !this.transfer.team2) return;

        const payload: TransferDTO = {
            name: this.transfer.name,
            team1: this.transfer.team1,
            team2: this.transfer.team2
        };

        this.loading.set(true);
        this.error.set(null);

        try {
            await this.apiService.patch('/private/update/transfer', payload);
            this.transfer = { name: '', team1: '', team2: '' };
            await this.loadData();
        } catch (error) {
            console.error('Ошибка трансфера:', error);
            this.error.set('Ошибка трансфера игрока');
        } finally {
            this.loading.set(false);
        }
    }

    async deletePlayerAction(): Promise<void> {
        if (!this.deletePlayer.name || !this.deletePlayer.title) return;
        if (!confirm(`Удалить игрока "${this.deletePlayer.name}"?`)) return;

        const payload: PlayerTeamDTO = {
            name: this.deletePlayer.name,
            title: this.deletePlayer.title
        };

        this.loading.set(true);
        this.error.set(null);

        try {
            await this.apiService.delete('/private/delete/player', payload);
            this.deletePlayer = { name: '', title: '' };
            await this.loadData();
        } catch (error) {
            console.error('Ошибка удаления игрока:', error);
            this.error.set('Ошибка удаления игрока');
        } finally {
            this.loading.set(false);
        }
    }
}