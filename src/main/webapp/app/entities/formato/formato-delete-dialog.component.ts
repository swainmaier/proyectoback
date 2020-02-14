import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormato } from 'app/shared/model/formato.model';
import { FormatoService } from './formato.service';

@Component({
  templateUrl: './formato-delete-dialog.component.html'
})
export class FormatoDeleteDialogComponent {
  formato?: IFormato;

  constructor(protected formatoService: FormatoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formatoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('formatoListModification');
      this.activeModal.close();
    });
  }
}
