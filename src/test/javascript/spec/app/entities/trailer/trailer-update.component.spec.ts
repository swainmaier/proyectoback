import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { TrailerUpdateComponent } from 'app/entities/trailer/trailer-update.component';
import { TrailerService } from 'app/entities/trailer/trailer.service';
import { Trailer } from 'app/shared/model/trailer.model';

describe('Component Tests', () => {
  describe('Trailer Management Update Component', () => {
    let comp: TrailerUpdateComponent;
    let fixture: ComponentFixture<TrailerUpdateComponent>;
    let service: TrailerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TrailerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrailerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrailerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrailerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trailer(123);
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
        const entity = new Trailer();
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
