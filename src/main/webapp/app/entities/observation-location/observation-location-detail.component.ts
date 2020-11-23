import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObservationLocation } from 'app/shared/model/observation-location.model';

@Component({
  selector: 'jhi-observation-location-detail',
  templateUrl: './observation-location-detail.component.html',
})
export class ObservationLocationDetailComponent implements OnInit {
  observationLocation: IObservationLocation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observationLocation }) => (this.observationLocation = observationLocation));
  }

  previousState(): void {
    window.history.back();
  }
}
