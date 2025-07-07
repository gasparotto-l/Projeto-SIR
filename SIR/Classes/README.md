# 📙 Documentação das Classes


### 🔶 `Celula.java`

> **Tipo:** Classe concreta  
> **Responsabilidade:** Representar um indivíduo da população (célula da grade) e controlar sua lógica de transição de estados.

### ✔️ O que ela faz:
- Guarda o estado atual (`SUSCETIVEL`, `INFECTADO`, `RECUPERADO`) e o próximo estado.
- Implementa a interface `ComportamentoCelular`.
- Conta vizinhos infectados, aplica regras probabilísticas, e define o próximo estado.

### 🧩 Utilização:
- Cada célula da grade em `AutomatoCelular` é uma instância de `Celula`.

### Comando comentado:
```java
public class Celula implements ComportamentoCelular {

    public enum Estado {
        SUSCETIVEL, INFECTADO, RECUPERADO
    }

    private Estado estadoAtual;
    private Estado proximoEstado;
    private int tempoInfectado;

    public Celula(Estado estadoInicial) {
        this.estadoAtual = estadoInicial;
        this.proximoEstado = estadoInicial;
        this.tempoInfectado = 0;
    }

    public Estado getEstado() {
        return estadoAtual;
    }

    public void definirProximoEstado(Estado estado) {
        this.proximoEstado = estado;
    }

    public void atualizarEstado() {
        this.estadoAtual = this.proximoEstado;
    }

    public int getTempoInfectado() {
        return tempoInfectado;
    }

    public void incrementarTempoInfectado() {
        this.tempoInfectado++;
    }

    public void resetarTempoInfectado() {
        this.tempoInfectado = 0;
    }

    // Lógica principal: decide o próximo estado da célula com base em regras probabilísticas e vizinhos
    @Override
    public void decidirProximoEstado(Celula[][] vizinhanca, TransicaoProbabilistica transicoes) {
        switch (estadoAtual) {
            case SUSCETIVEL:
                if (transicoes.sorteio(transicoes.Pv)) {
                    definirProximoEstado(Estado.RECUPERADO); // vacinação
                } else if (transicoes.sorteio(transicoes.Ps)) {
                    definirProximoEstado(Estado.INFECTADO); // caso importado
                } else {
                    int infectados = contarInfectados(vizinhanca);
                    double Pi = transicoes.calcularProbInfeccao(infectados);
                    definirProximoEstado(transicoes.sorteio(Pi) ? Estado.INFECTADO : Estado.SUSCETIVEL);
                }
                break;
            case INFECTADO:
                if (transicoes.sorteio(transicoes.Pc)) {
                    definirProximoEstado(Estado.RECUPERADO); // cura
                    resetarTempoInfectado();
                } else if (transicoes.sorteio(transicoes.Pd)) {
                    definirProximoEstado(Estado.SUSCETIVEL); // morte → retorna como suscetível
                    resetarTempoInfectado();
                } else {
                    definirProximoEstado(Estado.INFECTADO);
                    incrementarTempoInfectado();
                }
                break;
            case RECUPERADO:
                definirProximoEstado(transicoes.sorteio(transicoes.Po) ? Estado.SUSCETIVEL : Estado.RECUPERADO); // pode morrer
                break;
        }
    }

    // Conta quantos vizinhos estão no estado INFECTADO
    private int contarInfectados(Celula[][] vizinhos) {
        int count = 0;
        for (Celula[] linha : vizinhos) {
            for (Celula c : linha) {
                if (c != null && c.getEstado() == Estado.INFECTADO) {
                    count++;
                }
            }
        }
        return count;
    }
}

```

## 🔶 `TransicaoProbabilistica.java`

> **Tipo:** Classe utilitária  
> **Responsabilidade:** Encapsular todos os parâmetros probabilísticos e realizar sorteios com base neles.

