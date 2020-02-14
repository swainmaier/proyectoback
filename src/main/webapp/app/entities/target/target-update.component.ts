import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarget, Target } from 'app/shared/model/target.model';
import { TargetService } from './target.service';

@Component({
  selector: 'jhi-target-update',
  templateUrl: './target-update.component.html'
})
export class TargetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: []
  });

  constructor(protected targetService: TargetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ target }) => {
      this.updateForm(target);
    });
  }

  updateForm(target: ITarget): void {
    this.editForm.patchValue({
      id: target.id,
      titulo: target.titulo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const target = this.createFromForm();
    if (target.id !== undefined) {
      this.subscribeToSaveResponse(this.targetService.update(target));
    } else {
      this.subscribeToSaveResponse(this.targetService.create(target));
    }
  }

  private createFromForm(): ITarget {
    return {
      ...new Target(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarget>>): void {
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
