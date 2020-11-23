import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEgg, Egg } from 'app/shared/model/egg.model';
import { EggService } from './egg.service';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from 'app/entities/eggs-and-chick/eggs-and-chick.service';

@Component({
  selector: 'jhi-egg-update',
  templateUrl: './egg-update.component.html',
})
export class EggUpdateComponent implements OnInit {
  isSaving = false;
  eggsandchicks: IEggsAndChick[] = [];
  eggLayDateDp: any;

  editForm = this.fb.group({
    id: [],
    eggNumber: [],
    eggStatus: [],
    eggLayDate: [],
    eggsAndChick: [],
  });

  constructor(
    protected eggService: EggService,
    protected eggsAndChickService: EggsAndChickService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ egg }) => {
      this.updateForm(egg);

      this.eggsAndChickService.query().subscribe((res: HttpResponse<IEggsAndChick[]>) => (this.eggsandchicks = res.body || []));
    });
  }

  updateForm(egg: IEgg): void {
    this.editForm.patchValue({
      id: egg.id,
      eggNumber: egg.eggNumber,
      eggStatus: egg.eggStatus,
      eggLayDate: egg.eggLayDate,
      eggsAndChick: egg.eggsAndChick,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const egg = this.createFromForm();
    if (egg.id !== undefined) {
      this.subscribeToSaveResponse(this.eggService.update(egg));
    } else {
      this.subscribeToSaveResponse(this.eggService.create(egg));
    }
  }

  private createFromForm(): IEgg {
    return {
      ...new Egg(),
      id: this.editForm.get(['id'])!.value,
      eggNumber: this.editForm.get(['eggNumber'])!.value,
      eggStatus: this.editForm.get(['eggStatus'])!.value,
      eggLayDate: this.editForm.get(['eggLayDate'])!.value,
      eggsAndChick: this.editForm.get(['eggsAndChick'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEgg>>): void {
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
