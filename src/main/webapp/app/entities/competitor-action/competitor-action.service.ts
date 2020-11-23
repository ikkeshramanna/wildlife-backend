import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetitorAction } from 'app/shared/model/competitor-action.model';

type EntityResponseType = HttpResponse<ICompetitorAction>;
type EntityArrayResponseType = HttpResponse<ICompetitorAction[]>;

@Injectable({ providedIn: 'root' })
export class CompetitorActionService {
  public resourceUrl = SERVER_API_URL + 'api/competitor-actions';

  constructor(protected http: HttpClient) {}

  create(competitorAction: ICompetitorAction): Observable<EntityResponseType> {
    return this.http.post<ICompetitorAction>(this.resourceUrl, competitorAction, { observe: 'response' });
  }

  update(competitorAction: ICompetitorAction): Observable<EntityResponseType> {
    return this.http.put<ICompetitorAction>(this.resourceUrl, competitorAction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetitorAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetitorAction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
