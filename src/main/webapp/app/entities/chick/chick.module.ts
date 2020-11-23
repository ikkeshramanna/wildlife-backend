import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { ChickComponent } from './chick.component';
import { ChickDetailComponent } from './chick-detail.component';
import { ChickUpdateComponent } from './chick-update.component';
import { ChickDeleteDialogComponent } from './chick-delete-dialog.component';
import { chickRoute } from './chick.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(chickRoute)],
  declarations: [ChickComponent, ChickDetailComponent, ChickUpdateComponent, ChickDeleteDialogComponent],
  entryComponents: [ChickDeleteDialogComponent],
})
export class WildlifeChickModule {}
