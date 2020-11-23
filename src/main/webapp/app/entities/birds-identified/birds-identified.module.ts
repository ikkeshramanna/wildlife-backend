import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { BirdsIdentifiedComponent } from './birds-identified.component';
import { BirdsIdentifiedDetailComponent } from './birds-identified-detail.component';
import { BirdsIdentifiedUpdateComponent } from './birds-identified-update.component';
import { BirdsIdentifiedDeleteDialogComponent } from './birds-identified-delete-dialog.component';
import { birdsIdentifiedRoute } from './birds-identified.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(birdsIdentifiedRoute)],
  declarations: [
    BirdsIdentifiedComponent,
    BirdsIdentifiedDetailComponent,
    BirdsIdentifiedUpdateComponent,
    BirdsIdentifiedDeleteDialogComponent,
  ],
  entryComponents: [BirdsIdentifiedDeleteDialogComponent],
})
export class WildlifeBirdsIdentifiedModule {}
