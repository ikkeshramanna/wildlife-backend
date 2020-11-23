import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeedingObservation } from 'app/shared/model/feeding-observation.model';

@Component({
  selector: 'jhi-feeding-observation-detail',
  templateUrl: './feeding-observation-detail.component.html',
})
export class FeedingObservationDetailComponent implements OnInit {
  feedingObservation: IFeedingObservation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedingObservation }) => (this.feedingObservation = feedingObservation));
  }

  previousState(): void {
    window.history.back();
  }
}
