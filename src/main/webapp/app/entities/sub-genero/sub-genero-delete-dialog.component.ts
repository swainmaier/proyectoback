import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubGenero } from 'app/shared/model/sub-genero.model';
import { SubGeneroService } from './sub-genero.service';

@Component({
  templateUrl: './sub-genero-delete-dialog.component.html'
})
export class SubGeneroDeleteDialogComponent {
  subGenero?: ISubGenero;

  constructor(protected subGeneroService: SubGeneroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subGeneroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subGeneroListModification');
      this.activeModal.close();
    });
  }
}
