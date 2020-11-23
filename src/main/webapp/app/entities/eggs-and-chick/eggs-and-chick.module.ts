import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { EggsAndChickComponent } from './eggs-and-chick.component';
import { EggsAndChickDetailComponent } from './eggs-and-chick-detail.component';
import { EggsAndChickUpdateComponent } from './eggs-and-chick-update.component';
import { EggsAndChickDeleteDialogComponent } from './eggs-and-chick-delete-dialog.component';
import { eggsAndChickRoute } from './eggs-and-chick.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(eggsAndChickRoute)],
  declarations: [EggsAndChickComponent, EggsAndChickDetailComponent, EggsAndChickUpdateComponent, EggsAndChickDeleteDialogComponent],
  entryComponents: [EggsAndChickDeleteDialogComponent],
})
export class WildlifeEggsAndChickModule {}
