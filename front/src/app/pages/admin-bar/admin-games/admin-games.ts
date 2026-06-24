import { Component, signal } from '@angular/core';
import { GameDTO } from '../../../dtos/GameDTO';
import { ApiService } from '../../../services/ApiService';

@Component({
    selector: 'app-admin-games',
    templateUrl: './admin-games.html',
    standalone: false,
    styleUrls: ['./admin-games.css']
})
export class AdminGames {
    newGame: GameDTO = {
        team1: '',
        team2: '',
        score1: 0,
        score2: 0,
        season: '',
        matchDate: ''
    };
    loading = signal(false);
    error = signal<string | null>(null);
    success = signal<string | null>(null);

    constructor(private apiService: ApiService) {}

    async createGame(): Promise<void> {
        if (!this.newGame.team1 || !this.newGame.team2) {
            this.error.set('Выберите обе команды');
            return;
        }

        this.loading.set(true);
        this.error.set(null);
        this.success.set(null);

        try {
            await this.apiService.post('/private/add/game', this.newGame);
            this.success.set('Матч успешно создан');
            this.newGame = {
                team1: '',
                team2: '',
                score1: 0,
                score2: 0,
                season: '',
                matchDate: ''
            };
        } catch (error) {
            console.error('Ошибка создания матча:', error);
            this.error.set('Ошибка создания матча');
        } finally {
            this.loading.set(false);
        }
    }
}