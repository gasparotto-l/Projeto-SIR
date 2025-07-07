# 📘 Documentação das Interfaces

### 🔷 `ComportamentoCelular.java`

> **Tipo:** Interface  
> **Responsabilidade:** Define o contrato de comportamento para qualquer célula da simulação.

### ✔️ O que ela faz:
- Declara o método `decidirProximoEstado(...)`, que define como uma célula avalia sua vizinhança e decide o próximo estado com base nas probabilidades.

### 🧩 Utilização:
- Implementada pela classe `Celula`, mas pode ser reutilizada por outras variantes no futuro.

### 🎯 Objetivo:
- Permitir que diferentes tipos de célula possam ter comportamentos distintos sem modificar o código do autômato.
- Facilita testes, extensão e uso de polimorfismo.

### Codigo Comentado:
```java
// Interface que define o comportamento esperado de uma célula.
// Isso permite que diferentes tipos de célula (com regras diferentes) possam ser usados no futuro.
public interface ComportamentoCelular {

    // Método que toda célula deve implementar.
    // A ideia é: com base nos vizinhos e nas regras, decidir qual será o próximo estado da célula.
    void decidirProximoEstado(Celula[][] vizinhanca, TransicaoProbabilistica transicoes);
}
```

## 🔷 `IAutomatoCelular.java`

> **Tipo:** Interface  
> **Responsabilidade:** Abstrair o comportamento genérico de um autômato celular.

### ✔️ O que ela faz:
- Define os métodos:
  - `executarPasso()` – para avançar a simulação.
  - `getGrade()` – retorna a grade de células.
  - `contarPorEstado(...)` – conta células por estado.

### 🧩 Utilização:
- `Simulacao` usa essa interface para que a implementação do autômato possa ser trocada facilmente.

### 🎯 Objetivo:
- Desacoplar a simulação da implementação concreta do autômato.
- Facilitar substituição, testes e extensão da lógica do autômato no futuro.

### Comando Comentado:

```java
// Interface que define o que um autômato celular precisa fazer.
// Isso permite que outras versões de autômatos possam ser criadas e usadas no lugar da atual.
public interface IAutomatoCelular {

    // Método que executa um passo da simulação (uma iteração).
    void executarPasso();

    // Retorna a grade de células que está sendo simulada.
    Celula[][] getGrade();

    // Conta quantas células estão em um determinado estado (S, I ou R).
    int contarPorEstado(Celula.Estado estado);
}
```

## 📌 Conclusão

O uso de interfaces neste projeto tem como foco:

- Aplicar **abstração**.
- Permitir **polimorfismo**.
- **Desacoplar** dependências diretas entre classes.
- **Preparar o código para crescimento futuro** sem quebras.
