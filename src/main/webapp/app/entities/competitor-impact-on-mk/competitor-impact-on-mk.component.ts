import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { CompetitorImpactOnMkService } from './competitor-impact-on-mk.service';
import { CompetitorImpactOnMkDeleteDialogComponent } from './competitor-impact-on-mk-delete-dialog.component';

@Component({
  selector: 'jhi-competitor-impact-on-mk',
  templateUrl: './competitor-impact-on-mk.component.html',
})
export class CompetitorImpactOnMkComponent implements OnInit, OnDestroy {
  competitorImpactOnMks?: ICompetitorImpactOnMk[];
  eventSubscriber?: Subscription;

  constructor(
    protected competitorImpactOnMkService: CompetitorImpactOnMkService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.competitorImpactOnMkService
      .query()
      .subscribe((res: HttpResponse<ICompetitorImpactOnMk[]>) => (this.competitorImpactOnMks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetitorImpactOnMks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetitorImpactOnMk): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompetitorImpactOnMks(): void {
    this.eventSubscriber = this.eventManager.subscribe('competitorImpactOnMkListModification', () => this.loadAll());
  }

  delete(competitorImpactOnMk: ICompetitorImpactOnMk): void {
    const modalRef = this.modalService.open(CompetitorImpactOnMkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competitorImpactOnMk = competitorImpactOnMk;
  }
}
