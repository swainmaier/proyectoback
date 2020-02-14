import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarget } from 'app/shared/model/target.model';
import { TargetService } from './target.service';
import { TargetDeleteDialogComponent } from './target-delete-dialog.component';

@Component({
  selector: 'jhi-target',
  templateUrl: './target.component.html'
})
export class TargetComponent implements OnInit, OnDestroy {
  targets?: ITarget[];
  eventSubscriber?: Subscription;

  constructor(protected targetService: TargetService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.targetService.query().subscribe((res: HttpResponse<ITarget[]>) => (this.targets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTargets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarget): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTargets(): void {
    this.eventSubscriber = this.eventManager.subscribe('targetListModification', () => this.loadAll());
  }

  delete(target: ITarget): void {
    const modalRef = this.modalService.open(TargetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.target = target;
  }
}
