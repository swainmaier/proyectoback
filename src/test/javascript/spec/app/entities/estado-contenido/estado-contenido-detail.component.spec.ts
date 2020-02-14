import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { EstadoContenidoDetailComponent } from 'app/entities/estado-contenido/estado-contenido-detail.component';
import { EstadoContenido } from 'app/shared/model/estado-contenido.model';

describe('Component Tests', () => {
  describe('EstadoContenido Management Detail Component', () => {
    let comp: EstadoContenidoDetailComponent;
    let fixture: ComponentFixture<EstadoContenidoDetailComponent>;
    const route = ({ data: of({ estadoContenido: new EstadoContenido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [EstadoContenidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoContenidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoContenidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoContenido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoContenido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
