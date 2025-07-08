# 📘 Documentação das Interfaces

Este projeto utiliza duas interfaces principais como forma de aplicar **abstração**, **polimorfismo** e **baixo acoplamento** na arquitetura orientada a objetos.


## 🔷 `ComportamentoCelular.java`

> **Tipo:** Interface  
> **Responsabilidade:** Define o contrato de comportamento para qualquer célula da simulação.

### ✔️ O que ela faz:
- Declara o método `decidirProximoEstado(...)`, que permite que uma célula analise sua vizinhança e tome uma decisão sobre qual será seu próximo estado.

### 🧩 Utilização:
- Implementada pela classe `Celula`, mas projetada para permitir que outras classes também possam representar "células" com comportamentos distintos no futuro.

### 🎯 Objetivo:
- Permitir a troca ou ampliação de tipos de células sem alterar o código principal.
- Aplicar **polimorfismo**: tratar diferentes tipos de célula da mesma forma.
- Isolar a lógica do comportamento celular da estrutura da simulação.

### Código Comentado:

```java
// Interface que define o comportamento esperado de uma célula.
// Isso permite que diferentes tipos de célula (com regras diferentes) possam ser usados no futuro.
public interface ComportamentoCelular {

    // Método que toda célula deve implementar.
    // A ideia é: com base nos vizinhos e nas regras, decidir qual será o próximo estado da célula.
    void decidirProximoEstado(Celula[][] vizinhanca, TransicaoProbabilistica transicoes);
}
```

## 🔷 IAutomatoCelular.java
> Tipo: Interface
> Responsabilidade: Abstrair o comportamento genérico de um autômato celular.

### ✔️ O que ela faz:
- Define os métodos essenciais que qualquer autômato celular deve implementar:

- executarPasso() – executa uma iteração da simulação.

- getGrade() – retorna a grade de células.

- contarPorEstado(int estado) – conta quantas células estão em um estado específico (0, 1 ou 2).

### 🧩 Utilização:
- A classe Simulacao usa apenas essa interface para manter independência da implementação concreta (AutomatoCelular).

- Isso permite que outras versões do autômato possam ser implementadas e testadas facilmente.


```java
// Interface que define o que um autômato celular precisa fazer.
// Isso permite que outras versões de autômatos possam ser criadas e usadas no lugar da atual.
public interface IAutomatoCelular {

    // Método que executa um passo da simulação (uma iteração).
    void executarPasso();

    // Retorna a grade de células que está sendo simulada.
    Celula[][] getGrade();

    // Conta quantas células estão em um determinado estado (S, I ou R), usando inteiros (0, 1, 2).
    int contarPorEstado(int estado);
}

```

### 🎯 Objetivo:
- Seguir o princípio de baixo acoplamento e programar para interfaces.

- Facilitar testes, extensão do projeto e possíveis melhorias futuras.

- Aplicar abstração, separando o que o autômato faz de como ele faz.