# Sistema Automotivo – Gestão de Estoque de Veículos

API REST (CRUD) em **Java 17 + Spring Boot 3 + MySQL** para gerenciar o estoque de uma concessionária:
cadastro de veículos, marcas e modelos, consulta com filtros, atualização e remoção.

Trabalho da disciplina de Programação Orientada a Objetos – UniFECAF.

## Pré-requisitos

- JDK 17 ou superior
- Maven 3.8+ (ou use o Maven embutido da sua IDE)
- MySQL 8+ rodando em `localhost:3306`

## Como rodar

1. Garanta que o MySQL está ligado. O banco `estoque_veiculos` é criado automaticamente.
2. Se o seu usuário/senha do MySQL não for `root`/`root`, defina antes de rodar:
   - Windows (PowerShell): `$env:DB_USER="root"; $env:DB_PASSWORD="sua_senha"`
   - Linux/macOS: `export DB_USER=root DB_PASSWORD=sua_senha`
3. Na pasta do projeto:

```bash
mvn spring-boot:run
```

4. Acesse:
   - Tela simples (frontend opcional): http://localhost:8080
   - API: http://localhost:8080/api/veiculos

> Opcional: `database/schema.sql` contém o modelo físico e dados de exemplo para a demonstração.

## Arquitetura

```
controller  ->  service  ->  repository  ->  MySQL
   (REST)     (regras)       (Spring Data JPA)
```

```
src/main/java/br/com/unifecaf/estoque
├── controller   MarcaController, ModeloController, VeiculoController
├── service      MarcaService, ModeloService, VeiculoService
├── repository   MarcaRepository, ModeloRepository, VeiculoRepository, VeiculoSpecs (filtros)
├── model        EntidadeBase, Marca, Modelo, Veiculo, StatusVeiculo, TipoVeiculo
├── dto          Requests/Responses (validação com Bean Validation)
└── exception    Tratamento global de erros (404, 400, 409)
```

## Endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/marcas` | Cadastra marca |
| GET | `/api/marcas` | Lista marcas |
| GET/PUT/DELETE | `/api/marcas/{id}` | Busca, atualiza, remove |
| POST | `/api/modelos` | Cadastra modelo (informa `marcaId`) |
| GET | `/api/modelos?marcaId=` | Lista modelos (filtro opcional por marca) |
| GET/PUT/DELETE | `/api/modelos/{id}` | Busca, atualiza, remove |
| POST | `/api/veiculos` | Cadastra veículo |
| GET | `/api/veiculos` | Lista/filtra veículos |
| GET | `/api/veiculos/{id}` | Busca por id |
| PUT | `/api/veiculos/{id}` | Atualização completa |
| PATCH | `/api/veiculos/{id}` | Atualiza preço, quilometragem e/ou status |
| DELETE | `/api/veiculos/{id}` | Remove veículo |

### Filtros de `GET /api/veiculos` (todos opcionais e combináveis)

`marca`, `modelo`, `precoMin`, `precoMax`, `anoMin`, `anoMax`, `status`, `tipo`

Exemplo: `/api/veiculos?marca=toyota&precoMax=150000&anoMin=2021&status=DISPONIVEL`

### Exemplo de corpo para cadastrar veículo

```json
{
  "modeloId": 1,
  "anoFabricacao": 2022,
  "cor": "Vermelho",
  "preco": 78000.00,
  "quilometragem": 15000,
  "tipo": "CARRO",
  "status": "DISPONIVEL"
}
```

Valores aceitos: `tipo` = CARRO, MOTO, SUV, PICAPE, CAMINHAO · `status` = DISPONIVEL, RESERVADO, VENDIDO, DESCONTINUADO.

Mais exemplos prontos de chamadas no arquivo `requests.http`.

## Conceitos de POO aplicados

- **Classes e atributos:** `Marca`, `Modelo`, `Veiculo`.
- **Encapsulamento:** atributos `private` com getters/setters; regra `marcarComoVendido()` dentro de `Veiculo`.
- **Herança:** `Marca`, `Modelo` e `Veiculo` herdam de `EntidadeBase` (id comum).
- **Associação:** `Veiculo` → `Modelo` → `Marca`.
- **Enums:** `StatusVeiculo` e `TipoVeiculo`.
- **Injeção de dependência / camadas:** controller → service → repository.
