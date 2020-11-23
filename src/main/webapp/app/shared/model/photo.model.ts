import { Moment } from 'moment';
import { ISighting } from 'app/shared/model/sighting.model';

export interface IPhoto {
  id?: number;
  title?: string;
  description?: string;
  imageContentType?: string;
  image?: any;
  addDate?: Moment;
  updateDate?: Moment;
  sighting?: ISighting;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public imageContentType?: string,
    public image?: any,
    public addDate?: Moment,
    public updateDate?: Moment,
    public sighting?: ISighting
  ) {}
}
