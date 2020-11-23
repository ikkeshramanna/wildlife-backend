import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedingObservation } from 'app/shared/model/feeding-observation.model';
import { FeedingObservationService } from './feeding-observation.service';
import { FeedingObservationDeleteDialogComponent } from './feeding-observation-delete-dialog.component';

@Component({
  selector: 'jhi-feeding-observation',
  templateUrl: './feeding-observation.component.html',
})
export class FeedingObservationComponent implements OnInit, OnDestroy {
  feedingObservations?: IFeedingObservation[];
  eventSubscriber?: Subscription;

  constructor(
    protected feedingObservationService: FeedingObservationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.feedingObservationService
      .query()
      .subscribe((res: HttpResponse<IFeedingObservation[]>) => (this.feedingObservations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFeedingObservations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFeedingObservation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFeedingObservations(): void {
    this.eventSubscriber = this.eventManager.subscribe('feedingObservationListModification', () => this.loadAll());
  }

  delete(feedingObservation: IFeedingObservation): void {
    const modalRef = this.modalService.open(FeedingObservationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feedingObservation = feedingObservation;
  }
}
