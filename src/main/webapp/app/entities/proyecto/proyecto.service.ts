import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProyecto } from 'app/shared/model/proyecto.model';

type EntityResponseType = HttpResponse<IProyecto>;
type EntityArrayResponseType = HttpResponse<IProyecto[]>;

@Injectable({ providedIn: 'root' })
export class ProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/proyectos';

  constructor(protected http: HttpClient) {}

  create(proyecto: IProyecto): Observable<EntityResponseType> {
    return this.http.post<IProyecto>(this.resourceUrl, proyecto, { observe: 'response' });
  }

  update(proyecto: IProyecto): Observable<EntityResponseType> {
    return this.http.put<IProyecto>(this.resourceUrl, proyecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProyecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
