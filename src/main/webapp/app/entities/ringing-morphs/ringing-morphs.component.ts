import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { RingingMorphsService } from './ringing-morphs.service';
import { RingingMorphsDeleteDialogComponent } from './ringing-morphs-delete-dialog.component';

@Component({
  selector: 'jhi-ringing-morphs',
  templateUrl: './ringing-morphs.component.html',
})
export class RingingMorphsComponent implements OnInit, OnDestroy {
  ringingMorphs?: IRingingMorphs[];
  eventSubscriber?: Subscription;

  constructor(
    protected ringingMorphsService: RingingMorphsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.ringingMorphsService.query().subscribe((res: HttpResponse<IRingingMorphs[]>) => (this.ringingMorphs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRingingMorphs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRingingMorphs): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRingingMorphs(): void {
    this.eventSubscriber = this.eventManager.subscribe('ringingMorphsListModification', () => this.loadAll());
  }

  delete(ringingMorphs: IRingingMorphs): void {
    const modalRef = this.modalService.open(RingingMorphsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ringingMorphs = ringingMorphs;
  }
}
