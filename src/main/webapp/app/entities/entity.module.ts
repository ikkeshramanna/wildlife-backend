import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.WildlifeCountryModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.WildlifeEmployeeModule),
      },
      {
        path: 'sighting',
        loadChildren: () => import('./sighting/sighting.module').then(m => m.WildlifeSightingModule),
      },
      {
        path: 'birds-identified',
        loadChildren: () => import('./birds-identified/birds-identified.module').then(m => m.WildlifeBirdsIdentifiedModule),
      },
      {
        path: 'bird-behaviour',
        loadChildren: () => import('./bird-behaviour/bird-behaviour.module').then(m => m.WildlifeBirdBehaviourModule),
      },
      {
        path: 'eggs-and-chick',
        loadChildren: () => import('./eggs-and-chick/eggs-and-chick.module').then(m => m.WildlifeEggsAndChickModule),
      },
      {
        path: 'egg',
        loadChildren: () => import('./egg/egg.module').then(m => m.WildlifeEggModule),
      },
      {
        path: 'chick',
        loadChildren: () => import('./chick/chick.module').then(m => m.WildlifeChickModule),
      },
      {
        path: 'competitors',
        loadChildren: () => import('./competitors/competitors.module').then(m => m.WildlifeCompetitorsModule),
      },
      {
        path: 'competitor-impact-on-mk',
        loadChildren: () =>
          import('./competitor-impact-on-mk/competitor-impact-on-mk.module').then(m => m.WildlifeCompetitorImpactOnMkModule),
      },
      {
        path: 'competitor-action',
        loadChildren: () => import('./competitor-action/competitor-action.module').then(m => m.WildlifeCompetitorActionModule),
      },
      {
        path: 'feeding-observation',
        loadChildren: () => import('./feeding-observation/feeding-observation.module').then(m => m.WildlifeFeedingObservationModule),
      },
      {
        path: 'nest-site-overview',
        loadChildren: () => import('./nest-site-overview/nest-site-overview.module').then(m => m.WildlifeNestSiteOverviewModule),
      },
      {
        path: 'maintenance',
        loadChildren: () => import('./maintenance/maintenance.module').then(m => m.WildlifeMaintenanceModule),
      },
      {
        path: 'ringing-morphs',
        loadChildren: () => import('./ringing-morphs/ringing-morphs.module').then(m => m.WildlifeRingingMorphsModule),
      },
      {
        path: 'species-category',
        loadChildren: () => import('./species-category/species-category.module').then(m => m.WildlifeSpeciesCategoryModule),
      },
      {
        path: 'species',
        loadChildren: () => import('./species/species.module').then(m => m.WildlifeSpeciesModule),
      },
      {
        path: 'tagged-animal',
        loadChildren: () => import('./tagged-animal/tagged-animal.module').then(m => m.WildlifeTaggedAnimalModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.WildlifePhotoModule),
      },
      {
        path: 'observation-location',
        loadChildren: () => import('./observation-location/observation-location.module').then(m => m.WildlifeObservationLocationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class WildlifeEntityModule {}
