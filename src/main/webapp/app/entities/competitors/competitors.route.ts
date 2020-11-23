import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompetitors, Competitors } from 'app/shared/model/competitors.model';
import { CompetitorsService } from './competitors.service';
import { CompetitorsComponent } from './competitors.component';
import { CompetitorsDetailComponent } from './competitors-detail.component';
import { CompetitorsUpdateComponent } from './competitors-update.component';

@Injectable({ providedIn: 'root' })
export class CompetitorsResolve implements Resolve<ICompetitors> {
  constructor(private service: CompetitorsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompetitors> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((competitors: HttpResponse<Competitors>) => {
          if (competitors.body) {
            return of(competitors.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Competitors());
  }
}

export const competitorsRoute: Routes = [
  {
    path: '',
    component: CompetitorsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitors.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompetitorsDetailComponent,
    resolve: {
      competitors: CompetitorsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitors.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompetitorsUpdateComponent,
    resolve: {
      competitors: CompetitorsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitors.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompetitorsUpdateComponent,
    resolve: {
      competitors: CompetitorsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.competitors.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
