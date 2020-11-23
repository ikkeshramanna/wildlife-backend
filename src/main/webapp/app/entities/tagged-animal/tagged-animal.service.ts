import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaggedAnimal } from 'app/shared/model/tagged-animal.model';

type EntityResponseType = HttpResponse<ITaggedAnimal>;
type EntityArrayResponseType = HttpResponse<ITaggedAnimal[]>;

@Injectable({ providedIn: 'root' })
export class TaggedAnimalService {
  public resourceUrl = SERVER_API_URL + 'api/tagged-animals';

  constructor(protected http: HttpClient) {}

  create(taggedAnimal: ITaggedAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taggedAnimal);
    return this.http
      .post<ITaggedAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taggedAnimal: ITaggedAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taggedAnimal);
    return this.http
      .put<ITaggedAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaggedAnimal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaggedAnimal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taggedAnimal: ITaggedAnimal): ITaggedAnimal {
    const copy: ITaggedAnimal = Object.assign({}, taggedAnimal, {
      dateOfBirth:
        taggedAnimal.dateOfBirth && taggedAnimal.dateOfBirth.isValid() ? taggedAnimal.dateOfBirth.format(DATE_FORMAT) : undefined,
      dateOfTagging:
        taggedAnimal.dateOfTagging && taggedAnimal.dateOfTagging.isValid() ? taggedAnimal.dateOfTagging.format(DATE_FORMAT) : undefined,
      dateTime: taggedAnimal.dateTime && taggedAnimal.dateTime.isValid() ? taggedAnimal.dateTime.format(DATE_FORMAT) : undefined,
      updateDate: taggedAnimal.updateDate && taggedAnimal.updateDate.isValid() ? taggedAnimal.updateDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? moment(res.body.dateOfBirth) : undefined;
      res.body.dateOfTagging = res.body.dateOfTagging ? moment(res.body.dateOfTagging) : undefined;
      res.body.dateTime = res.body.dateTime ? moment(res.body.dateTime) : undefined;
      res.body.updateDate = res.body.updateDate ? moment(res.body.updateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taggedAnimal: ITaggedAnimal) => {
        taggedAnimal.dateOfBirth = taggedAnimal.dateOfBirth ? moment(taggedAnimal.dateOfBirth) : undefined;
        taggedAnimal.dateOfTagging = taggedAnimal.dateOfTagging ? moment(taggedAnimal.dateOfTagging) : undefined;
        taggedAnimal.dateTime = taggedAnimal.dateTime ? moment(taggedAnimal.dateTime) : undefined;
        taggedAnimal.updateDate = taggedAnimal.updateDate ? moment(taggedAnimal.updateDate) : undefined;
      });
    }
    return res;
  }
}
