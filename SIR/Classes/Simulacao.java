import org.knowm.xchart.*; // Biblioteca usada para gerar gráficos
import java.util.*;        // Importa utilitários como List, ArrayList

// Classe principal que roda a simulação e exibe o gráfico final com os dados
public class Simulacao {

    private final IAutomatoCelular automato; // Interface do autômato celular (baixo acoplamento)
    private final XYChart chart;             // Gráfico que mostra a evolução dos estados
    private final List<Integer> suscetiveis = new ArrayList<>(); // Quantidade de S em cada passo
    private final List<Integer> infectados = new ArrayList<>();  // Quantidade de I em cada passo
    private final List<Integer> recuperados = new ArrayList<>(); // Quantidade de R em cada passo

    // Construtor que recebe o autômato e configura o gráfico
    public Simulacao(IAutomatoCelular automato) {
        this.automato = automato;

        // Cria o gráfico com título e eixos
        this.chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Evolução SIR")
                .xAxisTitle("Iterações")   // eixo X = tempo
                .yAxisTitle("População")   // eixo Y = número de pessoas
                .build();
    }

    // Método que executa a simulação por um número de passos
    public void rodar(int passos) {
        for (int t = 0; t < passos; t++) {
            automato.executarPasso(); // Executa um ciclo da simulação

            // Guarda quantos indivíduos existem em cada estado após o passo
            suscetiveis.add(automato.contarPorEstado(Estado.SUSCETIVEL));
            infectados.add(automato.contarPorEstado(Estado.INFECTADO));
            recuperados.add(automato.contarPorEstado(Estado.RECUPERADO));
        }

        // Adiciona os dados coletados no gráfico
        chart.addSeries("Suscetíveis", geraX(passos), suscetiveis);
        chart.addSeries("Infectados", geraX(passos), infectados);
        chart.addSeries("Recuperados", geraX(passos), recuperados);

        // Exibe o gráfico em uma janela Swing
        new SwingWrapper<>(chart).displayChart();
    }

    // Gera a lista [0, 1, 2, ..., n-1] para representar o eixo X do gráfico
    private List<Integer> geraX(int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < n; i++) x.add(i);
        return x;
    }

    // Método main: ponto de entrada do programa
    public static void main(String[] args) {
        // Cria os parâmetros probabilísticos (Pv, Ps, Pc, Pd, Po, k)
        TransicaoProbabilistica params = new TransicaoProbabilistica(
            0.03,  // Pv - vacinação
            0.01,  // Ps - caso importado
            0.6,   // Pc - cura
            0.3,   // Pd - morte (infectado)
            0.1,   // Po - morte (recuperado)
            1.0    // k  - infectividade
        );

        // Cria o autômato com a grade 200x200
        IAutomatoCelular automato = new AutomatoCelular(200, 200, params);

        // Cria a simulação e executa por 100 passos
        new Simulacao(automato).rodar(100);
    }
}
