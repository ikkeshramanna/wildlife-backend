import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';

export interface IEmployee {
  id?: number;
  name?: string;
  surname?: string;
  dob?: Moment;
  address?: string;
  email?: string;
  addDate?: Moment;
  updateDate?: Moment;
  mobileNumber?: string;
  country?: ICountry;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public dob?: Moment,
    public address?: string,
    public email?: string,
    public addDate?: Moment,
    public updateDate?: Moment,
    public mobileNumber?: string,
    public country?: ICountry
  ) {}
}
