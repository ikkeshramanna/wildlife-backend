import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEggsAndChick, EggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from './eggs-and-chick.service';

@Component({
  selector: 'jhi-eggs-and-chick-update',
  templateUrl: './eggs-and-chick-update.component.html',
})
export class EggsAndChickUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    clutchNumber: [],
    eggsPresent: [null, [Validators.required]],
    chicksPresent: [null, [Validators.required]],
    photoTaken: [],
    comments: [],
  });

  constructor(protected eggsAndChickService: EggsAndChickService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eggsAndChick }) => {
      this.updateForm(eggsAndChick);
    });
  }

  updateForm(eggsAndChick: IEggsAndChick): void {
    this.editForm.patchValue({
      id: eggsAndChick.id,
      clutchNumber: eggsAndChick.clutchNumber,
      eggsPresent: eggsAndChick.eggsPresent,
      chicksPresent: eggsAndChick.chicksPresent,
      photoTaken: eggsAndChick.photoTaken,
      comments: eggsAndChick.comments,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eggsAndChick = this.createFromForm();
    if (eggsAndChick.id !== undefined) {
      this.subscribeToSaveResponse(this.eggsAndChickService.update(eggsAndChick));
    } else {
      this.subscribeToSaveResponse(this.eggsAndChickService.create(eggsAndChick));
    }
  }

  private createFromForm(): IEggsAndChick {
    return {
      ...new EggsAndChick(),
      id: this.editForm.get(['id'])!.value,
      clutchNumber: this.editForm.get(['clutchNumber'])!.value,
      eggsPresent: this.editForm.get(['eggsPresent'])!.value,
      chicksPresent: this.editForm.get(['chicksPresent'])!.value,
      photoTaken: this.editForm.get(['photoTaken'])!.value,
      comments: this.editForm.get(['comments'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEggsAndChick>>): void {
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
