// Interface que define o que qualquer célula da simulação deve ser capaz de fazer
public interface ComportamentoCelular {
    void decidirProximoEstado(Celula[][] vizinhanca, TransicaoProbabilistica transicoes);
}
