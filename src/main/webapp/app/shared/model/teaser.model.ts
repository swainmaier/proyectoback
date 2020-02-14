import { IContenido } from 'app/shared/model/contenido.model';

export interface ITeaser {
  id?: number;
  titulo?: string;
  url?: string;
  contenido?: IContenido;
}

export class Teaser implements ITeaser {
  constructor(public id?: number, public titulo?: string, public url?: string, public contenido?: IContenido) {}
}
