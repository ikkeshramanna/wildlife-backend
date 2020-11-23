import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISighting } from 'app/shared/model/sighting.model';
import { SightingService } from './sighting.service';
import { SightingDeleteDialogComponent } from './sighting-delete-dialog.component';

@Component({
  selector: 'jhi-sighting',
  templateUrl: './sighting.component.html',
})
export class SightingComponent implements OnInit, OnDestroy {
  sightings?: ISighting[];
  eventSubscriber?: Subscription;

  constructor(protected sightingService: SightingService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.sightingService.query().subscribe((res: HttpResponse<ISighting[]>) => (this.sightings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSightings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISighting): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSightings(): void {
    this.eventSubscriber = this.eventManager.subscribe('sightingListModification', () => this.loadAll());
  }

  delete(sighting: ISighting): void {
    const modalRef = this.modalService.open(SightingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sighting = sighting;
  }
}
