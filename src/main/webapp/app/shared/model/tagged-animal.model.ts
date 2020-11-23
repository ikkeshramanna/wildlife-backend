import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { ISpecies } from 'app/shared/model/species.model';
import { TagType } from 'app/shared/model/enumerations/tag-type.model';
import { TaggedAnimalSexType } from 'app/shared/model/enumerations/tagged-animal-sex-type.model';

export interface ITaggedAnimal {
  id?: number;
  name?: string;
  dateOfBirth?: Moment;
  dateOfTagging?: Moment;
  physicalTraits?: string;
  tagType?: TagType;
  dateTime?: Moment;
  sexType?: TaggedAnimalSexType;
  updateDate?: Moment;
  taggedAnimal?: IEmployee;
  species?: ISpecies;
}

export class TaggedAnimal implements ITaggedAnimal {
  constructor(
    public id?: number,
    public name?: string,
    public dateOfBirth?: Moment,
    public dateOfTagging?: Moment,
    public physicalTraits?: string,
    public tagType?: TagType,
    public dateTime?: Moment,
    public sexType?: TaggedAnimalSexType,
    public updateDate?: Moment,
    public taggedAnimal?: IEmployee,
    public species?: ISpecies
  ) {}
}
