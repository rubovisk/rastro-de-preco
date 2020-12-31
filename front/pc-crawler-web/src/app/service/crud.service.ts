import { Injectable } from '@angular/core';
import {PComponent} from './PComponent';
import { catchError, map } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CrudService {
  GET_COMPONENT_API: string = "http://localhost:8051/web-crawler/component/";
  GET_ALLDATES_API: string = "http://localhost:8051/web-crawler/find/alldates";
  GET_CHART_LINE_API: string = "http://localhost:8051/web-crawler/find/prices/";

  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private httpClient: HttpClient) { }

  GetPcomponents(tipo){
    return this.httpClient.get(this.GET_COMPONENT_API + tipo);
  }

  GetAllDates(){
    return this.httpClient.get(this.GET_ALLDATES_API);
  }

  GetPriceHistory(tipo){
    return this.httpClient.get(this.GET_CHART_LINE_API + tipo);
  }

   // Error 
   handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Handle client error
      errorMessage = error.error.message;
    } else {
      // Handle server error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
