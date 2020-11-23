import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { BirdBehaviourService } from './bird-behaviour.service';

@Component({
  templateUrl: './bird-behaviour-delete-dialog.component.html',
})
export class BirdBehaviourDeleteDialogComponent {
  birdBehaviour?: IBirdBehaviour;

  constructor(
    protected birdBehaviourService: BirdBehaviourService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.birdBehaviourService.delete(id).subscribe(() => {
      this.eventManager.broadcast('birdBehaviourListModification');
      this.activeModal.close();
    });
  }
}
