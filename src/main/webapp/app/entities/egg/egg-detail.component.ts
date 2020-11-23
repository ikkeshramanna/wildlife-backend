import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEgg } from 'app/shared/model/egg.model';

@Component({
  selector: 'jhi-egg-detail',
  templateUrl: './egg-detail.component.html',
})
export class EggDetailComponent implements OnInit {
  egg: IEgg | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ egg }) => (this.egg = egg));
  }

  previousState(): void {
    window.history.back();
  }
}
