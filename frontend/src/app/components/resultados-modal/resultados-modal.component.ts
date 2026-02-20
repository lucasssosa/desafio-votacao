import { Component, Input, Output, EventEmitter, SimpleChanges, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaskCpfPipe } from '../../pipes/mask-cpf.pipe';

@Component({
  selector: 'app-resultados-modal',
  standalone: true,
  imports: [CommonModule, MaskCpfPipe],
  templateUrl: './resultados-modal.component.html'
})
export class ResultadosModalComponent implements OnChanges {
  @Input() pauta: any;
  @Output() fechar = new EventEmitter<void>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['pauta'] && this.pauta) {
      console.log('Dados recebidos no modal:', this.pauta);
    }
  }

  get totalVotos() {
    return this.pauta?.votos?.length || 0;
  }

  get votosSim() {
    return this.pauta?.votos?.filter((v: any) => v.decisao === 'SIM').length || 0;
  }

  get votosNao() {
    return this.pauta?.votos?.filter((v: any) => v.decisao === 'NAO').length || 0;
  }

  porcentagem(votos: number) {
    if (this.totalVotos === 0) return 0;
    return (votos / this.totalVotos) * 100;
  }
}