import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INestSiteOverview, NestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { NestSiteOverviewService } from './nest-site-overview.service';

@Component({
  selector: 'jhi-nest-site-overview-update',
  templateUrl: './nest-site-overview-update.component.html',
})
export class NestSiteOverviewUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numberPeople: [null, [Validators.required]],
    purposeOfVisit: [null, [Validators.required]],
    signsOfUse: [null, [Validators.required]],
    nestingSubstrate: [],
    maintenanceDone: [],
    maintenanceRequired: [],
    comments: [],
  });

  constructor(
    protected nestSiteOverviewService: NestSiteOverviewService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nestSiteOverview }) => {
      this.updateForm(nestSiteOverview);
    });
  }

  updateForm(nestSiteOverview: INestSiteOverview): void {
    this.editForm.patchValue({
      id: nestSiteOverview.id,
      numberPeople: nestSiteOverview.numberPeople,
      purposeOfVisit: nestSiteOverview.purposeOfVisit,
      signsOfUse: nestSiteOverview.signsOfUse,
      nestingSubstrate: nestSiteOverview.nestingSubstrate,
      maintenanceDone: nestSiteOverview.maintenanceDone,
      maintenanceRequired: nestSiteOverview.maintenanceRequired,
      comments: nestSiteOverview.comments,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nestSiteOverview = this.createFromForm();
    if (nestSiteOverview.id !== undefined) {
      this.subscribeToSaveResponse(this.nestSiteOverviewService.update(nestSiteOverview));
    } else {
      this.subscribeToSaveResponse(this.nestSiteOverviewService.create(nestSiteOverview));
    }
  }

  private createFromForm(): INestSiteOverview {
    return {
      ...new NestSiteOverview(),
      id: this.editForm.get(['id'])!.value,
      numberPeople: this.editForm.get(['numberPeople'])!.value,
      purposeOfVisit: this.editForm.get(['purposeOfVisit'])!.value,
      signsOfUse: this.editForm.get(['signsOfUse'])!.value,
      nestingSubstrate: this.editForm.get(['nestingSubstrate'])!.value,
      maintenanceDone: this.editForm.get(['maintenanceDone'])!.value,
      maintenanceRequired: this.editForm.get(['maintenanceRequired'])!.value,
      comments: this.editForm.get(['comments'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INestSiteOverview>>): void {
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
