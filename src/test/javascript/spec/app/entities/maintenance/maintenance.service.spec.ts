import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MaintenanceService } from 'app/entities/maintenance/maintenance.service';
import { IMaintenance, Maintenance } from 'app/shared/model/maintenance.model';
import { StrutType } from 'app/shared/model/enumerations/strut-type.model';
import { BoxConditionType } from 'app/shared/model/enumerations/box-condition-type.model';
import { BeePlasticType } from 'app/shared/model/enumerations/bee-plastic-type.model';
import { HatchType } from 'app/shared/model/enumerations/hatch-type.model';
import { StepsType } from 'app/shared/model/enumerations/steps-type.model';
import { HandHoldsType } from 'app/shared/model/enumerations/hand-holds-type.model';
import { ClearingType } from 'app/shared/model/enumerations/clearing-type.model';
import { PathType } from 'app/shared/model/enumerations/path-type.model';
import { SiteDescriptionType } from 'app/shared/model/enumerations/site-description-type.model';
import { SideType } from 'app/shared/model/enumerations/side-type.model';
import { TreeSpeciesType } from 'app/shared/model/enumerations/tree-species-type.model';

describe('Service Tests', () => {
  describe('Maintenance Service', () => {
    let injector: TestBed;
    let service: MaintenanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IMaintenance;
    let expectedResult: IMaintenance | IMaintenance[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MaintenanceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Maintenance(
        0,
        StrutType.GOOD,
        BoxConditionType.GOOD,
        BeePlasticType.PRESENT,
        HatchType.GOOD,
        StepsType.ADDED_1,
        HandHoldsType.ADDED_1,
        'AAAAAAA',
        'AAAAAAA',
        ClearingType.GOOD,
        PathType.GOOD,
        'AAAAAAA',
        'AAAAAAA',
        SiteDescriptionType.ACCURATE,
        'AAAAAAA',
        SideType.RIGHT,
        TreeSpeciesType.CAN_PAN,
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

      it('should create a Maintenance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Maintenance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Maintenance', () => {
        const returnedFromService = Object.assign(
          {
            struts: 'BBBBBB',
            boxCondition: 'BBBBBB',
            beePlastic: 'BBBBBB',
            hatch: 'BBBBBB',
            steps: 'BBBBBB',
            handholds: 'BBBBBB',
            treeNeedsReplacing: 'BBBBBB',
            boxNeedsReplacing: 'BBBBBB',
            clearing: 'BBBBBB',
            path: 'BBBBBB',
            additionalVisit: 'BBBBBB',
            drillRequired: 'BBBBBB',
            siteDescription: 'BBBBBB',
            bearing: 'BBBBBB',
            side: 'BBBBBB',
            treeSpecies: 'BBBBBB',
            height: 1,
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

      it('should return a list of Maintenance', () => {
        const returnedFromService = Object.assign(
          {
            struts: 'BBBBBB',
            boxCondition: 'BBBBBB',
            beePlastic: 'BBBBBB',
            hatch: 'BBBBBB',
            steps: 'BBBBBB',
            handholds: 'BBBBBB',
            treeNeedsReplacing: 'BBBBBB',
            boxNeedsReplacing: 'BBBBBB',
            clearing: 'BBBBBB',
            path: 'BBBBBB',
            additionalVisit: 'BBBBBB',
            drillRequired: 'BBBBBB',
            siteDescription: 'BBBBBB',
            bearing: 'BBBBBB',
            side: 'BBBBBB',
            treeSpecies: 'BBBBBB',
            height: 1,
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

      it('should delete a Maintenance', () => {
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
