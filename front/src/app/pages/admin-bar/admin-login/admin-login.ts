import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/AuthService';

@Component({
    selector: 'app-admin-login',
    standalone: false,
    templateUrl: './admin-login.html',
    styleUrl: './admin-login.css',
})
export class AdminLogin {
    login: string = '';
    password: string = '';
    error: string | null = null;
    loading: boolean = false;

    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    async onSubmit(): Promise<void> {

        if (!this.login || !this.password) {
            this.error = 'Введите логин и пароль';
            return;
        }

        this.loading = true;
        this.error = null;

        const success = await this.authService.login(this.login, this.password);

        if (success) {
            this.router.navigate(['/admin-tool234']);
        } else {
            this.error = 'Неверный логин или пароль';
        }

        this.loading = false;
    }
}