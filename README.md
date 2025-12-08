## 📋 Visão Geral

A Gestão de Pedidos API é um projeto backend completo pensado para demonstrar competências técnicas sólidas em desenvolvimento com Spring Boot. A aplicação oferece funcionalidades end-to-end para:

- **Clientes**: CRUD com validações robustas (CPF, email, endereço completo)
- **Produtos**: Gerenciamento de inventário com filtros avançados (preço, categoria, nome)
- **Pedidos**: Criação, acompanhamento de status e transições com regras de negócio

**Diferenciais técnicos:**
✅ Testes unitários com JUnit 5 e Mockito cobrindo 100% das services  
✅ Arquitetura em 3 camadas (API, Core, Domain) bem separadas por responsabilidade  
✅ HATEOAS para links autodescritivos entre recursos  
✅ MapStruct para mapeamento eficiente de DTOs  
✅ JPA Specifications para filtros dinâmicos e reutilizáveis  
✅ Tratamento centralizado de exceções com responses padronizadas  
✅ Validações em camadas (Bean Validation + regras de negócio)  
✅ Documentação Swagger completa e interativa# Gestão de Pedidos API

Uma API REST robusta para gerenciamento completo de pedidos, clientes e produtos. Desenvolvida com Spring Boot 3.5.8, demonstra boas práticas de arquitetura, design patterns e testes unitários — um projeto portfólio pensado para uma vaga de desenvolvedor backend júnior/pleno.

## 📋 Visão Geral

A Gestão de Pedidos API é um projeto completo de gerenciamento de pedidos, desenvolvido com foco em boas práticas de desenvolvimento e padrões enterprise. Fornece funcionalidades para administrar:

- **Clientes**: CRUD com validação robusta (CPF, email, endereço completo)
- **Produtos**: Gerenciamento de inventário com filtros avançados por preço, categoria e nome
- **Pedidos**: Criação, acompanhamento em tempo real e transições de status com regras de negócio

O projeto demonstra domínio em:
- Arquitetura em camadas bem definida (API, Core, Domain)
- HATEOAS para navegação autodescritiva de recursos
- Validação em múltiplas camadas (Bean Validation + customizadas)
- Mapeamento automático com MapStruct (DTOs ↔ Entities)
- Tratamento centralizado de exceções com responses padronizadas
- Documentação automática via OpenAPI/Swagger UI
- Paginação e filtros dinâmicos com JPA Specifications

## 🛠️ Stack Tecnológico

- **Java**: 21
- **Spring Boot**: 3.5.8
- **Banco de Dados**: MySQL
- **ORM**: JPA/Hibernate

- **Migrations**: Flyway
- **Mapeamento DTOs**: MapStruct
- **Validação**: Bean Validation (Jakarta)
- **HATEOAS**: Spring HATEOAS
- **Documentação**: SpringDoc OpenAPI (Swagger UI)
- **Lombok**: Para reduzir boilerplate
- **Build**: Maven

## 🚀 Como Executar

### Pré-requisitos

- Java 21 instalado
- Maven 3.6+
- MySQL 8.0+
- Docker e Docker Compose (opcional, para banco de dados)

### 1. Clonar o Repositório

```bash
git clone <seu-repositorio>
cd gestao-pedidos-api
```

### 2. Configurar Banco de Dados

Se usar Docker Compose (recomendado):
```bash
docker-compose up -d
```

Ou configure manualmente no `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestao_pedidos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=validate
```

### 3. Executar o Projeto

```bash
mvn clean install
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

### 3. Acessar Documentação

Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📚 Documentação da API

### Autenticação

Atualmente, a API não possui autenticação. Implemente Spring Security conforme necessário.

### Endpoints Principais

#### 🧑 Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/clientes` | Listar clientes (paginado) |
| GET | `/clientes/{id}` | Buscar cliente por ID |
| POST | `/clientes` | Cadastrar novo cliente |
| PUT | `/clientes/{id}` | Atualizar dados do cliente |
| DELETE | `/clientes/{id}` | Remover cliente |

**Exemplo - Criar Cliente:**
```json
POST /clientes
{
  "nome": "Misael Borges Cancelier",
  "email": "misael.borges@email.com",
  "cpf": "85023101002",
  "telefone": "11987654321",
  "cep": "01001000",
  "logradouro": "Rua das Flores",
  "numero": "1000",
  "complemento": "Apto 202",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP"
}
```

