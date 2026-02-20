import { Component, OnInit, HostListener, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PautaService } from './services/pauta.service';
import { Pauta } from './models/pauta.model';
import { SessaoModalComponent } from './components/sessao-modal/sessao-modal.component';
import { VotosModalComponent } from './components/votos-modal/votos-modal.component';
import { ResultadosModalComponent } from './components/resultados-modal/resultados-modal.component';
import { ToastService } from './services/toast.service';
import { ToastComponent } from './components/toast/toast.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    SessaoModalComponent, 
    VotosModalComponent, 
    ResultadosModalComponent, 
    ToastComponent
  ],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  private readonly pautaService = inject(PautaService);
  private readonly toast = inject(ToastService);

  public pautas: Pauta[] = [];
  public pautaSelecionada: Pauta | null = null;
  public kebabAberto: number | null = null;

  public modalPauta = false;
  public exibirModalSessao = false;
  public exibirModalVoto = false;
  public exibirResultados = false;

  public novaPauta = { titulo: '', descricao: '' };
 
  public abrirModalPauta(): void {
  this.modalPauta = !this.modalPauta;
}

  ngOnInit(): void {
    this.carregarPautas();
  }

  public carregarPautas(): void {
    this.pautaService.listarPautas().subscribe({
      next: (dados) => this.pautas = dados,
      error: (err) => this.tratarErro('Erro ao carregar pautas', err)
    });
  }

  public cadastrarPauta(): void {
    this.pautaService.criarPauta(this.novaPauta).subscribe({
      next: () => {
        this.toast.exibir('Pauta criada com sucesso!', 'sucesso');
        this.modalPauta = false;
        this.limparCampos();
        this.carregarPautas();
      },
      error: (err) => this.tratarErro('Erro ao criar pauta', err)
    });
  }

  public abrirModalSessao(pauta: Pauta): void {
    this.pautaSelecionada = { ...pauta };
    this.exibirModalSessao = true;
    this.kebabAberto = null;
  }

  public confirmarSessao(segundos: number): void {
    if (!this.pautaSelecionada?.id) return;

    this.pautaService.abrirSessao(this.pautaSelecionada.id, segundos).subscribe({
      next: () => {
        this.exibirModalSessao = false;
        this.carregarPautas();
        this.toast.exibir('Sessão aberta com sucesso!', 'sucesso');
      },
      error: (err) => this.tratarErro('Erro ao abrir sessão', err)
    });
  }

  public abrirModalVoto(pauta: Pauta): void {
    if (pauta.status !== 'ABERTA') {
      this.toast.exibir('Esta pauta não está aberta para votação.', 'erro');
      return;
    }
    this.pautaSelecionada = { ...pauta };
    this.exibirModalVoto = true;
    this.kebabAberto = null;
  }

  public registrarVoto(dados: {voto: string, cpf: string}): void {
    if (!this.pautaSelecionada?.id) return;

    this.pautaService.votar(this.pautaSelecionada.id, dados.voto, dados.cpf).subscribe({
      next: () => {
        this.exibirModalVoto = false;
        this.toast.exibir('Voto computado com sucesso!', 'sucesso');
      },
      error: (err) => {
        const msg = err.error?.message === "UNABLE_TO_VOTE" 
          ? "CPF inválido ou não habilitado!" 
          :this.tratarErro('Erro ao votar', err);
      }
    });
  }

  public executarApuracao(id?: number): void {
    if (!id) return;

    this.pautaService.apurarPauta(id).subscribe({
      next: (res) => {
        const total = `(${res.votosSim} vs ${res.votosNao})`;
        const mensagem = res.status === 'EMPATE' 
          ? `A votação terminou em EMPATE! ${total}`
          : `Pauta ${res.status}! ${total}`;

        this.toast.exibir(mensagem, 'sucesso');
        this.kebabAberto = null;
        this.carregarPautas();
      },
      error: (err) => this.tratarErro('Erro ao apurar pauta', err)
    });
  }

  public abrirModalResultados(pauta: Pauta): void {
    this.pautaSelecionada = { ...pauta };
    this.exibirResultados = true;
    this.kebabAberto = null;
  }

  public toggleKebab(index: number, event: Event): void {
    event.stopPropagation();
    this.kebabAberto = this.kebabAberto === index ? null : index;
  }

  @HostListener('document:click')
  public fecharKebab(): void {
    this.kebabAberto = null;
  }

  public limparCampos(): void {
    this.novaPauta = { titulo: '', descricao: '' };
  }

  private tratarErro(mensagem: string, err: any): void {
    const detalhe = err.error?.message || 'Servidor indisponível';
    this.toast.exibir(`${mensagem}: ${detalhe}`, 'erro');
  }
}