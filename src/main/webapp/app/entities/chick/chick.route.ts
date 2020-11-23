import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChick, Chick } from 'app/shared/model/chick.model';
import { ChickService } from './chick.service';
import { ChickComponent } from './chick.component';
import { ChickDetailComponent } from './chick-detail.component';
import { ChickUpdateComponent } from './chick-update.component';

@Injectable({ providedIn: 'root' })
export class ChickResolve implements Resolve<IChick> {
  constructor(private service: ChickService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChick> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chick: HttpResponse<Chick>) => {
          if (chick.body) {
            return of(chick.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Chick());
  }
}

export const chickRoute: Routes = [
  {
    path: '',
    component: ChickComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.chick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChickDetailComponent,
    resolve: {
      chick: ChickResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.chick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChickUpdateComponent,
    resolve: {
      chick: ChickResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.chick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChickUpdateComponent,
    resolve: {
      chick: ChickResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.chick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
