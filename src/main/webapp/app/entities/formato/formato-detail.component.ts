import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormato } from 'app/shared/model/formato.model';

@Component({
  selector: 'jhi-formato-detail',
  templateUrl: './formato-detail.component.html'
})
export class FormatoDetailComponent implements OnInit {
  formato: IFormato | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formato }) => (this.formato = formato));
  }

  previousState(): void {
    window.history.back();
  }
}
