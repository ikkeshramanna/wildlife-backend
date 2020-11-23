import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { TaggedAnimalService } from './tagged-animal.service';
import { TaggedAnimalDeleteDialogComponent } from './tagged-animal-delete-dialog.component';

@Component({
  selector: 'jhi-tagged-animal',
  templateUrl: './tagged-animal.component.html',
})
export class TaggedAnimalComponent implements OnInit, OnDestroy {
  taggedAnimals?: ITaggedAnimal[];
  eventSubscriber?: Subscription;

  constructor(
    protected taggedAnimalService: TaggedAnimalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.taggedAnimalService.query().subscribe((res: HttpResponse<ITaggedAnimal[]>) => (this.taggedAnimals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaggedAnimals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaggedAnimal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaggedAnimals(): void {
    this.eventSubscriber = this.eventManager.subscribe('taggedAnimalListModification', () => this.loadAll());
  }

  delete(taggedAnimal: ITaggedAnimal): void {
    const modalRef = this.modalService.open(TaggedAnimalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taggedAnimal = taggedAnimal;
  }
}
