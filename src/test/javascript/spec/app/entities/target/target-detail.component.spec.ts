import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { TargetDetailComponent } from 'app/entities/target/target-detail.component';
import { Target } from 'app/shared/model/target.model';

describe('Component Tests', () => {
  describe('Target Management Detail Component', () => {
    let comp: TargetDetailComponent;
    let fixture: ComponentFixture<TargetDetailComponent>;
    const route = ({ data: of({ target: new Target(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TargetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TargetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TargetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load target on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.target).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
