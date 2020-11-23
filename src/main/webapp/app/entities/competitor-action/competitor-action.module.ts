import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { CompetitorActionComponent } from './competitor-action.component';
import { CompetitorActionDetailComponent } from './competitor-action-detail.component';
import { CompetitorActionUpdateComponent } from './competitor-action-update.component';
import { CompetitorActionDeleteDialogComponent } from './competitor-action-delete-dialog.component';
import { competitorActionRoute } from './competitor-action.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(competitorActionRoute)],
  declarations: [
    CompetitorActionComponent,
    CompetitorActionDetailComponent,
    CompetitorActionUpdateComponent,
    CompetitorActionDeleteDialogComponent,
  ],
  entryComponents: [CompetitorActionDeleteDialogComponent],
})
export class WildlifeCompetitorActionModule {}
