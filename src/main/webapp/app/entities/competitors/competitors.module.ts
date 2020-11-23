import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { CompetitorsComponent } from './competitors.component';
import { CompetitorsDetailComponent } from './competitors-detail.component';
import { CompetitorsUpdateComponent } from './competitors-update.component';
import { CompetitorsDeleteDialogComponent } from './competitors-delete-dialog.component';
import { competitorsRoute } from './competitors.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(competitorsRoute)],
  declarations: [CompetitorsComponent, CompetitorsDetailComponent, CompetitorsUpdateComponent, CompetitorsDeleteDialogComponent],
  entryComponents: [CompetitorsDeleteDialogComponent],
})
export class WildlifeCompetitorsModule {}
