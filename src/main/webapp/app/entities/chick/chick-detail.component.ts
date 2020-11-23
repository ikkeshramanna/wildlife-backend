import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChick } from 'app/shared/model/chick.model';

@Component({
  selector: 'jhi-chick-detail',
  templateUrl: './chick-detail.component.html',
})
export class ChickDetailComponent implements OnInit {
  chick: IChick | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chick }) => (this.chick = chick));
  }

  previousState(): void {
    window.history.back();
  }
}
