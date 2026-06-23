import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { NotFound } from './pages/not-found/not-found';
import { Team } from './pages/team/team';
import { Games } from './pages/games/games';
import { Teams } from './pages/teams/teams';
import { Players } from './pages/players/players';
import { Admin } from './pages/admin/admin';

const routes: Routes = [
  {path:"", redirectTo:"/home", pathMatch:'full'},
  {path:"home", component: Home},
  {path:"teams", component:Teams},
  {path:"games", component: Games},
  {path:"team/:id", component: Team},
  {path:"players",component: Players},
  {path:"admin-tool234", component:Admin},
  {path:"**", component: NotFound}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
