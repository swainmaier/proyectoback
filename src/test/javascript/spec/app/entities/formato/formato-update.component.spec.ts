import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { FormatoUpdateComponent } from 'app/entities/formato/formato-update.component';
import { FormatoService } from 'app/entities/formato/formato.service';
import { Formato } from 'app/shared/model/formato.model';

describe('Component Tests', () => {
  describe('Formato Management Update Component', () => {
    let comp: FormatoUpdateComponent;
    let fixture: ComponentFixture<FormatoUpdateComponent>;
    let service: FormatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [FormatoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormatoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormatoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Formato(123);
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
        const entity = new Formato();
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
