import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { TrailerDetailComponent } from 'app/entities/trailer/trailer-detail.component';
import { Trailer } from 'app/shared/model/trailer.model';

describe('Component Tests', () => {
  describe('Trailer Management Detail Component', () => {
    let comp: TrailerDetailComponent;
    let fixture: ComponentFixture<TrailerDetailComponent>;
    const route = ({ data: of({ trailer: new Trailer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TrailerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrailerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrailerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load trailer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trailer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
