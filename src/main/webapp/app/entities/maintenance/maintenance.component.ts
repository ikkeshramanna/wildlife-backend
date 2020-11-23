import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMaintenance } from 'app/shared/model/maintenance.model';
import { MaintenanceService } from './maintenance.service';
import { MaintenanceDeleteDialogComponent } from './maintenance-delete-dialog.component';

@Component({
  selector: 'jhi-maintenance',
  templateUrl: './maintenance.component.html',
})
export class MaintenanceComponent implements OnInit, OnDestroy {
  maintenances?: IMaintenance[];
  eventSubscriber?: Subscription;

  constructor(
    protected maintenanceService: MaintenanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.maintenanceService.query().subscribe((res: HttpResponse<IMaintenance[]>) => (this.maintenances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMaintenances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMaintenance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMaintenances(): void {
    this.eventSubscriber = this.eventManager.subscribe('maintenanceListModification', () => this.loadAll());
  }

  delete(maintenance: IMaintenance): void {
    const modalRef = this.modalService.open(MaintenanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.maintenance = maintenance;
  }
}
