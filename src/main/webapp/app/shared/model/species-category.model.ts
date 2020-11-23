import { Moment } from 'moment';
import { SpeciesCategoryType } from 'app/shared/model/enumerations/species-category-type.model';

export interface ISpeciesCategory {
  id?: number;
  name?: string;
  speciesCategoryType?: SpeciesCategoryType;
  pictureContentType?: string;
  picture?: any;
  description?: string;
  addDate?: Moment;
  updateDate?: Moment;
}

export class SpeciesCategory implements ISpeciesCategory {
  constructor(
    public id?: number,
    public name?: string,
    public speciesCategoryType?: SpeciesCategoryType,
    public pictureContentType?: string,
    public picture?: any,
    public description?: string,
    public addDate?: Moment,
    public updateDate?: Moment
  ) {}
}
