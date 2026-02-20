🏛️ Assembly - Módulo de Plenário
Sistema de votação cooperativa para gestão de pautas, sessões e apuração de resultados.

📋 Funcionalidades Implementadas

✅ Gestão de Pautas: Criação e listagem.

✅ Controle de Sessão: Abertura de votação com tempo customizável.

✅ Sistema de Votos: Registro de decisões (SIM/NÃO) com validação de CPF.

✅ Apuração Automática: Cálculo de resultados (Aprovada, Reprovada, Empate) e encerramento.

🔄 Fluxo de Estados da Pauta
A pauta percorre os seguintes estados:

- CRIADA: Estado inicial após o cadastro.
- ABERTA: Sessão iniciada e recebendo votos.
- ENCERRADA: Tempo expirado, aguardando apuração.
- APROVADA / REPROVADA / EMPATE: Resultado final após processamento.

🚀 Tecnologias
- Frontend: Angular 19+

- Backend: Java 17 / Spring Boot 3+

- Comunicação: REST API / JSON

📦 Dependências do Backend
O projeto utiliza o ecossistema Spring Boot para garantir uma base sólida, escalável e de fácil manutenção.

🔧 Core Tecnológico
- Spring Web: Responsável por expor a API REST. Gerencia as rotas, os controladores e o servidor.
- Spring Data JPA: Abstração sobre o Hibernate que facilita a persistência de dados. Permite realizar operações de CRUD no banco de dados utilizando interfaces Java, eliminando a necessidade de SQL manual.
- Validation (Hibernate Validator): Garante a integridade dos dados. Utiliza anotações como @NotBlank e @Size nos DTOs para validar entradas (ex: CPF, títulos e datas) antes de processar a lógica de negócio.

💾 Armazenamento e Utilitários
- H2 Database: Banco de dados relacional em memória. Ideal para desenvolvimento e testes rápidos, pois não requer instalação externa.
- Lombok: Biblioteca focada em agilidade. Reduz o código repetitivo através de anotações como @Data, @Getter e @Setter, mantendo as classes de modelo limpas e legíveis.

🎨 Tailwind CSS: O Motor de Estilização
Diferente do CSS tradicional, o Tailwind permite construir interfaces modernas sem sair do arquivo HTML/Template, garantindo velocidade e consistência visual.

📂 Estrutura de Monorepo
A arquitetura de monorepo foi escolhida pela facilidade de configuração, permitindo que o desenvolvedor tenha o ecossistema completo (frontend e backend) disponível com apenas um comando de clone, simplificando o setup inicial do desafio.

🛠️ Como Rodar o Projeto

Pré-requisitos
- Java 17
- Node.js 18 ou superior
- Angular CLI (npm install -g @angular/cli)

1. Clonar o Repositório
git clone <url-do-repositorio>
cd <nome-da-pasta>

2. Backend (Spring Boot)
   
Abra um terminal na pasta /backend:

./mvnw spring-boot:run

O servidor iniciará em: http://localhost:8080

3. Frontend (Angular)
   
Abra um segundo terminal na pasta /frontend:

npm install

ng serve

A aplicação estará disponível em: http://localhost:4200
