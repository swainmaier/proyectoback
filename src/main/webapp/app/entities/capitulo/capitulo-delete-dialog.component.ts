import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICapitulo } from 'app/shared/model/capitulo.model';
import { CapituloService } from './capitulo.service';

@Component({
  templateUrl: './capitulo-delete-dialog.component.html'
})
export class CapituloDeleteDialogComponent {
  capitulo?: ICapitulo;

  constructor(protected capituloService: CapituloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.capituloService.delete(id).subscribe(() => {
      this.eventManager.broadcast('capituloListModification');
      this.activeModal.close();
    });
  }
}
