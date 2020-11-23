import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecies } from 'app/shared/model/species.model';
import { SpeciesService } from './species.service';
import { SpeciesDeleteDialogComponent } from './species-delete-dialog.component';

@Component({
  selector: 'jhi-species',
  templateUrl: './species.component.html',
})
export class SpeciesComponent implements OnInit, OnDestroy {
  species?: ISpecies[];
  eventSubscriber?: Subscription;

  constructor(
    protected speciesService: SpeciesService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.speciesService.query().subscribe((res: HttpResponse<ISpecies[]>) => (this.species = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSpecies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpecies): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInSpecies(): void {
    this.eventSubscriber = this.eventManager.subscribe('speciesListModification', () => this.loadAll());
  }

  delete(species: ISpecies): void {
    const modalRef = this.modalService.open(SpeciesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.species = species;
  }
}
