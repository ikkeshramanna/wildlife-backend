import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaggedAnimal, TaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { TaggedAnimalService } from './tagged-animal.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { ISpecies } from 'app/shared/model/species.model';
import { SpeciesService } from 'app/entities/species/species.service';

type SelectableEntity = IEmployee | ISpecies;

@Component({
  selector: 'jhi-tagged-animal-update',
  templateUrl: './tagged-animal-update.component.html',
})
export class TaggedAnimalUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  species: ISpecies[] = [];
  dateOfBirthDp: any;
  dateOfTaggingDp: any;
  dateTimeDp: any;
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    dateOfBirth: [],
    dateOfTagging: [],
    physicalTraits: [],
    tagType: [],
    dateTime: [],
    sexType: [],
    updateDate: [],
    taggedAnimal: [],
    species: [],
  });

  constructor(
    protected taggedAnimalService: TaggedAnimalService,
    protected employeeService: EmployeeService,
    protected speciesService: SpeciesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taggedAnimal }) => {
      this.updateForm(taggedAnimal);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.speciesService.query().subscribe((res: HttpResponse<ISpecies[]>) => (this.species = res.body || []));
    });
  }

  updateForm(taggedAnimal: ITaggedAnimal): void {
    this.editForm.patchValue({
      id: taggedAnimal.id,
      name: taggedAnimal.name,
      dateOfBirth: taggedAnimal.dateOfBirth,
      dateOfTagging: taggedAnimal.dateOfTagging,
      physicalTraits: taggedAnimal.physicalTraits,
      tagType: taggedAnimal.tagType,
      dateTime: taggedAnimal.dateTime,
      sexType: taggedAnimal.sexType,
      updateDate: taggedAnimal.updateDate,
      taggedAnimal: taggedAnimal.taggedAnimal,
      species: taggedAnimal.species,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taggedAnimal = this.createFromForm();
    if (taggedAnimal.id !== undefined) {
      this.subscribeToSaveResponse(this.taggedAnimalService.update(taggedAnimal));
    } else {
      this.subscribeToSaveResponse(this.taggedAnimalService.create(taggedAnimal));
    }
  }

  private createFromForm(): ITaggedAnimal {
    return {
      ...new TaggedAnimal(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      dateOfTagging: this.editForm.get(['dateOfTagging'])!.value,
      physicalTraits: this.editForm.get(['physicalTraits'])!.value,
      tagType: this.editForm.get(['tagType'])!.value,
      dateTime: this.editForm.get(['dateTime'])!.value,
      sexType: this.editForm.get(['sexType'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value,
      taggedAnimal: this.editForm.get(['taggedAnimal'])!.value,
      species: this.editForm.get(['species'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaggedAnimal>>): void {
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
}
