import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RedmProyectosTestModule } from '../../../test.module';
import { EstadoProyectoComponent } from 'app/entities/estado-proyecto/estado-proyecto.component';
import { EstadoProyectoService } from 'app/entities/estado-proyecto/estado-proyecto.service';
import { EstadoProyecto } from 'app/shared/model/estado-proyecto.model';

describe('Component Tests', () => {
  describe('EstadoProyecto Management Component', () => {
    let comp: EstadoProyectoComponent;
    let fixture: ComponentFixture<EstadoProyectoComponent>;
    let service: EstadoProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [EstadoProyectoComponent]
      })
        .overrideTemplate(EstadoProyectoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoProyectoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoProyectoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EstadoProyecto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estadoProyectos && comp.estadoProyectos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
