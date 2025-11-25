# Fatec GLab

Repositório do projeto **Fatec GLab** – aplicação desenvolvida para apoiar reservas de salas de aula na FATEC.

## Visão Geral

O Fatec GLab tem como objetivo facilitar o gerenciamento, registro e acompanhamento de reservas das salas de aula, promovendo integração entre professores, alunos e a estrutura da faculdade.

## Funcionalidades Principais

- Cadastro de usuários (alunos e professores)
- Registro reserva de laboratório
- Gerenciamento de reservas
- Relatórios e estatísticas de uso
- Interface intuitiva e amigável

## Tecnologias Utilizadas

- **Backend:** Java (Maven, Spring Boot)
- **Frontend:** Next.js
- **Banco de dados:** MongoDB (hospedado na nuvem, conectado diretamente ao backend)
- **Ferramentas de versionamento:** Git e GitHub

## Projeto Online

Acesse a aplicação Fatec GLab diretamente pela nuvem:

- [https://glab-seven.vercel.app/](https://glab-seven.vercel.app/)

## Como rodar o projeto localmente

### Pré-requisitos

- [Java JDK](https://www.oracle.com/java/technologies/downloads/) (versão recomendada: 11+)
- [Maven](https://maven.apache.org/)
- [Node.js](https://nodejs.org/)
- Git

### Passos para executar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/Memlith/fatec-glab.git
   cd fatec-glab
   ```

2. **Configure as variáveis de ambiente:**
   - Copie o arquivo de exemplo:
     ```bash
     cp .env.example .env
     ```
   - Edite o arquivo `.env` se necessário (os dados do MongoDB já estão configurados no Spring Boot para conexão direta à nuvem).

---

#### Backend Java (Maven, Spring Boot)

3. **Compile e execute a aplicação backend:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - O backend normalmente estará disponível em `http://localhost:3333`

---

#### Frontend React (Node.js)

4. **Acesse a pasta do frontend** (ex: `frontend/`)
   ```bash
   cd frontend
   ```

5. **Instale as dependências do frontend:**
   ```bash
   npm install
   ```

6. **Execute a aplicação frontend:**
   ```bash
   npm run dev
   ```
   - O frontend normalmente estará disponível em `http://localhost:3000`

---

> Ajuste os caminhos das pastas, variáveis ou configurações conforme a estrutura e necessidades do seu projeto!

## Como Contribuir

1. Fork este repositório
2. Crie uma branch para sua feature (`git checkout -b feature/nome-da-feature`)
3. Commit suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Push para sua branch (`git push origin feature/nome-da-feature`)
5. Abra um Pull Request
