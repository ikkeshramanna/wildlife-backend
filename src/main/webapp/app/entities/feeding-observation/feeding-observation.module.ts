import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { FeedingObservationComponent } from './feeding-observation.component';
import { FeedingObservationDetailComponent } from './feeding-observation-detail.component';
import { FeedingObservationUpdateComponent } from './feeding-observation-update.component';
import { FeedingObservationDeleteDialogComponent } from './feeding-observation-delete-dialog.component';
import { feedingObservationRoute } from './feeding-observation.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(feedingObservationRoute)],
  declarations: [
    FeedingObservationComponent,
    FeedingObservationDetailComponent,
    FeedingObservationUpdateComponent,
    FeedingObservationDeleteDialogComponent,
  ],
  entryComponents: [FeedingObservationDeleteDialogComponent],
})
export class WildlifeFeedingObservationModule {}
