import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoContenido } from 'app/shared/model/estado-contenido.model';
import { EstadoContenidoService } from './estado-contenido.service';
import { EstadoContenidoDeleteDialogComponent } from './estado-contenido-delete-dialog.component';

@Component({
  selector: 'jhi-estado-contenido',
  templateUrl: './estado-contenido.component.html'
})
export class EstadoContenidoComponent implements OnInit, OnDestroy {
  estadoContenidos?: IEstadoContenido[];
  eventSubscriber?: Subscription;

  constructor(
    protected estadoContenidoService: EstadoContenidoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.estadoContenidoService.query().subscribe((res: HttpResponse<IEstadoContenido[]>) => (this.estadoContenidos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEstadoContenidos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEstadoContenido): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEstadoContenidos(): void {
    this.eventSubscriber = this.eventManager.subscribe('estadoContenidoListModification', () => this.loadAll());
  }

  delete(estadoContenido: IEstadoContenido): void {
    const modalRef = this.modalService.open(EstadoContenidoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estadoContenido = estadoContenido;
  }
}
