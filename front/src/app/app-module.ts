import { LOCALE_ID, NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Home } from './pages/home/home';
import { NotFound } from './pages/not-found/not-found';
import { Team } from './pages/team/team';
import { Admin } from './pages/admin-bar/admin/admin';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { Games } from './pages/games/games';
import { Teams } from './pages/teams/teams';
import { Players } from './pages/players/players';
import localeRu from '@angular/common/locales/ru';
import { registerLocaleData } from '@angular/common';
import { AdminTeams } from './pages/admin-bar/admin-teams/admin-teams';
import { AdminPlayers } from './pages/admin-bar/admin-players/admin-players';
import { AdminGames } from './pages/admin-bar/admin-games/admin-games';
import { AuthInterceptor } from './interseptors/AuthInterceptor';
import { AdminLogin } from './pages/admin-bar/admin-login/admin-login';
registerLocaleData(localeRu);

@NgModule({
  declarations: [
    App,
    Home,
    Team,
    NotFound,
    Admin,
    Games,
    Teams,
    Players,
    AdminTeams,
    AdminPlayers,
    AdminGames,
    AdminLogin
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    HttpClient,
    { provide: LOCALE_ID, useValue: 'ru' },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [App],
})
export class AppModule {}
