import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormato } from 'app/shared/model/formato.model';
import { FormatoService } from './formato.service';
import { FormatoDeleteDialogComponent } from './formato-delete-dialog.component';

@Component({
  selector: 'jhi-formato',
  templateUrl: './formato.component.html'
})
export class FormatoComponent implements OnInit, OnDestroy {
  formatoes?: IFormato[];
  eventSubscriber?: Subscription;

  constructor(protected formatoService: FormatoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.formatoService.query().subscribe((res: HttpResponse<IFormato[]>) => (this.formatoes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFormatoes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFormato): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFormatoes(): void {
    this.eventSubscriber = this.eventManager.subscribe('formatoListModification', () => this.loadAll());
  }

  delete(formato: IFormato): void {
    const modalRef = this.modalService.open(FormatoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.formato = formato;
  }
}
