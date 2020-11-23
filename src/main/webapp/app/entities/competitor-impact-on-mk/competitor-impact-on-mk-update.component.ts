import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompetitorImpactOnMk, CompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { CompetitorImpactOnMkService } from './competitor-impact-on-mk.service';

@Component({
  selector: 'jhi-competitor-impact-on-mk-update',
  templateUrl: './competitor-impact-on-mk-update.component.html',
})
export class CompetitorImpactOnMkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    impact: [null, [Validators.required]],
  });

  constructor(
    protected competitorImpactOnMkService: CompetitorImpactOnMkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitorImpactOnMk }) => {
      this.updateForm(competitorImpactOnMk);
    });
  }

  updateForm(competitorImpactOnMk: ICompetitorImpactOnMk): void {
    this.editForm.patchValue({
      id: competitorImpactOnMk.id,
      impact: competitorImpactOnMk.impact,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competitorImpactOnMk = this.createFromForm();
    if (competitorImpactOnMk.id !== undefined) {
      this.subscribeToSaveResponse(this.competitorImpactOnMkService.update(competitorImpactOnMk));
    } else {
      this.subscribeToSaveResponse(this.competitorImpactOnMkService.create(competitorImpactOnMk));
    }
  }

  private createFromForm(): ICompetitorImpactOnMk {
    return {
      ...new CompetitorImpactOnMk(),
      id: this.editForm.get(['id'])!.value,
      impact: this.editForm.get(['impact'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitorImpactOnMk>>): void {
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
