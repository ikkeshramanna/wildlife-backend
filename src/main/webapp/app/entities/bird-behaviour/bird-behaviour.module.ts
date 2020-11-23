import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { BirdBehaviourComponent } from './bird-behaviour.component';
import { BirdBehaviourDetailComponent } from './bird-behaviour-detail.component';
import { BirdBehaviourUpdateComponent } from './bird-behaviour-update.component';
import { BirdBehaviourDeleteDialogComponent } from './bird-behaviour-delete-dialog.component';
import { birdBehaviourRoute } from './bird-behaviour.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(birdBehaviourRoute)],
  declarations: [BirdBehaviourComponent, BirdBehaviourDetailComponent, BirdBehaviourUpdateComponent, BirdBehaviourDeleteDialogComponent],
  entryComponents: [BirdBehaviourDeleteDialogComponent],
})
export class WildlifeBirdBehaviourModule {}
