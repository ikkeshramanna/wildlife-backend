import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EggsAndChickService } from 'app/entities/eggs-and-chick/eggs-and-chick.service';
import { IEggsAndChick, EggsAndChick } from 'app/shared/model/eggs-and-chick.model';

describe('Service Tests', () => {
  describe('EggsAndChick Service', () => {
    let injector: TestBed;
    let service: EggsAndChickService;
    let httpMock: HttpTestingController;
    let elemDefault: IEggsAndChick;
    let expectedResult: IEggsAndChick | IEggsAndChick[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EggsAndChickService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EggsAndChick(0, 'AAAAAAA', false, false, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EggsAndChick', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EggsAndChick()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EggsAndChick', () => {
        const returnedFromService = Object.assign(
          {
            clutchNumber: 'BBBBBB',
            eggsPresent: true,
            chicksPresent: true,
            photoTaken: 'BBBBBB',
            comments: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EggsAndChick', () => {
        const returnedFromService = Object.assign(
          {
            clutchNumber: 'BBBBBB',
            eggsPresent: true,
            chicksPresent: true,
            photoTaken: 'BBBBBB',
            comments: 'BBBBBB',
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

      it('should delete a EggsAndChick', () => {
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
