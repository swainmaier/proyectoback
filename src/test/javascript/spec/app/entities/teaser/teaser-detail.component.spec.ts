import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { TeaserDetailComponent } from 'app/entities/teaser/teaser-detail.component';
import { Teaser } from 'app/shared/model/teaser.model';

describe('Component Tests', () => {
  describe('Teaser Management Detail Component', () => {
    let comp: TeaserDetailComponent;
    let fixture: ComponentFixture<TeaserDetailComponent>;
    const route = ({ data: of({ teaser: new Teaser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [TeaserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TeaserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeaserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load teaser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.teaser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
