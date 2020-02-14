import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { EstadoProyectoDetailComponent } from 'app/entities/estado-proyecto/estado-proyecto-detail.component';
import { EstadoProyecto } from 'app/shared/model/estado-proyecto.model';

describe('Component Tests', () => {
  describe('EstadoProyecto Management Detail Component', () => {
    let comp: EstadoProyectoDetailComponent;
    let fixture: ComponentFixture<EstadoProyectoDetailComponent>;
    const route = ({ data: of({ estadoProyecto: new EstadoProyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [EstadoProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoProyectoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoProyecto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoProyecto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
