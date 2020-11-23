import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BirdsIdentifiedService } from 'app/entities/birds-identified/birds-identified.service';
import { IBirdsIdentified, BirdsIdentified } from 'app/shared/model/birds-identified.model';
import { SeenDuringType } from 'app/shared/model/enumerations/seen-during-type.model';
import { BirdType } from 'app/shared/model/enumerations/bird-type.model';
import { SexType } from 'app/shared/model/enumerations/sex-type.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { BirdLocationType } from 'app/shared/model/enumerations/bird-location-type.model';

describe('Service Tests', () => {
  describe('BirdsIdentified Service', () => {
    let injector: TestBed;
    let service: BirdsIdentifiedService;
    let httpMock: HttpTestingController;
    let elemDefault: IBirdsIdentified;
    let expectedResult: IBirdsIdentified | IBirdsIdentified[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BirdsIdentifiedService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BirdsIdentified(
        0,
        SeenDuringType.OPPORTUNISTIC,
        BirdType.UNKNOWN,
        SexType.MALE,
        StatusType.MISSING,
        BirdLocationType.IN_BOX_CAVITY,
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a BirdsIdentified', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BirdsIdentified()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BirdsIdentified', () => {
        const returnedFromService = Object.assign(
          {
            seenDuring: 'BBBBBB',
            type: 'BBBBBB',
            sex: 'BBBBBB',
            status: 'BBBBBB',
            birdLocation: 'BBBBBB',
            southing: 'BBBBBB',
            easting: 'BBBBBB',
            comments: 'BBBBBB',
            colorComboL: 'BBBBBB',
            colorComboR: 'BBBBBB',
            birdIRN: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BirdsIdentified', () => {
        const returnedFromService = Object.assign(
          {
            seenDuring: 'BBBBBB',
            type: 'BBBBBB',
            sex: 'BBBBBB',
            status: 'BBBBBB',
            birdLocation: 'BBBBBB',
            southing: 'BBBBBB',
            easting: 'BBBBBB',
            comments: 'BBBBBB',
            colorComboL: 'BBBBBB',
            colorComboR: 'BBBBBB',
            birdIRN: 'BBBBBB',
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

      it('should delete a BirdsIdentified', () => {
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
