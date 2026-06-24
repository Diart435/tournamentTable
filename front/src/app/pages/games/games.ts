import { Component, signal, OnInit, ViewChild } from '@angular/core';
import { Game } from './game';
import axios from 'axios';
import { MatDatepicker } from '@angular/material/datepicker';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';

export const MY_DATE_FORMATS = {
    parse: {
        dateInput: 'DD-MM-YYYY',
    },
    display: {
        dateInput: 'DD-MM-YYYY',
        monthYearLabel: 'MMMM YYYY',
        dateA11yLabel: 'LL',
        monthYearA11yLabel: 'MMMM YYYY',
    },
};

@Component({
    selector: 'app-games',
    standalone: false,
    templateUrl: './games.html',
    styleUrl: './games.css',
    providers: [
        { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS },
    ],
})
export class Games implements OnInit {
    @ViewChild('picker') picker!: MatDatepicker<Date>;

    games = signal<Game[]>([]);
    loading = signal<boolean>(true);
    error = signal<string | null>(null);
    
    selectedDate: Date | null = null;
    dateFilterActive = signal<boolean>(false);
    filterDateString = signal<string>('');

    constructor(private dateAdapter: DateAdapter<Date>) {
        this.dateAdapter.setLocale('ru');
    }

    ngOnInit(): void {
        this.loadAllGames();
    }

    loadAllGames(): void {
        this.loading.set(true);
        this.error.set(null);
        this.dateFilterActive.set(false);
        this.filterDateString.set('');

        axios.get<Game[]>('/public/get/games')
            .then(response => {
                this.games.set(response.data);
            })
            .catch(() => {
                this.error.set('Не удалось загрузить данные');
                this.games.set([]);
            })
            .finally(() => {
                this.loading.set(false);
            });
    }

    onDateSelected(date: Date | null): void {
        if (!date) {
            this.loadAllGames();
            return;
        }

        this.selectedDate = date;
        this.dateFilterActive.set(true);
        this.loading.set(true);
        this.error.set(null);

        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        const dateParam = `${day}-${month}-${year}`;
        this.filterDateString.set(dateParam);

        axios.get<Game[]>(`/public/get/games/${dateParam}`)
            .then(response => {
                this.games.set(response.data);
                if (this.games().length === 0) {
                    this.error.set(`На ${dateParam} матчей нет`);
                }
            })
            .catch(() => {
                this.error.set(`Не удалось загрузить игры за ${dateParam}`);
                this.games.set([]);
            })
            .finally(() => {
                this.loading.set(false);
            });
    }

    resetFilter(): void {
        this.selectedDate = null;
        this.dateFilterActive.set(false);
        this.filterDateString.set('');
        this.loadAllGames();
    }

    refresh(): void {
        if (this.dateFilterActive() && this.selectedDate) {
            this.onDateSelected(this.selectedDate);
        } else {
            this.loadAllGames();
        }
    }

    formatDisplayDate(dateString: string): string {
        if (!dateString) return '';
        return dateString.replace(/-/g, '.');
    }
}