import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProyecto, Proyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from './proyecto.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEstadoProyecto } from 'app/shared/model/estado-proyecto.model';
import { EstadoProyectoService } from 'app/entities/estado-proyecto/estado-proyecto.service';
import { ITarget } from 'app/shared/model/target.model';
import { TargetService } from 'app/entities/target/target.service';

type SelectableEntity = IEstadoProyecto | ITarget;

@Component({
  selector: 'jhi-proyecto-update',
  templateUrl: './proyecto-update.component.html'
})
export class ProyectoUpdateComponent implements OnInit {
  isSaving = false;
  estadoproyectos: IEstadoProyecto[] = [];
  targets: ITarget[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    sumary: [],
    slogan: [],
    code: [],
    fechaPub: [],
    sinopsis: [],
    storyline: [],
    logline: [],
    imagen: [],
    imagenContentType: [],
    estadoProyecto: [],
    target: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected proyectoService: ProyectoService,
    protected estadoProyectoService: EstadoProyectoService,
    protected targetService: TargetService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proyecto }) => {
      this.updateForm(proyecto);

      this.estadoProyectoService.query().subscribe((res: HttpResponse<IEstadoProyecto[]>) => (this.estadoproyectos = res.body || []));

      this.targetService.query().subscribe((res: HttpResponse<ITarget[]>) => (this.targets = res.body || []));
    });
  }

  updateForm(proyecto: IProyecto): void {
    this.editForm.patchValue({
      id: proyecto.id,
      titulo: proyecto.titulo,
      sumary: proyecto.sumary,
      slogan: proyecto.slogan,
      code: proyecto.code,
      fechaPub: proyecto.fechaPub,
      sinopsis: proyecto.sinopsis,
      storyline: proyecto.storyline,
      logline: proyecto.logline,
      imagen: proyecto.imagen,
      imagenContentType: proyecto.imagenContentType,
      estadoProyecto: proyecto.estadoProyecto,
      target: proyecto.target
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('redmProyectosApp.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proyecto = this.createFromForm();
    if (proyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.proyectoService.update(proyecto));
    } else {
      this.subscribeToSaveResponse(this.proyectoService.create(proyecto));
    }
  }

  private createFromForm(): IProyecto {
    return {
      ...new Proyecto(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      sumary: this.editForm.get(['sumary'])!.value,
      slogan: this.editForm.get(['slogan'])!.value,
      code: this.editForm.get(['code'])!.value,
      fechaPub: this.editForm.get(['fechaPub'])!.value,
      sinopsis: this.editForm.get(['sinopsis'])!.value,
      storyline: this.editForm.get(['storyline'])!.value,
      logline: this.editForm.get(['logline'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      estadoProyecto: this.editForm.get(['estadoProyecto'])!.value,
      target: this.editForm.get(['target'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProyecto>>): void {
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
