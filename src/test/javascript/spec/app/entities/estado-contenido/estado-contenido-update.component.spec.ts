import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { EstadoContenidoUpdateComponent } from 'app/entities/estado-contenido/estado-contenido-update.component';
import { EstadoContenidoService } from 'app/entities/estado-contenido/estado-contenido.service';
import { EstadoContenido } from 'app/shared/model/estado-contenido.model';

describe('Component Tests', () => {
  describe('EstadoContenido Management Update Component', () => {
    let comp: EstadoContenidoUpdateComponent;
    let fixture: ComponentFixture<EstadoContenidoUpdateComponent>;
    let service: EstadoContenidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [EstadoContenidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoContenidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoContenidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoContenidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoContenido(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoContenido();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
