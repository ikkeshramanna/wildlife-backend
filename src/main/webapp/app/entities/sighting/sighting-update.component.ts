import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISighting, Sighting } from 'app/shared/model/sighting.model';
import { SightingService } from './sighting.service';
import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';
import { BirdsIdentifiedService } from 'app/entities/birds-identified/birds-identified.service';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from 'app/entities/eggs-and-chick/eggs-and-chick.service';
import { IFeedingObservation } from 'app/shared/model/feeding-observation.model';
import { FeedingObservationService } from 'app/entities/feeding-observation/feeding-observation.service';
import { INestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { NestSiteOverviewService } from 'app/entities/nest-site-overview/nest-site-overview.service';
import { IMaintenance } from 'app/shared/model/maintenance.model';
import { MaintenanceService } from 'app/entities/maintenance/maintenance.service';
import { IRingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { RingingMorphsService } from 'app/entities/ringing-morphs/ringing-morphs.service';
import { IObservationLocation } from 'app/shared/model/observation-location.model';
import { ObservationLocationService } from 'app/entities/observation-location/observation-location.service';
import { ISpecies } from 'app/shared/model/species.model';
import { SpeciesService } from 'app/entities/species/species.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { TaggedAnimalService } from 'app/entities/tagged-animal/tagged-animal.service';

type SelectableEntity =
  | IBirdsIdentified
  | IEggsAndChick
  | IFeedingObservation
  | INestSiteOverview
  | IMaintenance
  | IRingingMorphs
  | IObservationLocation
  | ISpecies
  | IEmployee
  | ITaggedAnimal;

@Component({
  selector: 'jhi-sighting-update',
  templateUrl: './sighting-update.component.html',
})
export class SightingUpdateComponent implements OnInit {
  isSaving = false;
  birdsidentifieds: IBirdsIdentified[] = [];
  eggsandchicks: IEggsAndChick[] = [];
  feedingobservations: IFeedingObservation[] = [];
  nestsiteoverviews: INestSiteOverview[] = [];
  maintenances: IMaintenance[] = [];
  ringingmorphs: IRingingMorphs[] = [];
  observationlocations: IObservationLocation[] = [];
  species: ISpecies[] = [];
  employees: IEmployee[] = [];
  taggedanimals: ITaggedAnimal[] = [];
  dateDp: any;
  addDateDp: any;
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    nestSite: [null, [Validators.required]],
    area: [null, [Validators.required]],
    nestType: [],
    year: [],
    month: [],
    date: [],
    observer: [null, [Validators.required]],
    gpsLatitude: [],
    gpsCoordinate: [],
    locationName: [],
    addDate: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
    birdsIdentified: [],
    eggsAndChick: [],
    feedingObservation: [],
    nestSiteOverview: [],
    maintenance: [],
    ringingMorphs: [],
    observationLocation: [],
    species: [],
    employee: [],
    taggedAnimal: [],
  });

  constructor(
    protected sightingService: SightingService,
    protected birdsIdentifiedService: BirdsIdentifiedService,
    protected eggsAndChickService: EggsAndChickService,
    protected feedingObservationService: FeedingObservationService,
    protected nestSiteOverviewService: NestSiteOverviewService,
    protected maintenanceService: MaintenanceService,
    protected ringingMorphsService: RingingMorphsService,
    protected observationLocationService: ObservationLocationService,
    protected speciesService: SpeciesService,
    protected employeeService: EmployeeService,
    protected taggedAnimalService: TaggedAnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sighting }) => {
      this.updateForm(sighting);

      this.birdsIdentifiedService
        .query({ filter: 'sighting-is-null' })
        .pipe(
          map((res: HttpResponse<IBirdsIdentified[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBirdsIdentified[]) => {
          if (!sighting.birdsIdentified || !sighting.birdsIdentified.id) {
            this.birdsidentifieds = resBody;
          } else {
            this.birdsIdentifiedService
              .find(sighting.birdsIdentified.id)
              .pipe(
                map((subRes: HttpResponse<IBirdsIdentified>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBirdsIdentified[]) => (this.birdsidentifieds = concatRes));
          }
        });

      this.eggsAndChickService
        .query({ filter: 'sighting-is-null' })
        .pipe(
          map((res: HttpResponse<IEggsAndChick[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEggsAndChick[]) => {
          if (!sighting.eggsAndChick || !sighting.eggsAndChick.id) {
            this.eggsandchicks = resBody;
          } else {
            this.eggsAndChickService
              .find(sighting.eggsAndChick.id)
              .pipe(
                map((subRes: HttpResponse<IEggsAndChick>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEggsAndChick[]) => (this.eggsandchicks = concatRes));
          }
        });

      this.feedingObservationService
        .query({ filter: 'sighting-is-null' })
        .pipe(
          map((res: HttpResponse<IFeedingObservation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IFeedingObservation[]) => {
          if (!sighting.feedingObservation || !sighting.feedingObservation.id) {
            this.feedingobservations = resBody;
          } else {
            this.feedingObservationService
              .find(sighting.feedingObservation.id)
              .pipe(
                map((subRes: HttpResponse<IFeedingObservation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IFeedingObservation[]) => (this.feedingobservations = concatRes));
          }
        });

      this.nestSiteOverviewService
        .query({ filter: 'sighting-is-null' })
        .pipe(
          map((res: HttpResponse<INestSiteOverview[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: INestSiteOverview[]) => {
          if (!sighting.nestSiteOverview || !sighting.nestSiteOverview.id) {
            this.nestsiteoverviews = resBody;
          } else {
            this.nestSiteOverviewService
              .find(sighting.nestSiteOverview.id)
              .pipe(
                map((subRes: HttpResponse<INestSiteOverview>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: INestSiteOverview[]) => (this.nestsiteoverviews = concatRes));
          }
        });

      this.maintenanceService
        .query({ filter: 'sighting-is-null' })
        .pipe(
          map((res: HttpResponse<IMaintenance[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMaintenance[]) => {
          if (!sighting.maintenance || !sighting.maintenance.id) {
            this.maintenances = resBody;
          } else {
            this.maintenanceService
              .find(sighting.maintenance.id)
              .pipe(
                map((subRes: HttpResponse<IMaintenance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMaintenance[]) => (this.maintenances = concatRes));
          }
        });

      this.ringingMorphsService
        .query({ filter: 'sighting-is-null' })
        .pipe(
          map((res: HttpResponse<IRingingMorphs[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRingingMorphs[]) => {
          if (!sighting.ringingMorphs || !sighting.ringingMorphs.id) {
            this.ringingmorphs = resBody;
          } else {
            this.ringingMorphsService
              .find(sighting.ringingMorphs.id)
              .pipe(
                map((subRes: HttpResponse<IRingingMorphs>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRingingMorphs[]) => (this.ringingmorphs = concatRes));
          }
        });

      this.observationLocationService
        .query()
        .subscribe((res: HttpResponse<IObservationLocation[]>) => (this.observationlocations = res.body || []));

      this.speciesService.query().subscribe((res: HttpResponse<ISpecies[]>) => (this.species = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.taggedAnimalService.query().subscribe((res: HttpResponse<ITaggedAnimal[]>) => (this.taggedanimals = res.body || []));
    });
  }

  updateForm(sighting: ISighting): void {
    this.editForm.patchValue({
      id: sighting.id,
      nestSite: sighting.nestSite,
      area: sighting.area,
      nestType: sighting.nestType,
      year: sighting.year,
      month: sighting.month,
      date: sighting.date,
      observer: sighting.observer,
      gpsLatitude: sighting.gpsLatitude,
      gpsCoordinate: sighting.gpsCoordinate,
      locationName: sighting.locationName,
      addDate: sighting.addDate,
      updateDate: sighting.updateDate,
      birdsIdentified: sighting.birdsIdentified,
      eggsAndChick: sighting.eggsAndChick,
      feedingObservation: sighting.feedingObservation,
      nestSiteOverview: sighting.nestSiteOverview,
      maintenance: sighting.maintenance,
      ringingMorphs: sighting.ringingMorphs,
      observationLocation: sighting.observationLocation,
      species: sighting.species,
      employee: sighting.employee,
      taggedAnimal: sighting.taggedAnimal,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sighting = this.createFromForm();
    if (sighting.id !== undefined) {
      this.subscribeToSaveResponse(this.sightingService.update(sighting));
    } else {
      this.subscribeToSaveResponse(this.sightingService.create(sighting));
    }
  }

  private createFromForm(): ISighting {
    return {
      ...new Sighting(),
      id: this.editForm.get(['id'])!.value,
      nestSite: this.editForm.get(['nestSite'])!.value,
      area: this.editForm.get(['area'])!.value,
      nestType: this.editForm.get(['nestType'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      date: this.editForm.get(['date'])!.value,
      observer: this.editForm.get(['observer'])!.value,
      gpsLatitude: this.editForm.get(['gpsLatitude'])!.value,
      gpsCoordinate: this.editForm.get(['gpsCoordinate'])!.value,
      locationName: this.editForm.get(['locationName'])!.value,
      addDate: this.editForm.get(['addDate'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value,
      birdsIdentified: this.editForm.get(['birdsIdentified'])!.value,
      eggsAndChick: this.editForm.get(['eggsAndChick'])!.value,
      feedingObservation: this.editForm.get(['feedingObservation'])!.value,
      nestSiteOverview: this.editForm.get(['nestSiteOverview'])!.value,
      maintenance: this.editForm.get(['maintenance'])!.value,
      ringingMorphs: this.editForm.get(['ringingMorphs'])!.value,
      observationLocation: this.editForm.get(['observationLocation'])!.value,
      species: this.editForm.get(['species'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      taggedAnimal: this.editForm.get(['taggedAnimal'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISighting>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
