import { Component, OnInit } from '@angular/core';
import {CrudService} from './../service/crud.service'
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-component-list',
  templateUrl: './component-list.component.html',
  styleUrls: ['./component-list.component.css']
})
export class ComponentListComponent implements OnInit {

  PComponents:any = [];
  tipo:String;
  constructor(
    private crudService: CrudService,
    private route: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    //this.tipo = this.route.snapshot.params['componentId'];
    this.route.params.subscribe(params => {
      this.tipo =  params['componentId'];
      this.crudService.GetPcomponents(this.tipo).subscribe(res => {
        console.log(res);
        this.PComponents = res;
      });
    });
  }

}
