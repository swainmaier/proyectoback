import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { CapituloDetailComponent } from 'app/entities/capitulo/capitulo-detail.component';
import { Capitulo } from 'app/shared/model/capitulo.model';

describe('Component Tests', () => {
  describe('Capitulo Management Detail Component', () => {
    let comp: CapituloDetailComponent;
    let fixture: ComponentFixture<CapituloDetailComponent>;
    const route = ({ data: of({ capitulo: new Capitulo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [CapituloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CapituloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CapituloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load capitulo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.capitulo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
