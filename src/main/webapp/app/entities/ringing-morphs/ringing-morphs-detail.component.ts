import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRingingMorphs } from 'app/shared/model/ringing-morphs.model';

@Component({
  selector: 'jhi-ringing-morphs-detail',
  templateUrl: './ringing-morphs-detail.component.html',
})
export class RingingMorphsDetailComponent implements OnInit {
  ringingMorphs: IRingingMorphs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ringingMorphs }) => (this.ringingMorphs = ringingMorphs));
  }

  previousState(): void {
    window.history.back();
  }
}
