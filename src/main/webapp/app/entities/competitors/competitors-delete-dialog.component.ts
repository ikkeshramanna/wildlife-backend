import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetitors } from 'app/shared/model/competitors.model';
import { CompetitorsService } from './competitors.service';

@Component({
  templateUrl: './competitors-delete-dialog.component.html',
})
export class CompetitorsDeleteDialogComponent {
  competitors?: ICompetitors;

  constructor(
    protected competitorsService: CompetitorsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competitorsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('competitorsListModification');
      this.activeModal.close();
    });
  }
}
