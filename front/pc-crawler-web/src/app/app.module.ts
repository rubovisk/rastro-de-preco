import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComponentListComponent } from './component-list/component-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { GraficoLinhaTempoComponent } from './grafico-linha-tempo/grafico-linha-tempo.component';
import {ChartModule} from 'primeng/chart';

@NgModule({
  declarations: [
    AppComponent,
    ComponentListComponent,
    GraficoLinhaTempoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    ChartModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
