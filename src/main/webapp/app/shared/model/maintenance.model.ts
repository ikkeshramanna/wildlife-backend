import { ISighting } from 'app/shared/model/sighting.model';
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

export interface IMaintenance {
  id?: number;
  struts?: StrutType;
  boxCondition?: BoxConditionType;
  beePlastic?: BeePlasticType;
  hatch?: HatchType;
  steps?: StepsType;
  handholds?: HandHoldsType;
  treeNeedsReplacing?: string;
  boxNeedsReplacing?: string;
  clearing?: ClearingType;
  path?: PathType;
  additionalVisit?: string;
  drillRequired?: string;
  siteDescription?: SiteDescriptionType;
  bearing?: string;
  side?: SideType;
  treeSpecies?: TreeSpeciesType;
  height?: number;
  comments?: string;
  sighting?: ISighting;
}

export class Maintenance implements IMaintenance {
  constructor(
    public id?: number,
    public struts?: StrutType,
    public boxCondition?: BoxConditionType,
    public beePlastic?: BeePlasticType,
    public hatch?: HatchType,
    public steps?: StepsType,
    public handholds?: HandHoldsType,
    public treeNeedsReplacing?: string,
    public boxNeedsReplacing?: string,
    public clearing?: ClearingType,
    public path?: PathType,
    public additionalVisit?: string,
    public drillRequired?: string,
    public siteDescription?: SiteDescriptionType,
    public bearing?: string,
    public side?: SideType,
    public treeSpecies?: TreeSpeciesType,
    public height?: number,
    public comments?: string,
    public sighting?: ISighting
  ) {}
}
