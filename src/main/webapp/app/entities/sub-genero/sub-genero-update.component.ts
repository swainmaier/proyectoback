import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubGenero, SubGenero } from 'app/shared/model/sub-genero.model';
import { SubGeneroService } from './sub-genero.service';

@Component({
  selector: 'jhi-sub-genero-update',
  templateUrl: './sub-genero-update.component.html'
})
export class SubGeneroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: []
  });

  constructor(protected subGeneroService: SubGeneroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subGenero }) => {
      this.updateForm(subGenero);
    });
  }

  updateForm(subGenero: ISubGenero): void {
    this.editForm.patchValue({
      id: subGenero.id,
      titulo: subGenero.titulo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subGenero = this.createFromForm();
    if (subGenero.id !== undefined) {
      this.subscribeToSaveResponse(this.subGeneroService.update(subGenero));
    } else {
      this.subscribeToSaveResponse(this.subGeneroService.create(subGenero));
    }
  }

  private createFromForm(): ISubGenero {
    return {
      ...new SubGenero(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubGenero>>): void {
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
