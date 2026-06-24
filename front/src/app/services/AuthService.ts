import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly STORAGE_KEY = 'admin_auth';

    isAuthenticated(): boolean {
        return !!sessionStorage.getItem(this.STORAGE_KEY);
    }

    async login(login: string, password: string): Promise<boolean> {
        try {
            const credentials = btoa(`${login}:${password}`);
            const response = await axios.get('/private/check',
                {
                    headers: { 
                        'Authorization': `Basic ${credentials}`,
                        'Content-Type': 'application/json'
                    }
                }
            );
            
            sessionStorage.setItem(this.STORAGE_KEY, credentials);
            
            return true;
            
        } catch (error: any) { 
            return false;
        }
    }

    logout(): void {
        sessionStorage.removeItem(this.STORAGE_KEY);
    }

    getAuthHeader(): { Authorization: string } | null {
        const credentials = sessionStorage.getItem(this.STORAGE_KEY);
        if (!credentials) return null;
        return { Authorization: `Basic ${credentials}` };
    }
}