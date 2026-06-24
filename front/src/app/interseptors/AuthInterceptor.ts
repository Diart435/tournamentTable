import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService';
import { catchError, throwError } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        const authHeader = this.authService.getAuthHeader();

        if (authHeader) {
            const authReq = req.clone({
                setHeaders: authHeader
            });

            return next.handle(authReq).pipe(
                catchError((error: HttpErrorResponse) => {
                    if (error.status === 401) {
                        this.authService.logout();
                        this.router.navigate(['/admin-tool234/login']);
                    }
                    return throwError(() => error);
                })
            );
        }

        return next.handle(req);
    }
}