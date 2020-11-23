import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetitorAction } from 'app/shared/model/competitor-action.model';
import { CompetitorActionService } from './competitor-action.service';

@Component({
  templateUrl: './competitor-action-delete-dialog.component.html',
})
export class CompetitorActionDeleteDialogComponent {
  competitorAction?: ICompetitorAction;

  constructor(
    protected competitorActionService: CompetitorActionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competitorActionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('competitorActionListModification');
      this.activeModal.close();
    });
  }
}
