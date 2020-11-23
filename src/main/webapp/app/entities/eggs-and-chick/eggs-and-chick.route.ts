import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEggsAndChick, EggsAndChick } from 'app/shared/model/eggs-and-chick.model';
import { EggsAndChickService } from './eggs-and-chick.service';
import { EggsAndChickComponent } from './eggs-and-chick.component';
import { EggsAndChickDetailComponent } from './eggs-and-chick-detail.component';
import { EggsAndChickUpdateComponent } from './eggs-and-chick-update.component';

@Injectable({ providedIn: 'root' })
export class EggsAndChickResolve implements Resolve<IEggsAndChick> {
  constructor(private service: EggsAndChickService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEggsAndChick> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eggsAndChick: HttpResponse<EggsAndChick>) => {
          if (eggsAndChick.body) {
            return of(eggsAndChick.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EggsAndChick());
  }
}

export const eggsAndChickRoute: Routes = [
  {
    path: '',
    component: EggsAndChickComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.eggsAndChick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EggsAndChickDetailComponent,
    resolve: {
      eggsAndChick: EggsAndChickResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.eggsAndChick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EggsAndChickUpdateComponent,
    resolve: {
      eggsAndChick: EggsAndChickResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.eggsAndChick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EggsAndChickUpdateComponent,
    resolve: {
      eggsAndChick: EggsAndChickResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.eggsAndChick.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
