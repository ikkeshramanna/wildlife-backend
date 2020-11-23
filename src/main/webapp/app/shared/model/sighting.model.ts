import { Moment } from 'moment';
import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { IFeedingObservation } from 'app/shared/model/feeding-observation.model';
import { INestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { IMaintenance } from 'app/shared/model/maintenance.model';
import { IRingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { IPhoto } from 'app/shared/model/photo.model';
import { ICompetitors } from 'app/shared/model/competitors.model';
import { IObservationLocation } from 'app/shared/model/observation-location.model';
import { ISpecies } from 'app/shared/model/species.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { NestSiteType } from 'app/shared/model/enumerations/nest-site-type.model';
import { AreaType } from 'app/shared/model/enumerations/area-type.model';
import { NestType } from 'app/shared/model/enumerations/nest-type.model';

export interface ISighting {
  id?: number;
  nestSite?: NestSiteType;
  area?: AreaType;
  nestType?: NestType;
  year?: number;
  month?: number;
  date?: Moment;
  observer?: string;
  gpsLatitude?: string;
  gpsCoordinate?: string;
  locationName?: string;
  addDate?: Moment;
  updateDate?: Moment;
  birdsIdentified?: IBirdsIdentified;
  eggsAndChick?: IEggsAndChick;
  feedingObservation?: IFeedingObservation;
  nestSiteOverview?: INestSiteOverview;
  maintenance?: IMaintenance;
  ringingMorphs?: IRingingMorphs;
  photos?: IPhoto[];
  competitors?: ICompetitors[];
  observationLocation?: IObservationLocation;
  species?: ISpecies;
  employee?: IEmployee;
  taggedAnimal?: ITaggedAnimal;
}

export class Sighting implements ISighting {
  constructor(
    public id?: number,
    public nestSite?: NestSiteType,
    public area?: AreaType,
    public nestType?: NestType,
    public year?: number,
    public month?: number,
    public date?: Moment,
    public observer?: string,
    public gpsLatitude?: string,
    public gpsCoordinate?: string,
    public locationName?: string,
    public addDate?: Moment,
    public updateDate?: Moment,
    public birdsIdentified?: IBirdsIdentified,
    public eggsAndChick?: IEggsAndChick,
    public feedingObservation?: IFeedingObservation,
    public nestSiteOverview?: INestSiteOverview,
    public maintenance?: IMaintenance,
    public ringingMorphs?: IRingingMorphs,
    public photos?: IPhoto[],
    public competitors?: ICompetitors[],
    public observationLocation?: IObservationLocation,
    public species?: ISpecies,
    public employee?: IEmployee,
    public taggedAnimal?: ITaggedAnimal
  ) {}
}
