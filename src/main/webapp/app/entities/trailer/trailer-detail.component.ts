import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrailer } from 'app/shared/model/trailer.model';

@Component({
  selector: 'jhi-trailer-detail',
  templateUrl: './trailer-detail.component.html'
})
export class TrailerDetailComponent implements OnInit {
  trailer: ITrailer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trailer }) => (this.trailer = trailer));
  }

  previousState(): void {
    window.history.back();
  }
}
