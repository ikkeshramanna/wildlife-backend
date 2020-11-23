import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INestSiteOverview, NestSiteOverview } from 'app/shared/model/nest-site-overview.model';
import { NestSiteOverviewService } from './nest-site-overview.service';
import { NestSiteOverviewComponent } from './nest-site-overview.component';
import { NestSiteOverviewDetailComponent } from './nest-site-overview-detail.component';
import { NestSiteOverviewUpdateComponent } from './nest-site-overview-update.component';

@Injectable({ providedIn: 'root' })
export class NestSiteOverviewResolve implements Resolve<INestSiteOverview> {
  constructor(private service: NestSiteOverviewService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INestSiteOverview> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nestSiteOverview: HttpResponse<NestSiteOverview>) => {
          if (nestSiteOverview.body) {
            return of(nestSiteOverview.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NestSiteOverview());
  }
}

export const nestSiteOverviewRoute: Routes = [
  {
    path: '',
    component: NestSiteOverviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.nestSiteOverview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NestSiteOverviewDetailComponent,
    resolve: {
      nestSiteOverview: NestSiteOverviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.nestSiteOverview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NestSiteOverviewUpdateComponent,
    resolve: {
      nestSiteOverview: NestSiteOverviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.nestSiteOverview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NestSiteOverviewUpdateComponent,
    resolve: {
      nestSiteOverview: NestSiteOverviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.nestSiteOverview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
