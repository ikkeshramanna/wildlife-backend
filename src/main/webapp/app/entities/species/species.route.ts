import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpecies, Species } from 'app/shared/model/species.model';
import { SpeciesService } from './species.service';
import { SpeciesComponent } from './species.component';
import { SpeciesDetailComponent } from './species-detail.component';
import { SpeciesUpdateComponent } from './species-update.component';

@Injectable({ providedIn: 'root' })
export class SpeciesResolve implements Resolve<ISpecies> {
  constructor(private service: SpeciesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecies> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((species: HttpResponse<Species>) => {
          if (species.body) {
            return of(species.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Species());
  }
}

export const speciesRoute: Routes = [
  {
    path: '',
    component: SpeciesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.species.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpeciesDetailComponent,
    resolve: {
      species: SpeciesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.species.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpeciesUpdateComponent,
    resolve: {
      species: SpeciesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.species.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpeciesUpdateComponent,
    resolve: {
      species: SpeciesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.species.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
