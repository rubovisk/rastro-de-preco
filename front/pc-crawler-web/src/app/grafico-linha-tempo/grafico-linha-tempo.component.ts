import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CrudService } from './../service/crud.service'

@Component({
  selector: 'app-grafico-linha-tempo',
  templateUrl: './grafico-linha-tempo.component.html',
  styleUrls: ['./grafico-linha-tempo.component.css']
})
export class GraficoLinhaTempoComponent implements OnInit {
  data: any;
  priceItems: any = [];
  tipo: String;
  options: any;
  constructor(private crudService: CrudService, private route: ActivatedRoute, private router: Router) {
    this.options = {
      legend: {display: true, position: 'right', align: 'start', padding: 0},
      tooltip: {enabled: false}
    }
   }

  ngOnInit(): void {
    this.configureAll();
  }

  configureAll() {
    this.crudService.GetAllDates().subscribe(dias => {
      this.route.params.subscribe(params => {
        this.tipo = params['componentId'];
        this.crudService.GetPriceHistory(this.tipo).subscribe(historico => {
          this.data = {
            labels: dias,
            datasets: historico
          }
        });
      })
    });
  }
}

/*sampleData = [
    //processador 1
    {
      label: 'Core i3-10100',
      data: [979.90, 899.00, 899.00],
      backgroundColor: "#1357EF"
    },
    //processador 2
    {
      label: 'Core i5-10400f',
      data: [1099.0, 1099.0, 1099.0],
      backgroundColor: "#326CEE"
    },
    //processador 3
    {
      label: 'Ryzen 5 3600',
      data: [1599.0, 1599.0, 1589.0],
      backgroundColor: "#020B1F"
    },
    //processador 4
    {
      label: 'Ryzen 5 5600X',
      data: [2359.0, 2379.9, 2399.9],
      backgroundColor: "#5C7BC0"
    }
  ];*/
