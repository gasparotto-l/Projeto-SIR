
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

    private void inicializar() {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                grade[i][j] = new Celula(Math.random() < 0.01 ? Celula.Estado.INFECTADO : Celula.Estado.SUSCETIVEL);
            }
        }
    }

    @Override
    public void executarPasso() {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                Celula[][] vizinhanca = obterVizinhanca(i, j);
                grade[i][j].decidirProximoEstado(vizinhanca, transicoes);
            }
        }

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                grade[i][j].atualizarEstado();
            }
        }
    }

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
