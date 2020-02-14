import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoProyecto } from 'app/shared/model/estado-proyecto.model';

@Component({
  selector: 'jhi-estado-proyecto-detail',
  templateUrl: './estado-proyecto-detail.component.html'
})
export class EstadoProyectoDetailComponent implements OnInit {
  estadoProyecto: IEstadoProyecto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoProyecto }) => (this.estadoProyecto = estadoProyecto));
  }

  previousState(): void {
    window.history.back();
  }
}
