import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISpeciesCategory, SpeciesCategory } from 'app/shared/model/species-category.model';
import { SpeciesCategoryService } from './species-category.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-species-category-update',
  templateUrl: './species-category-update.component.html',
})
export class SpeciesCategoryUpdateComponent implements OnInit {
  isSaving = false;
  addDateDp: any;
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    speciesCategoryType: [null, [Validators.required]],
    picture: [null, [Validators.required]],
    pictureContentType: [],
    description: [null, [Validators.required]],
    addDate: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected speciesCategoryService: SpeciesCategoryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ speciesCategory }) => {
      this.updateForm(speciesCategory);
    });
  }

  updateForm(speciesCategory: ISpeciesCategory): void {
    this.editForm.patchValue({
      id: speciesCategory.id,
      name: speciesCategory.name,
      speciesCategoryType: speciesCategory.speciesCategoryType,
      picture: speciesCategory.picture,
      pictureContentType: speciesCategory.pictureContentType,
      description: speciesCategory.description,
      addDate: speciesCategory.addDate,
      updateDate: speciesCategory.updateDate,
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
    const speciesCategory = this.createFromForm();
    if (speciesCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.speciesCategoryService.update(speciesCategory));
    } else {
      this.subscribeToSaveResponse(this.speciesCategoryService.create(speciesCategory));
    }
  }

  private createFromForm(): ISpeciesCategory {
    return {
      ...new SpeciesCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      speciesCategoryType: this.editForm.get(['speciesCategoryType'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      description: this.editForm.get(['description'])!.value,
      addDate: this.editForm.get(['addDate'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpeciesCategory>>): void {
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
