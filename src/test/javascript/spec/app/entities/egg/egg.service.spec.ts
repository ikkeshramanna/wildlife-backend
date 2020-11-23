import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EggService } from 'app/entities/egg/egg.service';
import { IEgg, Egg } from 'app/shared/model/egg.model';
import { EggStatusType } from 'app/shared/model/enumerations/egg-status-type.model';

describe('Service Tests', () => {
  describe('Egg Service', () => {
    let injector: TestBed;
    let service: EggService;
    let httpMock: HttpTestingController;
    let elemDefault: IEgg;
    let expectedResult: IEgg | IEgg[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EggService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Egg(0, 'AAAAAAA', EggStatusType.FERTILE, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            eggLayDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Egg', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            eggLayDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            eggLayDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Egg()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Egg', () => {
        const returnedFromService = Object.assign(
          {
            eggNumber: 'BBBBBB',
            eggStatus: 'BBBBBB',
            eggLayDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            eggLayDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Egg', () => {
        const returnedFromService = Object.assign(
          {
            eggNumber: 'BBBBBB',
            eggStatus: 'BBBBBB',
            eggLayDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            eggLayDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Egg', () => {
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
