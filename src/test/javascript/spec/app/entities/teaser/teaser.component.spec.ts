import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { TeaserComponent } from 'app/entities/teaser/teaser.component';
import { TeaserService } from 'app/entities/teaser/teaser.service';
import { Teaser } from 'app/shared/model/teaser.model';

describe('Component Tests', () => {
  describe('Teaser Management Component', () => {
    let comp: TeaserComponent;
    let fixture: ComponentFixture<TeaserComponent>;
    let service: TeaserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TeaserComponent]
      })
        .overrideTemplate(TeaserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeaserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeaserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Teaser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.teasers && comp.teasers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
