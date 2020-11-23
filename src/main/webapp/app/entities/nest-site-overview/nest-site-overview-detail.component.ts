import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INestSiteOverview } from 'app/shared/model/nest-site-overview.model';

@Component({
  selector: 'jhi-nest-site-overview-detail',
  templateUrl: './nest-site-overview-detail.component.html',
})
export class NestSiteOverviewDetailComponent implements OnInit {
  nestSiteOverview: INestSiteOverview | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nestSiteOverview }) => (this.nestSiteOverview = nestSiteOverview));
  }

  previousState(): void {
    window.history.back();
  }
}
