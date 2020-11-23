import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChick } from 'app/shared/model/chick.model';
import { ChickService } from './chick.service';
import { ChickDeleteDialogComponent } from './chick-delete-dialog.component';

@Component({
  selector: 'jhi-chick',
  templateUrl: './chick.component.html',
})
export class ChickComponent implements OnInit, OnDestroy {
  chicks?: IChick[];
  eventSubscriber?: Subscription;

  constructor(protected chickService: ChickService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.chickService.query().subscribe((res: HttpResponse<IChick[]>) => (this.chicks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChicks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChick): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChicks(): void {
    this.eventSubscriber = this.eventManager.subscribe('chickListModification', () => this.loadAll());
  }

  delete(chick: IChick): void {
    const modalRef = this.modalService.open(ChickDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chick = chick;
  }
}
