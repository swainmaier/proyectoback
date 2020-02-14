import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITemporada, Temporada } from 'app/shared/model/temporada.model';
import { TemporadaService } from './temporada.service';
import { IContenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from 'app/entities/contenido/contenido.service';

@Component({
  selector: 'jhi-temporada-update',
  templateUrl: './temporada-update.component.html'
})
export class TemporadaUpdateComponent implements OnInit {
  isSaving = false;
  contenidos: IContenido[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    numero: [],
    cantCapitulos: [],
    duracionTotal: [],
    contenido: []
  });

  constructor(
    protected temporadaService: TemporadaService,
    protected contenidoService: ContenidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ temporada }) => {
      this.updateForm(temporada);

      this.contenidoService.query().subscribe((res: HttpResponse<IContenido[]>) => (this.contenidos = res.body || []));
    });
  }

  updateForm(temporada: ITemporada): void {
    this.editForm.patchValue({
      id: temporada.id,
      titulo: temporada.titulo,
      numero: temporada.numero,
      cantCapitulos: temporada.cantCapitulos,
      duracionTotal: temporada.duracionTotal,
      contenido: temporada.contenido
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const temporada = this.createFromForm();
    if (temporada.id !== undefined) {
      this.subscribeToSaveResponse(this.temporadaService.update(temporada));
    } else {
      this.subscribeToSaveResponse(this.temporadaService.create(temporada));
    }
  }

  private createFromForm(): ITemporada {
    return {
      ...new Temporada(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      cantCapitulos: this.editForm.get(['cantCapitulos'])!.value,
      duracionTotal: this.editForm.get(['duracionTotal'])!.value,
      contenido: this.editForm.get(['contenido'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITemporada>>): void {
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
