import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SpeciesService } from 'app/entities/species/species.service';
import { ISpecies, Species } from 'app/shared/model/species.model';
import { FeedingTraitType } from 'app/shared/model/enumerations/feeding-trait-type.model';

describe('Service Tests', () => {
  describe('Species Service', () => {
    let injector: TestBed;
    let service: SpeciesService;
    let httpMock: HttpTestingController;
    let elemDefault: ISpecies;
    let expectedResult: ISpecies | ISpecies[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SpeciesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Species(
        0,
        'image/png',
        'AAAAAAA',
        FeedingTraitType.OMNIVORE,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        currentDate
      );
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

      it('should create a Species', () => {
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

        service.create(new Species()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Species', () => {
        const returnedFromService = Object.assign(
          {
            picture: 'BBBBBB',
            feedingTraitType: 'BBBBBB',
            speciesType: 'BBBBBB',
            commonName: 'BBBBBB',
            latinName: 'BBBBBB',
            description: 'BBBBBB',
            isIndigenous: true,
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

      it('should return a list of Species', () => {
        const returnedFromService = Object.assign(
          {
            picture: 'BBBBBB',
            feedingTraitType: 'BBBBBB',
            speciesType: 'BBBBBB',
            commonName: 'BBBBBB',
            latinName: 'BBBBBB',
            description: 'BBBBBB',
            isIndigenous: true,
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

      it('should delete a Species', () => {
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
