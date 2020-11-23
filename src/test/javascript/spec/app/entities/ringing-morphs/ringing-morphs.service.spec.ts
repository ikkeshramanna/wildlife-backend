import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RingingMorphsService } from 'app/entities/ringing-morphs/ringing-morphs.service';
import { IRingingMorphs, RingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { MkType } from 'app/shared/model/enumerations/mk-type.model';
import { ReasonForCaptureType } from 'app/shared/model/enumerations/reason-for-capture-type.model';
import { CaptureMethodType } from 'app/shared/model/enumerations/capture-method-type.model';

describe('Service Tests', () => {
  describe('RingingMorphs Service', () => {
    let injector: TestBed;
    let service: RingingMorphsService;
    let httpMock: HttpTestingController;
    let elemDefault: IRingingMorphs;
    let expectedResult: IRingingMorphs | IRingingMorphs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RingingMorphsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RingingMorphs(
        0,
        MkType.CHICK,
        ReasonForCaptureType.CHICK_RINGING,
        CaptureMethodType.NESTLING,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
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

      it('should create a RingingMorphs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RingingMorphs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RingingMorphs', () => {
        const returnedFromService = Object.assign(
          {
            mkType: 'BBBBBB',
            reasonForCapture: 'BBBBBB',
            captureMethod: 'BBBBBB',
            name: 'BBBBBB',
            age: 1,
            weight: 1,
            head: 1,
            exposedCulmen: 1,
            culmenToSkull: 1,
            wing: 1,
            p8: 1,
            p8Brush: 1,
            tail: 1,
            tailBrush: 1,
            tarsusLength: 1,
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

      it('should return a list of RingingMorphs', () => {
        const returnedFromService = Object.assign(
          {
            mkType: 'BBBBBB',
            reasonForCapture: 'BBBBBB',
            captureMethod: 'BBBBBB',
            name: 'BBBBBB',
            age: 1,
            weight: 1,
            head: 1,
            exposedCulmen: 1,
            culmenToSkull: 1,
            wing: 1,
            p8: 1,
            p8Brush: 1,
            tail: 1,
            tailBrush: 1,
            tarsusLength: 1,
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

      it('should delete a RingingMorphs', () => {
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
