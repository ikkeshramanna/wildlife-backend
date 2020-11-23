import { ISighting } from 'app/shared/model/sighting.model';
import { VisitPurposeType } from 'app/shared/model/enumerations/visit-purpose-type.model';
import { UseSignType } from 'app/shared/model/enumerations/use-sign-type.model';

export interface INestSiteOverview {
  id?: number;
  numberPeople?: number;
  purposeOfVisit?: VisitPurposeType;
  signsOfUse?: UseSignType;
  nestingSubstrate?: string;
  maintenanceDone?: string;
  maintenanceRequired?: string;
  comments?: string;
  sighting?: ISighting;
}

export class NestSiteOverview implements INestSiteOverview {
  constructor(
    public id?: number,
    public numberPeople?: number,
    public purposeOfVisit?: VisitPurposeType,
    public signsOfUse?: UseSignType,
    public nestingSubstrate?: string,
    public maintenanceDone?: string,
    public maintenanceRequired?: string,
    public comments?: string,
    public sighting?: ISighting
  ) {}
}
