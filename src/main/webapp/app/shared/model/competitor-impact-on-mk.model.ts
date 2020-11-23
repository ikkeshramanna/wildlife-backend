import { ICompetitors } from 'app/shared/model/competitors.model';
import { ImpactOnMKType } from 'app/shared/model/enumerations/impact-on-mk-type.model';

export interface ICompetitorImpactOnMk {
  id?: number;
  impact?: ImpactOnMKType;
  competitors?: ICompetitors[];
}

export class CompetitorImpactOnMk implements ICompetitorImpactOnMk {
  constructor(public id?: number, public impact?: ImpactOnMKType, public competitors?: ICompetitors[]) {}
}
