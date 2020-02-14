import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstadoProyecto } from 'app/shared/model/estado-proyecto.model';

type EntityResponseType = HttpResponse<IEstadoProyecto>;
type EntityArrayResponseType = HttpResponse<IEstadoProyecto[]>;

@Injectable({ providedIn: 'root' })
export class EstadoProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/estado-proyectos';

  constructor(protected http: HttpClient) {}

  create(estadoProyecto: IEstadoProyecto): Observable<EntityResponseType> {
    return this.http.post<IEstadoProyecto>(this.resourceUrl, estadoProyecto, { observe: 'response' });
  }

  update(estadoProyecto: IEstadoProyecto): Observable<EntityResponseType> {
    return this.http.put<IEstadoProyecto>(this.resourceUrl, estadoProyecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoProyecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
