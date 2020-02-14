import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeaser } from 'app/shared/model/teaser.model';

@Component({
  selector: 'jhi-teaser-detail',
  templateUrl: './teaser-detail.component.html'
})
export class TeaserDetailComponent implements OnInit {
  teaser: ITeaser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teaser }) => (this.teaser = teaser));
  }

  previousState(): void {
    window.history.back();
  }
}
