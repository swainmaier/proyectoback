import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CapituloService } from 'app/entities/capitulo/capitulo.service';
import { ICapitulo, Capitulo } from 'app/shared/model/capitulo.model';

describe('Service Tests', () => {
  describe('Capitulo Service', () => {
    let injector: TestBed;
    let service: CapituloService;
    let httpMock: HttpTestingController;
    let elemDefault: ICapitulo;
    let expectedResult: ICapitulo | ICapitulo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CapituloService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Capitulo(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Capitulo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Capitulo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Capitulo', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            numero: 1,
            sinopsis: 'BBBBBB',
            logline: 'BBBBBB',
            caratula: 'BBBBBB',
            duracion: 1,
            vimeoID: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Capitulo', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            numero: 1,
            sinopsis: 'BBBBBB',
            logline: 'BBBBBB',
            caratula: 'BBBBBB',
            duracion: 1,
            vimeoID: 'BBBBBB'
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

      it('should delete a Capitulo', () => {
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
