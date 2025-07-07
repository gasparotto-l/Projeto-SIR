# 🧪 Simulação do Modelo SIR com Autômatos Celulares (Java - POO)

Este projeto implementa uma simulação computacional do modelo epidemiológico **SIR (Suscetível-Infectado-Recuperado)** utilizando **Autômatos Celulares 2D**, programado em **Java**, com foco nos conceitos de **Programação Orientada a Objetos (POO)**.

> 🔬 Trabalho acadêmico desenvolvido em grupo para a disciplina de **Programação Orientada a Objetos**, com o objetivo de aplicar conceitos de modelagem, encapsulamento, abstração e visualização de dados científicos.



## 📚 Objetivos

- Compreender a dinâmica de propagação de doenças infecciosas através de autômatos celulares.
- Implementar o modelo SIR com base em **regras de transição probabilísticas (PCA)**.
- Aplicar técnicas e boas práticas de **POO** em Java.
- Visualizar a evolução dos estados `S`, `I` e `R` com gráficos.



## 🧱 Estrutura de Classes

| Classe / Interface              | Papel                                                               |
|--------------------------------|---------------------------------------------------------------------|
| `Celula`                       | Representa um indivíduo da população com estado (S, I, R).          |
| `ComportamentoCelular`         | Interface que define o comportamento de transição da célula.        |
| `TransicaoProbabilistica`      | Encapsula os parâmetros probabilísticos (vacinação, cura, etc).     |
| `AutomatoCelular`              | Controla a grade de células e aplica as regras de transição.        |
| `IAutomatoCelular`             | Interface para abstrair o funcionamento do autômato.                |
| `Simulacao`                    | Gerencia a simulação, coleta dados e exibe gráficos com XChart.     |



## 🧪 Parâmetros do Modelo

Os parâmetros utilizados na simulação são baseados na Figura 1 do artigo de referência:

| Parâmetro             | Descrição                      | Valor Sugerido |
|----------------------|----------------------------------|----------------|
| `Pv`                 | Probabilidade de vacinação       | 3%             |
| `Ps`                 | Probabilidade de caso importado  | 1%             |
| `Pc`                 | Probabilidade de cura            | 60%            |
| `Pd`                 | Probabilidade de morte (infectado) | 30%          |
| `Po`                 | Probabilidade de morte (recuperado) | 10%         |
| `k`                  | Infectividade (taxa de contágio) | 1.0            |
| Grade                | Tamanho da população (2D)        | 200 x 200      |
| Tempo                | Iterações da simulação           | 100            |

---

## 🧮 Lógica do Algoritmo (Resumo)

1. Cada célula avalia seu estado e vizinhança:
   - Se `Suscetível`: pode se vacinar, importar infecção ou ser infectado.
   - Se `Infectado`: pode se curar, morrer ou permanecer infectado.
   - Se `Recuperado`: pode morrer ou manter estado.
2. As decisões são baseadas em **sorteios probabilísticos**.
3. A grade é atualizada **simultaneamente** após todos decidirem.
4. A evolução dos grupos `S`, `I` e `R` é registrada a cada passo.



## 📈 Visualização de Dados

A evolução dos estados é exibida em um gráfico dinâmico gerado com a biblioteca **XChart**, onde cada linha representa a contagem de indivíduos suscetíveis, infectados e recuperados ao longo do tempo.



### 📦 Pré-requisitos

- Java 8+ instalado.
- Biblioteca [`XChart`](https://knowm.org/open-source/xchart/) adicionada ao classpath (pode usar Maven ou JAR manual).

