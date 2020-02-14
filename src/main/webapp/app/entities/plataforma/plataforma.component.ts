import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from './plataforma.service';
import { PlataformaDeleteDialogComponent } from './plataforma-delete-dialog.component';

@Component({
  selector: 'jhi-plataforma',
  templateUrl: './plataforma.component.html'
})
export class PlataformaComponent implements OnInit, OnDestroy {
  plataformas?: IPlataforma[];
  eventSubscriber?: Subscription;

  constructor(protected plataformaService: PlataformaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.plataformaService.query().subscribe((res: HttpResponse<IPlataforma[]>) => (this.plataformas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlataformas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlataforma): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlataformas(): void {
    this.eventSubscriber = this.eventManager.subscribe('plataformaListModification', () => this.loadAll());
  }

  delete(plataforma: IPlataforma): void {
    const modalRef = this.modalService.open(PlataformaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plataforma = plataforma;
  }
}
