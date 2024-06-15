### Classe `Comment`
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

### Classe `Post`
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

### Classe `CommentController`
- **Objetivo**: Controla as operações relacionadas aos comentários.
- **Anotações**:
  - `@RestController`: Indica que a classe é um controlador REST.
  - `@RequestMapping("/comments")`: Define a rota base para comentários.
- **Métodos**:
  - `getCommentsByPostId`: Retorna todos os comentários de um post específico.
  - `createComment`: Cria um novo comentário.
  - `deleteComment`: Deleta um comentário por seu identificador.

### Classe `PostController`
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

### Resumo
O projeto BlogAPI é uma aplicação backend que utiliza Spring Boot para gerenciar um blog com posts e comentários. A estrutura do projeto é dividida em modelos de dados (`model`), controladores de rotas (`controller`) e serviços de lógica de negócio (`service`). Cada classe e método tem um propósito claro e bem definido, facilitando a manutenção e expansão da aplicação.
