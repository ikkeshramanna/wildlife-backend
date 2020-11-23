import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpeciesCategory } from 'app/shared/model/species-category.model';

type EntityResponseType = HttpResponse<ISpeciesCategory>;
type EntityArrayResponseType = HttpResponse<ISpeciesCategory[]>;

@Injectable({ providedIn: 'root' })
export class SpeciesCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/species-categories';

  constructor(protected http: HttpClient) {}

  create(speciesCategory: ISpeciesCategory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(speciesCategory);
    return this.http
      .post<ISpeciesCategory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(speciesCategory: ISpeciesCategory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(speciesCategory);
    return this.http
      .put<ISpeciesCategory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpeciesCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpeciesCategory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(speciesCategory: ISpeciesCategory): ISpeciesCategory {
    const copy: ISpeciesCategory = Object.assign({}, speciesCategory, {
      addDate: speciesCategory.addDate && speciesCategory.addDate.isValid() ? speciesCategory.addDate.format(DATE_FORMAT) : undefined,
      updateDate:
        speciesCategory.updateDate && speciesCategory.updateDate.isValid() ? speciesCategory.updateDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((speciesCategory: ISpeciesCategory) => {
        speciesCategory.addDate = speciesCategory.addDate ? moment(speciesCategory.addDate) : undefined;
        speciesCategory.updateDate = speciesCategory.updateDate ? moment(speciesCategory.updateDate) : undefined;
      });
    }
    return res;
  }
}
