import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { CapituloUpdateComponent } from 'app/entities/capitulo/capitulo-update.component';
import { CapituloService } from 'app/entities/capitulo/capitulo.service';
import { Capitulo } from 'app/shared/model/capitulo.model';

describe('Component Tests', () => {
  describe('Capitulo Management Update Component', () => {
    let comp: CapituloUpdateComponent;
    let fixture: ComponentFixture<CapituloUpdateComponent>;
    let service: CapituloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [CapituloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CapituloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CapituloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CapituloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Capitulo(123);
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
        const entity = new Capitulo();
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
