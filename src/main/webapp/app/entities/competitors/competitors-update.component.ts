import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompetitors, Competitors } from 'app/shared/model/competitors.model';
import { CompetitorsService } from './competitors.service';
import { ISpecies } from 'app/shared/model/species.model';
import { SpeciesService } from 'app/entities/species/species.service';
import { ICompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { CompetitorImpactOnMkService } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk.service';
import { ICompetitorAction } from 'app/shared/model/competitor-action.model';
import { CompetitorActionService } from 'app/entities/competitor-action/competitor-action.service';
import { ISighting } from 'app/shared/model/sighting.model';
import { SightingService } from 'app/entities/sighting/sighting.service';

type SelectableEntity = ISpecies | ICompetitorImpactOnMk | ICompetitorAction | ISighting;

type SelectableManyToManyEntity = ICompetitorImpactOnMk | ICompetitorAction;

@Component({
  selector: 'jhi-competitors-update',
  templateUrl: './competitors-update.component.html',
})
export class CompetitorsUpdateComponent implements OnInit {
  isSaving = false;
  species: ISpecies[] = [];
  competitorimpactonmks: ICompetitorImpactOnMk[] = [];
  competitoractions: ICompetitorAction[] = [];
  sightings: ISighting[] = [];

  editForm = this.fb.group({
    id: [],
    mkAround: [],
    noOfIndividuals: [null, [Validators.required]],
    competitorBehaviour: [null, [Validators.required]],
    competitorLocation: [null, [Validators.required]],
    competitorSpecies: [],
    competitorImpactOnMks: [],
    competitorActions: [],
    sighting: [],
  });

  constructor(
    protected competitorsService: CompetitorsService,
    protected speciesService: SpeciesService,
    protected competitorImpactOnMkService: CompetitorImpactOnMkService,
    protected competitorActionService: CompetitorActionService,
    protected sightingService: SightingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitors }) => {
      this.updateForm(competitors);

      this.speciesService.query().subscribe((res: HttpResponse<ISpecies[]>) => (this.species = res.body || []));

      this.competitorImpactOnMkService
        .query()
        .subscribe((res: HttpResponse<ICompetitorImpactOnMk[]>) => (this.competitorimpactonmks = res.body || []));

      this.competitorActionService.query().subscribe((res: HttpResponse<ICompetitorAction[]>) => (this.competitoractions = res.body || []));

      this.sightingService.query().subscribe((res: HttpResponse<ISighting[]>) => (this.sightings = res.body || []));
    });
  }

  updateForm(competitors: ICompetitors): void {
    this.editForm.patchValue({
      id: competitors.id,
      mkAround: competitors.mkAround,
      noOfIndividuals: competitors.noOfIndividuals,
      competitorBehaviour: competitors.competitorBehaviour,
      competitorLocation: competitors.competitorLocation,
      competitorSpecies: competitors.competitorSpecies,
      competitorImpactOnMks: competitors.competitorImpactOnMks,
      competitorActions: competitors.competitorActions,
      sighting: competitors.sighting,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competitors = this.createFromForm();
    if (competitors.id !== undefined) {
      this.subscribeToSaveResponse(this.competitorsService.update(competitors));
    } else {
      this.subscribeToSaveResponse(this.competitorsService.create(competitors));
    }
  }

  private createFromForm(): ICompetitors {
    return {
      ...new Competitors(),
      id: this.editForm.get(['id'])!.value,
      mkAround: this.editForm.get(['mkAround'])!.value,
      noOfIndividuals: this.editForm.get(['noOfIndividuals'])!.value,
      competitorBehaviour: this.editForm.get(['competitorBehaviour'])!.value,
      competitorLocation: this.editForm.get(['competitorLocation'])!.value,
      competitorSpecies: this.editForm.get(['competitorSpecies'])!.value,
      competitorImpactOnMks: this.editForm.get(['competitorImpactOnMks'])!.value,
      competitorActions: this.editForm.get(['competitorActions'])!.value,
      sighting: this.editForm.get(['sighting'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitors>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
