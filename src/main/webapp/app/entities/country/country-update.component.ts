import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICountry, Country } from 'app/shared/model/country.model';
import { CountryService } from './country.service';
import { ISpecies } from 'app/shared/model/species.model';
import { SpeciesService } from 'app/entities/species/species.service';

@Component({
  selector: 'jhi-country-update',
  templateUrl: './country-update.component.html',
})
export class CountryUpdateComponent implements OnInit {
  isSaving = false;
  species: ISpecies[] = [];
  addDateDp: any;

  editForm = this.fb.group({
    id: [],
    countryName: [null, [Validators.required]],
    addDate: [null, [Validators.required]],
    species: [],
  });

  constructor(
    protected countryService: CountryService,
    protected speciesService: SpeciesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ country }) => {
      this.updateForm(country);

      this.speciesService.query().subscribe((res: HttpResponse<ISpecies[]>) => (this.species = res.body || []));
    });
  }

  updateForm(country: ICountry): void {
    this.editForm.patchValue({
      id: country.id,
      countryName: country.countryName,
      addDate: country.addDate,
      species: country.species,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const country = this.createFromForm();
    if (country.id !== undefined) {
      this.subscribeToSaveResponse(this.countryService.update(country));
    } else {
      this.subscribeToSaveResponse(this.countryService.create(country));
    }
  }

  private createFromForm(): ICountry {
    return {
      ...new Country(),
      id: this.editForm.get(['id'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
      addDate: this.editForm.get(['addDate'])!.value,
      species: this.editForm.get(['species'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>): void {
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

  trackById(index: number, item: ISpecies): any {
    return item.id;
  }

  getSelected(selectedVals: ISpecies[], option: ISpecies): ISpecies {
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
