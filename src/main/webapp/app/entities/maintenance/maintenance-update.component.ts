import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMaintenance, Maintenance } from 'app/shared/model/maintenance.model';
import { MaintenanceService } from './maintenance.service';

@Component({
  selector: 'jhi-maintenance-update',
  templateUrl: './maintenance-update.component.html',
})
export class MaintenanceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    struts: [null, [Validators.required]],
    boxCondition: [null, [Validators.required]],
    beePlastic: [null, [Validators.required]],
    hatch: [null, [Validators.required]],
    steps: [],
    handholds: [],
    treeNeedsReplacing: [],
    boxNeedsReplacing: [],
    clearing: [null, [Validators.required]],
    path: [null, [Validators.required]],
    additionalVisit: [],
    drillRequired: [],
    siteDescription: [],
    bearing: [null, [Validators.required]],
    side: [],
    treeSpecies: [null, [Validators.required]],
    height: [null, [Validators.required]],
    comments: [],
  });

  constructor(protected maintenanceService: MaintenanceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ maintenance }) => {
      this.updateForm(maintenance);
    });
  }

  updateForm(maintenance: IMaintenance): void {
    this.editForm.patchValue({
      id: maintenance.id,
      struts: maintenance.struts,
      boxCondition: maintenance.boxCondition,
      beePlastic: maintenance.beePlastic,
      hatch: maintenance.hatch,
      steps: maintenance.steps,
      handholds: maintenance.handholds,
      treeNeedsReplacing: maintenance.treeNeedsReplacing,
      boxNeedsReplacing: maintenance.boxNeedsReplacing,
      clearing: maintenance.clearing,
      path: maintenance.path,
      additionalVisit: maintenance.additionalVisit,
      drillRequired: maintenance.drillRequired,
      siteDescription: maintenance.siteDescription,
      bearing: maintenance.bearing,
      side: maintenance.side,
      treeSpecies: maintenance.treeSpecies,
      height: maintenance.height,
      comments: maintenance.comments,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const maintenance = this.createFromForm();
    if (maintenance.id !== undefined) {
      this.subscribeToSaveResponse(this.maintenanceService.update(maintenance));
    } else {
      this.subscribeToSaveResponse(this.maintenanceService.create(maintenance));
    }
  }

  private createFromForm(): IMaintenance {
    return {
      ...new Maintenance(),
      id: this.editForm.get(['id'])!.value,
      struts: this.editForm.get(['struts'])!.value,
      boxCondition: this.editForm.get(['boxCondition'])!.value,
      beePlastic: this.editForm.get(['beePlastic'])!.value,
      hatch: this.editForm.get(['hatch'])!.value,
      steps: this.editForm.get(['steps'])!.value,
      handholds: this.editForm.get(['handholds'])!.value,
      treeNeedsReplacing: this.editForm.get(['treeNeedsReplacing'])!.value,
      boxNeedsReplacing: this.editForm.get(['boxNeedsReplacing'])!.value,
      clearing: this.editForm.get(['clearing'])!.value,
      path: this.editForm.get(['path'])!.value,
      additionalVisit: this.editForm.get(['additionalVisit'])!.value,
      drillRequired: this.editForm.get(['drillRequired'])!.value,
      siteDescription: this.editForm.get(['siteDescription'])!.value,
      bearing: this.editForm.get(['bearing'])!.value,
      side: this.editForm.get(['side'])!.value,
      treeSpecies: this.editForm.get(['treeSpecies'])!.value,
      height: this.editForm.get(['height'])!.value,
      comments: this.editForm.get(['comments'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaintenance>>): void {
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
