import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TaggedAnimalService } from 'app/entities/tagged-animal/tagged-animal.service';
import { ITaggedAnimal, TaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { TagType } from 'app/shared/model/enumerations/tag-type.model';
import { TaggedAnimalSexType } from 'app/shared/model/enumerations/tagged-animal-sex-type.model';

describe('Service Tests', () => {
  describe('TaggedAnimal Service', () => {
    let injector: TestBed;
    let service: TaggedAnimalService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaggedAnimal;
    let expectedResult: ITaggedAnimal | ITaggedAnimal[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TaggedAnimalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TaggedAnimal(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        TagType.CHIP,
        currentDate,
        TaggedAnimalSexType.MALE,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT),
            dateOfTagging: currentDate.format(DATE_FORMAT),
            dateTime: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TaggedAnimal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT),
            dateOfTagging: currentDate.format(DATE_FORMAT),
            dateTime: currentDate.format(DATE_FORMAT),
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            dateOfTagging: currentDate,
            dateTime: currentDate,
            updateDate: currentDate,
          },
          returnedFromService
        );

        service.create(new TaggedAnimal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaggedAnimal', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            dateOfTagging: currentDate.format(DATE_FORMAT),
            physicalTraits: 'BBBBBB',
            tagType: 'BBBBBB',
            dateTime: currentDate.format(DATE_FORMAT),
            sexType: 'BBBBBB',
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            dateOfTagging: currentDate,
            dateTime: currentDate,
            updateDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TaggedAnimal', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            dateOfTagging: currentDate.format(DATE_FORMAT),
            physicalTraits: 'BBBBBB',
            tagType: 'BBBBBB',
            dateTime: currentDate.format(DATE_FORMAT),
            sexType: 'BBBBBB',
            updateDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
            dateOfTagging: currentDate,
            dateTime: currentDate,
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

      it('should delete a TaggedAnimal', () => {
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
