import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoProyecto } from 'app/shared/model/estado-proyecto.model';
import { EstadoProyectoService } from './estado-proyecto.service';
import { EstadoProyectoDeleteDialogComponent } from './estado-proyecto-delete-dialog.component';

@Component({
  selector: 'jhi-estado-proyecto',
  templateUrl: './estado-proyecto.component.html'
})
export class EstadoProyectoComponent implements OnInit, OnDestroy {
  estadoProyectos?: IEstadoProyecto[];
  eventSubscriber?: Subscription;

  constructor(
    protected estadoProyectoService: EstadoProyectoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.estadoProyectoService.query().subscribe((res: HttpResponse<IEstadoProyecto[]>) => (this.estadoProyectos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEstadoProyectos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEstadoProyecto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEstadoProyectos(): void {
    this.eventSubscriber = this.eventManager.subscribe('estadoProyectoListModification', () => this.loadAll());
  }

  delete(estadoProyecto: IEstadoProyecto): void {
    const modalRef = this.modalService.open(EstadoProyectoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estadoProyecto = estadoProyecto;
  }
}
