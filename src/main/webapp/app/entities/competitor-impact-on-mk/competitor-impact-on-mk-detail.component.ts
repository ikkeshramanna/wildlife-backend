import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';

@Component({
  selector: 'jhi-competitor-impact-on-mk-detail',
  templateUrl: './competitor-impact-on-mk-detail.component.html',
})
export class CompetitorImpactOnMkDetailComponent implements OnInit {
  competitorImpactOnMk: ICompetitorImpactOnMk | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitorImpactOnMk }) => (this.competitorImpactOnMk = competitorImpactOnMk));
  }

  previousState(): void {
    window.history.back();
  }
}
