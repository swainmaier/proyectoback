import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrailer } from 'app/shared/model/trailer.model';
import { TrailerService } from './trailer.service';
import { TrailerDeleteDialogComponent } from './trailer-delete-dialog.component';

@Component({
  selector: 'jhi-trailer',
  templateUrl: './trailer.component.html'
})
export class TrailerComponent implements OnInit, OnDestroy {
  trailers?: ITrailer[];
  eventSubscriber?: Subscription;

  constructor(protected trailerService: TrailerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.trailerService.query().subscribe((res: HttpResponse<ITrailer[]>) => (this.trailers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrailers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrailer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrailers(): void {
    this.eventSubscriber = this.eventManager.subscribe('trailerListModification', () => this.loadAll());
  }

  delete(trailer: ITrailer): void {
    const modalRef = this.modalService.open(TrailerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trailer = trailer;
  }
}
