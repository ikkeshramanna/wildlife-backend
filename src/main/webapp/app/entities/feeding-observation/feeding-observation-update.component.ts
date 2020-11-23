import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFeedingObservation, FeedingObservation } from 'app/shared/model/feeding-observation.model';
import { FeedingObservationService } from './feeding-observation.service';

@Component({
  selector: 'jhi-feeding-observation-update',
  templateUrl: './feeding-observation-update.component.html',
})
export class FeedingObservationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    noFieldWorkers: [null, [Validators.required]],
    type: [null, [Validators.required]],
    breedingAttempt: [],
    breedingStage: [null, [Validators.required]],
    breedingOutcome: [],
    preyItem: [null, [Validators.required]],
    preySpecies: [null, [Validators.required]],
    time: [],
    cloud: [null, [Validators.required]],
    wind: [null, [Validators.required]],
    rain: [null, [Validators.required]],
    outcome: [null, [Validators.required]],
    comment: [],
  });

  constructor(
    protected feedingObservationService: FeedingObservationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedingObservation }) => {
      this.updateForm(feedingObservation);
    });
  }

  updateForm(feedingObservation: IFeedingObservation): void {
    this.editForm.patchValue({
      id: feedingObservation.id,
      noFieldWorkers: feedingObservation.noFieldWorkers,
      type: feedingObservation.type,
      breedingAttempt: feedingObservation.breedingAttempt,
      breedingStage: feedingObservation.breedingStage,
      breedingOutcome: feedingObservation.breedingOutcome,
      preyItem: feedingObservation.preyItem,
      preySpecies: feedingObservation.preySpecies,
      time: feedingObservation.time,
      cloud: feedingObservation.cloud,
      wind: feedingObservation.wind,
      rain: feedingObservation.rain,
      outcome: feedingObservation.outcome,
      comment: feedingObservation.comment,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedingObservation = this.createFromForm();
    if (feedingObservation.id !== undefined) {
      this.subscribeToSaveResponse(this.feedingObservationService.update(feedingObservation));
    } else {
      this.subscribeToSaveResponse(this.feedingObservationService.create(feedingObservation));
    }
  }

  private createFromForm(): IFeedingObservation {
    return {
      ...new FeedingObservation(),
      id: this.editForm.get(['id'])!.value,
      noFieldWorkers: this.editForm.get(['noFieldWorkers'])!.value,
      type: this.editForm.get(['type'])!.value,
      breedingAttempt: this.editForm.get(['breedingAttempt'])!.value,
      breedingStage: this.editForm.get(['breedingStage'])!.value,
      breedingOutcome: this.editForm.get(['breedingOutcome'])!.value,
      preyItem: this.editForm.get(['preyItem'])!.value,
      preySpecies: this.editForm.get(['preySpecies'])!.value,
      time: this.editForm.get(['time'])!.value,
      cloud: this.editForm.get(['cloud'])!.value,
      wind: this.editForm.get(['wind'])!.value,
      rain: this.editForm.get(['rain'])!.value,
      outcome: this.editForm.get(['outcome'])!.value,
      comment: this.editForm.get(['comment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedingObservation>>): void {
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
