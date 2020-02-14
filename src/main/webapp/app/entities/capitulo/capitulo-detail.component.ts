import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapitulo } from 'app/shared/model/capitulo.model';

@Component({
  selector: 'jhi-capitulo-detail',
  templateUrl: './capitulo-detail.component.html'
})
export class CapituloDetailComponent implements OnInit {
  capitulo: ICapitulo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ capitulo }) => (this.capitulo = capitulo));
  }

  previousState(): void {
    window.history.back();
  }
}
