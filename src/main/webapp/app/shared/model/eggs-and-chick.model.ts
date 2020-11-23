import { IEgg } from 'app/shared/model/egg.model';
import { IChick } from 'app/shared/model/chick.model';
import { ISighting } from 'app/shared/model/sighting.model';

export interface IEggsAndChick {
  id?: number;
  clutchNumber?: string;
  eggsPresent?: boolean;
  chicksPresent?: boolean;
  photoTaken?: string;
  comments?: string;
  eggs?: IEgg[];
  chicks?: IChick[];
  sighting?: ISighting;
}

export class EggsAndChick implements IEggsAndChick {
  constructor(
    public id?: number,
    public clutchNumber?: string,
    public eggsPresent?: boolean,
    public chicksPresent?: boolean,
    public photoTaken?: string,
    public comments?: string,
    public eggs?: IEgg[],
    public chicks?: IChick[],
    public sighting?: ISighting
  ) {
    this.eggsPresent = this.eggsPresent || false;
    this.chicksPresent = this.chicksPresent || false;
  }
}
