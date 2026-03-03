# Design Patterns — Gang of Four

Resumo dos 23 padrões de projeto do livro *Design Patterns: Elements of Reusable Object-Oriented Software* (Gamma, Helm, Johnson, Vlissides).

---

## Padrões de Criação (Creational)

Lidam com mecanismos de criação de objetos, buscando criar objetos de maneira adequada à situação.

| Padrão | Resumo |
|--------|--------|
| **Abstract Factory** | Fornece uma interface para criar famílias de objetos relacionados ou dependentes sem especificar suas classes concretas. |
| **Builder** | Separa a construção de um objeto complexo de sua representação, permitindo que o mesmo processo crie representações diferentes. |
| **Factory Method** | Define uma interface para criar um objeto, mas deixa as subclasses decidirem qual classe instanciar. |
| **Prototype** | Especifica os tipos de objetos a criar usando uma instância prototípica e cria novos objetos copiando esse protótipo. |
| **Singleton** | Garante que uma classe tenha apenas uma instância e fornece um ponto global de acesso a ela. |

---

## Padrões Estruturais (Structural)

Lidam com a composição de classes e objetos para formar estruturas maiores.

| Padrão | Resumo |
|--------|--------|
| **Adapter** | Converte a interface de uma classe em outra interface que os clientes esperam, permitindo que classes incompatíveis trabalhem juntas. |
| **Bridge** | Desacopla uma abstração de sua implementação, permitindo que as duas variem independentemente. |
| **Composite** | Compõe objetos em estruturas de árvore para representar hierarquias parte-todo, permitindo que clientes tratem objetos individuais e compostos de forma uniforme. |
| **Decorator** | Anexa responsabilidades adicionais a um objeto dinamicamente, fornecendo uma alternativa flexível à herança para extensão de funcionalidade. |
| **Facade** | Fornece uma interface unificada e simplificada para um conjunto de interfaces em um subsistema. |
| **Flyweight** | Usa compartilhamento para suportar eficientemente um grande número de objetos de granularidade fina. |
| **Proxy** | Fornece um substituto ou representante de outro objeto para controlar o acesso a ele. |

---

## Padrões Comportamentais (Behavioral)

Lidam com algoritmos e a atribuição de responsabilidades entre objetos.

| Padrão | Resumo |
|--------|--------|
| **Chain of Responsibility** | Evita acoplar o remetente de uma solicitação ao seu receptor, encadeando os objetos receptores e passando a solicitação ao longo da cadeia até que seja tratada. |
| **Command** | Encapsula uma solicitação como um objeto, permitindo parametrizar clientes, enfileirar ou registrar solicitações e suportar operações desfazíveis. |
| **Interpreter** | Dada uma linguagem, define uma representação para sua gramática e um interpretador que usa a representação para interpretar sentenças nessa linguagem. |
| **Iterator** | Fornece uma maneira de acessar sequencialmente os elementos de um objeto agregado sem expor sua representação subjacente. |
| **Mediator** | Define um objeto que encapsula como um conjunto de objetos interage, promovendo o acoplamento fraco ao evitar que os objetos se refiram uns aos outros explicitamente. |
| **Memento** | Sem violar o encapsulamento, captura e externaliza o estado interno de um objeto para que ele possa ser restaurado posteriormente. |
| **Observer** | Define uma dependência um-para-muitos entre objetos de modo que, quando um objeto muda de estado, todos os seus dependentes são notificados e atualizados automaticamente. |
| **State** | Permite que um objeto altere seu comportamento quando seu estado interno muda, parecendo mudar de classe. |
| **Strategy** | Define uma família de algoritmos, encapsula cada um deles e os torna intercambiáveis, permitindo que o algoritmo varie independentemente dos clientes que o utilizam. |
| **Template Method** | Define o esqueleto de um algoritmo em uma operação, adiando alguns passos para subclasses, sem alterar a estrutura do algoritmo. |
| **Visitor** | Representa uma operação a ser executada sobre os elementos de uma estrutura de objeto, permitindo definir nova operação sem alterar as classes dos elementos sobre os quais opera. |

---

## Visão Geral

```
23 padrões no total
├── Criação    →  5 padrões
├── Estrutural →  7 padrões
└── Comportamental → 11 padrões
```

## Referência

> Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994).
> *Design Patterns: Elements of Reusable Object-Oriented Software.*
> Addison-Wesley.
