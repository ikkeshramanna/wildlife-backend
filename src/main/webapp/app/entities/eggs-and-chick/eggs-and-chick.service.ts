import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEggsAndChick } from 'app/shared/model/eggs-and-chick.model';

type EntityResponseType = HttpResponse<IEggsAndChick>;
type EntityArrayResponseType = HttpResponse<IEggsAndChick[]>;

@Injectable({ providedIn: 'root' })
export class EggsAndChickService {
  public resourceUrl = SERVER_API_URL + 'api/eggs-and-chicks';

  constructor(protected http: HttpClient) {}

  create(eggsAndChick: IEggsAndChick): Observable<EntityResponseType> {
    return this.http.post<IEggsAndChick>(this.resourceUrl, eggsAndChick, { observe: 'response' });
  }

  update(eggsAndChick: IEggsAndChick): Observable<EntityResponseType> {
    return this.http.put<IEggsAndChick>(this.resourceUrl, eggsAndChick, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEggsAndChick>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEggsAndChick[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
