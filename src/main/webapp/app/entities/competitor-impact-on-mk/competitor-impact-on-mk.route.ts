import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompetitorImpactOnMk, CompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';
import { CompetitorImpactOnMkService } from './competitor-impact-on-mk.service';
import { CompetitorImpactOnMkComponent } from './competitor-impact-on-mk.component';
import { CompetitorImpactOnMkDetailComponent } from './competitor-impact-on-mk-detail.component';
import { CompetitorImpactOnMkUpdateComponent } from './competitor-impact-on-mk-update.component';

@Injectable({ providedIn: 'root' })
export class CompetitorImpactOnMkResolve implements Resolve<ICompetitorImpactOnMk> {
  constructor(private service: CompetitorImpactOnMkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompetitorImpactOnMk> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((competitorImpactOnMk: HttpResponse<CompetitorImpactOnMk>) => {
          if (competitorImpactOnMk.body) {
            return of(competitorImpactOnMk.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompetitorImpactOnMk());
  }
}

export const competitorImpactOnMkRoute: Routes = [
  {
    path: '',
    component: CompetitorImpactOnMkComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorImpactOnMk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompetitorImpactOnMkDetailComponent,
    resolve: {
      competitorImpactOnMk: CompetitorImpactOnMkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorImpactOnMk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompetitorImpactOnMkUpdateComponent,
    resolve: {
      competitorImpactOnMk: CompetitorImpactOnMkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorImpactOnMk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompetitorImpactOnMkUpdateComponent,
    resolve: {
      competitorImpactOnMk: CompetitorImpactOnMkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorImpactOnMk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
