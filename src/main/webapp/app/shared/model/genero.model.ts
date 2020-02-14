import { IContenido } from 'app/shared/model/contenido.model';
import { ISubGenero } from 'app/shared/model/sub-genero.model';

export interface IGenero {
  id?: number;
  titulo?: string;
  contenidos?: IContenido[];
  subGenero?: ISubGenero;
}

export class Genero implements IGenero {
  constructor(public id?: number, public titulo?: string, public contenidos?: IContenido[], public subGenero?: ISubGenero) {}
}
