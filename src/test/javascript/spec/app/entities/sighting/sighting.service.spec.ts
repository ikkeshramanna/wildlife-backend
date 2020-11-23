import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SightingService } from 'app/entities/sighting/sighting.service';
import { ISighting, Sighting } from 'app/shared/model/sighting.model';
import { NestSiteType } from 'app/shared/model/enumerations/nest-site-type.model';
import { AreaType } from 'app/shared/model/enumerations/area-type.model';
import { NestType } from 'app/shared/model/enumerations/nest-type.model';

describe('Service Tests', () => {
  describe('Sighting Service', () => {
    let injector: TestBed;
    let service: SightingService;
    let httpMock: HttpTestingController;
    let elemDefault: ISighting;
    let expectedResult: ISighting | ISighting[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SightingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Sighting(
        0,
        NestSiteType.BAMBOUS_VIRIEUX_3,
        AreaType.FERNEY,
        NestType.WOODBOX,
        0,
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
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

      it('should create a Sighting', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            addDate: currentDate,
            updateDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Sighting()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Sighting', () => {
        const returnedFromService = Object.assign(
          {
            nestSite: 'BBBBBB',
            area: 'BBBBBB',
            nestType: 'BBBBBB',
            year: 1,
            month: 1,
            date: currentDate.format(DATE_FORMAT),
            observer: 'BBBBBB',
            gpsLatitude: 'BBBBBB',
            gpsCoordinate: 'BBBBBB',
            locationName: 'BBBBBB',
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
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

      it('should return a list of Sighting', () => {
        const returnedFromService = Object.assign(
          {
            nestSite: 'BBBBBB',
            area: 'BBBBBB',
            nestType: 'BBBBBB',
            year: 1,
            month: 1,
            date: currentDate.format(DATE_FORMAT),
            observer: 'BBBBBB',
            gpsLatitude: 'BBBBBB',
            gpsCoordinate: 'BBBBBB',
            locationName: 'BBBBBB',
            addDate: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
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

      it('should delete a Sighting', () => {
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
