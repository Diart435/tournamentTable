import { Injectable } from '@angular/core';
import axios from 'axios';
import { AuthService } from './AuthService';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    constructor(private authService: AuthService) {}

    private getHeaders() {
        const authHeader = this.authService.getAuthHeader();
        return authHeader || {};
    }

    async get<T>(url: string): Promise<T> {
        const response = await axios.get<T>(url, {
            headers: this.getHeaders()
        });
        return response.data;
    }

    async post<T>(url: string, data: any): Promise<T> {
        const response = await axios.post<T>(url, data, {
            headers: {
                ...this.getHeaders(),
                'Content-Type': 'application/json'
            }
        });
        return response.data;
    }

    async patch<T>(url: string, data: any): Promise<T> {
        const response = await axios.patch<T>(url, data, {
            headers: {
                ...this.getHeaders(),
                'Content-Type': 'application/json'
            }
        });
        return response.data;
    }

    async delete<T>(url: string, data?: any): Promise<T> {
        const response = await axios.delete<T>(url, {
            headers: {
                ...this.getHeaders(),
                'Content-Type': 'application/json'
            },
            data: data
        });
        return response.data;
    }
}