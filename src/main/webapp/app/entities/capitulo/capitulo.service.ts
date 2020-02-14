import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICapitulo } from 'app/shared/model/capitulo.model';

type EntityResponseType = HttpResponse<ICapitulo>;
type EntityArrayResponseType = HttpResponse<ICapitulo[]>;

@Injectable({ providedIn: 'root' })
export class CapituloService {
  public resourceUrl = SERVER_API_URL + 'api/capitulos';

  constructor(protected http: HttpClient) {}

  create(capitulo: ICapitulo): Observable<EntityResponseType> {
    return this.http.post<ICapitulo>(this.resourceUrl, capitulo, { observe: 'response' });
  }

  update(capitulo: ICapitulo): Observable<EntityResponseType> {
    return this.http.put<ICapitulo>(this.resourceUrl, capitulo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICapitulo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICapitulo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
