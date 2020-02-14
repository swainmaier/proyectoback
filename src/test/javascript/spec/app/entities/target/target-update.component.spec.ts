import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { TargetUpdateComponent } from 'app/entities/target/target-update.component';
import { TargetService } from 'app/entities/target/target.service';
import { Target } from 'app/shared/model/target.model';

describe('Component Tests', () => {
  describe('Target Management Update Component', () => {
    let comp: TargetUpdateComponent;
    let fixture: ComponentFixture<TargetUpdateComponent>;
    let service: TargetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TargetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TargetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TargetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TargetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Target(123);
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
        const entity = new Target();
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
