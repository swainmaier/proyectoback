import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { SubGeneroDetailComponent } from 'app/entities/sub-genero/sub-genero-detail.component';
import { SubGenero } from 'app/shared/model/sub-genero.model';

describe('Component Tests', () => {
  describe('SubGenero Management Detail Component', () => {
    let comp: SubGeneroDetailComponent;
    let fixture: ComponentFixture<SubGeneroDetailComponent>;
    const route = ({ data: of({ subGenero: new SubGenero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [SubGeneroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubGeneroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubGeneroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subGenero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subGenero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
