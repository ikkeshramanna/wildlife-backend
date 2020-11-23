import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WildlifeSharedModule } from 'app/shared/shared.module';
import { SpeciesCategoryComponent } from './species-category.component';
import { SpeciesCategoryDetailComponent } from './species-category-detail.component';
import { SpeciesCategoryUpdateComponent } from './species-category-update.component';
import { SpeciesCategoryDeleteDialogComponent } from './species-category-delete-dialog.component';
import { speciesCategoryRoute } from './species-category.route';

@NgModule({
  imports: [WildlifeSharedModule, RouterModule.forChild(speciesCategoryRoute)],
  declarations: [
    SpeciesCategoryComponent,
    SpeciesCategoryDetailComponent,
    SpeciesCategoryUpdateComponent,
    SpeciesCategoryDeleteDialogComponent,
  ],
  entryComponents: [SpeciesCategoryDeleteDialogComponent],
})
export class WildlifeSpeciesCategoryModule {}
