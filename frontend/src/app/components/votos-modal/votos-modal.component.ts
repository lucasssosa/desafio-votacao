import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Pauta } from '../../models/pauta.model';

@Component({
  selector: 'app-votos-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './votos-modal.component.html'
})
export class VotosModalComponent {
  @Input() public pauta: Pauta | null = null;
  @Output() public fechar = new EventEmitter<void>();
  @Output() public confirmarVoto = new EventEmitter<{ voto: string; cpf: string }>();

  public votoSelecionado: string | null = null;
  public cpfFormatado: string = '';

  public formatarCPF(event: Event): void {
    const input = event.target as HTMLInputElement;
    let valor = input.value.replace(/\D/g, '');

    if (valor.length > 11) {
      valor = valor.substring(0, 11);
    }

    if (valor.length > 0) {
      valor = valor.replace(/^(\d{3})(\d)/, '$1.$2')
                   .replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3')
                   .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    }

    this.cpfFormatado = valor;
    input.value = valor;
  }

  public podeConfirmar(): boolean {
    const cpfLimpo = this.cpfFormatado.replace(/\D/g, '');
    return !!this.votoSelecionado && cpfLimpo.length === 11;
  }

  public confirmar(): void {
    if (this.podeConfirmar()) {
      const cpfLimpo = this.cpfFormatado.replace(/\D/g, '');
      this.confirmarVoto.emit({ 
        voto: this.votoSelecionado!, 
        cpf: cpfLimpo 
      });
    }
  }
}