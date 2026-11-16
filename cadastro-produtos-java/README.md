# Cadastro de Produtos — Java

Sistema desktop simples para cadastro de **produtos** e **categorias** de uma loja de eletrônicos, feito em Java com telas em `JOptionPane` (Swing).

Projeto desenvolvido no capítulo 6 do curso **Nano Java (FIAP)**, praticando orientação a objetos e organização em camadas.

## Funcionalidades

- Cadastrar categoria (sem permitir duplicidade)
- Cadastrar produto (nome, descrição, categoria, preço e data de cadastro)
- Alterar dados de um produto
- Consultar produto por ID
- Consultar produtos por categoria
- Encerrar o sistema

O sistema já inicia com as categorias **Eletrônicos, Celulares, Livros e Games** e com dois produtos de exemplo (Kindle e iPhone 14 PRO MAX).

## Tecnologias

- Java 17
- Swing (`JOptionPane`)
- Collections API (`List`, `Vector`) e Streams
- `BigDecimal` para valores e `LocalDateTime` para datas
- Maven

## Estrutura

```
src/main/java/br/com/fiap/nano/produtos
├── App.java                          # ponto de entrada (menu principal)
├── model
│   ├── Categoria.java
│   └── Produto.java
├── repository                        # "banco de dados" em memória
│   ├── CategoriaCollectionRepository.java
│   └── ProdutoCollectionRepository.java
└── view                              # telas (JOptionPane)
    ├── Opcao.java                    # enum com as opções do menu
    ├── OpcaoView.java
    ├── CategoriaView.java
    └── ProdutoView.java
```

- **model**: entidades do sistema. Um produto pertence a uma categoria; a categoria não conhece seus produtos.
- **repository**: simula um repositório de dados usando coleções Java (os dados ficam só em memória enquanto o programa roda).
- **view**: interação com o usuário.

## Como executar

**IntelliJ IDEA:** abra a pasta do projeto (ele é reconhecido como projeto Maven) e rode a classe `App`.

**Terminal (Maven):**

```bash
mvn package
java -jar target/produtos-1.0.0.jar
```

## Observações

Os dados não são persistidos em banco: ao fechar o programa, as categorias e produtos cadastrados durante o uso são perdidos.
