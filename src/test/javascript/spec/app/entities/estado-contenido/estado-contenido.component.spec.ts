import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { EstadoContenidoComponent } from 'app/entities/estado-contenido/estado-contenido.component';
import { EstadoContenidoService } from 'app/entities/estado-contenido/estado-contenido.service';
import { EstadoContenido } from 'app/shared/model/estado-contenido.model';

describe('Component Tests', () => {
  describe('EstadoContenido Management Component', () => {
    let comp: EstadoContenidoComponent;
    let fixture: ComponentFixture<EstadoContenidoComponent>;
    let service: EstadoContenidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [EstadoContenidoComponent]
      })
        .overrideTemplate(EstadoContenidoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoContenidoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoContenidoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EstadoContenido(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estadoContenidos && comp.estadoContenidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
