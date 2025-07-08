# 📙 Documentação das Classes

---

## 🔶 `Estado.java`

> **Tipo:** Classe utilitária (constantes estáticas)  
> **Responsabilidade:** Substituir o uso de `enum` definindo os estados da célula (`SUSCETIVEL`, `INFECTADO`, `RECUPERADO`) como inteiros.

### ✔️ O que ela faz:
- Define três constantes inteiras para os estados do modelo SIR:
  - `SUSCETIVEL = 0`
  - `INFECTADO = 1`
  - `RECUPERADO = 2`

### 🧩 Utilização:
- Usada por `Celula`, `AutomatoCelular` e `Simulacao` no lugar do `enum`.

### 🎯 Objetivo:
- Tornar o código mais acessível para quem ainda não estudou `enum` em Java.
- Facilitar a manipulação de estados usando números inteiros simples.



## 🔶 `Celula.java`

> **Tipo:** Classe concreta  
> **Responsabilidade:** Representar um indivíduo da população (célula da grade) e controlar sua lógica de transição de estados.

### ✔️ O que ela faz:
- Armazena o estado atual e o próximo estado da célula (como inteiro).
- Implementa a interface `ComportamentoCelular`.
- Aplica as regras de transição com base na vizinhança e nas probabilidades.
- Controla o tempo de infecção para possíveis decisões.

### 🧩 Utilização:
- Cada elemento da grade em `AutomatoCelular` é uma instância de `Celula`.
- Chamado em cada iteração da simulação.



## 🔶 `TransicaoProbabilistica.java`

> **Tipo:** Classe utilitária  
> **Responsabilidade:** Encapsular os parâmetros probabilísticos e fornecer funções auxiliares.

### ✔️ O que ela faz:
- Armazena os parâmetros `Pv`, `Ps`, `Pc`, `Pd`, `Po`, `k`.
- Executa sorteios com base em probabilidades.
- Calcula a chance de infecção com base no número de vizinhos infectados (`1 - e^(-k * n)`).

### 🧩 Utilização:
- Utilizada por `Celula` no método `decidirProximoEstado(...)`.



## 🔶 `AutomatoCelular.java`

> **Tipo:** Classe concreta, implementa `IAutomatoCelular`  
> **Responsabilidade:** Controlar a grade de células e aplicar a lógica do autômato.

### ✔️ O que ela faz:
- Inicializa uma grade de `Celula[][]` com maioria suscetível e 1% infectada.
- A cada iteração:
  1. Cada célula decide o próximo estado com base na vizinhança.
  2. Todos os estados são atualizados simultaneamente.
- Trata bordas com contorno toroidal (vizinhança circular).

### 🧩 Utilização:
- Executado diretamente pela classe `Simulacao`.


## 🔶 `Simulacao.java`

> **Tipo:** Classe principal (executável)  
> **Responsabilidade:** Executar a simulação, armazenar os dados e gerar o gráfico com os resultados.

### ✔️ O que ela faz:
- Instancia o autômato e define os parâmetros iniciais.
- Roda a simulação por um número fixo de iterações.
- Armazena a contagem de `S`, `I` e `R` a cada passo.
- Utiliza a biblioteca **XChart** para exibir um gráfico com os dados.

### 🧩 Utilização:
- Ponto de entrada da aplicação (contém o método `main`).



## 🧩 Fluxo Geral das Classes

```
Simulacao
└── usa → IAutomatoCelular (interface)
└── implementado por → AutomatoCelular
├── contém → Celula[][] (grade de indivíduos)
│ └── cada Celula implementa → ComportamentoCelular (interface)
│ └── usa → TransicaoProbabilistica (regras e probabilidades)
└── utiliza constantes da → Estado (SUSCETIVEL, INFECTADO, RECUPERADO)
```


## 📌 Conclusão

As classes deste projeto foram organizadas para aplicar os seguintes princípios de POO:

- **Responsabilidade única:** cada classe tem uma função bem definida.
- **Encapsulamento:** os dados de cada célula e do autômato estão protegidos.
- **Abstração e polimorfismo:** via interfaces `ComportamentoCelular` e `IAutomatoCelular`.
- **Simplicidade:** ao usar `int` em vez de `enum`, o projeto ficou mais acessível sem perder estrutura.

Este projeto demonstra de forma clara como aplicar POO em uma simulação científica, integrando lógica matemática, modelagem computacional e visualização gráfica.
