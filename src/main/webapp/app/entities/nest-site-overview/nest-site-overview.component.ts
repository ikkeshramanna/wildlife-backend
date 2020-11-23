import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { NestSiteOverviewService } from './nest-site-overview.service';
import { NestSiteOverviewDeleteDialogComponent } from './nest-site-overview-delete-dialog.component';

@Component({
  selector: 'jhi-nest-site-overview',
  templateUrl: './nest-site-overview.component.html',
})
export class NestSiteOverviewComponent implements OnInit, OnDestroy {
  nestSiteOverviews?: INestSiteOverview[];
  eventSubscriber?: Subscription;

  constructor(
    protected nestSiteOverviewService: NestSiteOverviewService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.nestSiteOverviewService.query().subscribe((res: HttpResponse<INestSiteOverview[]>) => (this.nestSiteOverviews = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNestSiteOverviews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INestSiteOverview): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNestSiteOverviews(): void {
    this.eventSubscriber = this.eventManager.subscribe('nestSiteOverviewListModification', () => this.loadAll());
  }

  delete(nestSiteOverview: INestSiteOverview): void {
    const modalRef = this.modalService.open(NestSiteOverviewDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nestSiteOverview = nestSiteOverview;
  }
}
