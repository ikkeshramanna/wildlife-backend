import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { TaggedAnimalComponent } from './tagged-animal.component';
import { TaggedAnimalDetailComponent } from './tagged-animal-detail.component';
import { TaggedAnimalUpdateComponent } from './tagged-animal-update.component';
import { TaggedAnimalDeleteDialogComponent } from './tagged-animal-delete-dialog.component';
import { taggedAnimalRoute } from './tagged-animal.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(taggedAnimalRoute)],
  declarations: [TaggedAnimalComponent, TaggedAnimalDetailComponent, TaggedAnimalUpdateComponent, TaggedAnimalDeleteDialogComponent],
  entryComponents: [TaggedAnimalDeleteDialogComponent],
})
export class WildlifeTaggedAnimalModule {}