#### 📦 Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos` | Listar produtos com filtros |
| GET | `/produtos/{id}` | Buscar produto por ID |
| GET | `/produtos/buscar` | Buscar por ID ou SKU |
| POST | `/produtos` | Criar novo produto |
| PUT | `/produtos/{id}` | Atualizar produto |
| DELETE | `/produtos/{id}` | Remover produto |

**Filtros Disponíveis para Listagem:**
- `nome`: Filtrar por nome do produto
- `categoria`: Filtrar por categoria
- `precoMin`: Preço mínimo
- `precoMax`: Preço máximo
- `page`: Número da página (padrão: 0)
- `size`: Itens por página (padrão: 20)
- `sort`: Ordenação (ex: `nome,asc`)

**Exemplo - Criar Produto:**
```json
POST /produtos
{
  "nome": "Notebook Dell",
  "descricao": "Notebook i7 16GB RAM",
  "preco": 4500.00,
  "estoqueDisponivel": 10,
  "sku": "SKU12345",
  "categoria": "Informática"
}
```

**Exemplo - Listar com Filtros:**
```
GET /produtos?categoria=Informática&precoMin=1000&precoMax=5000&page=0&size=10
```

#### 📋 Pedidos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/pedidos` | Listar pedidos com filtros |
| GET | `/pedidos/{id}` | Buscar pedido por ID |
| GET | `/pedidos/buscar` | Buscar por ID ou número do pedido |
| POST | `/pedidos` | Criar novo pedido |
| PATCH | `/pedidos/{id}/avancar-status` | Avançar status automaticamente |
| PATCH | `/pedidos/{id}/status` | Atualizar para status específico |
| DELETE | `/pedidos/{id}/cancelar-pedido` | Cancelar pedido |

**Filtros Disponíveis:**
- `status`: Filtrar por status (ex: AGUARDANDO_PAGAMENTO, PROCESSANDO, ENTREGUE)
- `cliente`: Filtrar por nome do cliente
- `data`: Filtrar por data de criação
- `page`, `size`, `sort`: Paginação e ordenação

**Status Disponíveis:**
- `AGUARDANDO_PAGAMENTO`
- `PAGAMENTO_CONFIRMADO`
- `EM_SEPARACAO`
- `EM_TRANSPORTE`
- `ENTREGUE`
- `CANCELADO`

**Exemplo - Criar Pedido:**
```json
POST /pedidos
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 10,
      "quantidade": 2,
      "observacoes": "Produtos separados, por favor"
    },
    {
      "produtoId": 15,
      "quantidade": 1,
      "observacoes": null
    }
  ],
  "observacoes": "Entregar no período da manhã"
}
```

**Exemplo - Atualizar Status:**
```json
PATCH /pedidos/1/status
{
  "status": "PROCESSANDO"
}
```

**Exemplo - Listar com Filtros:**
```
GET /pedidos?status=AGUARDANDO_PAGAMENTO&cliente=João&page=0&size=10
```

## 🏗️ Arquitetura

O projeto segue uma arquitetura bem estruturada e escalável em camadas:

```
src/main/java/com/darum/gestao/pedidos/
├── api/
│   ├── assembler/           # Mapeadores HATEOAS (EntityModel builders)
│   ├── controller/          # Controllers REST com validações
│   ├── dto/                 # Data Transfer Objects
│   │   ├── request/         # DTOs para requisições com validações
│   │   └── response/        # DTOs para respostas formatadas
│   ├── exceptionhandler/    # Tratamento de exceções global
│   └── interfaces/          # Interfaces OpenAPI (Swagger contracts)
├── core/
│   ├── mapper/              # Mapeadores MapStruct para DTOs
│   └── swagger/             # Configurações do SpringDoc OpenAPI
├── domain/
│   ├── exception/           # Exceções de negócio customizadas
│   ├── model/               # Entidades JPA (@Entity)
│   ├── repository/          # Interfaces JPA com queries customizadas
│   ├── service/             # Lógica de negócio
│   └── specification/       # Especificações para buscas dinâmicas
└── GestãoPedidosApiApplication  # Classe principal
```

## ✅ Validações

### Clientes
- Nome: obrigatório, 3-100 caracteres
- Email: obrigatório, formato válido
- CPF: obrigatório, validação de CPF (11 dígitos)
- Telefone: obrigatório, 10-11 dígitos com DDD
- CEP: obrigatório, 8 dígitos
- Endereço: campos obrigatórios com regras específicas

