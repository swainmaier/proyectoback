import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeaser } from 'app/shared/model/teaser.model';
import { TeaserService } from './teaser.service';
import { TeaserDeleteDialogComponent } from './teaser-delete-dialog.component';

@Component({
  selector: 'jhi-teaser',
  templateUrl: './teaser.component.html'
})
export class TeaserComponent implements OnInit, OnDestroy {
  teasers?: ITeaser[];
  eventSubscriber?: Subscription;

  constructor(protected teaserService: TeaserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.teaserService.query().subscribe((res: HttpResponse<ITeaser[]>) => (this.teasers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTeasers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITeaser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTeasers(): void {
    this.eventSubscriber = this.eventManager.subscribe('teaserListModification', () => this.loadAll());
  }

  delete(teaser: ITeaser): void {
    const modalRef = this.modalService.open(TeaserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.teaser = teaser;
  }
}
