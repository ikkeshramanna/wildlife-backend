import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBirdBehaviour, BirdBehaviour } from 'app/shared/model/bird-behaviour.model';
import { BirdBehaviourService } from './bird-behaviour.service';
import { BirdBehaviourComponent } from './bird-behaviour.component';
import { BirdBehaviourDetailComponent } from './bird-behaviour-detail.component';
import { BirdBehaviourUpdateComponent } from './bird-behaviour-update.component';

@Injectable({ providedIn: 'root' })
export class BirdBehaviourResolve implements Resolve<IBirdBehaviour> {
  constructor(private service: BirdBehaviourService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBirdBehaviour> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((birdBehaviour: HttpResponse<BirdBehaviour>) => {
          if (birdBehaviour.body) {
            return of(birdBehaviour.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BirdBehaviour());
  }
}

export const birdBehaviourRoute: Routes = [
  {
    path: '',
    component: BirdBehaviourComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdBehaviour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BirdBehaviourDetailComponent,
    resolve: {
      birdBehaviour: BirdBehaviourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdBehaviour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BirdBehaviourUpdateComponent,
    resolve: {
      birdBehaviour: BirdBehaviourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdBehaviour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BirdBehaviourUpdateComponent,
    resolve: {
      birdBehaviour: BirdBehaviourResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.birdBehaviour.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
