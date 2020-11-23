import { Moment } from 'moment';
import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { ISpeciesCategory } from 'app/shared/model/species-category.model';
import { ICountry } from 'app/shared/model/country.model';
import { FeedingTraitType } from 'app/shared/model/enumerations/feeding-trait-type.model';

export interface ISpecies {
  id?: number;
  pictureContentType?: string;
  picture?: any;
  feedingTraitType?: FeedingTraitType;
  speciesType?: string;
  commonName?: string;
  latinName?: string;
  description?: string;
  isIndigenous?: boolean;
  addDate?: Moment;
  updateDate?: Moment;
  taggedAnimals?: ITaggedAnimal[];
  speciesCategory?: ISpeciesCategory;
  countries?: ICountry[];
}

export class Species implements ISpecies {
  constructor(
    public id?: number,
    public pictureContentType?: string,
    public picture?: any,
    public feedingTraitType?: FeedingTraitType,
    public speciesType?: string,
    public commonName?: string,
    public latinName?: string,
    public description?: string,
    public isIndigenous?: boolean,
    public addDate?: Moment,
    public updateDate?: Moment,
    public taggedAnimals?: ITaggedAnimal[],
    public speciesCategory?: ISpeciesCategory,
    public countries?: ICountry[]
  ) {
    this.isIndigenous = this.isIndigenous || false;
  }
}
