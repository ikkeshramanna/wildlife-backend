import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBirdsIdentified, BirdsIdentified } from 'app/shared/model/birds-identified.model';
import { BirdsIdentifiedService } from './birds-identified.service';
import { IBirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { BirdBehaviourService } from 'app/entities/bird-behaviour/bird-behaviour.service';

@Component({
  selector: 'jhi-birds-identified-update',
  templateUrl: './birds-identified-update.component.html',
})
export class BirdsIdentifiedUpdateComponent implements OnInit {
  isSaving = false;
  birdbehaviours: IBirdBehaviour[] = [];

  editForm = this.fb.group({
    id: [],
    seenDuring: [null, [Validators.required]],
    type: [null, [Validators.required]],
    sex: [null, [Validators.required]],
    status: [null, [Validators.required]],
    birdLocation: [null, [Validators.required]],
    southing: [],
    easting: [],
    comments: [],
    colorComboL: [null, [Validators.required]],
    colorComboR: [null, [Validators.required]],
    birdIRN: [],
    birdBehaviours: [],
  });

  constructor(
    protected birdsIdentifiedService: BirdsIdentifiedService,
    protected birdBehaviourService: BirdBehaviourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ birdsIdentified }) => {
      this.updateForm(birdsIdentified);

      this.birdBehaviourService.query().subscribe((res: HttpResponse<IBirdBehaviour[]>) => (this.birdbehaviours = res.body || []));
    });
  }

  updateForm(birdsIdentified: IBirdsIdentified): void {
    this.editForm.patchValue({
      id: birdsIdentified.id,
      seenDuring: birdsIdentified.seenDuring,
      type: birdsIdentified.type,
      sex: birdsIdentified.sex,
      status: birdsIdentified.status,
      birdLocation: birdsIdentified.birdLocation,
      southing: birdsIdentified.southing,
      easting: birdsIdentified.easting,
      comments: birdsIdentified.comments,
      colorComboL: birdsIdentified.colorComboL,
      colorComboR: birdsIdentified.colorComboR,
      birdIRN: birdsIdentified.birdIRN,
      birdBehaviours: birdsIdentified.birdBehaviours,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const birdsIdentified = this.createFromForm();
    if (birdsIdentified.id !== undefined) {
      this.subscribeToSaveResponse(this.birdsIdentifiedService.update(birdsIdentified));
    } else {
      this.subscribeToSaveResponse(this.birdsIdentifiedService.create(birdsIdentified));
    }
  }

  private createFromForm(): IBirdsIdentified {
    return {
      ...new BirdsIdentified(),
      id: this.editForm.get(['id'])!.value,
      seenDuring: this.editForm.get(['seenDuring'])!.value,
      type: this.editForm.get(['type'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      status: this.editForm.get(['status'])!.value,
      birdLocation: this.editForm.get(['birdLocation'])!.value,
      southing: this.editForm.get(['southing'])!.value,
      easting: this.editForm.get(['easting'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      colorComboL: this.editForm.get(['colorComboL'])!.value,
      colorComboR: this.editForm.get(['colorComboR'])!.value,
      birdIRN: this.editForm.get(['birdIRN'])!.value,
      birdBehaviours: this.editForm.get(['birdBehaviours'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBirdsIdentified>>): void {
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

  trackById(index: number, item: IBirdBehaviour): any {
    return item.id;
  }

  getSelected(selectedVals: IBirdBehaviour[], option: IBirdBehaviour): IBirdBehaviour {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
