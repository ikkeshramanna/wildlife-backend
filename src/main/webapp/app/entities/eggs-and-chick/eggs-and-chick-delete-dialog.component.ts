import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from './eggs-and-chick.service';

@Component({
  templateUrl: './eggs-and-chick-delete-dialog.component.html',
})
export class EggsAndChickDeleteDialogComponent {
  eggsAndChick?: IEggsAndChick;

  constructor(
    protected eggsAndChickService: EggsAndChickService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eggsAndChickService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eggsAndChickListModification');
      this.activeModal.close();
    });
  }
}
