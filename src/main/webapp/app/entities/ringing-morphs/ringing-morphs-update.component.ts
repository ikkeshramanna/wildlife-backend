import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRingingMorphs, RingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { RingingMorphsService } from './ringing-morphs.service';

@Component({
  selector: 'jhi-ringing-morphs-update',
  templateUrl: './ringing-morphs-update.component.html',
})
export class RingingMorphsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    mkType: [null, [Validators.required]],
    reasonForCapture: [null, [Validators.required]],
    captureMethod: [null, [Validators.required]],
    name: [null, [Validators.required]],
    age: [null, [Validators.required]],
    weight: [null, [Validators.required]],
    head: [null, [Validators.required]],
    exposedCulmen: [null, [Validators.required]],
    culmenToSkull: [null, [Validators.required]],
    wing: [null, [Validators.required]],
    p8: [null, [Validators.required]],
    p8Brush: [null, [Validators.required]],
    tail: [null, [Validators.required]],
    tailBrush: [null, [Validators.required]],
    tarsusLength: [null, [Validators.required]],
    comments: [],
  });

  constructor(protected ringingMorphsService: RingingMorphsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ringingMorphs }) => {
      this.updateForm(ringingMorphs);
    });
  }

  updateForm(ringingMorphs: IRingingMorphs): void {
    this.editForm.patchValue({
      id: ringingMorphs.id,
      mkType: ringingMorphs.mkType,
      reasonForCapture: ringingMorphs.reasonForCapture,
      captureMethod: ringingMorphs.captureMethod,
      name: ringingMorphs.name,
      age: ringingMorphs.age,
      weight: ringingMorphs.weight,
      head: ringingMorphs.head,
      exposedCulmen: ringingMorphs.exposedCulmen,
      culmenToSkull: ringingMorphs.culmenToSkull,
      wing: ringingMorphs.wing,
      p8: ringingMorphs.p8,
      p8Brush: ringingMorphs.p8Brush,
      tail: ringingMorphs.tail,
      tailBrush: ringingMorphs.tailBrush,
      tarsusLength: ringingMorphs.tarsusLength,
      comments: ringingMorphs.comments,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ringingMorphs = this.createFromForm();
    if (ringingMorphs.id !== undefined) {
      this.subscribeToSaveResponse(this.ringingMorphsService.update(ringingMorphs));
    } else {
      this.subscribeToSaveResponse(this.ringingMorphsService.create(ringingMorphs));
    }
  }

  private createFromForm(): IRingingMorphs {
    return {
      ...new RingingMorphs(),
      id: this.editForm.get(['id'])!.value,
      mkType: this.editForm.get(['mkType'])!.value,
      reasonForCapture: this.editForm.get(['reasonForCapture'])!.value,
      captureMethod: this.editForm.get(['captureMethod'])!.value,
      name: this.editForm.get(['name'])!.value,
      age: this.editForm.get(['age'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      head: this.editForm.get(['head'])!.value,
      exposedCulmen: this.editForm.get(['exposedCulmen'])!.value,
      culmenToSkull: this.editForm.get(['culmenToSkull'])!.value,
      wing: this.editForm.get(['wing'])!.value,
      p8: this.editForm.get(['p8'])!.value,
      p8Brush: this.editForm.get(['p8Brush'])!.value,
      tail: this.editForm.get(['tail'])!.value,
      tailBrush: this.editForm.get(['tailBrush'])!.value,
      tarsusLength: this.editForm.get(['tarsusLength'])!.value,
      comments: this.editForm.get(['comments'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRingingMorphs>>): void {
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
