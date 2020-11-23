import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FeedingObservationService } from 'app/entities/feeding-observation/feeding-observation.service';
import { IFeedingObservation, FeedingObservation } from 'app/shared/model/feeding-observation.model';
import { FeedingObservationType } from 'app/shared/model/enumerations/feeding-observation-type.model';
import { BreedAttemptType } from 'app/shared/model/enumerations/breed-attempt-type.model';
import { BreedingStageType } from 'app/shared/model/enumerations/breeding-stage-type.model';
import { BreedingOutcomeType } from 'app/shared/model/enumerations/breeding-outcome-type.model';
import { PreyItemType } from 'app/shared/model/enumerations/prey-item-type.model';
import { PreySpeciesType } from 'app/shared/model/enumerations/prey-species-type.model';
import { CloudType } from 'app/shared/model/enumerations/cloud-type.model';
import { WindType } from 'app/shared/model/enumerations/wind-type.model';
import { RainType } from 'app/shared/model/enumerations/rain-type.model';
import { FeedingObservationOutcomeType } from 'app/shared/model/enumerations/feeding-observation-outcome-type.model';

describe('Service Tests', () => {
  describe('FeedingObservation Service', () => {
    let injector: TestBed;
    let service: FeedingObservationService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeedingObservation;
    let expectedResult: IFeedingObservation | IFeedingObservation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FeedingObservationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FeedingObservation(
        0,
        0,
        FeedingObservationType.FOOD_ITEM_PASS,
        BreedAttemptType.YES,
        BreedingStageType.EGG,
        BreedingOutcomeType.ONGOING,
        PreyItemType.UNKNOWN,
        PreySpeciesType.UNKNOWN,
        'AAAAAAA',
        CloudType.OVERCAST,
        WindType.CALM,
        RainType.HEAVY,
        FeedingObservationOutcomeType.UNKNOWN,
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

      it('should create a FeedingObservation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FeedingObservation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FeedingObservation', () => {
        const returnedFromService = Object.assign(
          {
            noFieldWorkers: 1,
            type: 'BBBBBB',
            breedingAttempt: 'BBBBBB',
            breedingStage: 'BBBBBB',
            breedingOutcome: 'BBBBBB',
            preyItem: 'BBBBBB',
            preySpecies: 'BBBBBB',
            time: 'BBBBBB',
            cloud: 'BBBBBB',
            wind: 'BBBBBB',
            rain: 'BBBBBB',
            outcome: 'BBBBBB',
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FeedingObservation', () => {
        const returnedFromService = Object.assign(
          {
            noFieldWorkers: 1,
            type: 'BBBBBB',
            breedingAttempt: 'BBBBBB',
            breedingStage: 'BBBBBB',
            breedingOutcome: 'BBBBBB',
            preyItem: 'BBBBBB',
            preySpecies: 'BBBBBB',
            time: 'BBBBBB',
            cloud: 'BBBBBB',
            wind: 'BBBBBB',
            rain: 'BBBBBB',
            outcome: 'BBBBBB',
            comment: 'BBBBBB',
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

      it('should delete a FeedingObservation', () => {
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
