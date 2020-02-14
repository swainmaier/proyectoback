import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { PlataformaDetailComponent } from 'app/entities/plataforma/plataforma-detail.component';
import { Plataforma } from 'app/shared/model/plataforma.model';

describe('Component Tests', () => {
  describe('Plataforma Management Detail Component', () => {
    let comp: PlataformaDetailComponent;
    let fixture: ComponentFixture<PlataformaDetailComponent>;
    const route = ({ data: of({ plataforma: new Plataforma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [PlataformaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlataformaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlataformaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plataforma on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plataforma).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
