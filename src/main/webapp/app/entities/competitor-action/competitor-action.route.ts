import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompetitorAction, CompetitorAction } from 'app/shared/model/competitor-action.model';
import { CompetitorActionService } from './competitor-action.service';
import { CompetitorActionComponent } from './competitor-action.component';
import { CompetitorActionDetailComponent } from './competitor-action-detail.component';
import { CompetitorActionUpdateComponent } from './competitor-action-update.component';

@Injectable({ providedIn: 'root' })
export class CompetitorActionResolve implements Resolve<ICompetitorAction> {
  constructor(private service: CompetitorActionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompetitorAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((competitorAction: HttpResponse<CompetitorAction>) => {
          if (competitorAction.body) {
            return of(competitorAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompetitorAction());
  }
}

export const competitorActionRoute: Routes = [
  {
    path: '',
    component: CompetitorActionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompetitorActionDetailComponent,
    resolve: {
      competitorAction: CompetitorActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompetitorActionUpdateComponent,
    resolve: {
      competitorAction: CompetitorActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompetitorActionUpdateComponent,
    resolve: {
      competitorAction: CompetitorActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitorAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
