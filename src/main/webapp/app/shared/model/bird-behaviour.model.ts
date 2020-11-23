import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';
import { BirdBehaviourType } from 'app/shared/model/enumerations/bird-behaviour-type.model';

export interface IBirdBehaviour {
  id?: number;
  behaviour?: BirdBehaviourType;
  birdsIdentifieds?: IBirdsIdentified[];
}

export class BirdBehaviour implements IBirdBehaviour {
  constructor(public id?: number, public behaviour?: BirdBehaviourType, public birdsIdentifieds?: IBirdsIdentified[]) {}
}
