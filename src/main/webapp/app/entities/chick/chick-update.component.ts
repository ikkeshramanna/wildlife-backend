import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChick, Chick } from 'app/shared/model/chick.model';
import { ChickService } from './chick.service';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from 'app/entities/eggs-and-chick/eggs-and-chick.service';

@Component({
  selector: 'jhi-chick-update',
  templateUrl: './chick-update.component.html',
})
export class ChickUpdateComponent implements OnInit {
  isSaving = false;
  eggsandchicks: IEggsAndChick[] = [];
  hatchDateDp: any;

  editForm = this.fb.group({
    id: [],
    chickNumber: [null, [Validators.required]],
    hatchDate: [null, [Validators.required]],
    chickStatus: [null, [Validators.required]],
    chickAge: [null, [Validators.required]],
    chickActive: [null, [Validators.required]],
    chickCondition: [null, [Validators.required]],
    chickRinged: [null, [Validators.required]],
    bloodSample: [null, [Validators.required]],
    eggsAndChick: [],
  });

  constructor(
    protected chickService: ChickService,
    protected eggsAndChickService: EggsAndChickService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chick }) => {
      this.updateForm(chick);

      this.eggsAndChickService.query().subscribe((res: HttpResponse<IEggsAndChick[]>) => (this.eggsandchicks = res.body || []));
    });
  }

  updateForm(chick: IChick): void {
    this.editForm.patchValue({
      id: chick.id,
      chickNumber: chick.chickNumber,
      hatchDate: chick.hatchDate,
      chickStatus: chick.chickStatus,
      chickAge: chick.chickAge,
      chickActive: chick.chickActive,
      chickCondition: chick.chickCondition,
      chickRinged: chick.chickRinged,
      bloodSample: chick.bloodSample,
      eggsAndChick: chick.eggsAndChick,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chick = this.createFromForm();
    if (chick.id !== undefined) {
      this.subscribeToSaveResponse(this.chickService.update(chick));
    } else {
      this.subscribeToSaveResponse(this.chickService.create(chick));
    }
  }

  private createFromForm(): IChick {
    return {
      ...new Chick(),
      id: this.editForm.get(['id'])!.value,
      chickNumber: this.editForm.get(['chickNumber'])!.value,
      hatchDate: this.editForm.get(['hatchDate'])!.value,
      chickStatus: this.editForm.get(['chickStatus'])!.value,
      chickAge: this.editForm.get(['chickAge'])!.value,
      chickActive: this.editForm.get(['chickActive'])!.value,
      chickCondition: this.editForm.get(['chickCondition'])!.value,
      chickRinged: this.editForm.get(['chickRinged'])!.value,
      bloodSample: this.editForm.get(['bloodSample'])!.value,
      eggsAndChick: this.editForm.get(['eggsAndChick'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChick>>): void {
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

  trackById(index: number, item: IEggsAndChick): any {
    return item.id;
  }
}
