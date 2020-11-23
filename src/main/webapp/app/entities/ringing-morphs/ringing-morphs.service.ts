import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRingingMorphs } from 'app/shared/model/ringing-morphs.model';

type EntityResponseType = HttpResponse<IRingingMorphs>;
type EntityArrayResponseType = HttpResponse<IRingingMorphs[]>;

@Injectable({ providedIn: 'root' })
export class RingingMorphsService {
  public resourceUrl = SERVER_API_URL + 'api/ringing-morphs';

  constructor(protected http: HttpClient) {}

  create(ringingMorphs: IRingingMorphs): Observable<EntityResponseType> {
    return this.http.post<IRingingMorphs>(this.resourceUrl, ringingMorphs, { observe: 'response' });
  }

  update(ringingMorphs: IRingingMorphs): Observable<EntityResponseType> {
    return this.http.put<IRingingMorphs>(this.resourceUrl, ringingMorphs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRingingMorphs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRingingMorphs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
