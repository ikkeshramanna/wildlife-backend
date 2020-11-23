import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFeedingObservation, FeedingObservation } from 'app/shared/model/feeding-observation.model';
import { FeedingObservationService } from './feeding-observation.service';
import { FeedingObservationComponent } from './feeding-observation.component';
import { FeedingObservationDetailComponent } from './feeding-observation-detail.component';
import { FeedingObservationUpdateComponent } from './feeding-observation-update.component';

@Injectable({ providedIn: 'root' })
export class FeedingObservationResolve implements Resolve<IFeedingObservation> {
  constructor(private service: FeedingObservationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeedingObservation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((feedingObservation: HttpResponse<FeedingObservation>) => {
          if (feedingObservation.body) {
            return of(feedingObservation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FeedingObservation());
  }
}

export const feedingObservationRoute: Routes = [
  {
    path: '',
    component: FeedingObservationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.feedingObservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeedingObservationDetailComponent,
    resolve: {
      feedingObservation: FeedingObservationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.feedingObservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeedingObservationUpdateComponent,
    resolve: {
      feedingObservation: FeedingObservationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.feedingObservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeedingObservationUpdateComponent,
    resolve: {
      feedingObservation: FeedingObservationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.feedingObservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