### ✔️ O que ela faz:
- Guarda probabilidades como `Pv`, `Ps`, `Pc`, `Pd`, `Po`, e o parâmetro `k`.
- Fornece métodos como:
  - `sorteio(probabilidade)`
  - `calcularProbInfeccao(vizinhosInfectados)`

### 🧩 Utilização:
- Usada por `Celula` e `AutomatoCelular` para decidir os estados com base em chance.

### Comando comentado:
```java
// Classe que armazena e aplica as regras probabilísticas da simulação.
// Cada probabilidade representa um evento possível no modelo SIR.

public class TransicaoProbabilistica {

    // Probabilidade de vacinação espontânea (S → R)
    public double Pv;

    // Probabilidade de caso importado (S → I sem vizinhos)
    public double Ps;

    // Probabilidade de cura (I → R)
    public double Pc;

    // Probabilidade de morte (I → S)
    public double Pd;

    // Probabilidade de morte (R → S)
    public double Po;

    // Parâmetro que controla a taxa de infecção via vizinhos (intensidade)
    public double k;

    // Construtor que recebe os parâmetros definidos no início da simulação
    public TransicaoProbabilistica(double Pv, double Ps, double Pc, double Pd, double Po, double k) {
        this.Pv = Pv;
        this.Ps = Ps;
        this.Pc = Pc;
        this.Pd = Pd;
        this.Po = Po;
        this.k = k;
    }

    // Realiza um sorteio com base em uma probabilidade: retorna true se o evento ocorre.
    // Exemplo: sorteio(0.3) retorna true com 30% de chance.
    public boolean sorteio(double probabilidade) {
        return Math.random() < probabilidade;
    }

    // Calcula a probabilidade de infecção com base no número de vizinhos infectados.
    // Fórmula baseada em processo estocástico: P = 1 - e^(-k * n)
    public double calcularProbInfeccao(int vizinhosInfectados) {
        return 1 - Math.exp(-k * vizinhosInfectados);
    }
}

```

## 🔶 `AutomatoCelular.java`

> **Tipo:** Classe concreta, implementa `IAutomatoCelular`  
> **Responsabilidade:** Controlar a grade de células e aplicar as regras de transição a cada iteração.

### ✔️ O que ela faz:
- Inicializa a população com 1% infectada.
- A cada passo:
  1. Pede para cada célula decidir seu próximo estado.
  2. Atualiza todas as células ao mesmo tempo.
- Trata as bordas com contorno toroidal.

### 🧩 Utilização:
- Chamado diretamente por `Simulacao`.

### Comando comentado:
```java
public class AutomatoCelular implements IAutomatoCelular {

    private final int largura;
    private final int altura;
    private final Celula[][] grade;
    private final TransicaoProbabilistica transicoes;

    public AutomatoCelular(int largura, int altura, TransicaoProbabilistica transicoes) {
        this.largura = largura;
        this.altura = altura;
        this.transicoes = transicoes;
        this.grade = new Celula[altura][largura];
        inicializar();
    }

    // Preenche a grade com 99% suscetíveis e 1% infectados
    private void inicializar() {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                grade[i][j] = new Celula(Math.random() < 0.01 ? Celula.Estado.INFECTADO : Celula.Estado.SUSCETIVEL);
            }
        }
    }

    @Override
    public void executarPasso() {
        // Etapa 1: cada célula decide seu próximo estado com base nos vizinhos
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                Celula[][] vizinhanca = obterVizinhanca(i, j);
                grade[i][j].decidirProximoEstado(vizinhanca, transicoes);
            }
        }

        // Etapa 2: todas as células atualizam seus estados simultaneamente
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                grade[i][j].atualizarEstado();
            }
        }
    }

    // Retorna os vizinhos 3x3 da célula na posição (x, y), com contorno toroidal
    private Celula[][] obterVizinhanca(int x, int y) {
        Celula[][] vizinhos = new Celula[3][3];
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = (x + dx + altura) % altura;
                int ny = (y + dy + largura) % largura;
                vizinhos[dx + 1][dy + 1] = grade[nx][ny];
            }
        }
        return vizinhos;
    }

    @Override
    public Celula[][] getGrade() {
        return grade;
    }

    @Override
    public int contarPorEstado(Celula.Estado estado) {
        int count = 0;
        for (Celula[] linha : grade) {
            for (Celula c : linha) {
                if (c.getEstado() == estado) count++;
            }
        }
        return count;
    }
}

```

