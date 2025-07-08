// Esta classe guarda todas as probabilidades usadas no modelo SIR
// Também possui métodos para realizar sorteios e calcular chance de infecção

public class TransicaoProbabilistica {

    // Pv: probabilidade de vacinação espontânea (S → R)
    public double Pv;

    // Ps: probabilidade de caso importado (S → I)
    public double Ps;

    // Pc: probabilidade de cura (I → R)
    public double Pc;

    // Pd: probabilidade de morte para infectados (I → S)
    public double Pd;

    // Po: probabilidade de morte para recuperados (R → S)
    public double Po;

    // k: fator de infectividade (controla o quanto os vizinhos infectam)
    public double k;

    // Construtor que recebe todos os parâmetros definidos no início da simulação
    public TransicaoProbabilistica(double Pv, double Ps, double Pc, double Pd, double Po, double k) {
        this.Pv = Pv;
        this.Ps = Ps;
        this.Pc = Pc;
        this.Pd = Pd;
        this.Po = Po;
        this.k = k;
    }

    // Retorna true com a probabilidade passada (ex: sorteio(0.3) tem 30% de chance de retornar true)
    public boolean sorteio(double probabilidade) {
        return Math.random() < probabilidade;
    }

    // Calcula a chance de infecção com base no número de vizinhos infectados
    // Fórmula: Pi = 1 - e^(-k * vizinhos)
    public double calcularProbInfeccao(int vizinhosInfectados) {
        return 1 - Math.exp(-k * vizinhosInfectados);
    }
}
