import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubGenero } from 'app/shared/model/sub-genero.model';

@Component({
  selector: 'jhi-sub-genero-detail',
  templateUrl: './sub-genero-detail.component.html'
})
export class SubGeneroDetailComponent implements OnInit {
  subGenero: ISubGenero | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subGenero }) => (this.subGenero = subGenero));
  }

  previousState(): void {
    window.history.back();
  }
}
