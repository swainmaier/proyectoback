import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITrailer } from 'app/shared/model/trailer.model';

type EntityResponseType = HttpResponse<ITrailer>;
type EntityArrayResponseType = HttpResponse<ITrailer[]>;

@Injectable({ providedIn: 'root' })
export class TrailerService {
  public resourceUrl = SERVER_API_URL + 'api/trailers';

  constructor(protected http: HttpClient) {}

  create(trailer: ITrailer): Observable<EntityResponseType> {
    return this.http.post<ITrailer>(this.resourceUrl, trailer, { observe: 'response' });
  }

  update(trailer: ITrailer): Observable<EntityResponseType> {
    return this.http.put<ITrailer>(this.resourceUrl, trailer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrailer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrailer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
