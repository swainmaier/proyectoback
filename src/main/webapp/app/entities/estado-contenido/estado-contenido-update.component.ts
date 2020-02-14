import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoContenido, EstadoContenido } from 'app/shared/model/estado-contenido.model';
import { EstadoContenidoService } from './estado-contenido.service';

@Component({
  selector: 'jhi-estado-contenido-update',
  templateUrl: './estado-contenido-update.component.html'
})
export class EstadoContenidoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: []
  });

  constructor(
    protected estadoContenidoService: EstadoContenidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoContenido }) => {
      this.updateForm(estadoContenido);
    });
  }

  updateForm(estadoContenido: IEstadoContenido): void {
    this.editForm.patchValue({
      id: estadoContenido.id,
      titulo: estadoContenido.titulo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoContenido = this.createFromForm();
    if (estadoContenido.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoContenidoService.update(estadoContenido));
    } else {
      this.subscribeToSaveResponse(this.estadoContenidoService.create(estadoContenido));
    }
  }

  private createFromForm(): IEstadoContenido {
    return {
      ...new EstadoContenido(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoContenido>>): void {
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
}
