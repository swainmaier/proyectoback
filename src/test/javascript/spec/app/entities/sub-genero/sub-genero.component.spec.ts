import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { SubGeneroComponent } from 'app/entities/sub-genero/sub-genero.component';
import { SubGeneroService } from 'app/entities/sub-genero/sub-genero.service';
import { SubGenero } from 'app/shared/model/sub-genero.model';

describe('Component Tests', () => {
  describe('SubGenero Management Component', () => {
    let comp: SubGeneroComponent;
    let fixture: ComponentFixture<SubGeneroComponent>;
    let service: SubGeneroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [SubGeneroComponent]
      })
        .overrideTemplate(SubGeneroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubGeneroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubGeneroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubGenero(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subGeneros && comp.subGeneros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
