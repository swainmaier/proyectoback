import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoProyecto } from 'app/shared/model/estado-proyecto.model';
import { EstadoProyectoService } from './estado-proyecto.service';

@Component({
  templateUrl: './estado-proyecto-delete-dialog.component.html'
})
export class EstadoProyectoDeleteDialogComponent {
  estadoProyecto?: IEstadoProyecto;

  constructor(
    protected estadoProyectoService: EstadoProyectoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoProyectoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoProyectoListModification');
      this.activeModal.close();
    });
  }
}
