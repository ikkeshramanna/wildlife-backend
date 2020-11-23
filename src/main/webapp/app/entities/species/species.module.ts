import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { SpeciesComponent } from './species.component';
import { SpeciesDetailComponent } from './species-detail.component';
import { SpeciesUpdateComponent } from './species-update.component';
import { SpeciesDeleteDialogComponent } from './species-delete-dialog.component';
import { speciesRoute } from './species.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(speciesRoute)],
  declarations: [SpeciesComponent, SpeciesDetailComponent, SpeciesUpdateComponent, SpeciesDeleteDialogComponent],
  entryComponents: [SpeciesDeleteDialogComponent],
})
export class WildlifeSpeciesModule {}
