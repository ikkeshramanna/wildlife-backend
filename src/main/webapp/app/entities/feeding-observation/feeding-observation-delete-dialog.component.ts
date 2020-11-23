import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeedingObservation } from 'app/shared/model/feeding-observation.model';
import { FeedingObservationService } from './feeding-observation.service';

@Component({
  templateUrl: './feeding-observation-delete-dialog.component.html',
})
export class FeedingObservationDeleteDialogComponent {
  feedingObservation?: IFeedingObservation;

  constructor(
    protected feedingObservationService: FeedingObservationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feedingObservationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('feedingObservationListModification');
      this.activeModal.close();
    });
  }
}
