import { IGenero } from 'app/shared/model/genero.model';

export interface ISubGenero {
  id?: number;
  titulo?: string;
  generos?: IGenero[];
}

export class SubGenero implements ISubGenero {
  constructor(public id?: number, public titulo?: string, public generos?: IGenero[]) {}
}
