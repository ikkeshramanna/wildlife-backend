import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NestSiteOverviewService } from 'app/entities/nest-site-overview/nest-site-overview.service';
import { INestSiteOverview, NestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { VisitPurposeType } from 'app/shared/model/enumerations/visit-purpose-type.model';
import { UseSignType } from 'app/shared/model/enumerations/use-sign-type.model';

describe('Service Tests', () => {
  describe('NestSiteOverview Service', () => {
    let injector: TestBed;
    let service: NestSiteOverviewService;
    let httpMock: HttpTestingController;
    let elemDefault: INestSiteOverview;
    let expectedResult: INestSiteOverview | INestSiteOverview[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NestSiteOverviewService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new NestSiteOverview(
        0,
        0,
        VisitPurposeType.OPPORTUNISTIC,
        UseSignType.MUTES,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a NestSiteOverview', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NestSiteOverview()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NestSiteOverview', () => {
        const returnedFromService = Object.assign(
          {
            numberPeople: 1,
            purposeOfVisit: 'BBBBBB',
            signsOfUse: 'BBBBBB',
            nestingSubstrate: 'BBBBBB',
            maintenanceDone: 'BBBBBB',
            maintenanceRequired: 'BBBBBB',
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

      it('should return a list of NestSiteOverview', () => {
        const returnedFromService = Object.assign(
          {
            numberPeople: 1,
            purposeOfVisit: 'BBBBBB',
            signsOfUse: 'BBBBBB',
            nestingSubstrate: 'BBBBBB',
            maintenanceDone: 'BBBBBB',
            maintenanceRequired: 'BBBBBB',
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

      it('should delete a NestSiteOverview', () => {
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
