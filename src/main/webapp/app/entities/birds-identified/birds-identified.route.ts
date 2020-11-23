import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBirdsIdentified, BirdsIdentified } from 'app/shared/model/birds-identified.model';
import { BirdsIdentifiedService } from './birds-identified.service';
import { BirdsIdentifiedComponent } from './birds-identified.component';
import { BirdsIdentifiedDetailComponent } from './birds-identified-detail.component';
import { BirdsIdentifiedUpdateComponent } from './birds-identified-update.component';

@Injectable({ providedIn: 'root' })
export class BirdsIdentifiedResolve implements Resolve<IBirdsIdentified> {
  constructor(private service: BirdsIdentifiedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBirdsIdentified> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((birdsIdentified: HttpResponse<BirdsIdentified>) => {
          if (birdsIdentified.body) {
            return of(birdsIdentified.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BirdsIdentified());
  }
}

export const birdsIdentifiedRoute: Routes = [
  {
    path: '',
    component: BirdsIdentifiedComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdsIdentified.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BirdsIdentifiedDetailComponent,
    resolve: {
      birdsIdentified: BirdsIdentifiedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdsIdentified.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BirdsIdentifiedUpdateComponent,
    resolve: {
      birdsIdentified: BirdsIdentifiedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdsIdentified.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BirdsIdentifiedUpdateComponent,
    resolve: {
      birdsIdentified: BirdsIdentifiedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdsIdentified.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
