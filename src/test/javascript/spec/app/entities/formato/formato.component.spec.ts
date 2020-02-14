import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { FormatoComponent } from 'app/entities/formato/formato.component';
import { FormatoService } from 'app/entities/formato/formato.service';
import { Formato } from 'app/shared/model/formato.model';

describe('Component Tests', () => {
  describe('Formato Management Component', () => {
    let comp: FormatoComponent;
    let fixture: ComponentFixture<FormatoComponent>;
    let service: FormatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [FormatoComponent]
      })
        .overrideTemplate(FormatoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormatoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormatoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Formato(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.formatoes && comp.formatoes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
