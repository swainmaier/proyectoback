import { IContenido } from 'app/shared/model/contenido.model';

export interface ITrailer {
  id?: number;
  titulo?: string;
  url?: string;
  contenido?: IContenido;
}

export class Trailer implements ITrailer {
  constructor(public id?: number, public titulo?: string, public url?: string, public contenido?: IContenido) {}
}
