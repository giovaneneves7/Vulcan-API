# VADOC (Vulcan API Documentation - em construção)

![image](https://github.com/NeveScript/Vulcan-API/assets/123518676/41cbe60b-883b-423d-b89f-f54b792882be)

### 🔎 Sobre:
Repositório com código do plugin para Wordpress, client e API Spring Boot para o website [www.vulcannovel.com.br](https://vulcannovel.com.br/).
O client é responsável por fornecer uma interface agradável para que o usuário posso registrar, obter e gerenciar dados presentes no banco de dados. O plugin, por sua vez, é a ferramenta que lida com eventos do website, como novas postagens, e reduz o trabalho manual e repetitivo.
A API responde a requisições vindas tanto do client quanto do plugin e trata os dados recebidos antes de salvá-los na base de dados, assim como retorna os dados requisitados de maneira precisa, utilizando JSON como linguagem de comunicação.

### ⚙ Ferramentas e Linguagens Utilizadas:
![Java](https://img.shields.io/badge/Java-orange?style=for-the-badge&logo=java-8&logoColor=white) 
![Azure](https://img.shields.io/badge/Azure-blue?style=for-the-badge&logo=microsoft-azure&logoColor=white) 
![Spring](https://img.shields.io/badge/Spring%20Boot-green?style=for-the-badge&logo=spring-boot&logoColor=white) 
![PHP](https://img.shields.io/badge/PHP-blue?style=for-the-badge&logo=php&logoColor=white) 
![CSS](https://img.shields.io/badge/CSS-purple?style=for-the-badge&logo=css-3&logoColor=white) 
![HTML](https://img.shields.io/badge/HTML-orange?style=for-the-badge&logo=HTML&logoColor=white)
![JS](https://img.shields.io/badge/JavaScript-yellow?style=for-the-badge&logo=javascript&logoColor=white)
![Intellij](https://img.shields.io/badge/Intellij%20IDEA-gray?style=for-the-badge&logo=intellij-idea&logoColor=white) 
![Wordpress](https://img.shields.io/badge/Wordpress-black?style=for-the-badge&logo=wordpress&logoColor=white) 
![MySQL](https://img.shields.io/badge/MySQL-red?style=for-the-badge&logo=mysql&logoColor=white) 
![Bootstrap](https://img.shields.io/badge/Bootstrap-cyan?style=for-the-badge&logo=bootstrap&logoColor=black) 
![JSON](https://img.shields.io/badge/JSON-yellow?style=for-the-badge&logo=json&logoColor=black) 
![POSTMAN](https://img.shields.io/badge/postman-white?style=for-the-badge&logo=postman&logoColor=red) 
![Ngrok](https://img.shields.io/badge/ngrok-black?style=for-the-badge&logo=ngrok&logoColor=red) 

### 📁 Diretórios:
> [Client](https://github.com/NeveScript/Vulcan-API/tree/master/src/main/client): Diretório da interface construída para que o usuário possa interagir com a API. <br>
> [Plugins (PHP)](https://github.com/NeveScript/Vulcan-API/tree/master/src/main/php): Diretório com códigos PHP do plugin para Wordpress que capta eventos do website, faz requisições à API e gerencia alguns recursos. <br>
> [API](https://github.com/NeveScript/Vulcan-API/tree/master/src/main/java/br/com/vulcan/jvulcan/api): Diretório com o código Java da API Spring Boot responsável por alimentar o site com recursos da base de dados.

## 📑 Lista de endpoints
Todos os endpoints com ``v1`` são privados necessitam de um cabeçalho chamado ``Api-Key`` para serem acessados, caso não haja chave de acesso, o endpoint retornará o status code 401 (UNAUTHORIZED) com uma mensagem descrevendo o erro.

### 1. Novels

| Método  | Endpoint                                          | Parâmetros                                          | Body                                        |
|---------|---------------------------------------------------|-----------------------------------------------------|---------------------------------------------|
| **GET** |``/nekoyasha7/jvulcan-api/v1/novels``              | ``?nacionalidade`` [String - Opcional], <br> ``?tipo`` [String - Opcional] |--                    |
| **GET** |``/nekoyasha7/jvulcan-api/v1/novels/novel/{slug}`` |---                                                  |                                             |
| **PUT** |``/nekoyasha7/jvulcan-api/v1/novels/novel``        |---                                                  |                                             |

> **1.1.0 -** ``/nekoyasha7/jvulcan-api/v1/novels``:
Retorna uma array com todas as novels cadastradas no banco de dados e uma sub-array com seus respectivos banners, se tiverem banner. <br>
**Exemplo de requisição:**
```json
[
  {
    "id":1,
    "banner":[
                {
                  "id"   : 1,
                  "nome" : "banner_1",
                  "link" : "https://www.host.com.br/banner_1.png"
                },
                
                {
                  "id"   : 2,
                  "nome" : "banner_2",
                  "link" : "https://www.host.com.br/banner_2.png"
                }
              ],
    "colocacao_total" : 1,
    "colocacao_mensal": 1,
    "visualizacoes_totais" : 2980477,
    "visualizacoes_mensais": 50610,
    "slug"        : "ndn-nome-da-model",
    "nome_projeto": "Nome da Novel",
    "nacionalidade":"nacionalidade",
    "capa":"https://host.com.br/nome_da_novel.webp",
    "indice":"ndn-nome-da-model",
    "autor_ou_tradutor":"Autor ou Tradutor",
    "cargo":"Cargo",
    "status_projeto":"status",
    "nota_media_projeto":"5.00",
    "data_criacao_indice":"2020-02-26 16:47:11",
    "sinopse":"Sinopse da model",
    "id_cargo":"id do cargo o Discord"
  }
]
```
> **1.1.1 -** ``?nacionalidade``: Filtro que retorna apenas novels com a nacionalidade especificada. Até o momento, na ``v1``, não é possível especificar mais de uma nacionalidade, caso precise de um filtro mais amplo, veja a seção **1.1.2**.   

| Nacionalidades | Descrição  |
|----------------|------------|
| oci            | Ocidental  |
| jp             | Japonesa   |
| ch             | Chinesa    |
| co             | Corenana   | 
| br             | Brasileira |

> **1.1.2-** ``?tipo``: Filtro que retorna apenas novels do tipo especificado. Os tipos são "traduções", que englobam as nacionalidades "oci", "jp", "ch" e "co", e "originais", que se limitam apenas às novels brasileiras (ver seção **1.1.1**).

### 2. Banners
| Método     | Endpoint                                     | Parâmetros                                          | Body                                        |
|------------|----------------------------------------------|-----------------------------------------------------|---------------------------------------------|
| **GET**    |``/nekoyasha7/jvulcan-api/v1/banners``        | ``?max`` (Integer, Opcional)|                       | --                                          |         
| **GET**    |``/nekoyasha7/jvulcan-api/v1/banners/banner`` | --                                                  |--                                           |
| **POST**   |``/nekoyasha7/jvulcan-api/v1/banners``        |--                                                   |                                             |
| **DELETE** |``/nekoyasha7/jvulcan-api/v1/banners/{id}``   |                                                     |--                                           |

### 3. Postagens
| Método     | Endpoint                                     | Parâmetros                                          | Body                                        |
|------------|----------------------------------------------|-----------------------------------------------------|---------------------------------------------|
| **POST**   |``/nekoyasha7/jvulcan-api/v1/posts/post``     |--                                                   | --                                          |   

