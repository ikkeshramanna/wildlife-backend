import { Moment } from 'moment';

export interface IObservationLocation {
  id?: number;
  name?: string;
  gpsLatitude?: string;
  gpsCoordinate?: string;
  locationName?: string;
  description?: string;
  addDate?: Moment;
  updateDate?: Moment;
}

export class ObservationLocation implements IObservationLocation {
  constructor(
    public id?: number,
    public name?: string,
    public gpsLatitude?: string,
    public gpsCoordinate?: string,
    public locationName?: string,
    public description?: string,
    public addDate?: Moment,
    public updateDate?: Moment
  ) {}
}
