import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';

type EntityResponseType = HttpResponse<ICompetitorImpactOnMk>;
type EntityArrayResponseType = HttpResponse<ICompetitorImpactOnMk[]>;

@Injectable({ providedIn: 'root' })
export class CompetitorImpactOnMkService {
  public resourceUrl = SERVER_API_URL + 'api/competitor-impact-on-mks';

  constructor(protected http: HttpClient) {}

  create(competitorImpactOnMk: ICompetitorImpactOnMk): Observable<EntityResponseType> {
    return this.http.post<ICompetitorImpactOnMk>(this.resourceUrl, competitorImpactOnMk, { observe: 'response' });
  }

  update(competitorImpactOnMk: ICompetitorImpactOnMk): Observable<EntityResponseType> {
    return this.http.put<ICompetitorImpactOnMk>(this.resourceUrl, competitorImpactOnMk, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetitorImpactOnMk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetitorImpactOnMk[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
