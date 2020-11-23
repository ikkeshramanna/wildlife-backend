import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CompetitorImpactOnMkService } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk.service';
import { ICompetitorImpactOnMk, CompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { ImpactOnMKType } from 'app/shared/model/enumerations/impact-on-mk-type.model';

describe('Service Tests', () => {
  describe('CompetitorImpactOnMk Service', () => {
    let injector: TestBed;
    let service: CompetitorImpactOnMkService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompetitorImpactOnMk;
    let expectedResult: ICompetitorImpactOnMk | ICompetitorImpactOnMk[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompetitorImpactOnMkService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CompetitorImpactOnMk(0, ImpactOnMKType.UNKNOWN);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompetitorImpactOnMk', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CompetitorImpactOnMk()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompetitorImpactOnMk', () => {
        const returnedFromService = Object.assign(
          {
            impact: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompetitorImpactOnMk', () => {
        const returnedFromService = Object.assign(
          {
            impact: 'BBBBBB',
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

      it('should delete a CompetitorImpactOnMk', () => {
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
