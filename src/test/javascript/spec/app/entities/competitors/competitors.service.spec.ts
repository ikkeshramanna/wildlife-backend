import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CompetitorsService } from 'app/entities/competitors/competitors.service';
import { ICompetitors, Competitors } from 'app/shared/model/competitors.model';
import { CompetitorBehaviourType } from 'app/shared/model/enumerations/competitor-behaviour-type.model';
import { CompetitorLocationType } from 'app/shared/model/enumerations/competitor-location-type.model';

describe('Service Tests', () => {
  describe('Competitors Service', () => {
    let injector: TestBed;
    let service: CompetitorsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompetitors;
    let expectedResult: ICompetitors | ICompetitors[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompetitorsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Competitors(0, 'AAAAAAA', 0, CompetitorBehaviourType.NO_INTERACTION, CompetitorLocationType.TRACES_IN_NEST);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Competitors', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Competitors()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Competitors', () => {
        const returnedFromService = Object.assign(
          {
            mkAround: 'BBBBBB',
            noOfIndividuals: 1,
            competitorBehaviour: 'BBBBBB',
            competitorLocation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Competitors', () => {
        const returnedFromService = Object.assign(
          {
            mkAround: 'BBBBBB',
            noOfIndividuals: 1,
            competitorBehaviour: 'BBBBBB',
            competitorLocation: 'BBBBBB',
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

      it('should delete a Competitors', () => {
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
