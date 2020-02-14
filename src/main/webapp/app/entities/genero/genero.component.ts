import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGenero } from 'app/shared/model/genero.model';
import { GeneroService } from './genero.service';
import { GeneroDeleteDialogComponent } from './genero-delete-dialog.component';

@Component({
  selector: 'jhi-genero',
  templateUrl: './genero.component.html'
})
export class GeneroComponent implements OnInit, OnDestroy {
  generos?: IGenero[];
  eventSubscriber?: Subscription;

  constructor(protected generoService: GeneroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.generoService.query().subscribe((res: HttpResponse<IGenero[]>) => (this.generos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGeneros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGenero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGeneros(): void {
    this.eventSubscriber = this.eventManager.subscribe('generoListModification', () => this.loadAll());
  }

  delete(genero: IGenero): void {
    const modalRef = this.modalService.open(GeneroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.genero = genero;
  }
}
