import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { TaggedAnimalService } from './tagged-animal.service';

@Component({
  templateUrl: './tagged-animal-delete-dialog.component.html',
})
export class TaggedAnimalDeleteDialogComponent {
  taggedAnimal?: ITaggedAnimal;

  constructor(
    protected taggedAnimalService: TaggedAnimalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taggedAnimalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taggedAnimalListModification');
      this.activeModal.close();
    });
  }
}
