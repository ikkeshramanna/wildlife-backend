import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { NestSiteOverviewService } from './nest-site-overview.service';

@Component({
  templateUrl: './nest-site-overview-delete-dialog.component.html',
})
export class NestSiteOverviewDeleteDialogComponent {
  nestSiteOverview?: INestSiteOverview;

  constructor(
    protected nestSiteOverviewService: NestSiteOverviewService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nestSiteOverviewService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nestSiteOverviewListModification');
      this.activeModal.close();
    });
  }
}
