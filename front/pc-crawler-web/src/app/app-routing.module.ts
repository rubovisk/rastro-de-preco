import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {ComponentListComponent} from './component-list/component-list.component'
import {GraficoLinhaTempoComponent} from './grafico-linha-tempo/grafico-linha-tempo.component'

const routes: Routes = [
  {path:'', pathMatch:'full', redirectTo:'add-component'},
  //{path:'component-list', component: ComponentListComponent},
  {
    path:'component-list/find/:componentId', component: ComponentListComponent
  },
  {
    path:'timeline/price/:componentId', component: GraficoLinhaTempoComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
