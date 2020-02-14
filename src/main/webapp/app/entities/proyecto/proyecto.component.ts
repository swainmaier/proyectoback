import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProyecto } from 'app/shared/model/proyecto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProyectoService } from './proyecto.service';
import { ProyectoDeleteDialogComponent } from './proyecto-delete-dialog.component';

@Component({
  selector: 'jhi-proyecto',
  templateUrl: './proyecto.component.html'
})
export class ProyectoComponent implements OnInit, OnDestroy {
  proyectos: IProyecto[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected proyectoService: ProyectoService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.proyectos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.proyectoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProyecto[]>) => this.paginateProyectos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.proyectos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProyectos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProyecto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInProyectos(): void {
    this.eventSubscriber = this.eventManager.subscribe('proyectoListModification', () => this.reset());
  }

  delete(proyecto: IProyecto): void {
    const modalRef = this.modalService.open(ProyectoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proyecto = proyecto;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProyectos(data: IProyecto[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.proyectos.push(data[i]);
      }
    }
  }
}
