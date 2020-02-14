import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEstadoProyecto, EstadoProyecto } from 'app/shared/model/estado-proyecto.model';
import { EstadoProyectoService } from './estado-proyecto.service';

@Component({
  selector: 'jhi-estado-proyecto-update',
  templateUrl: './estado-proyecto-update.component.html'
})
export class EstadoProyectoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: []
  });

  constructor(protected estadoProyectoService: EstadoProyectoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoProyecto }) => {
      this.updateForm(estadoProyecto);
    });
  }

  updateForm(estadoProyecto: IEstadoProyecto): void {
    this.editForm.patchValue({
      id: estadoProyecto.id,
      titulo: estadoProyecto.titulo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoProyecto = this.createFromForm();
    if (estadoProyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoProyectoService.update(estadoProyecto));
    } else {
      this.subscribeToSaveResponse(this.estadoProyectoService.create(estadoProyecto));
    }
  }

  private createFromForm(): IEstadoProyecto {
    return {
      ...new EstadoProyecto(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoProyecto>>): void {
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
