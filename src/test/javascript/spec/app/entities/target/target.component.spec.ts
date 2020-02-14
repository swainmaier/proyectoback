import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { TargetComponent } from 'app/entities/target/target.component';
import { TargetService } from 'app/entities/target/target.service';
import { Target } from 'app/shared/model/target.model';

describe('Component Tests', () => {
  describe('Target Management Component', () => {
    let comp: TargetComponent;
    let fixture: ComponentFixture<TargetComponent>;
    let service: TargetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TargetComponent]
      })
        .overrideTemplate(TargetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TargetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TargetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Target(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.targets && comp.targets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
