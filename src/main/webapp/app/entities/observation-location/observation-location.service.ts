import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObservationLocation } from 'app/shared/model/observation-location.model';

type EntityResponseType = HttpResponse<IObservationLocation>;
type EntityArrayResponseType = HttpResponse<IObservationLocation[]>;

@Injectable({ providedIn: 'root' })
export class ObservationLocationService {
  public resourceUrl = SERVER_API_URL + 'api/observation-locations';

  constructor(protected http: HttpClient) {}

  create(observationLocation: IObservationLocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observationLocation);
    return this.http
      .post<IObservationLocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(observationLocation: IObservationLocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observationLocation);
    return this.http
      .put<IObservationLocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObservationLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObservationLocation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(observationLocation: IObservationLocation): IObservationLocation {
    const copy: IObservationLocation = Object.assign({}, observationLocation, {
      addDate:
        observationLocation.addDate && observationLocation.addDate.isValid() ? observationLocation.addDate.format(DATE_FORMAT) : undefined,
      updateDate:
        observationLocation.updateDate && observationLocation.updateDate.isValid()
          ? observationLocation.updateDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.addDate = res.body.addDate ? moment(res.body.addDate) : undefined;
      res.body.updateDate = res.body.updateDate ? moment(res.body.updateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((observationLocation: IObservationLocation) => {
        observationLocation.addDate = observationLocation.addDate ? moment(observationLocation.addDate) : undefined;
        observationLocation.updateDate = observationLocation.updateDate ? moment(observationLocation.updateDate) : undefined;
      });
    }
    return res;
  }
}
