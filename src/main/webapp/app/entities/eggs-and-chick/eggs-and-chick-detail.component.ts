import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';

@Component({
  selector: 'jhi-eggs-and-chick-detail',
  templateUrl: './eggs-and-chick-detail.component.html',
})
export class EggsAndChickDetailComponent implements OnInit {
  eggsAndChick: IEggsAndChick | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eggsAndChick }) => (this.eggsAndChick = eggsAndChick));
  }

  previousState(): void {
    window.history.back();
  }
}
