import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObservationLocation, ObservationLocation } from 'app/shared/model/observation-location.model';
import { ObservationLocationService } from './observation-location.service';
import { ObservationLocationComponent } from './observation-location.component';
import { ObservationLocationDetailComponent } from './observation-location-detail.component';
import { ObservationLocationUpdateComponent } from './observation-location-update.component';

@Injectable({ providedIn: 'root' })
export class ObservationLocationResolve implements Resolve<IObservationLocation> {
  constructor(private service: ObservationLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObservationLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((observationLocation: HttpResponse<ObservationLocation>) => {
          if (observationLocation.body) {
            return of(observationLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ObservationLocation());
  }
}

export const observationLocationRoute: Routes = [
  {
    path: '',
    component: ObservationLocationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.observationLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObservationLocationDetailComponent,
    resolve: {
      observationLocation: ObservationLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.observationLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObservationLocationUpdateComponent,
    resolve: {
      observationLocation: ObservationLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.observationLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObservationLocationUpdateComponent,
    resolve: {
      observationLocation: ObservationLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.observationLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
