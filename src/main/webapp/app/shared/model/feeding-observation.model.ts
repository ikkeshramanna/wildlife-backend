import { ISighting } from 'app/shared/model/sighting.model';
import { FeedingObservationType } from 'app/shared/model/enumerations/feeding-observation-type.model';
import { BreedAttemptType } from 'app/shared/model/enumerations/breed-attempt-type.model';
import { BreedingStageType } from 'app/shared/model/enumerations/breeding-stage-type.model';
import { BreedingOutcomeType } from 'app/shared/model/enumerations/breeding-outcome-type.model';
import { PreyItemType } from 'app/shared/model/enumerations/prey-item-type.model';
import { PreySpeciesType } from 'app/shared/model/enumerations/prey-species-type.model';
import { CloudType } from 'app/shared/model/enumerations/cloud-type.model';
import { WindType } from 'app/shared/model/enumerations/wind-type.model';
import { RainType } from 'app/shared/model/enumerations/rain-type.model';
import { FeedingObservationOutcomeType } from 'app/shared/model/enumerations/feeding-observation-outcome-type.model';

export interface IFeedingObservation {
  id?: number;
  noFieldWorkers?: number;
  type?: FeedingObservationType;
  breedingAttempt?: BreedAttemptType;
  breedingStage?: BreedingStageType;
  breedingOutcome?: BreedingOutcomeType;
  preyItem?: PreyItemType;
  preySpecies?: PreySpeciesType;
  time?: string;
  cloud?: CloudType;
  wind?: WindType;
  rain?: RainType;
  outcome?: FeedingObservationOutcomeType;
  comment?: string;
  sighting?: ISighting;
}

export class FeedingObservation implements IFeedingObservation {
  constructor(
    public id?: number,
    public noFieldWorkers?: number,
    public type?: FeedingObservationType,
    public breedingAttempt?: BreedAttemptType,
    public breedingStage?: BreedingStageType,
    public breedingOutcome?: BreedingOutcomeType,
    public preyItem?: PreyItemType,
    public preySpecies?: PreySpeciesType,
    public time?: string,
    public cloud?: CloudType,
    public wind?: WindType,
    public rain?: RainType,
    public outcome?: FeedingObservationOutcomeType,
    public comment?: string,
    public sighting?: ISighting
  ) {}
}
