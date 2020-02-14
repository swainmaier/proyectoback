import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubGenero } from 'app/shared/model/sub-genero.model';

type EntityResponseType = HttpResponse<ISubGenero>;
type EntityArrayResponseType = HttpResponse<ISubGenero[]>;

@Injectable({ providedIn: 'root' })
export class SubGeneroService {
  public resourceUrl = SERVER_API_URL + 'api/sub-generos';

  constructor(protected http: HttpClient) {}

  create(subGenero: ISubGenero): Observable<EntityResponseType> {
    return this.http.post<ISubGenero>(this.resourceUrl, subGenero, { observe: 'response' });
  }

  update(subGenero: ISubGenero): Observable<EntityResponseType> {
    return this.http.put<ISubGenero>(this.resourceUrl, subGenero, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubGenero>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubGenero[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
