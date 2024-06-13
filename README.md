### Explicação dos Métodos e Sua Sintaxe para MongoDB

#### Contexto

Estamos trabalhando com uma aplicação de blog que utiliza um banco de dados MongoDB para armazenar posts e comentários. Queremos criar consultas (queries) que nos permitam buscar dados específicos do banco de dados de forma eficiente. Usamos o Spring Data MongoDB para definir essas consultas em Java.

### Consultas (Queries)

#### Query 1: Listar posts por autor

```java
@Query("{ 'author': ?0 }")
List<Post> listarPorAutor(String author);
```

- **Objetivo**: Encontrar todos os posts escritos por um autor específico.
- **Explicação**:
  - `@Query` é uma anotação usada para definir uma consulta personalizada em MongoDB.
  - `"{ 'author': ?0 }"` é a consulta em si, onde `author` é o campo no documento do MongoDB e `?0` é um parâmetro que será substituído pelo valor fornecido.
  - Este método retorna uma lista de posts (`List<Post>`) que foram escritos pelo autor especificado.

#### Query 2: Listar comentários por postId

```java
@Query("{ 'postId': ?0 }")
List<Comment> listarPorPostId(String postId);
```

- **Objetivo**: Encontrar todos os comentários associados a um post específico.
- **Explicação**:
  - `@Query` define uma consulta personalizada em MongoDB.
  - `"{ 'postId': ?0 }"` é a consulta que filtra os comentários pelo campo `postId`.
  - Este método retorna uma lista de comentários (`List<Comment>`) que pertencem ao post especificado pelo `postId`.

#### Query 3: Contar comentários por post (usando agregação)

```java
@Aggregation(pipeline = {
    "{ '$group': { '_id': '$postId', 'count': { '$sum': 1 } } }"
})
List<Map<String, Object>> contarComentariosPorPost();
```

- **Objetivo**: Contar quantos comentários existem para cada post.
- **Explicação**:
  - `@Aggregation` é uma anotação usada para definir uma consulta de agregação em MongoDB.
  - `"$group": { "_id": "$postId", "count": { "$sum": 1 } }` agrupa os documentos de comentário pelo campo `postId` e conta quantos comentários existem para cada grupo.
  - Este método retorna uma lista de mapas (`List<Map<String, Object>>`) onde cada mapa contém o `postId` e o número de comentários (`count`) para aquele post.

#### Query 4: Listar posts com uma determinada hashtag

```java
@Query("{ 'hashtags': { '$regex': ?0, '$options': 'i' } }")
List<Post> listarPorHashtag(String hashtag);
```

- **Objetivo**: Encontrar todos os posts que contêm uma hashtag específica.
- **Explicação**:
  - `@Query` define uma consulta personalizada em MongoDB.
  - `"{ 'hashtags': { '$regex': ?0, '$options': 'i' } }"` utiliza uma expressão regular (`$regex`) para buscar posts cujas hashtags contenham o valor especificado. A opção `'i'` torna a busca case-insensitive.
  - Este método retorna uma lista de posts (`List<Post>`) que contêm a hashtag especificada.

#### Query 5: Listar posts e seus comentários (usando agregação)

```java
@Aggregation(pipeline = {
    "{ '$match': { '_id': ObjectId(?0) } }",
    "{ '$lookup': { 'from': 'comments', 'localField': '_id', 'foreignField': 'postId', 'as': 'comments' } }"
})
List<Map<String, Object>> listarPostComComentarios(String postId);
```

- **Objetivo**: Encontrar um post específico junto com todos os seus comentários.
- **Explicação**:
  - `@Aggregation` define uma consulta de agregação em MongoDB.
  - `"$match": { "_id": ObjectId(?0) }` filtra os posts pelo ID fornecido.
  - `"$lookup": { "from": "comments", "localField": "_id", "foreignField": "postId", "as": "comments" }` realiza uma operação de junção (join) entre a coleção de posts e a coleção de comentários, combinando os documentos com base no campo `postId`.
  - Este método retorna uma lista de mapas (`List<Map<String, Object>>`) onde cada mapa contém um post e seus comentários associados.

#### Query 6: Listar comentários de um autor específico

```java
@Aggregation(pipeline = {
    "{ '$lookup': { 'from': 'posts', 'localField': 'postId', 'foreignField': '_id', 'as': 'post' } }",
    "{ '$unwind': '$post' }",
    "{ '$match': { 'post.author': ?0 } }"
})
List<Comment> listarComentariosPorAutor(String author);
```

- **Objetivo**: Encontrar todos os comentários de posts escritos por um autor específico.
- **Explicação**:
  - `@Aggregation` define uma consulta de agregação em MongoDB.
  - `"$lookup": { "from": "posts", "localField": "postId", "foreignField": "_id", "as": "post" }` realiza uma operação de junção entre a coleção de comentários e a coleção de posts.
  - `"$unwind": "$post"` decompõe o array de posts para fazer correspondência com os comentários.
  - `"$match": { "post.author": ?0 }` filtra os resultados para retornar apenas os comentários dos posts cujo autor corresponde ao valor fornecido.
  - Este método retorna uma lista de comentários (`List<Comment>`) escritos para posts do autor especificado.

### Seleção da Melhor Query

**Selecionada: Listar posts e seus comentários (usando agregação)**

- **Objetivo**: Esta consulta busca um post específico junto com todos os seus comentários.
- **Sintaxe**:
  ```java
  @Aggregation(pipeline = {
      "{ '$match': { '_id': ObjectId(?0) } }",
      "{ '$lookup': { 'from': 'comments', 'localField': '_id', 'foreignField': 'postId', 'as': 'comments' } }"
  })
  List<Map<String, Object>> listarPostComComentarios(String postId);
  ```
- **Justificativa**:
  - **Complexidade**: Mostra o uso de agregação para combinar dados de duas coleções diferentes.
  - **Utilidade**: Permite obter uma visão completa de um post junto com todos os seus comentários, o que é útil em uma aplicação de blog.
  - **Reutilização**: Pode ser usada em várias partes da aplicação, como na visualização detalhada de um post.

Esta explicação deve ajudar a entender o propósito e a sintaxe das queries e métodos de consulta, mesmo para alguém que não tem muita experiência em programação.
