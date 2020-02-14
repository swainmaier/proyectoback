import { IContenido } from 'app/shared/model/contenido.model';

export interface IEstadoContenido {
  id?: number;
  titulo?: string;
  contenidos?: IContenido[];
}

export class EstadoContenido implements IEstadoContenido {
  constructor(public id?: number, public titulo?: string, public contenidos?: IContenido[]) {}
}
