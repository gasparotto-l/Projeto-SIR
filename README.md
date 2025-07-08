# 🧪 Simulação do Modelo SIR com Autômatos Celulares (Java - POO)

Este projeto implementa uma simulação computacional do modelo epidemiológico **SIR (Suscetível-Infectado-Recuperado)** utilizando **Autômatos Celulares 2D**, programado em **Java**, com foco nos conceitos de **Programação Orientada a Objetos (POO)**.

> 🔬 Trabalho acadêmico desenvolvido em grupo para a disciplina de **Programação Orientada a Objetos**, com o objetivo de aplicar conceitos como encapsulamento, abstração, interfaces, e visualização de dados científicos através de simulações.

---

## 📚 Objetivos

- Compreender a dinâmica de propagação de doenças infecciosas através de autômatos celulares.
- Implementar o modelo SIR com base em **regras de transição probabilísticas (PCA)**.
- Aplicar boas práticas de **POO** com foco em simplicidade e clareza.
- Visualizar a evolução dos estados `S`, `I` e `R` em forma de gráfico.

---

## 🧱 Estrutura de Classes

| Classe / Interface              | Papel                                                                 |
|--------------------------------|------------------------------------------------------------------------|
| `Estado`                       | Define os estados da célula usando constantes inteiras (`S`, `I`, `R`). |
| `Celula`                       | Representa um indivíduo da população com estado e comportamento.        |
| `ComportamentoCelular`         | Interface que define como uma célula decide seu próximo estado.         |
| `TransicaoProbabilistica`      | Encapsula as regras de transição com base em probabilidades.            |
| `AutomatoCelular`              | Gerencia a grade de células e aplica as atualizações simultâneas.       |
| `IAutomatoCelular`             | Interface que abstrai o funcionamento do autômato.                      |
| `Simulacao`                    | Executa a simulação, coleta os dados e exibe o gráfico usando XChart.   |

---

## 🧪 Parâmetros do Modelo

Os parâmetros abaixo controlam o comportamento da simulação:

| Parâmetro             | Descrição                            | Valor Sugerido |
|----------------------|----------------------------------------|----------------|
| `Pv`                 | Probabilidade de vacinação espontânea  | 3%             |
| `Ps`                 | Probabilidade de caso importado        | 1%             |
| `Pc`                 | Probabilidade de cura                  | 60%            |
| `Pd`                 | Probabilidade de morte (infectado)     | 30%            |
| `Po`                 | Probabilidade de morte (recuperado)    | 10%            |
| `k`                  | Infectividade (vizinhos infectados)    | 1.0            |
| `Grade`              | Tamanho da população (2D)              | 200 x 200      |
| `Tempo`              | Iterações da simulação                 | 100            |

---

## 🧮 Lógica do Algoritmo (Resumo)

1. Cada célula avalia seu estado atual e seus vizinhos:
   - Se `Suscetível`: pode se vacinar, pegar infecção importada ou ser infectada por contato.
   - Se `Infectado`: pode se curar, morrer ou continuar infectado.
   - Se `Recuperado`: pode morrer (voltar a ser suscetível) ou continuar recuperado.
2. Todas as decisões são baseadas em **sorteios com probabilidades definidas**.
3. Após todas as células decidirem, a grade é **atualizada simultaneamente**.
4. O número de indivíduos em cada estado é **registrado a cada passo**.

---

## 📈 Visualização de Dados

- Os dados são exibidos em um **gráfico gerado com XChart**.
- As curvas mostram a evolução da quantidade de `Suscetíveis`, `Infectados` e `Recuperados` ao longo das iterações.
- O gráfico é mostrado ao final da simulação em uma janela gráfica.



## 📦 Pré-requisitos

- Java 8 ou superior instalado.
- Biblioteca **[XChart](https://knowm.org/open-source/xchart/)** adicionada ao classpath.
  - Se estiver usando Eclipse ou IntelliJ, adicione o `.jar` da XChart manualmente na build path.


## ▶️ Como Executar
- Compile todos os arquivos .java (incluindo os da biblioteca XChart).
- Execute a classe Simulacao.java.
   - O gráfico aparecerá ao fim da simulação.


