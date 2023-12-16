# Tetris - Programação Orientada a Objetos

## Descrição
Este projeto é uma implementação do clássico jogo Tetris, realizada como parte da disciplina de Programação Orientada a Objetos. O foco está em aplicar os princípios de programação orientada a objetos e desenvolver habilidades em Java, particularmente em relação à construção de interfaces gráficas e lógica de jogo.

Desenvolvido por: **Jose Mendes** (a040866@umaia.pt)

## Funcionalidades Implementadas
- Estrutura de projeto base em Java com organização inicial dos diretórios.
- Configuração inicial do ambiente de desenvolvimento e do sistema de build usando Gradle.
- Criação de esqueletos iniciais para as classes principais do jogo (ainda sem implementação de lógica específica).

## Funcionalidades Planeadas
- Implementação da lógica de movimentação e rotação das peças do Tetris.
- Desenvolvimento da lógica para eliminação de linhas e pontuação.
- Criação da interface gráfica do usuário para interação com o jogo.
- Adição de controles para o usuário (teclado ou mouse).

## Arquitetura do Projeto
O projeto segue o padrão MVC (Model-View-Controller), dividindo a lógica do jogo, a interface do usuário e o controle do jogo em partes separadas e interconectadas.

### Estrutura de Diretórios
A estrutura de diretórios do projeto é a seguinte:

```bash
directory_structure = """
├───.gradle
│   ├───7.4
│   │   ├───checksums
│   │   ├───dependencies-accessors
│   │   ├───executionHistory
│   │   ├───fileChanges
│   │   ├───fileHashes
│   │   └───vcsMetadata
│   ├───buildOutputCleanup
│   └───vcs-1
├───.idea
│   ├───codeStyles
│   ├───inspectionProfiles
│   └───modules
│       └───app
├───.run
├───app
│   ├───build
│   │   ├───classes
│   │   │   └───java
│   │   │       ├───main
│   │   │       │   └───Tetris
│   │   │       │       ├───controller
│   │   │       │       ├───model
│   │   │       │       └───view
│   │   │       └───test
│   │   │           └───Tetris
│   │   ├───distributions
│   │   ├───generated
│   │   │   └───sources
│   │   │       ├───annotationProcessor
│   │   │       │   └───java
│   │   │       │       ├───main
│   │   │       │       └───test
│   │   │       └───headers
│   │   │           └───java
│   │   │               ├───main
│   │   │               └───test
│   │   ├───libs
│   │   ├───reports
│   │   │   └───tests
│   │   │       └───test
│   │   │           ├───classes
│   │   │           ├───css
│   │   │           ├───js
│   │   │           └───packages
│   │   ├───resources
│   │   │   ├───main
│   │   │   └───test
│   │   ├───scripts
│   │   ├───test-results
│   │   │   └───test
│   │   │       └───binary
│   │   └───tmp
│   │       ├───compileJava
│   │       ├───compileTestJava
│   │       ├───jar
│   │       └───test
│   └───src
│       ├───main
│       │   ├───java
│       │   │   └───Tetris
│       │   │       ├───controller
│       │   │       ├───model
│       │   │       └───view
│       │   └───resources
│       └───test
│           ├───java
│           │   └───Tetris
│           └───resources
├───build
│   ├───classes
│   │   └───java
│   ├───distributions
│   ├───libs
│   ├───resources
│   ├───scripts
│   └───tmp
│       └───jar
├───Docs
│   └───IMG
└───gradle
└───wrapper
"""
```
## Tecnologias Utilizadas
- Java
- Gradle para gestao de dependências e build

## Como Executar
Instruções para clonar o repositório, compilar e executar o jogo (a serem adicionadas conforme o avanço do desenvolvimento).

## Licença
Informações sobre a licença do projeto.

## Status do Projeto
O projeto está em desenvolvimento ativo. Ainda não há uma versão funcional disponível.
