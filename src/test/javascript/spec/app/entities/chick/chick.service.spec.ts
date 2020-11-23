import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ChickService } from 'app/entities/chick/chick.service';
import { IChick, Chick } from 'app/shared/model/chick.model';
import { ChicKStatusType } from 'app/shared/model/enumerations/chic-k-status-type.model';
import { ChickConditionType } from 'app/shared/model/enumerations/chick-condition-type.model';

describe('Service Tests', () => {
  describe('Chick Service', () => {
    let injector: TestBed;
    let service: ChickService;
    let httpMock: HttpTestingController;
    let elemDefault: IChick;
    let expectedResult: IChick | IChick[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ChickService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Chick(0, 0, currentDate, ChicKStatusType.ALIVE, 0, 'AAAAAAA', ChickConditionType.GOOD, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hatchDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Chick', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hatchDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hatchDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Chick()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Chick', () => {
        const returnedFromService = Object.assign(
          {
            chickNumber: 1,
            hatchDate: currentDate.format(DATE_FORMAT),
            chickStatus: 'BBBBBB',
            chickAge: 1,
            chickActive: 'BBBBBB',
            chickCondition: 'BBBBBB',
            chickRinged: 'BBBBBB',
            bloodSample: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hatchDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Chick', () => {
        const returnedFromService = Object.assign(
          {
            chickNumber: 1,
            hatchDate: currentDate.format(DATE_FORMAT),
            chickStatus: 'BBBBBB',
            chickAge: 1,
            chickActive: 'BBBBBB',
            chickCondition: 'BBBBBB',
            chickRinged: 'BBBBBB',
            bloodSample: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hatchDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Chick', () => {
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
