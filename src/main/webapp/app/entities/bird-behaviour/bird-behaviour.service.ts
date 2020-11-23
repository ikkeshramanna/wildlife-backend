import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBirdBehaviour } from 'app/shared/model/bird-behaviour.model';

type EntityResponseType = HttpResponse<IBirdBehaviour>;
type EntityArrayResponseType = HttpResponse<IBirdBehaviour[]>;

@Injectable({ providedIn: 'root' })
export class BirdBehaviourService {
  public resourceUrl = SERVER_API_URL + 'api/bird-behaviours';

  constructor(protected http: HttpClient) {}

  create(birdBehaviour: IBirdBehaviour): Observable<EntityResponseType> {
    return this.http.post<IBirdBehaviour>(this.resourceUrl, birdBehaviour, { observe: 'response' });
  }

  update(birdBehaviour: IBirdBehaviour): Observable<EntityResponseType> {
    return this.http.put<IBirdBehaviour>(this.resourceUrl, birdBehaviour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBirdBehaviour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBirdBehaviour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
