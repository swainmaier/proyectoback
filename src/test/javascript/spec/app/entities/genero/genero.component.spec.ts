import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { GeneroComponent } from 'app/entities/genero/genero.component';
import { GeneroService } from 'app/entities/genero/genero.service';
import { Genero } from 'app/shared/model/genero.model';

describe('Component Tests', () => {
  describe('Genero Management Component', () => {
    let comp: GeneroComponent;
    let fixture: ComponentFixture<GeneroComponent>;
    let service: GeneroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [GeneroComponent]
      })
        .overrideTemplate(GeneroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeneroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeneroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Genero(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.generos && comp.generos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
