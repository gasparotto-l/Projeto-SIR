// Interface que define o comportamento esperado de um autômato celular
public interface IAutomatoCelular {
    void executarPasso();
    Celula[][] getGrade();
    int contarPorEstado(int estado);
}
