import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { PlataformaComponent } from 'app/entities/plataforma/plataforma.component';
import { PlataformaService } from 'app/entities/plataforma/plataforma.service';
import { Plataforma } from 'app/shared/model/plataforma.model';

describe('Component Tests', () => {
  describe('Plataforma Management Component', () => {
    let comp: PlataformaComponent;
    let fixture: ComponentFixture<PlataformaComponent>;
    let service: PlataformaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [PlataformaComponent]
      })
        .overrideTemplate(PlataformaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlataformaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlataformaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Plataforma(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plataformas && comp.plataformas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
