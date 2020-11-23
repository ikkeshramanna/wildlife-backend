import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetitorAction } from 'app/shared/model/competitor-action.model';

@Component({
  selector: 'jhi-competitor-action-detail',
  templateUrl: './competitor-action-detail.component.html',
})
export class CompetitorActionDetailComponent implements OnInit {
  competitorAction: ICompetitorAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitorAction }) => (this.competitorAction = competitorAction));
  }

  previousState(): void {
    window.history.back();
  }
}
