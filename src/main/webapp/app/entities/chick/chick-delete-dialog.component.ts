import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChick } from 'app/shared/model/chick.model';
import { ChickService } from './chick.service';

@Component({
  templateUrl: './chick-delete-dialog.component.html',
})
export class ChickDeleteDialogComponent {
  chick?: IChick;

  constructor(protected chickService: ChickService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chickService.delete(id).subscribe(() => {
      this.eventManager.broadcast('chickListModification');
      this.activeModal.close();
    });
  }
}
