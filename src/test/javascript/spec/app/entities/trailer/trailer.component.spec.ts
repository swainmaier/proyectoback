import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { TrailerComponent } from 'app/entities/trailer/trailer.component';
import { TrailerService } from 'app/entities/trailer/trailer.service';
import { Trailer } from 'app/shared/model/trailer.model';

describe('Component Tests', () => {
  describe('Trailer Management Component', () => {
    let comp: TrailerComponent;
    let fixture: ComponentFixture<TrailerComponent>;
    let service: TrailerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TrailerComponent]
      })
        .overrideTemplate(TrailerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrailerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrailerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Trailer(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trailers && comp.trailers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
