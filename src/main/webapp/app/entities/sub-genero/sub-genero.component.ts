import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubGenero } from 'app/shared/model/sub-genero.model';
import { SubGeneroService } from './sub-genero.service';
import { SubGeneroDeleteDialogComponent } from './sub-genero-delete-dialog.component';

@Component({
  selector: 'jhi-sub-genero',
  templateUrl: './sub-genero.component.html'
})
export class SubGeneroComponent implements OnInit, OnDestroy {
  subGeneros?: ISubGenero[];
  eventSubscriber?: Subscription;

  constructor(protected subGeneroService: SubGeneroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.subGeneroService.query().subscribe((res: HttpResponse<ISubGenero[]>) => (this.subGeneros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubGeneros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubGenero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubGeneros(): void {
    this.eventSubscriber = this.eventManager.subscribe('subGeneroListModification', () => this.loadAll());
  }

  delete(subGenero: ISubGenero): void {
    const modalRef = this.modalService.open(SubGeneroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subGenero = subGenero;
  }
}
