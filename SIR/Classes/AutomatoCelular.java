// Esta classe representa o autômato celular que simula a propagação da doença
// Ele é responsável por manter a grade de células e aplicar as regras de transição

public class AutomatoCelular implements IAutomatoCelular {

    private final int largura;                         // Quantidade de colunas da grade
    private final int altura;                          // Quantidade de linhas da grade
    private final Celula[][] grade;                    // Grade bidimensional de células
    private final TransicaoProbabilistica transicoes;  // Regras probabilísticas do modelo

    // Construtor do autômato. Define o tamanho da grade e as regras.
    public AutomatoCelular(int largura, int altura, TransicaoProbabilistica transicoes) {
        this.largura = largura;
        this.altura = altura;
        this.transicoes = transicoes;
        this.grade = new Celula[altura][largura]; // grade[linha][coluna]
        inicializar(); // Preenche a grade com células
    }

    // Inicializa a grade com células SUSCETÍVEIS e alguns poucos INFECTADOS aleatórios
    private void inicializar() {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                // Cada célula tem 1% de chance de começar INFECTADA, senão é SUSCETÍVEL
                grade[i][j] = new Celula(Math.random() < 0.01 ? Estado.INFECTADO : Estado.SUSCETIVEL);
            }
        }
    }

    // Método que executa um passo da simulação (uma iteração)
    @Override
    public void executarPasso() {
        // 1ª etapa: cada célula decide seu próximo estado com base nos vizinhos
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                Celula[][] vizinhanca = obterVizinhanca(i, j);
                grade[i][j].decidirProximoEstado(vizinhanca, transicoes);
            }
        }

        // 2ª etapa: depois que todas decidiram, atualizamos seus estados de fato
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                grade[i][j].atualizarEstado();
            }
        }
    }

    // Retorna os vizinhos (3x3) de uma célula na posição (x, y)
    // A grade é toroidal: bordas "se conectam", não existe borda fixa
    private Celula[][] obterVizinhanca(int x, int y) {
        Celula[][] vizinhos = new Celula[3][3];
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Cálculo para contorno toroidal (volta pro outro lado da grade)
                int nx = (x + dx + altura) % altura;
                int ny = (y + dy + largura) % largura;
                vizinhos[dx + 1][dy + 1] = grade[nx][ny];
            }
        }
        return vizinhos;
    }

    // Retorna toda a grade de células
    @Override
    public Celula[][] getGrade() {
        return grade;
    }

    // Conta quantas células na grade estão em um estado específico (S, I ou R)
    @Override
    public int contarPorEstado(int estado) {
        int count = 0;
        for (Celula[] linha : grade) {
            for (Celula c : linha) {
                if (c.getEstado() == estado) {
                    count++;
                }
            }
        }
        return count;
    }
}
