import { Moment } from 'moment';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { ChicKStatusType } from 'app/shared/model/enumerations/chic-k-status-type.model';
import { ChickConditionType } from 'app/shared/model/enumerations/chick-condition-type.model';

export interface IChick {
  id?: number;
  chickNumber?: number;
  hatchDate?: Moment;
  chickStatus?: ChicKStatusType;
  chickAge?: number;
  chickActive?: string;
  chickCondition?: ChickConditionType;
  chickRinged?: string;
  bloodSample?: string;
  eggsAndChick?: IEggsAndChick;
}

export class Chick implements IChick {
  constructor(
    public id?: number,
    public chickNumber?: number,
    public hatchDate?: Moment,
    public chickStatus?: ChicKStatusType,
    public chickAge?: number,
    public chickActive?: string,
    public chickCondition?: ChickConditionType,
    public chickRinged?: string,
    public bloodSample?: string,
    public eggsAndChick?: IEggsAndChick
  ) {}
}
