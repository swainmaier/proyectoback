import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICapitulo, Capitulo } from 'app/shared/model/capitulo.model';
import { CapituloService } from './capitulo.service';
import { ITemporada } from 'app/shared/model/temporada.model';
import { TemporadaService } from 'app/entities/temporada/temporada.service';

@Component({
  selector: 'jhi-capitulo-update',
  templateUrl: './capitulo-update.component.html'
})
export class CapituloUpdateComponent implements OnInit {
  isSaving = false;
  temporadas: ITemporada[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    numero: [],
    sinopsis: [],
    logline: [],
    caratula: [],
    duracion: [],
    vimeoID: [],
    temporada: []
  });

  constructor(
    protected capituloService: CapituloService,
    protected temporadaService: TemporadaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ capitulo }) => {
      this.updateForm(capitulo);

      this.temporadaService.query().subscribe((res: HttpResponse<ITemporada[]>) => (this.temporadas = res.body || []));
    });
  }

  updateForm(capitulo: ICapitulo): void {
    this.editForm.patchValue({
      id: capitulo.id,
      titulo: capitulo.titulo,
      numero: capitulo.numero,
      sinopsis: capitulo.sinopsis,
      logline: capitulo.logline,
      caratula: capitulo.caratula,
      duracion: capitulo.duracion,
      vimeoID: capitulo.vimeoID,
      temporada: capitulo.temporada
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const capitulo = this.createFromForm();
    if (capitulo.id !== undefined) {
      this.subscribeToSaveResponse(this.capituloService.update(capitulo));
    } else {
      this.subscribeToSaveResponse(this.capituloService.create(capitulo));
    }
  }

  private createFromForm(): ICapitulo {
    return {
      ...new Capitulo(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      sinopsis: this.editForm.get(['sinopsis'])!.value,
      logline: this.editForm.get(['logline'])!.value,
      caratula: this.editForm.get(['caratula'])!.value,
      duracion: this.editForm.get(['duracion'])!.value,
      vimeoID: this.editForm.get(['vimeoID'])!.value,
      temporada: this.editForm.get(['temporada'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICapitulo>>): void {
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

  trackById(index: number, item: ITemporada): any {
    return item.id;
  }
}
