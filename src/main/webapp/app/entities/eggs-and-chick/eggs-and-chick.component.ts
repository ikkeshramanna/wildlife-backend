import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from './eggs-and-chick.service';
import { EggsAndChickDeleteDialogComponent } from './eggs-and-chick-delete-dialog.component';

@Component({
  selector: 'jhi-eggs-and-chick',
  templateUrl: './eggs-and-chick.component.html',
})
export class EggsAndChickComponent implements OnInit, OnDestroy {
  eggsAndChicks?: IEggsAndChick[];
  eventSubscriber?: Subscription;

  constructor(
    protected eggsAndChickService: EggsAndChickService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.eggsAndChickService.query().subscribe((res: HttpResponse<IEggsAndChick[]>) => (this.eggsAndChicks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEggsAndChicks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEggsAndChick): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEggsAndChicks(): void {
    this.eventSubscriber = this.eventManager.subscribe('eggsAndChickListModification', () => this.loadAll());
  }

  delete(eggsAndChick: IEggsAndChick): void {
    const modalRef = this.modalService.open(EggsAndChickDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eggsAndChick = eggsAndChick;
  }
}
