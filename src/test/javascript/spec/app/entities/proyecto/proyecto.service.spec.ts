import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProyectoService } from 'app/entities/proyecto/proyecto.service';
import { IProyecto, Proyecto } from 'app/shared/model/proyecto.model';

describe('Service Tests', () => {
  describe('Proyecto Service', () => {
    let injector: TestBed;
    let service: ProyectoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProyecto;
    let expectedResult: IProyecto | IProyecto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProyectoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Proyecto(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Proyecto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Proyecto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Proyecto', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            sumary: 'BBBBBB',
            slogan: 'BBBBBB',
            code: 'BBBBBB',
            fechaPub: 'BBBBBB',
            sinopsis: 'BBBBBB',
            storyline: 'BBBBBB',
            logline: 'BBBBBB',
            imagen: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Proyecto', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            sumary: 'BBBBBB',
            slogan: 'BBBBBB',
            code: 'BBBBBB',
            fechaPub: 'BBBBBB',
            sinopsis: 'BBBBBB',
            storyline: 'BBBBBB',
            logline: 'BBBBBB',
            imagen: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Proyecto', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
