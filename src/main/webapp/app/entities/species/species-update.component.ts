import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISpecies, Species } from 'app/shared/model/species.model';
import { SpeciesService } from './species.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISpeciesCategory } from 'app/shared/model/species-category.model';
import { SpeciesCategoryService } from 'app/entities/species-category/species-category.service';

@Component({
  selector: 'jhi-species-update',
  templateUrl: './species-update.component.html',
})
export class SpeciesUpdateComponent implements OnInit {
  isSaving = false;
  speciescategories: ISpeciesCategory[] = [];
  addDateDp: any;
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    picture: [null, [Validators.required]],
    pictureContentType: [],
    feedingTraitType: [null, [Validators.required]],
    speciesType: [null, [Validators.required]],
    commonName: [null, [Validators.required]],
    latinName: [null, [Validators.required]],
    description: [null, [Validators.required]],
    isIndigenous: [],
    addDate: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
    speciesCategory: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected speciesService: SpeciesService,
    protected speciesCategoryService: SpeciesCategoryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ species }) => {
      this.updateForm(species);

      this.speciesCategoryService.query().subscribe((res: HttpResponse<ISpeciesCategory[]>) => (this.speciescategories = res.body || []));
    });
  }

  updateForm(species: ISpecies): void {
    this.editForm.patchValue({
      id: species.id,
      picture: species.picture,
      pictureContentType: species.pictureContentType,
      feedingTraitType: species.feedingTraitType,
      speciesType: species.speciesType,
      commonName: species.commonName,
      latinName: species.latinName,
      description: species.description,
      isIndigenous: species.isIndigenous,
      addDate: species.addDate,
      updateDate: species.updateDate,
      speciesCategory: species.speciesCategory,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('wildlifeApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const species = this.createFromForm();
    if (species.id !== undefined) {
      this.subscribeToSaveResponse(this.speciesService.update(species));
    } else {
      this.subscribeToSaveResponse(this.speciesService.create(species));
    }
  }

  private createFromForm(): ISpecies {
    return {
      ...new Species(),
      id: this.editForm.get(['id'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      feedingTraitType: this.editForm.get(['feedingTraitType'])!.value,
      speciesType: this.editForm.get(['speciesType'])!.value,
      commonName: this.editForm.get(['commonName'])!.value,
      latinName: this.editForm.get(['latinName'])!.value,
      description: this.editForm.get(['description'])!.value,
      isIndigenous: this.editForm.get(['isIndigenous'])!.value,
      addDate: this.editForm.get(['addDate'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value,
      speciesCategory: this.editForm.get(['speciesCategory'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecies>>): void {
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

  trackById(index: number, item: ISpeciesCategory): any {
    return item.id;
  }
}
