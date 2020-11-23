import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { BirdBehaviourService } from './bird-behaviour.service';
import { BirdBehaviourDeleteDialogComponent } from './bird-behaviour-delete-dialog.component';

@Component({
  selector: 'jhi-bird-behaviour',
  templateUrl: './bird-behaviour.component.html',
})
export class BirdBehaviourComponent implements OnInit, OnDestroy {
  birdBehaviours?: IBirdBehaviour[];
  eventSubscriber?: Subscription;

  constructor(
    protected birdBehaviourService: BirdBehaviourService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.birdBehaviourService.query().subscribe((res: HttpResponse<IBirdBehaviour[]>) => (this.birdBehaviours = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBirdBehaviours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBirdBehaviour): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBirdBehaviours(): void {
    this.eventSubscriber = this.eventManager.subscribe('birdBehaviourListModification', () => this.loadAll());
  }

  delete(birdBehaviour: IBirdBehaviour): void {
    const modalRef = this.modalService.open(BirdBehaviourDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.birdBehaviour = birdBehaviour;
  }
}
