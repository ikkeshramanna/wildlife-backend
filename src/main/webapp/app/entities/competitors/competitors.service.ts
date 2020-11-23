import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetitors } from 'app/shared/model/competitors.model';

type EntityResponseType = HttpResponse<ICompetitors>;
type EntityArrayResponseType = HttpResponse<ICompetitors[]>;

@Injectable({ providedIn: 'root' })
export class CompetitorsService {
  public resourceUrl = SERVER_API_URL + 'api/competitors';

  constructor(protected http: HttpClient) {}

  create(competitors: ICompetitors): Observable<EntityResponseType> {
    return this.http.post<ICompetitors>(this.resourceUrl, competitors, { observe: 'response' });
  }

  update(competitors: ICompetitors): Observable<EntityResponseType> {
    return this.http.put<ICompetitors>(this.resourceUrl, competitors, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetitors>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetitors[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
