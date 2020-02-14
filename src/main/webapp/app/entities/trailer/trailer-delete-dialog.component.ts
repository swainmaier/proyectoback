import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrailer } from 'app/shared/model/trailer.model';
import { TrailerService } from './trailer.service';

@Component({
  templateUrl: './trailer-delete-dialog.component.html'
})
export class TrailerDeleteDialogComponent {
  trailer?: ITrailer;

  constructor(protected trailerService: TrailerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trailerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trailerListModification');
      this.activeModal.close();
    });
  }
}