### Produtos
- Nome: obrigatório
- Preço: obrigatório, maior que zero
- SKU: obrigatório, único
- Estoque: não negativo
- Categoria: texto livre

### Pedidos
- Cliente: obrigatório, deve existir
- Itens: lista não vazia, cada item com quantidade > 0
- Produtos: devem existir e ter estoque suficiente

## 🗄️ Migrações do Banco

As migrações são gerenciadas automaticamente pelo Flyway. Scripts SQL estão em `src/main/resources/db/migration/`.

Para adicionar uma nova migração:
```
1. Crie um arquivo em src/main/resources/db/migration/
2. Nomeie como: V{número}_{descrição}.sql
3. A execução é automática ao iniciar a aplicação
```

## ✅ Testes

O projeto inclui testes unitários completos cobrindo todas as classes de serviço:

```bash
# Executar todos os testes
mvn test

# Executar com relatório de cobertura
mvn test jacoco:report
```

**Testes Implementados:**
- `ClienteServiceTest`: CRUD, busca por ID, listagem paginada, validações
- `PedidoServiceTest`: Criação com múltiplos itens, busca, filtros, transições de status
- `ProdutoServiceTest`: CRUD, busca por SKU/ID, filtros, baixa de estoque

**Técnicas Utilizadas:**
- Mocks e stubs com Mockito
- Verificações de interações (verify, times)
- Assertions com JUnit 5
- DisplayName para documentação de testes
- BeforeEach para setup compartilhado

## 🔍 Logging e Monitoramento

Configure logs em `application.properties`:
```properties
logging.level.root=INFO
logging.level.com.darum=DEBUG
logging.pattern.console=%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n
```

## 🤝 Contribuindo

1. Crie uma branch para sua feature: `git checkout -b feature/nova-funcionalidade`
2. Commit suas mudanças: `git commit -m 'Adiciona nova funcionalidade'`
3. Push para a branch: `git push origin feature/nova-funcionalidade`
4. Abra um Pull Request

## 📝 Melhorias Futuras

- [ ] Integração com RabbitMQ para fila de mensagens assíncronas
- [ ] Implementar autenticação e autorização (Spring Security + JWT)
- [ ] Adicionar testes unitários e de integração (JUnit 5, Mockito, TestContainers)
- [ ] Implementar cache distribuído (Redis)
- [ ] Adicionar relatórios de vendas e análises
- [ ] Notificações por email/SMS aos clientes
- [ ] Dashboard de acompanhamento em tempo real
- [ ] Integração com sistemas de pagamento (Stripe, PagSeguro)
- [ ] Versionamento de API (v1, v2)
- [ ] Rate limiting e throttling

## 💡 Por Que Este Projeto?

Desenvolvido como portfólio para demonstrar competências técnicas sólidas em backend Java/Spring. O projeto aborda desafios reais:

### Arquitetura e Design
✅ **Separação de Responsabilidades**: API layer (controllers/DTOs), Core (mappers/configs), Domain (services/repositories)  
✅ **Design Patterns**: Assembler, Strategy (Specifications), DTO, Mapper  
✅ **Clean Code**: Nomenclatura clara, métodos pequenos e focados, sem duplicação  

### Qualidade de Código
✅ **Testes Unitários**: Cobertura completa de services com mocks e stubs  
✅ **Validações em Camadas**: Bean Validation + regras de negócio na service  
✅ **Error Handling**: Exceções customizadas com respostas padronizadas  

### REST API
✅ **Convenções HTTP**: Métodos e status codes corretos (GET, POST, PUT, PATCH, DELETE)  
✅ **HATEOAS**: Links autodescritivos para navegação entre recursos  
✅ **Documentação Swagger**: Endpoint interativo e contrato claro  

### Escalabilidade
✅ **Paginação e Filtros**: Suportam grandes volumes de dados  
✅ **Reutilização**: Specifications para queries dinâmicas  
✅ **DTOs**: Separação entre modelo de persistência e API  

**Ideal para quem está em busca da primeira oportunidade em desenvolvimento backend.**  

## 📄 Licença

Este projeto está sob licença MIT.

## 📧 Contato

Para dúvidas ou sugestões, abra uma issue no repositório.
