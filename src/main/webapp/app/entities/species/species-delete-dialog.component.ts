import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecies } from 'app/shared/model/species.model';
import { SpeciesService } from './species.service';

@Component({
  templateUrl: './species-delete-dialog.component.html',
})
export class SpeciesDeleteDialogComponent {
  species?: ISpecies;

  constructor(protected speciesService: SpeciesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.speciesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('speciesListModification');
      this.activeModal.close();
    });
  }
}
