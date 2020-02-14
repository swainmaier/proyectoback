import { IContenido } from 'app/shared/model/contenido.model';

export interface IPlataforma {
  id?: number;
  titulo?: string;
  contenidos?: IContenido[];
}

export class Plataforma implements IPlataforma {
  constructor(public id?: number, public titulo?: string, public contenidos?: IContenido[]) {}
}
