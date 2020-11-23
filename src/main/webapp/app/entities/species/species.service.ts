import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpecies } from 'app/shared/model/species.model';

type EntityResponseType = HttpResponse<ISpecies>;
type EntityArrayResponseType = HttpResponse<ISpecies[]>;

@Injectable({ providedIn: 'root' })
export class SpeciesService {
  public resourceUrl = SERVER_API_URL + 'api/species';

  constructor(protected http: HttpClient) {}

  create(species: ISpecies): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(species);
    return this.http
      .post<ISpecies>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(species: ISpecies): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(species);
    return this.http
      .put<ISpecies>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpecies>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpecies[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(species: ISpecies): ISpecies {
    const copy: ISpecies = Object.assign({}, species, {
      addDate: species.addDate && species.addDate.isValid() ? species.addDate.format(DATE_FORMAT) : undefined,
      updateDate: species.updateDate && species.updateDate.isValid() ? species.updateDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((species: ISpecies) => {
        species.addDate = species.addDate ? moment(species.addDate) : undefined;
        species.updateDate = species.updateDate ? moment(species.updateDate) : undefined;
      });
    }
    return res;
  }
}
