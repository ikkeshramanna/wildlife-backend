import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEgg, Egg } from 'app/shared/model/egg.model';
import { EggService } from './egg.service';
import { EggComponent } from './egg.component';
import { EggDetailComponent } from './egg-detail.component';
import { EggUpdateComponent } from './egg-update.component';

@Injectable({ providedIn: 'root' })
export class EggResolve implements Resolve<IEgg> {
  constructor(private service: EggService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEgg> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((egg: HttpResponse<Egg>) => {
          if (egg.body) {
            return of(egg.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Egg());
  }
}

export const eggRoute: Routes = [
  {
    path: '',
    component: EggComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.egg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EggDetailComponent,
    resolve: {
      egg: EggResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.egg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EggUpdateComponent,
    resolve: {
      egg: EggResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.egg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EggUpdateComponent,
    resolve: {
      egg: EggResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.egg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
