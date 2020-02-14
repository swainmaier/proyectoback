import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeaser } from 'app/shared/model/teaser.model';
import { TeaserService } from './teaser.service';

@Component({
  templateUrl: './teaser-delete-dialog.component.html'
})
export class TeaserDeleteDialogComponent {
  teaser?: ITeaser;

  constructor(protected teaserService: TeaserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teaserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('teaserListModification');
      this.activeModal.close();
    });
  }
}
