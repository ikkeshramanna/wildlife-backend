import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';

@Component({
  selector: 'jhi-birds-identified-detail',
  templateUrl: './birds-identified-detail.component.html',
})
export class BirdsIdentifiedDetailComponent implements OnInit {
  birdsIdentified: IBirdsIdentified | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ birdsIdentified }) => (this.birdsIdentified = birdsIdentified));
  }

  previousState(): void {
    window.history.back();
  }
}
