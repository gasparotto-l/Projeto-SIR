

public class TransicaoProbabilistica {


    public double Pv;


    public double Ps;


    public double Pc;


    public double Pd;


    public double Po;


    public double k;


    public TransicaoProbabilistica(double Pv, double Ps, double Pc, double Pd, double Po, double k) {
        this.Pv = Pv;
        this.Ps = Ps;
        this.Pc = Pc;
        this.Pd = Pd;
        this.Po = Po;
        this.k = k;
    }

    
    public boolean sorteio(double probabilidade) {
        return Math.random() < probabilidade;
    }

    
    public double calcularProbInfeccao(int vizinhosInfectados) {
        return 1 - Math.exp(-k * vizinhosInfectados);
    }
}
