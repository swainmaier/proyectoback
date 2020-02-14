import { ITemporada } from 'app/shared/model/temporada.model';

export interface ICapitulo {
  id?: number;
  titulo?: string;
  numero?: number;
  sinopsis?: string;
  logline?: string;
  caratula?: string;
  duracion?: number;
  vimeoID?: string;
  temporada?: ITemporada;
}

export class Capitulo implements ICapitulo {
  constructor(
    public id?: number,
    public titulo?: string,
    public numero?: number,
    public sinopsis?: string,
    public logline?: string,
    public caratula?: string,
    public duracion?: number,
    public vimeoID?: string,
    public temporada?: ITemporada
  ) {}
}
