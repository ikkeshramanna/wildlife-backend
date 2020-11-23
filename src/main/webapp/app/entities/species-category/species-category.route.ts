import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpeciesCategory, SpeciesCategory } from 'app/shared/model/species-category.model';
import { SpeciesCategoryService } from './species-category.service';
import { SpeciesCategoryComponent } from './species-category.component';
import { SpeciesCategoryDetailComponent } from './species-category-detail.component';
import { SpeciesCategoryUpdateComponent } from './species-category-update.component';

@Injectable({ providedIn: 'root' })
export class SpeciesCategoryResolve implements Resolve<ISpeciesCategory> {
  constructor(private service: SpeciesCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpeciesCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((speciesCategory: HttpResponse<SpeciesCategory>) => {
          if (speciesCategory.body) {
            return of(speciesCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SpeciesCategory());
  }
}

export const speciesCategoryRoute: Routes = [
  {
    path: '',
    component: SpeciesCategoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.speciesCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpeciesCategoryDetailComponent,
    resolve: {
      speciesCategory: SpeciesCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.speciesCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpeciesCategoryUpdateComponent,
    resolve: {
      speciesCategory: SpeciesCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.speciesCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpeciesCategoryUpdateComponent,
    resolve: {
      speciesCategory: SpeciesCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.speciesCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
