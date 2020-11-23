import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBirdBehaviour } from 'app/shared/model/bird-behaviour.model';

@Component({
  selector: 'jhi-bird-behaviour-detail',
  templateUrl: './bird-behaviour-detail.component.html',
})
export class BirdBehaviourDetailComponent implements OnInit {
  birdBehaviour: IBirdBehaviour | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ birdBehaviour }) => (this.birdBehaviour = birdBehaviour));
  }

  previousState(): void {
    window.history.back();
  }
}
