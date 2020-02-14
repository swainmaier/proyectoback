import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from './contenido.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto/proyecto.service';
import { IEstadoContenido } from 'app/shared/model/estado-contenido.model';
import { EstadoContenidoService } from 'app/entities/estado-contenido/estado-contenido.service';
import { IFormato } from 'app/shared/model/formato.model';
import { FormatoService } from 'app/entities/formato/formato.service';
import { IGenero } from 'app/shared/model/genero.model';
import { GeneroService } from 'app/entities/genero/genero.service';
import { IPlataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from 'app/entities/plataforma/plataforma.service';

type SelectableEntity = IProyecto | IEstadoContenido | IFormato | IGenero | IPlataforma;

@Component({
  selector: 'jhi-contenido-update',
  templateUrl: './contenido-update.component.html'
})
export class ContenidoUpdateComponent implements OnInit {
  isSaving = false;
  proyectos: IProyecto[] = [];
  estadocontenidos: IEstadoContenido[] = [];
  formatoes: IFormato[] = [];
  generos: IGenero[] = [];
  plataformas: IPlataforma[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    cantTemporadas: [],
    fechaPublicacion: [],
    caratula: [],
    proyecto: [],
    estadoContenido: [],
    formato: [],
    genero: [],
    plataforma: []
  });

  constructor(
    protected contenidoService: ContenidoService,
    protected proyectoService: ProyectoService,
    protected estadoContenidoService: EstadoContenidoService,
    protected formatoService: FormatoService,
    protected generoService: GeneroService,
    protected plataformaService: PlataformaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenido }) => {
      this.updateForm(contenido);

      this.proyectoService.query().subscribe((res: HttpResponse<IProyecto[]>) => (this.proyectos = res.body || []));

      this.estadoContenidoService.query().subscribe((res: HttpResponse<IEstadoContenido[]>) => (this.estadocontenidos = res.body || []));

      this.formatoService.query().subscribe((res: HttpResponse<IFormato[]>) => (this.formatoes = res.body || []));

      this.generoService.query().subscribe((res: HttpResponse<IGenero[]>) => (this.generos = res.body || []));

      this.plataformaService.query().subscribe((res: HttpResponse<IPlataforma[]>) => (this.plataformas = res.body || []));
    });
  }

  updateForm(contenido: IContenido): void {
    this.editForm.patchValue({
      id: contenido.id,
      titulo: contenido.titulo,
      cantTemporadas: contenido.cantTemporadas,
      fechaPublicacion: contenido.fechaPublicacion,
      caratula: contenido.caratula,
      proyecto: contenido.proyecto,
      estadoContenido: contenido.estadoContenido,
      formato: contenido.formato,
      genero: contenido.genero,
      plataforma: contenido.plataforma
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contenido = this.createFromForm();
    if (contenido.id !== undefined) {
      this.subscribeToSaveResponse(this.contenidoService.update(contenido));
    } else {
      this.subscribeToSaveResponse(this.contenidoService.create(contenido));
    }
  }

  private createFromForm(): IContenido {
    return {
      ...new Contenido(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      cantTemporadas: this.editForm.get(['cantTemporadas'])!.value,
      fechaPublicacion: this.editForm.get(['fechaPublicacion'])!.value,
      caratula: this.editForm.get(['caratula'])!.value,
      proyecto: this.editForm.get(['proyecto'])!.value,
      estadoContenido: this.editForm.get(['estadoContenido'])!.value,
      formato: this.editForm.get(['formato'])!.value,
      genero: this.editForm.get(['genero'])!.value,
      plataforma: this.editForm.get(['plataforma'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContenido>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
