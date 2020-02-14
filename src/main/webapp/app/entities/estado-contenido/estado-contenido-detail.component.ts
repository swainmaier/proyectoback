import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoContenido } from 'app/shared/model/estado-contenido.model';

@Component({
  selector: 'jhi-estado-contenido-detail',
  templateUrl: './estado-contenido-detail.component.html'
})
export class EstadoContenidoDetailComponent implements OnInit {
  estadoContenido: IEstadoContenido | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoContenido }) => (this.estadoContenido = estadoContenido));
  }

  previousState(): void {
    window.history.back();
  }
}
