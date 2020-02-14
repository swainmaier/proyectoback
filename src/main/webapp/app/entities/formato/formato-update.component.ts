import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFormato, Formato } from 'app/shared/model/formato.model';
import { FormatoService } from './formato.service';

@Component({
  selector: 'jhi-formato-update',
  templateUrl: './formato-update.component.html'
})
export class FormatoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: []
  });

  constructor(protected formatoService: FormatoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formato }) => {
      this.updateForm(formato);
    });
  }

  updateForm(formato: IFormato): void {
    this.editForm.patchValue({
      id: formato.id,
      titulo: formato.titulo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formato = this.createFromForm();
    if (formato.id !== undefined) {
      this.subscribeToSaveResponse(this.formatoService.update(formato));
    } else {
      this.subscribeToSaveResponse(this.formatoService.create(formato));
    }
  }

  private createFromForm(): IFormato {
    return {
      ...new Formato(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormato>>): void {
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
