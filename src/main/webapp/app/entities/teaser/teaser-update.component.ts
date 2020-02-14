import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITeaser, Teaser } from 'app/shared/model/teaser.model';
import { TeaserService } from './teaser.service';
import { IContenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from 'app/entities/contenido/contenido.service';

@Component({
  selector: 'jhi-teaser-update',
  templateUrl: './teaser-update.component.html'
})
export class TeaserUpdateComponent implements OnInit {
  isSaving = false;
  contenidos: IContenido[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    url: [],
    contenido: []
  });

  constructor(
    protected teaserService: TeaserService,
    protected contenidoService: ContenidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teaser }) => {
      this.updateForm(teaser);

      this.contenidoService.query().subscribe((res: HttpResponse<IContenido[]>) => (this.contenidos = res.body || []));
    });
  }

  updateForm(teaser: ITeaser): void {
    this.editForm.patchValue({
      id: teaser.id,
      titulo: teaser.titulo,
      url: teaser.url,
      contenido: teaser.contenido
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teaser = this.createFromForm();
    if (teaser.id !== undefined) {
      this.subscribeToSaveResponse(this.teaserService.update(teaser));
    } else {
      this.subscribeToSaveResponse(this.teaserService.create(teaser));
    }
  }

  private createFromForm(): ITeaser {
    return {
      ...new Teaser(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      url: this.editForm.get(['url'])!.value,
      contenido: this.editForm.get(['contenido'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeaser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IContenido): any {
    return item.id;
  }
}