## 🔶 `Simulacao.java`

> **Tipo:** Classe executável  
> **Responsabilidade:** Orquestrar a execução da simulação, registrar os dados e exibir os gráficos.

### ✔️ O que ela faz:
- Instancia o `AutomatoCelular`.
- Executa a simulação por `N` passos.
- Coleta dados sobre `S`, `I` e `R`.
- Usa a biblioteca `XChart` para exibir os gráficos.

### Comando comentado:
```java
import org.knowm.xchart.*;
import java.util.*;

public class Simulacao {

    private final IAutomatoCelular automato;
    private final XYChart chart;
    private final List<Integer> suscetiveis = new ArrayList<>();
    private final List<Integer> infectados = new ArrayList<>();
    private final List<Integer> recuperados = new ArrayList<>();

    public Simulacao(IAutomatoCelular automato) {
        this.automato = automato;

        // Cria o gráfico com título e eixos
        this.chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Evolução SIR")
                .xAxisTitle("Iterações")
                .yAxisTitle("População")
                .build();
    }

    public void rodar(int passos) {
        for (int t = 0; t < passos; t++) {
            automato.executarPasso(); // faz a simulação andar 1 iteração

            // Registra quantas células existem em cada estado
            suscetiveis.add(automato.contarPorEstado(Celula.Estado.SUSCETIVEL));
            infectados.add(automato.contarPorEstado(Celula.Estado.INFECTADO));
            recuperados.add(automato.contarPorEstado(Celula.Estado.RECUPERADO));
        }

        // Adiciona os dados ao gráfico
        chart.addSeries("Suscetíveis", geraX(passos), suscetiveis);
        chart.addSeries("Infectados", geraX(passos), infectados);
        chart.addSeries("Recuperados", geraX(passos), recuperados);

        // Exibe o gráfico em uma janela Swing
        new SwingWrapper<>(chart).displayChart();
    }

    // Gera o eixo X para o gráfico (lista de 0 a passos-1)
    private List<Integer> geraX(int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < n; i++) x.add(i);
        return x;
    }

    public static void main(String[] args) {
        // Define os parâmetros da simulação (vacinação, cura, etc.)
        TransicaoProbabilistica params = new TransicaoProbabilistica(0.03, 0.01, 0.6, 0.3, 0.1, 1.0);

        // Cria o autômato celular com a grade 200x200
        IAutomatoCelular automato = new AutomatoCelular(200, 200, params);

        // Executa a simulação com 100 passos
        new Simulacao(automato).rodar(100);
    }
}

```

## 🧩 Fluxo Geral das Classes

```
Simulacao
  └── usa → IAutomatoCelular (interface)
               └── implementado por → AutomatoCelular
                      ├── contém → Celula[][] (grade de indivíduos)
                      │     └── cada Celula implementa → ComportamentoCelular (interface)
                      │            └── usa → TransicaoProbabilistica (regras e probabilidades)
                      └── executa a lógica de vizinhança e iteração
```



## 📌 Conclusão

As classes foram organizadas com as seguintes boas práticas:

- **Responsabilidade única**: cada classe tem uma função clara.
- **Encapsulamento**: variáveis privadas, métodos públicos claros.
- **Polimorfismo via interface** (`Celula` implementa `ComportamentoCelular`).
- **Desacoplamento** entre simulação e lógica interna.

Esse projeto demonstra um uso prático e bem estruturado da Programação Orientada a Objetos.
