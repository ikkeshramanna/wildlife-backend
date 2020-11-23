import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpeciesCategory } from 'app/shared/model/species-category.model';
import { SpeciesCategoryService } from './species-category.service';

@Component({
  templateUrl: './species-category-delete-dialog.component.html',
})
export class SpeciesCategoryDeleteDialogComponent {
  speciesCategory?: ISpeciesCategory;

  constructor(
    protected speciesCategoryService: SpeciesCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.speciesCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('speciesCategoryListModification');
      this.activeModal.close();
    });
  }
}
