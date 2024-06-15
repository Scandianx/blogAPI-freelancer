### Explicação do Projeto BlogAPI

### Introdução
Este projeto é uma API de um blog construída usando o framework Spring Boot e MongoDB como banco de dados. A API permite a criação, leitura, atualização e exclusão de posts e comentários. Vamos explicar cada parte do código para que você possa entender como tudo funciona.

### Estrutura do Projeto
O projeto está organizado em três pacotes principais:
1. `model`: Contém as classes que representam os dados (Post e Comment).
2. `controller`: Contém as classes que definem as rotas da API e controlam as operações.
3. `service`: Contém as classes que implementam a lógica de negócio.

### Classes do Pacote `model`

#### Classe `Comment`
- **Objetivo**: Representa um comentário em um post do blog.
- **Anotações**:
  - `@Document(collection = "comments")`: Indica que a classe será armazenada na coleção "comments" no MongoDB.
  - `@Id`: Define o campo `id` como identificador único.
- **Campos**:
  - `id`: Identificador único do comentário.
  - `postId`: Identificador do post ao qual o comentário pertence.
  - `commenter`: Nome do autor do comentário.
  - `content`: Conteúdo do comentário.
  - `commentedDate`: Data e hora em que o comentário foi feito.

#### Classe `Post`
- **Objetivo**: Representa um post no blog.
- **Anotações**:
  - `@Document(collection = "posts")`: Indica que a classe será armazenada na coleção "posts" no MongoDB.
  - `@Id`: Define o campo `id` como identificador único.
- **Campos**:
  - `id`: Identificador único do post.
  - `title`: Título do post.
  - `content`: Conteúdo do post.
  - `author`: Autor do post.
  - `hashtags`: Hashtags associadas ao post.
  - `createdDate`: Data e hora em que o post foi criado.
  - `inactive`: Indica se o post está ativo ou inativo.

### Classes do Pacote `controller`

#### Classe `CommentController`
- **Objetivo**: Controla as operações relacionadas aos comentários.
- **Anotações**:
  - `@RestController`: Indica que a classe é um controlador REST.
  - `@RequestMapping("/comments")`: Define a rota base para comentários.
- **Métodos**:
  - `getCommentsByPostId`: Retorna todos os comentários de um post específico.
  - `createComment`: Cria um novo comentário.
  - `deleteComment`: Deleta um comentário por seu identificador.

#### Classe `PostController`
- **Objetivo**: Controla as operações relacionadas aos posts.
- **Anotações**:
  - `@RestController`: Indica que a classe é um controlador REST.
  - `@RequestMapping("/posts")`: Define a rota base para posts.
- **Métodos**:
  - `getAllPosts`: Retorna todos os posts.
  - `getPostById`: Retorna um post específico pelo seu identificador.
  - `createPost`: Cria um novo post.
  - `updatePost`: Atualiza um post existente.
  - `updatePostPartially`: Atualiza parcialmente um post existente.
  - `deletePost`: Deleta um post por seu identificador, marcando-o como inativo.

### HATEOAS (Hypermedia as the Engine of Application State)
HATEOAS é um conceito de RESTful APIs que permite que as respostas do servidor incluam links para outras ações possíveis, proporcionando navegação dinâmica da API.

#### No Projeto BlogAPI:
- **Uso de EntityModel**: Utilizado para encapsular os dados retornados juntamente com links de navegação.
- **Exemplo de Links**:
  - No método `createComment`, após criar um comentário, a resposta inclui um link para obter todos os comentários do post (`comments`).
  - No método `getPostById`, a resposta inclui links para obter o próprio post (`self`), todos os posts (`posts`) e os comentários do post (`comments`).

**Benefícios**:
- Melhora a navegabilidade da API.
- Facilita a descoberta de novas ações possíveis para o cliente.

### Tratamento de Erros
- **ResponseEntity**: Utilizado para retornar respostas HTTP com códigos de status apropriados.
  - `404 Not Found`: Retornado quando um post ou comentário não é encontrado.
  - `422 Unprocessable Entity`: Retornado quando a validação dos dados de entrada falha.
  - `204 No Content`: Retornado quando um post ou comentário é deletado com sucesso.
- **Verificações de Validade**: Antes de criar ou atualizar posts e comentários, são realizadas verificações para garantir que todos os campos obrigatórios estejam preenchidos.

### DTOs (Data Transfer Objects)
- **Objetivo**: Os DTOs são utilizados para transferir dados entre as camadas da aplicação, garantindo que apenas os dados necessários sejam expostos ou recebidos.
- **Exemplo no Projeto BlogAPI**:
  - `CommentRequest` e `CommentResponse`: Usados para transferir dados relacionados a comentários.
  - `PostRequest` e `PostResponse`: Usados para transferir dados relacionados a posts.
- **Conversão com ModelMapper**: Utilizado para converter entre entidades e DTOs, facilitando a transformação de dados.

### Resumo
O projeto BlogAPI é uma aplicação backend que utiliza Spring Boot para gerenciar um blog com posts e comentários. A estrutura do projeto é dividida em modelos de dados (`model`), controladores de rotas (`controller`) e serviços de lógica de negócio (`service`). O uso de HATEOAS melhora a navegabilidade da API, o tratamento de erros assegura respostas HTTP apropriadas e os DTOs garantem a transferência eficiente de dados. Cada classe e método tem um propósito claro e bem definido, facilitando a manutenção e expansão da aplicação.
