import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { NestSiteOverviewComponent } from './nest-site-overview.component';
import { NestSiteOverviewDetailComponent } from './nest-site-overview-detail.component';
import { NestSiteOverviewUpdateComponent } from './nest-site-overview-update.component';
import { NestSiteOverviewDeleteDialogComponent } from './nest-site-overview-delete-dialog.component';
import { nestSiteOverviewRoute } from './nest-site-overview.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(nestSiteOverviewRoute)],
  declarations: [
    NestSiteOverviewComponent,
    NestSiteOverviewDetailComponent,
    NestSiteOverviewUpdateComponent,
    NestSiteOverviewDeleteDialogComponent,
  ],
  entryComponents: [NestSiteOverviewDeleteDialogComponent],
})
export class WildlifeNestSiteOverviewModule {}
