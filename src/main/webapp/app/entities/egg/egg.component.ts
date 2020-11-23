import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEgg } from 'app/shared/model/egg.model';
import { EggService } from './egg.service';
import { EggDeleteDialogComponent } from './egg-delete-dialog.component';

@Component({
  selector: 'jhi-egg',
  templateUrl: './egg.component.html',
})
export class EggComponent implements OnInit, OnDestroy {
  eggs?: IEgg[];
  eventSubscriber?: Subscription;

  constructor(protected eggService: EggService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.eggService.query().subscribe((res: HttpResponse<IEgg[]>) => (this.eggs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEggs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEgg): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEggs(): void {
    this.eventSubscriber = this.eventManager.subscribe('eggListModification', () => this.loadAll());
  }

  delete(egg: IEgg): void {
    const modalRef = this.modalService.open(EggDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.egg = egg;
  }
}
