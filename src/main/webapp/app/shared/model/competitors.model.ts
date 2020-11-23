import { ISpecies } from 'app/shared/model/species.model';
import { ICompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { ICompetitorAction } from 'app/shared/model/competitor-action.model';
import { ISighting } from 'app/shared/model/sighting.model';
import { CompetitorBehaviourType } from 'app/shared/model/enumerations/competitor-behaviour-type.model';
import { CompetitorLocationType } from 'app/shared/model/enumerations/competitor-location-type.model';

export interface ICompetitors {
  id?: number;
  mkAround?: string;
  noOfIndividuals?: number;
  competitorBehaviour?: CompetitorBehaviourType;
  competitorLocation?: CompetitorLocationType;
  competitorSpecies?: ISpecies;
  competitorImpactOnMks?: ICompetitorImpactOnMk[];
  competitorActions?: ICompetitorAction[];
  sighting?: ISighting;
}

export class Competitors implements ICompetitors {
  constructor(
    public id?: number,
    public mkAround?: string,
    public noOfIndividuals?: number,
    public competitorBehaviour?: CompetitorBehaviourType,
    public competitorLocation?: CompetitorLocationType,
    public competitorSpecies?: ISpecies,
    public competitorImpactOnMks?: ICompetitorImpactOnMk[],
    public competitorActions?: ICompetitorAction[],
    public sighting?: ISighting
  ) {}
}
