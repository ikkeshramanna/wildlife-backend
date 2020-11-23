import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISpeciesCategory } from 'app/shared/model/species-category.model';

@Component({
  selector: 'jhi-species-category-detail',
  templateUrl: './species-category-detail.component.html',
})
export class SpeciesCategoryDetailComponent implements OnInit {
  speciesCategory: ISpeciesCategory | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ speciesCategory }) => (this.speciesCategory = speciesCategory));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
