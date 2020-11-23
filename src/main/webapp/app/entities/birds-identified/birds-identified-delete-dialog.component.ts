import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';
import { BirdsIdentifiedService } from './birds-identified.service';

@Component({
  templateUrl: './birds-identified-delete-dialog.component.html',
})
export class BirdsIdentifiedDeleteDialogComponent {
  birdsIdentified?: IBirdsIdentified;

  constructor(
    protected birdsIdentifiedService: BirdsIdentifiedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.birdsIdentifiedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('birdsIdentifiedListModification');
      this.activeModal.close();
    });
  }
}
