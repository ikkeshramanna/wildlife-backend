import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpeciesCategory } from 'app/shared/model/species-category.model';
import { SpeciesCategoryService } from './species-category.service';
import { SpeciesCategoryDeleteDialogComponent } from './species-category-delete-dialog.component';

@Component({
  selector: 'jhi-species-category',
  templateUrl: './species-category.component.html',
})
export class SpeciesCategoryComponent implements OnInit, OnDestroy {
  speciesCategories?: ISpeciesCategory[];
  eventSubscriber?: Subscription;

  constructor(
    protected speciesCategoryService: SpeciesCategoryService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.speciesCategoryService.query().subscribe((res: HttpResponse<ISpeciesCategory[]>) => (this.speciesCategories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSpeciesCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpeciesCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInSpeciesCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('speciesCategoryListModification', () => this.loadAll());
  }

  delete(speciesCategory: ISpeciesCategory): void {
    const modalRef = this.modalService.open(SpeciesCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.speciesCategory = speciesCategory;
  }
}
