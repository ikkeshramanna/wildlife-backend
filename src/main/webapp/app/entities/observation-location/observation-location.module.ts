import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { ObservationLocationComponent } from './observation-location.component';
import { ObservationLocationDetailComponent } from './observation-location-detail.component';
import { ObservationLocationUpdateComponent } from './observation-location-update.component';
import { ObservationLocationDeleteDialogComponent } from './observation-location-delete-dialog.component';
import { observationLocationRoute } from './observation-location.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(observationLocationRoute)],
  declarations: [
    ObservationLocationComponent,
    ObservationLocationDetailComponent,
    ObservationLocationUpdateComponent,
    ObservationLocationDeleteDialogComponent,
  ],
  entryComponents: [ObservationLocationDeleteDialogComponent],
})
export class WildlifeObservationLocationModule {}
