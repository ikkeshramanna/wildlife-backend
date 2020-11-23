import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BirdBehaviourService } from 'app/entities/bird-behaviour/bird-behaviour.service';
import { IBirdBehaviour, BirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { BirdBehaviourType } from 'app/shared/model/enumerations/bird-behaviour-type.model';

describe('Service Tests', () => {
  describe('BirdBehaviour Service', () => {
    let injector: TestBed;
    let service: BirdBehaviourService;
    let httpMock: HttpTestingController;
    let elemDefault: IBirdBehaviour;
    let expectedResult: IBirdBehaviour | IBirdBehaviour[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BirdBehaviourService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BirdBehaviour(0, BirdBehaviourType.FLEW_AWAY_FROM_SITE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BirdBehaviour', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BirdBehaviour()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BirdBehaviour', () => {
        const returnedFromService = Object.assign(
          {
            behaviour: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BirdBehaviour', () => {
        const returnedFromService = Object.assign(
          {
            behaviour: 'BBBBBB',
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

      it('should delete a BirdBehaviour', () => {
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
