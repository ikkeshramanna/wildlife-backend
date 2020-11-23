import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { RingingMorphsComponent } from './ringing-morphs.component';
import { RingingMorphsDetailComponent } from './ringing-morphs-detail.component';
import { RingingMorphsUpdateComponent } from './ringing-morphs-update.component';
import { RingingMorphsDeleteDialogComponent } from './ringing-morphs-delete-dialog.component';
import { ringingMorphsRoute } from './ringing-morphs.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(ringingMorphsRoute)],
  declarations: [RingingMorphsComponent, RingingMorphsDetailComponent, RingingMorphsUpdateComponent, RingingMorphsDeleteDialogComponent],
  entryComponents: [RingingMorphsDeleteDialogComponent],
})
export class WildlifeRingingMorphsModule {}
