import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEgg } from 'app/shared/model/egg.model';

type EntityResponseType = HttpResponse<IEgg>;
type EntityArrayResponseType = HttpResponse<IEgg[]>;

@Injectable({ providedIn: 'root' })
export class EggService {
  public resourceUrl = SERVER_API_URL + 'api/eggs';

  constructor(protected http: HttpClient) {}

  create(egg: IEgg): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(egg);
    return this.http
      .post<IEgg>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(egg: IEgg): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(egg);
    return this.http
      .put<IEgg>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEgg>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEgg[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(egg: IEgg): IEgg {
    const copy: IEgg = Object.assign({}, egg, {
      eggLayDate: egg.eggLayDate && egg.eggLayDate.isValid() ? egg.eggLayDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.eggLayDate = res.body.eggLayDate ? moment(res.body.eggLayDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((egg: IEgg) => {
        egg.eggLayDate = egg.eggLayDate ? moment(egg.eggLayDate) : undefined;
      });
    }
    return res;
  }
}
