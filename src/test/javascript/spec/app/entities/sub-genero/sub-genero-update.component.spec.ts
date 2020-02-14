import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { SubGeneroUpdateComponent } from 'app/entities/sub-genero/sub-genero-update.component';
import { SubGeneroService } from 'app/entities/sub-genero/sub-genero.service';
import { SubGenero } from 'app/shared/model/sub-genero.model';

describe('Component Tests', () => {
  describe('SubGenero Management Update Component', () => {
    let comp: SubGeneroUpdateComponent;
    let fixture: ComponentFixture<SubGeneroUpdateComponent>;
    let service: SubGeneroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [SubGeneroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubGeneroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubGeneroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubGeneroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubGenero(123);
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
        const entity = new SubGenero();
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
