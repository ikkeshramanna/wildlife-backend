import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { MaintenanceComponent } from './maintenance.component';
import { MaintenanceDetailComponent } from './maintenance-detail.component';
import { MaintenanceUpdateComponent } from './maintenance-update.component';
import { MaintenanceDeleteDialogComponent } from './maintenance-delete-dialog.component';
import { maintenanceRoute } from './maintenance.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(maintenanceRoute)],
  declarations: [MaintenanceComponent, MaintenanceDetailComponent, MaintenanceUpdateComponent, MaintenanceDeleteDialogComponent],
  entryComponents: [MaintenanceDeleteDialogComponent],
})
export class WildlifeMaintenanceModule {}
