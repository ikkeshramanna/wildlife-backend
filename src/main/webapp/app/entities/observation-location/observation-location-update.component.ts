import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IObservationLocation, ObservationLocation } from 'app/shared/model/observation-location.model';
import { ObservationLocationService } from './observation-location.service';

@Component({
  selector: 'jhi-observation-location-update',
  templateUrl: './observation-location-update.component.html',
})
export class ObservationLocationUpdateComponent implements OnInit {
  isSaving = false;
  addDateDp: any;
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    gpsLatitude: [],
    gpsCoordinate: [],
    locationName: [],
    description: [],
    addDate: [],
    updateDate: [],
  });

  constructor(
    protected observationLocationService: ObservationLocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observationLocation }) => {
      this.updateForm(observationLocation);
    });
  }

  updateForm(observationLocation: IObservationLocation): void {
    this.editForm.patchValue({
      id: observationLocation.id,
      name: observationLocation.name,
      gpsLatitude: observationLocation.gpsLatitude,
      gpsCoordinate: observationLocation.gpsCoordinate,
      locationName: observationLocation.locationName,
      description: observationLocation.description,
      addDate: observationLocation.addDate,
      updateDate: observationLocation.updateDate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observationLocation = this.createFromForm();
    if (observationLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.observationLocationService.update(observationLocation));
    } else {
      this.subscribeToSaveResponse(this.observationLocationService.create(observationLocation));
    }
  }

  private createFromForm(): IObservationLocation {
    return {
      ...new ObservationLocation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      gpsLatitude: this.editForm.get(['gpsLatitude'])!.value,
      gpsCoordinate: this.editForm.get(['gpsCoordinate'])!.value,
      locationName: this.editForm.get(['locationName'])!.value,
      description: this.editForm.get(['description'])!.value,
      addDate: this.editForm.get(['addDate'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservationLocation>>): void {
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
}
