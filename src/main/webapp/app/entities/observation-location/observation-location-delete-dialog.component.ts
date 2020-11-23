import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObservationLocation } from 'app/shared/model/observation-location.model';
import { ObservationLocationService } from './observation-location.service';

@Component({
  templateUrl: './observation-location-delete-dialog.component.html',
})
export class ObservationLocationDeleteDialogComponent {
  observationLocation?: IObservationLocation;

  constructor(
    protected observationLocationService: ObservationLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.observationLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('observationLocationListModification');
      this.activeModal.close();
    });
  }
}
