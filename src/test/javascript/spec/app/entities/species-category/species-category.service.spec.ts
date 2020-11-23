import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SpeciesCategoryService } from 'app/entities/species-category/species-category.service';
import { ISpeciesCategory, SpeciesCategory } from 'app/shared/model/species-category.model';
import { SpeciesCategoryType } from 'app/shared/model/enumerations/species-category-type.model';

describe('Service Tests', () => {
  describe('SpeciesCategory Service', () => {
    let injector: TestBed;
    let service: SpeciesCategoryService;
    let httpMock: HttpTestingController;
    let elemDefault: ISpeciesCategory;
    let expectedResult: ISpeciesCategory | ISpeciesCategory[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SpeciesCategoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SpeciesCategory(
        0,
        'AAAAAAA',
        SpeciesCategoryType.FAUNA,
        'image/png',
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

      it('should create a SpeciesCategory', () => {
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

        service.create(new SpeciesCategory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SpeciesCategory', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            speciesCategoryType: 'BBBBBB',
            picture: 'BBBBBB',
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

      it('should return a list of SpeciesCategory', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            speciesCategoryType: 'BBBBBB',
            picture: 'BBBBBB',
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

      it('should delete a SpeciesCategory', () => {
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
