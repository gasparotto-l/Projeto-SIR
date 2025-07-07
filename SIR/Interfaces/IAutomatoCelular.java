public interface IAutomatoCelular {
    void executarPasso();
    Celula[][] getGrade();
    int contarPorEstado(Celula.Estado estado);
}
