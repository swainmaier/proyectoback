import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { EstadoProyectoUpdateComponent } from 'app/entities/estado-proyecto/estado-proyecto-update.component';
import { EstadoProyectoService } from 'app/entities/estado-proyecto/estado-proyecto.service';
import { EstadoProyecto } from 'app/shared/model/estado-proyecto.model';

describe('Component Tests', () => {
  describe('EstadoProyecto Management Update Component', () => {
    let comp: EstadoProyectoUpdateComponent;
    let fixture: ComponentFixture<EstadoProyectoUpdateComponent>;
    let service: EstadoProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [EstadoProyectoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoProyectoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoProyectoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoProyectoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoProyecto(123);
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
        const entity = new EstadoProyecto();
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
