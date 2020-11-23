import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { RingingMorphsService } from './ringing-morphs.service';

@Component({
  templateUrl: './ringing-morphs-delete-dialog.component.html',
})
export class RingingMorphsDeleteDialogComponent {
  ringingMorphs?: IRingingMorphs;

  constructor(
    protected ringingMorphsService: RingingMorphsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ringingMorphsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ringingMorphsListModification');
      this.activeModal.close();
    });
  }
}
