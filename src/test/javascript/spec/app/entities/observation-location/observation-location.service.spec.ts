import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ObservationLocationService } from 'app/entities/observation-location/observation-location.service';
import { IObservationLocation, ObservationLocation } from 'app/shared/model/observation-location.model';

describe('Service Tests', () => {
  describe('ObservationLocation Service', () => {
    let injector: TestBed;
    let service: ObservationLocationService;
    let httpMock: HttpTestingController;
    let elemDefault: IObservationLocation;
    let expectedResult: IObservationLocation | IObservationLocation[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ObservationLocationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ObservationLocation(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ObservationLocation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            addDate: currentDate,
            updateDate: currentDate,
          },
          returnedFromService
        );

        service.create(new ObservationLocation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ObservationLocation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            gpsLatitude: 'BBBBBB',
            gpsCoordinate: 'BBBBBB',
            locationName: 'BBBBBB',
            description: 'BBBBBB',
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            addDate: currentDate,
            updateDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ObservationLocation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            gpsLatitude: 'BBBBBB',
            gpsCoordinate: 'BBBBBB',
            locationName: 'BBBBBB',
            description: 'BBBBBB',
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            addDate: currentDate,
            updateDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ObservationLocation', () => {
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
