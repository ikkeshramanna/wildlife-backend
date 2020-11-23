import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetitors } from 'app/shared/model/competitors.model';

@Component({
  selector: 'jhi-competitors-detail',
  templateUrl: './competitors-detail.component.html',
})
export class CompetitorsDetailComponent implements OnInit {
  competitors: ICompetitors | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competitors }) => (this.competitors = competitors));
  }

  previousState(): void {
    window.history.back();
  }
}
