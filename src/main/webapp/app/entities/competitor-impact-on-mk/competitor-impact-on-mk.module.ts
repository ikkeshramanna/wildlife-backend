import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { CompetitorImpactOnMkComponent } from './competitor-impact-on-mk.component';
import { CompetitorImpactOnMkDetailComponent } from './competitor-impact-on-mk-detail.component';
import { CompetitorImpactOnMkUpdateComponent } from './competitor-impact-on-mk-update.component';
import { CompetitorImpactOnMkDeleteDialogComponent } from './competitor-impact-on-mk-delete-dialog.component';
import { competitorImpactOnMkRoute } from './competitor-impact-on-mk.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(competitorImpactOnMkRoute)],
  declarations: [
    CompetitorImpactOnMkComponent,
    CompetitorImpactOnMkDetailComponent,
    CompetitorImpactOnMkUpdateComponent,
    CompetitorImpactOnMkDeleteDialogComponent,
  ],
  entryComponents: [CompetitorImpactOnMkDeleteDialogComponent],
})
export class WildlifeCompetitorImpactOnMkModule {}
