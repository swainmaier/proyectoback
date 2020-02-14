import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { TeaserUpdateComponent } from 'app/entities/teaser/teaser-update.component';
import { TeaserService } from 'app/entities/teaser/teaser.service';
import { Teaser } from 'app/shared/model/teaser.model';

describe('Component Tests', () => {
  describe('Teaser Management Update Component', () => {
    let comp: TeaserUpdateComponent;
    let fixture: ComponentFixture<TeaserUpdateComponent>;
    let service: TeaserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TeaserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TeaserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeaserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeaserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Teaser(123);
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
        const entity = new Teaser();
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
