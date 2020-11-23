import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBirdsIdentified } from 'app/shared/model/birds-identified.model';

type EntityResponseType = HttpResponse<IBirdsIdentified>;
type EntityArrayResponseType = HttpResponse<IBirdsIdentified[]>;

@Injectable({ providedIn: 'root' })
export class BirdsIdentifiedService {
  public resourceUrl = SERVER_API_URL + 'api/birds-identifieds';

  constructor(protected http: HttpClient) {}

  create(birdsIdentified: IBirdsIdentified): Observable<EntityResponseType> {
    return this.http.post<IBirdsIdentified>(this.resourceUrl, birdsIdentified, { observe: 'response' });
  }

  update(birdsIdentified: IBirdsIdentified): Observable<EntityResponseType> {
    return this.http.put<IBirdsIdentified>(this.resourceUrl, birdsIdentified, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBirdsIdentified>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBirdsIdentified[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
