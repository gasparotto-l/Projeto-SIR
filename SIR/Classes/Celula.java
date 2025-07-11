
public class Celula implements ComportamentoCelular {

    private int estadoAtual;        
    private int proximoEstado;      
    private int tempoInfectado;     

    
    public Celula(int estadoInicial) {
        this.estadoAtual = estadoInicial;
        this.proximoEstado = estadoInicial;
        this.tempoInfectado = 0;
    }

    
    public int getEstado() {
        return estadoAtual;
    }

    
    public void definirProximoEstado(int estado) {
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

    
    @Override
    public void decidirProximoEstado(Celula[][] vizinhanca, TransicaoProbabilistica transicoes) {
        if (estadoAtual == Estado.SUSCETIVEL) {
            
            if (transicoes.sorteio(transicoes.Pv)) {
                definirProximoEstado(Estado.RECUPERADO);
            }
            
            else if (transicoes.sorteio(transicoes.Ps)) {
                definirProximoEstado(Estado.INFECTADO);
            }
            
            else {
                int infectados = contarInfectados(vizinhanca);
                double Pi = transicoes.calcularProbInfeccao(infectados);
                definirProximoEstado(transicoes.sorteio(Pi) ? Estado.INFECTADO : Estado.SUSCETIVEL);
            }

        } else if (estadoAtual == Estado.INFECTADO) {
            
            if (transicoes.sorteio(transicoes.Pc)) {
                definirProximoEstado(Estado.RECUPERADO);
                resetarTempoInfectado();
            }
            
            else if (transicoes.sorteio(transicoes.Pd)) {
                definirProximoEstado(Estado.SUSCETIVEL);
                resetarTempoInfectado();
            }
            
            else {
                definirProximoEstado(Estado.INFECTADO);
                incrementarTempoInfectado();
            }

        } else if (estadoAtual == Estado.RECUPERADO) {
            
            boolean morreu = transicoes.sorteio(transicoes.Po);
            definirProximoEstado(morreu ? Estado.SUSCETIVEL : Estado.RECUPERADO);
        }
    }

    
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
