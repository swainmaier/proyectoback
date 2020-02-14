import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrailer, Trailer } from 'app/shared/model/trailer.model';
import { TrailerService } from './trailer.service';
import { IContenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from 'app/entities/contenido/contenido.service';

@Component({
  selector: 'jhi-trailer-update',
  templateUrl: './trailer-update.component.html'
})
export class TrailerUpdateComponent implements OnInit {
  isSaving = false;
  contenidos: IContenido[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    url: [],
    contenido: []
  });

  constructor(
    protected trailerService: TrailerService,
    protected contenidoService: ContenidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trailer }) => {
      this.updateForm(trailer);

      this.contenidoService.query().subscribe((res: HttpResponse<IContenido[]>) => (this.contenidos = res.body || []));
    });
  }

  updateForm(trailer: ITrailer): void {
    this.editForm.patchValue({
      id: trailer.id,
      titulo: trailer.titulo,
      url: trailer.url,
      contenido: trailer.contenido
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trailer = this.createFromForm();
    if (trailer.id !== undefined) {
      this.subscribeToSaveResponse(this.trailerService.update(trailer));
    } else {
      this.subscribeToSaveResponse(this.trailerService.create(trailer));
    }
  }

  private createFromForm(): ITrailer {
    return {
      ...new Trailer(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      url: this.editForm.get(['url'])!.value,
      contenido: this.editForm.get(['contenido'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrailer>>): void {
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
