import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITeaser } from 'app/shared/model/teaser.model';

type EntityResponseType = HttpResponse<ITeaser>;
type EntityArrayResponseType = HttpResponse<ITeaser[]>;

@Injectable({ providedIn: 'root' })
export class TeaserService {
  public resourceUrl = SERVER_API_URL + 'api/teasers';

  constructor(protected http: HttpClient) {}

  create(teaser: ITeaser): Observable<EntityResponseType> {
    return this.http.post<ITeaser>(this.resourceUrl, teaser, { observe: 'response' });
  }

  update(teaser: ITeaser): Observable<EntityResponseType> {
    return this.http.put<ITeaser>(this.resourceUrl, teaser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITeaser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITeaser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
