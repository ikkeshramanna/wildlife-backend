import { Moment } from 'moment';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggStatusType } from 'app/shared/model/enumerations/egg-status-type.model';

export interface IEgg {
  id?: number;
  eggNumber?: string;
  eggStatus?: EggStatusType;
  eggLayDate?: Moment;
  eggsAndChick?: IEggsAndChick;
}

export class Egg implements IEgg {
  constructor(
    public id?: number,
    public eggNumber?: string,
    public eggStatus?: EggStatusType,
    public eggLayDate?: Moment,
    public eggsAndChick?: IEggsAndChick
  ) {}
}
