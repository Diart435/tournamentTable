import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Home } from './pages/home/home';
import { NotFound } from './pages/not-found/not-found';
import { Team } from './pages/team/team';
import { Admin } from './pages/admin/admin';
import { HttpClient } from '@angular/common/http';
import { Games } from './pages/games/games';
import { Teams } from './pages/teams/teams';
import { Players } from './pages/players/players';

@NgModule({
  declarations: [App, Home, Team, NotFound, Admin, Games, Teams, Players],
  imports: [BrowserModule, AppRoutingModule, ReactiveFormsModule, FormsModule],
  providers: [provideBrowserGlobalErrorListeners(), HttpClient],
  bootstrap: [App],
})
export class AppModule {}
