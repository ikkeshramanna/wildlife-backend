import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetitorAction } from 'app/shared/model/competitor-action.model';
import { CompetitorActionService } from './competitor-action.service';
import { CompetitorActionDeleteDialogComponent } from './competitor-action-delete-dialog.component';

@Component({
  selector: 'jhi-competitor-action',
  templateUrl: './competitor-action.component.html',
})
export class CompetitorActionComponent implements OnInit, OnDestroy {
  competitorActions?: ICompetitorAction[];
  eventSubscriber?: Subscription;

  constructor(
    protected competitorActionService: CompetitorActionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.competitorActionService.query().subscribe((res: HttpResponse<ICompetitorAction[]>) => (this.competitorActions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetitorActions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetitorAction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompetitorActions(): void {
    this.eventSubscriber = this.eventManager.subscribe('competitorActionListModification', () => this.loadAll());
  }

  delete(competitorAction: ICompetitorAction): void {
    const modalRef = this.modalService.open(CompetitorActionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competitorAction = competitorAction;
  }
}
