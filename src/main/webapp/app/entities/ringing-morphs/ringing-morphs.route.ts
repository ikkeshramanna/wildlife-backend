import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRingingMorphs, RingingMorphs } from 'app/shared/model/ringing-morphs.model';
import { RingingMorphsService } from './ringing-morphs.service';
import { RingingMorphsComponent } from './ringing-morphs.component';
import { RingingMorphsDetailComponent } from './ringing-morphs-detail.component';
import { RingingMorphsUpdateComponent } from './ringing-morphs-update.component';

@Injectable({ providedIn: 'root' })
export class RingingMorphsResolve implements Resolve<IRingingMorphs> {
  constructor(private service: RingingMorphsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRingingMorphs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ringingMorphs: HttpResponse<RingingMorphs>) => {
          if (ringingMorphs.body) {
            return of(ringingMorphs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RingingMorphs());
  }
}

export const ringingMorphsRoute: Routes = [
  {
    path: '',
    component: RingingMorphsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.ringingMorphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RingingMorphsDetailComponent,
    resolve: {
      ringingMorphs: RingingMorphsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.ringingMorphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RingingMorphsUpdateComponent,
    resolve: {
      ringingMorphs: RingingMorphsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.ringingMorphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RingingMorphsUpdateComponent,
    resolve: {
      ringingMorphs: RingingMorphsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.ringingMorphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
