import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetitors } from 'app/shared/model/competitors.model';
import { CompetitorsService } from './competitors.service';
import { CompetitorsDeleteDialogComponent } from './competitors-delete-dialog.component';

@Component({
  selector: 'jhi-competitors',
  templateUrl: './competitors.component.html',
})
export class CompetitorsComponent implements OnInit, OnDestroy {
  competitors?: ICompetitors[];
  eventSubscriber?: Subscription;

  constructor(
    protected competitorsService: CompetitorsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.competitorsService.query().subscribe((res: HttpResponse<ICompetitors[]>) => (this.competitors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetitors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetitors): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompetitors(): void {
    this.eventSubscriber = this.eventManager.subscribe('competitorsListModification', () => this.loadAll());
  }

  delete(competitors: ICompetitors): void {
    const modalRef = this.modalService.open(CompetitorsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competitors = competitors;
  }
}
