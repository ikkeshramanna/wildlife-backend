import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEgg } from 'app/shared/model/egg.model';
import { EggService } from './egg.service';

@Component({
  templateUrl: './egg-delete-dialog.component.html',
})
export class EggDeleteDialogComponent {
  egg?: IEgg;

  constructor(protected eggService: EggService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eggService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eggListModification');
      this.activeModal.close();
    });
  }
}
