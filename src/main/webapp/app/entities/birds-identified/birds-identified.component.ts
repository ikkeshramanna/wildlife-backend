import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';
import { BirdsIdentifiedService } from './birds-identified.service';
import { BirdsIdentifiedDeleteDialogComponent } from './birds-identified-delete-dialog.component';

@Component({
  selector: 'jhi-birds-identified',
  templateUrl: './birds-identified.component.html',
})
export class BirdsIdentifiedComponent implements OnInit, OnDestroy {
  birdsIdentifieds?: IBirdsIdentified[];
  eventSubscriber?: Subscription;

  constructor(
    protected birdsIdentifiedService: BirdsIdentifiedService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.birdsIdentifiedService.query().subscribe((res: HttpResponse<IBirdsIdentified[]>) => (this.birdsIdentifieds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBirdsIdentifieds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBirdsIdentified): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBirdsIdentifieds(): void {
    this.eventSubscriber = this.eventManager.subscribe('birdsIdentifiedListModification', () => this.loadAll());
  }

  delete(birdsIdentified: IBirdsIdentified): void {
    const modalRef = this.modalService.open(BirdsIdentifiedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.birdsIdentified = birdsIdentified;
  }
}
