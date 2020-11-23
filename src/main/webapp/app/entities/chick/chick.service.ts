import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChick } from 'app/shared/model/chick.model';

type EntityResponseType = HttpResponse<IChick>;
type EntityArrayResponseType = HttpResponse<IChick[]>;

@Injectable({ providedIn: 'root' })
export class ChickService {
  public resourceUrl = SERVER_API_URL + 'api/chicks';

  constructor(protected http: HttpClient) {}

  create(chick: IChick): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chick);
    return this.http
      .post<IChick>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chick: IChick): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chick);
    return this.http
      .put<IChick>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChick>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChick[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chick: IChick): IChick {
    const copy: IChick = Object.assign({}, chick, {
      hatchDate: chick.hatchDate && chick.hatchDate.isValid() ? chick.hatchDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hatchDate = res.body.hatchDate ? moment(res.body.hatchDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chick: IChick) => {
        chick.hatchDate = chick.hatchDate ? moment(chick.hatchDate) : undefined;
      });
    }
    return res;
  }
}
