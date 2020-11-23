import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IObservationLocation } from 'app/shared/model/observation-location.model';
import { ObservationLocationService } from './observation-location.service';
import { ObservationLocationDeleteDialogComponent } from './observation-location-delete-dialog.component';

@Component({
  selector: 'jhi-observation-location',
  templateUrl: './observation-location.component.html',
})
export class ObservationLocationComponent implements OnInit, OnDestroy {
  observationLocations?: IObservationLocation[];
  eventSubscriber?: Subscription;

  constructor(
    protected observationLocationService: ObservationLocationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.observationLocationService
      .query()
      .subscribe((res: HttpResponse<IObservationLocation[]>) => (this.observationLocations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInObservationLocations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IObservationLocation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInObservationLocations(): void {
    this.eventSubscriber = this.eventManager.subscribe('observationLocationListModification', () => this.loadAll());
  }

  delete(observationLocation: IObservationLocation): void {
    const modalRef = this.modalService.open(ObservationLocationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.observationLocation = observationLocation;
  }
}
