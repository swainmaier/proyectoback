import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoContenido } from 'app/shared/model/estado-contenido.model';
import { EstadoContenidoService } from './estado-contenido.service';

@Component({
  templateUrl: './estado-contenido-delete-dialog.component.html'
})
export class EstadoContenidoDeleteDialogComponent {
  estadoContenido?: IEstadoContenido;

  constructor(
    protected estadoContenidoService: EstadoContenidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoContenidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoContenidoListModification');
      this.activeModal.close();
    });
  }
}
