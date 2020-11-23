import { ICompetitors } from 'app/shared/model/competitors.model';
import { ActionType } from 'app/shared/model/enumerations/action-type.model';

export interface ICompetitorAction {
  id?: number;
  action?: ActionType;
  competitors?: ICompetitors[];
}

export class CompetitorAction implements ICompetitorAction {
  constructor(public id?: number, public action?: ActionType, public competitors?: ICompetitors[]) {}
}
