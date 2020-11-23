import { IBirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { ISighting } from 'app/shared/model/sighting.model';
import { SeenDuringType } from 'app/shared/model/enumerations/seen-during-type.model';
import { BirdType } from 'app/shared/model/enumerations/bird-type.model';
import { SexType } from 'app/shared/model/enumerations/sex-type.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { BirdLocationType } from 'app/shared/model/enumerations/bird-location-type.model';

export interface IBirdsIdentified {
  id?: number;
  seenDuring?: SeenDuringType;
  type?: BirdType;
  sex?: SexType;
  status?: StatusType;
  birdLocation?: BirdLocationType;
  southing?: string;
  easting?: string;
  comments?: string;
  colorComboL?: string;
  colorComboR?: string;
  birdIRN?: string;
  birdBehaviours?: IBirdBehaviour[];
  sighting?: ISighting;
}

export class BirdsIdentified implements IBirdsIdentified {
  constructor(
    public id?: number,
    public seenDuring?: SeenDuringType,
    public type?: BirdType,
    public sex?: SexType,
    public status?: StatusType,
    public birdLocation?: BirdLocationType,
    public southing?: string,
    public easting?: string,
    public comments?: string,
    public colorComboL?: string,
    public colorComboR?: string,
    public birdIRN?: string,
    public birdBehaviours?: IBirdBehaviour[],
    public sighting?: ISighting
  ) {}
}
