import { Moment } from 'moment';
import { ISpecies } from 'app/shared/model/species.model';

export interface ICountry {
  id?: number;
  countryName?: string;
  addDate?: Moment;
  species?: ISpecies[];
}

export class Country implements ICountry {
  constructor(public id?: number, public countryName?: string, public addDate?: Moment, public species?: ISpecies[]) {}
}
