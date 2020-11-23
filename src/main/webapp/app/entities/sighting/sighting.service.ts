import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISighting } from 'app/shared/model/sighting.model';

type EntityResponseType = HttpResponse<ISighting>;
type EntityArrayResponseType = HttpResponse<ISighting[]>;

@Injectable({ providedIn: 'root' })
export class SightingService {
  public resourceUrl = SERVER_API_URL + 'api/sightings';

  constructor(protected http: HttpClient) {}

  create(sighting: ISighting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sighting);
    return this.http
      .post<ISighting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sighting: ISighting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sighting);
    return this.http
      .put<ISighting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISighting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISighting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sighting: ISighting): ISighting {
    const copy: ISighting = Object.assign({}, sighting, {
      date: sighting.date && sighting.date.isValid() ? sighting.date.format(DATE_FORMAT) : undefined,
      addDate: sighting.addDate && sighting.addDate.isValid() ? sighting.addDate.format(DATE_FORMAT) : undefined,
      updateDate: sighting.updateDate && sighting.updateDate.isValid() ? sighting.updateDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.addDate = res.body.addDate ? moment(res.body.addDate) : undefined;
      res.body.updateDate = res.body.updateDate ? moment(res.body.updateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sighting: ISighting) => {
        sighting.date = sighting.date ? moment(sighting.date) : undefined;
        sighting.addDate = sighting.addDate ? moment(sighting.addDate) : undefined;
        sighting.updateDate = sighting.updateDate ? moment(sighting.updateDate) : undefined;
      });
    }
    return res;
  }
}
