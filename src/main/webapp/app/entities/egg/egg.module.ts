import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { EggComponent } from './egg.component';
import { EggDetailComponent } from './egg-detail.component';
import { EggUpdateComponent } from './egg-update.component';
import { EggDeleteDialogComponent } from './egg-delete-dialog.component';
import { eggRoute } from './egg.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(eggRoute)],
  declarations: [EggComponent, EggDetailComponent, EggUpdateComponent, EggDeleteDialogComponent],
  entryComponents: [EggDeleteDialogComponent],
})
export class WildlifeEggModule {}
