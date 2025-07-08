// Classe que representa uma célula do autômato (ou seja, um indivíduo)
// Cada célula tem um estado (S, I ou R), um próximo estado e um tempo que ela ficou infectada
public class Celula implements ComportamentoCelular {

    private int estadoAtual;        // Estado atual da célula
    private int proximoEstado;      // Estado que ela vai assumir na próxima iteração
    private int tempoInfectado;     // Quantas iterações a célula ficou infectada

    // Construtor: define o estado inicial e zera o tempo infectado
    public Celula(int estadoInicial) {
        this.estadoAtual = estadoInicial;
        this.proximoEstado = estadoInicial;
        this.tempoInfectado = 0;
    }

    // Retorna o estado atual da célula
    public int getEstado() {
        return estadoAtual;
    }

    // Define o próximo estado que a célula deve assumir
    public void definirProximoEstado(int estado) {
        this.proximoEstado = estado;
    }

    // Atualiza o estado da célula: ela "vira" o estado definido anteriormente
    public void atualizarEstado() {
        this.estadoAtual = this.proximoEstado;
    }

    // Retorna o tempo que a célula ficou infectada
    public int getTempoInfectado() {
        return tempoInfectado;
    }

    // Aumenta em 1 o tempo de infecção
    public void incrementarTempoInfectado() {
        this.tempoInfectado++;
    }

    // Reseta o tempo de infecção para zero
    public void resetarTempoInfectado() {
        this.tempoInfectado = 0;
    }

    // Lógica principal: define o próximo estado da célula com base nas regras e nos vizinhos
    @Override
    public void decidirProximoEstado(Celula[][] vizinhanca, TransicaoProbabilistica transicoes) {
        if (estadoAtual == Estado.SUSCETIVEL) {
            // SUSCETÍVEL pode se vacinar
            if (transicoes.sorteio(transicoes.Pv)) {
                definirProximoEstado(Estado.RECUPERADO);
            }
            // Ou se infectar diretamente (caso importado)
            else if (transicoes.sorteio(transicoes.Ps)) {
                definirProximoEstado(Estado.INFECTADO);
            }
            // Ou se infectar por contato com vizinhos infectados
            else {
                int infectados = contarInfectados(vizinhanca);
                double Pi = transicoes.calcularProbInfeccao(infectados);
                definirProximoEstado(transicoes.sorteio(Pi) ? Estado.INFECTADO : Estado.SUSCETIVEL);
            }

        } else if (estadoAtual == Estado.INFECTADO) {
            // INFECTADO pode se curar
            if (transicoes.sorteio(transicoes.Pc)) {
                definirProximoEstado(Estado.RECUPERADO);
                resetarTempoInfectado();
            }
            // Ou morrer (volta a ser suscetível)
            else if (transicoes.sorteio(transicoes.Pd)) {
                definirProximoEstado(Estado.SUSCETIVEL);
                resetarTempoInfectado();
            }
            // Ou continuar infectado
            else {
                definirProximoEstado(Estado.INFECTADO);
                incrementarTempoInfectado();
            }

        } else if (estadoAtual == Estado.RECUPERADO) {
            // RECUPERADO pode morrer e voltar a ser suscetível
            boolean morreu = transicoes.sorteio(transicoes.Po);
            definirProximoEstado(morreu ? Estado.SUSCETIVEL : Estado.RECUPERADO);
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
