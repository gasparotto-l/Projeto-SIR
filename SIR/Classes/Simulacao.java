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
        this.chart = new XYChartBuilder().width(800).height(600).title("Evolução SIR").xAxisTitle("Iterações").yAxisTitle("População").build();
    }

    public void rodar(int passos) {
        for (int t = 0; t < passos; t++) {
            automato.executarPasso();
            suscetiveis.add(automato.contarPorEstado(Celula.Estado.SUSCETIVEL));
            infectados.add(automato.contarPorEstado(Celula.Estado.INFECTADO));
            recuperados.add(automato.contarPorEstado(Celula.Estado.RECUPERADO));
        }

        chart.addSeries("Suscetíveis", geraX(passos), suscetiveis);
        chart.addSeries("Infectados", geraX(passos), infectados);
        chart.addSeries("Recuperados", geraX(passos), recuperados);

        new SwingWrapper<>(chart).displayChart();
    }

    private List<Integer> geraX(int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < n; i++) x.add(i);
        return x;
    }

    public static void main(String[] args) {
        TransicaoProbabilistica params = new TransicaoProbabilistica(0.03, 0.01, 0.6, 0.3, 0.1, 1.0);
        IAutomatoCelular automato = new AutomatoCelular(200, 200, params);
        new Simulacao(automato).rodar(100);
    }
}
