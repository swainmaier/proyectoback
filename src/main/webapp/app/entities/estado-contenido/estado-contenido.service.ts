import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstadoContenido } from 'app/shared/model/estado-contenido.model';

type EntityResponseType = HttpResponse<IEstadoContenido>;
type EntityArrayResponseType = HttpResponse<IEstadoContenido[]>;

@Injectable({ providedIn: 'root' })
export class EstadoContenidoService {
  public resourceUrl = SERVER_API_URL + 'api/estado-contenidos';

  constructor(protected http: HttpClient) {}

  create(estadoContenido: IEstadoContenido): Observable<EntityResponseType> {
    return this.http.post<IEstadoContenido>(this.resourceUrl, estadoContenido, { observe: 'response' });
  }

  update(estadoContenido: IEstadoContenido): Observable<EntityResponseType> {
    return this.http.put<IEstadoContenido>(this.resourceUrl, estadoContenido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoContenido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoContenido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
