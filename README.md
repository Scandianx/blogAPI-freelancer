# BlogAPI created with MongoDB and SpringBoot.

### Explicação dos Métodos e Sua Sintaxe

#### Contexto

Estamos trabalhando com uma aplicação de blog que utiliza um banco de dados MongoDB para armazenar posts e comentários. Queremos criar consultas (queries) que nos permitam buscar dados específicos do banco de dados de forma eficiente. Usamos o Spring Data JPA para definir essas consultas em Java.

### Consultas (Queries)

1. **Listar posts por autor**
   - **Definição**: Esta consulta busca todos os posts que foram escritos por um autor específico.
   - **Sintaxe**:
     ```java
     @NamedQuery(name = "Post.listarPorAutor",
                 query = "SELECT p FROM Post p WHERE p.author = ?1")
     ```
   - **Explicação**: 
     - `@NamedQuery` é uma anotação que define uma consulta nomeada.
     - `SELECT p FROM Post p` seleciona todos os posts (`p`).
     - `WHERE p.author = ?1` filtra os posts para retornar apenas aqueles cujo autor (`author`) corresponde ao parâmetro fornecido (`?1`).

2. **Listar comentários por postId**
   - **Definição**: Esta consulta busca todos os comentários associados a um determinado post.
   - **Sintaxe**:
     ```java
     @NamedQuery(name = "Comment.listarPorPostId",
                 query = "SELECT c FROM Comment c WHERE c.postId = ?1")
     ```
   - **Explicação**:
     - `@NamedQuery` define uma consulta nomeada.
     - `SELECT c FROM Comment c` seleciona todos os comentários (`c`).
     - `WHERE c.postId = ?1` filtra os comentários para retornar apenas aqueles cujo `postId` corresponde ao parâmetro fornecido (`?1`).

3. **Contar comentários por post**
   - **Definição**: Esta consulta conta quantos comentários existem para cada post.
   - **Sintaxe**:
     ```java
     @Query("SELECT p.id, COUNT(c) FROM Post p LEFT JOIN Comment c ON p.id = c.postId GROUP BY p.id")
     List<Object[]> contarComentariosPorPost();
     ```
   - **Explicação**:
     - `@Query` é uma anotação que permite definir consultas personalizadas.
     - `SELECT p.id, COUNT(c)` seleciona o ID do post e o número de comentários associados a ele.
     - `LEFT JOIN Comment c ON p.id = c.postId` realiza uma junção à esquerda para combinar posts e seus comentários com base no `postId`.
     - `GROUP BY p.id` agrupa os resultados por ID do post.

4. **Listar posts com uma determinada hashtag**
   - **Definição**: Esta consulta busca todos os posts que contêm uma determinada hashtag.
   - **Sintaxe**:
     ```java
     @NamedQuery(name = "Post.listarPorHashtag",
                 query = "SELECT p FROM Post p WHERE p.hashtags LIKE ?1")
     ```
   - **Explicação**:
     - `@NamedQuery` define uma consulta nomeada.
     - `SELECT p FROM Post p` seleciona todos os posts (`p`).
     - `WHERE p.hashtags LIKE ?1` filtra os posts para retornar aqueles que contêm a hashtag fornecida (`?1`).

5. **Listar posts e seus comentários**
   - **Definição**: Esta consulta busca um post específico junto com todos os seus comentários.
   - **Sintaxe**:
     ```java
     @Query("SELECT p, c FROM Post p LEFT JOIN Comment c ON p.id = c.postId WHERE p.id = ?1")
     List<Object[]> listarPostComComentarios(String postId);
     ```
   - **Explicação**:
     - `@Query` define uma consulta personalizada.
     - `SELECT p, c` seleciona tanto os posts (`p`) quanto os comentários (`c`).
     - `LEFT JOIN Comment c ON p.id = c.postId` realiza uma junção à esquerda para combinar posts e comentários com base no `postId`.
     - `WHERE p.id = ?1` filtra para retornar apenas o post e seus comentários cujo ID do post corresponde ao parâmetro fornecido (`?1`).

6. **Listar comentários de um autor específico**
   - **Definição**: Esta consulta busca todos os comentários que pertencem a posts escritos por um autor específico.
   - **Sintaxe**:
     ```java
     @Query("SELECT c FROM Comment c JOIN Post p ON c.postId = p.id WHERE p.author = ?1")
     List<Comment> listarComentariosPorAutor(String author);
     ```
   - **Explicação**:
     - `@Query` define uma consulta personalizada.
     - `SELECT c FROM Comment c` seleciona todos os comentários (`c`).
     - `JOIN Post p ON c.postId = p.id` realiza uma junção entre comentários e posts com base no `postId`.
     - `WHERE p.author = ?1` filtra para retornar apenas os comentários associados a posts cujo autor corresponde ao parâmetro fornecido (`?1`).

### Métodos de Consulta

1. **Listar posts por autor**
   - **Definição**: Método para listar posts escritos por um autor específico.
   - **Sintaxe**:
     ```java
     List<Post> listarPorAutor(String author);
     ```

2. **Listar comentários por postId**
   - **Definição**: Método para listar comentários associados a um determinado post.
   - **Sintaxe**:
     ```java
     List<Comment> listarPorPostId(String postId);
     ```

### Seleção da Melhor Query

**Selecionada: Listar posts e seus comentários (usando join)**

- **Definição**: Esta consulta busca um post específico junto com todos os seus comentários.
- **Sintaxe**:
  ```java
  @Query("SELECT p, c FROM Post p LEFT JOIN Comment c ON p.id = c.postId WHERE p.id = ?1")
  List<Object[]> listarPostComComentarios(String postId);
  ```
- **Justificativa**:
  - **Complexidade**: Demonstra o uso de `JOIN` para combinar dados de duas coleções diferentes.
  - **Utilidade**: Permite obter uma visão completa de um post junto com todos os seus comentários, algo que é muito útil em uma aplicação de blog.
  - **Reutilização**: Pode ser usada em várias partes da aplicação, como na visualização detalhada de um post.

Essa explicação deve ajudar a entender o propósito e a sintaxe das queries e métodos de consulta, mesmo para alguém que não tem muita experiência em programação.
