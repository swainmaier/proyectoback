import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RedmProyectosTestModule } from '../../../test.module';
import { FormatoDetailComponent } from 'app/entities/formato/formato-detail.component';
import { Formato } from 'app/shared/model/formato.model';

describe('Component Tests', () => {
  describe('Formato Management Detail Component', () => {
    let comp: FormatoDetailComponent;
    let fixture: ComponentFixture<FormatoDetailComponent>;
    const route = ({ data: of({ formato: new Formato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RedmProyectosTestModule],
        declarations: [FormatoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormatoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormatoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load formato on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
