# Fatec GLab

Repositório do projeto **Fatec GLab** – aplicação desenvolvida para apoiar reservas de salas de aula na FATEC.

## Visão Geral

O Fatec GLab tem como objetivo facilitar o gerenciamento, registro e acompanhamento de reservas das salas de aula, promovendo integração entre professores, alunos e a estrutura da faculdade.

## Funcionalidades Principais

- Registro reserva de laboratório
- Gerenciamento de reservas
- Interface intuitiva e amigável
- Sistema de Login e Auth

## Tecnologias Utilizadas

- **Backend:** Java (Maven, Spring Boot)
- **Frontend:** Next.js
- **Mobile App:** Kotlin
- **Banco de dados:** MongoDB (hospedado na nuvem, conectado diretamente ao backend)
- **Ferramentas de versionamento:** Git e GitHub

## Projeto Online

Acesse a aplicação Fatec GLab diretamente pela nuvem:

- [Fatec GLab](https://fatecid-glab.vercel.app)

## Como rodar o projeto localmente

### Pré-requisitos

- [Java JDK](https://www.oracle.com/java/technologies/downloads/) (versão recomendada: 17)
- [Maven](https://maven.apache.org/)
- [Node.js](https://nodejs.org/)
- Android Studio
- Git

### Passos para executar

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/Memlith/fatec-glab.git
   cd fatec-glab
   ```

2. **Configure as variáveis de ambiente:**
  - Edite o arquivo application.properties

---

#### Backend Java (Maven, Spring Boot)

3. **Compile e execute a aplicação backend:**
   
   ```bash
   cd backend/
   mvn clean install
   mvn spring-boot:run
   ```
   - O backend normalmente estará disponível em `http://localhost:3333`

---

#### Frontend React (Node.js)

4. **Compile e execute a aplicação frontend**

   ```bash
   cd frontend/
   npm install
   npm run dev
   ```
   - O frontend normalmente estará disponível em `http://localhost:3000`

---

#### Mobile Android (Kotlin)

5. **Abra a pasta pelo Android Studio e selecione a pasta `android/`**

---

## Como Contribuir

1. Fork este repositório
2. Crie uma branch para sua feature (`git checkout -b feature/nome-da-feature`)
3. Commit suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Push para sua branch (`git push origin feature/nome-da-feature`)
5. Abra um Pull Request
6. Aguarde as verificações do Actions
