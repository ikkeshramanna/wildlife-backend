import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompetitorAction, CompetitorAction } from 'app/shared/model/competitor-action.model';
import { CompetitorActionService } from './competitor-action.service';

@Component({
  selector: 'jhi-competitor-action-update',
  templateUrl: './competitor-action-update.component.html',
})
export class CompetitorActionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    action: [null, [Validators.required]],
  });

  constructor(
    protected competitorActionService: CompetitorActionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitorAction }) => {
      this.updateForm(competitorAction);
    });
  }

  updateForm(competitorAction: ICompetitorAction): void {
    this.editForm.patchValue({
      id: competitorAction.id,
      action: competitorAction.action,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competitorAction = this.createFromForm();
    if (competitorAction.id !== undefined) {
      this.subscribeToSaveResponse(this.competitorActionService.update(competitorAction));
    } else {
      this.subscribeToSaveResponse(this.competitorActionService.create(competitorAction));
    }
  }

  private createFromForm(): ICompetitorAction {
    return {
      ...new CompetitorAction(),
      id: this.editForm.get(['id'])!.value,
      action: this.editForm.get(['action'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitorAction>>): void {
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
