import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarget } from 'app/shared/model/target.model';
import { TargetService } from './target.service';

@Component({
  templateUrl: './target-delete-dialog.component.html'
})
export class TargetDeleteDialogComponent {
  target?: ITarget;

  constructor(protected targetService: TargetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.targetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('targetListModification');
      this.activeModal.close();
    });
  }
}
