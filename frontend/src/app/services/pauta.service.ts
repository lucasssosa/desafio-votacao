import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pauta } from '../models/pauta.model';
import { Voto } from '../models/voto.model';

// Interface para garantir contrato no voto
interface VotoRequest {
  cpf: string;
  decisao: string;
}

@Injectable({ providedIn: 'root' })
export class PautaService {
  private readonly http = inject(HttpClient);
  
  private readonly API = 'http://localhost:8080/api/v1/pautas';

  public listarPautas(): Observable<Pauta[]> {
    return this.http.get<Pauta[]>(this.API);
  }

  public criarPauta(pauta: Partial<Pauta>): Observable<Pauta> {
    return this.http.post<Pauta>(this.API, pauta);
  }

  public abrirSessao(pautaId: number, segundos: number): Observable<Pauta> {
    return this.http.post<Pauta>(`${this.API}/${pautaId}/sessao`, { segundos });
  }

  public votar(pautaId: number, voto: string, cpf: string): Observable<void> {
    const payload: VotoRequest = { cpf, decisao: voto };
    return this.http.post<void>(`${this.API}/${pautaId}/votos`, payload);
  }

  public apurarPauta(id: number): Observable<any> {
    return this.http.put<any>(`${this.API}/${id}/resultado`, {});
  }

  public buscarVotos(pautaId: number): Observable<Voto[]> {
    return this.http.get<Voto[]>(`${this.API}/${pautaId}/votos`);
  }
}