import { ISighting } from 'app/shared/model/sighting.model';
import { MkType } from 'app/shared/model/enumerations/mk-type.model';
import { ReasonForCaptureType } from 'app/shared/model/enumerations/reason-for-capture-type.model';
import { CaptureMethodType } from 'app/shared/model/enumerations/capture-method-type.model';

export interface IRingingMorphs {
  id?: number;
  mkType?: MkType;
  reasonForCapture?: ReasonForCaptureType;
  captureMethod?: CaptureMethodType;
  name?: string;
  age?: number;
  weight?: number;
  head?: number;
  exposedCulmen?: number;
  culmenToSkull?: number;
  wing?: number;
  p8?: number;
  p8Brush?: number;
  tail?: number;
  tailBrush?: number;
  tarsusLength?: number;
  comments?: string;
  sighting?: ISighting;
}

export class RingingMorphs implements IRingingMorphs {
  constructor(
    public id?: number,
    public mkType?: MkType,
    public reasonForCapture?: ReasonForCaptureType,
    public captureMethod?: CaptureMethodType,
    public name?: string,
    public age?: number,
    public weight?: number,
    public head?: number,
    public exposedCulmen?: number,
    public culmenToSkull?: number,
    public wing?: number,
    public p8?: number,
    public p8Brush?: number,
    public tail?: number,
    public tailBrush?: number,
    public tarsusLength?: number,
    public comments?: string,
    public sighting?: ISighting
  ) {}
}
