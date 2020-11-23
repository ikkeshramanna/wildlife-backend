import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { CompetitorImpactOnMkService } from './competitor-impact-on-mk.service';

@Component({
  templateUrl: './competitor-impact-on-mk-delete-dialog.component.html',
})
export class CompetitorImpactOnMkDeleteDialogComponent {
  competitorImpactOnMk?: ICompetitorImpactOnMk;

  constructor(
    protected competitorImpactOnMkService: CompetitorImpactOnMkService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competitorImpactOnMkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('competitorImpactOnMkListModification');
      this.activeModal.close();
    });
  }
}
