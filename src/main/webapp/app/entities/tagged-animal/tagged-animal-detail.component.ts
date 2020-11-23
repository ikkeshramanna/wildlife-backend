import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';

@Component({
  selector: 'jhi-tagged-animal-detail',
  templateUrl: './tagged-animal-detail.component.html',
})
export class TaggedAnimalDetailComponent implements OnInit {
  taggedAnimal: ITaggedAnimal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taggedAnimal }) => (this.taggedAnimal = taggedAnimal));
  }

  previousState(): void {
    window.history.back();
  }
}
