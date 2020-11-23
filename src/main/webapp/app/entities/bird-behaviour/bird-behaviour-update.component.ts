import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBirdBehaviour, BirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { BirdBehaviourService } from './bird-behaviour.service';

@Component({
  selector: 'jhi-bird-behaviour-update',
  templateUrl: './bird-behaviour-update.component.html',
})
export class BirdBehaviourUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    behaviour: [null, [Validators.required]],
  });

  constructor(protected birdBehaviourService: BirdBehaviourService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ birdBehaviour }) => {
      this.updateForm(birdBehaviour);
    });
  }

  updateForm(birdBehaviour: IBirdBehaviour): void {
    this.editForm.patchValue({
      id: birdBehaviour.id,
      behaviour: birdBehaviour.behaviour,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const birdBehaviour = this.createFromForm();
    if (birdBehaviour.id !== undefined) {
      this.subscribeToSaveResponse(this.birdBehaviourService.update(birdBehaviour));
    } else {
      this.subscribeToSaveResponse(this.birdBehaviourService.create(birdBehaviour));
    }
  }

  private createFromForm(): IBirdBehaviour {
    return {
      ...new BirdBehaviour(),
      id: this.editForm.get(['id'])!.value,
      behaviour: this.editForm.get(['behaviour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBirdBehaviour>>): void {
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
