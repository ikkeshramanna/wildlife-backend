import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INestSiteOverview } from 'app/shared/model/nest-site-overview.model';

type EntityResponseType = HttpResponse<INestSiteOverview>;
type EntityArrayResponseType = HttpResponse<INestSiteOverview[]>;

@Injectable({ providedIn: 'root' })
export class NestSiteOverviewService {
  public resourceUrl = SERVER_API_URL + 'api/nest-site-overviews';

  constructor(protected http: HttpClient) {}

  create(nestSiteOverview: INestSiteOverview): Observable<EntityResponseType> {
    return this.http.post<INestSiteOverview>(this.resourceUrl, nestSiteOverview, { observe: 'response' });
  }

  update(nestSiteOverview: INestSiteOverview): Observable<EntityResponseType> {
    return this.http.put<INestSiteOverview>(this.resourceUrl, nestSiteOverview, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INestSiteOverview>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INestSiteOverview[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
