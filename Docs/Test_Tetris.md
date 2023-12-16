# POO tbg05

## Descrição

Este projeto é uma implementação do clássico jogo Tetris, desenvolvido para a disciplina de Programação Orientada a Objetos. O jogo vai utilizar a linguagem de programação Java, com foco em demonstrar habilidades de programação orientada a objetos, manipulação de GUI e lógica de jogos.

**Example**:

In this exciting platform game you can ...... […].

This project was developed by *Jose Mendes* (*a040866*@umaia.pt)  for POO 2023⁄24.

### IMPLEMENTED FEATURES

> This section should contain a list of implemented features and their descriptions. In the end of the section, include two or three screenshots that illustrate the most important features.

**Examples**:

- **Swap position** - ...
- **Turn left or rigth** - ...

### PLANNED FEATURES

> ...

### DESIGN

> ... :

- **Problem in Context.** ... [link to the relevant lines of code](https://help.github.com/en/articles/creating-a-permanent-link-to-a-code-snippet) in the appropriate version.
- **The Pattern.** ...
- **Implementation.** Show how the pattern roles, operations and associations were mapped to the concrete design classes. Illustrate it with a UML class diagram, and refer to the corresponding source code with links to the relevant lines (these should be [relative links](https://help.github.com/en/articles/about-readmes#relative-links-and-image-paths-in-readme-files). When doing this, always point to the latest version of the code.
- **Consequences.** Benefits and liabilities of the design after the pattern instantiation, eventually comparing these consequences with those of alternative solutions.

**Example of one of such subsections**:

------

####

**Problem in Context**

...

**The Pattern**

...

**Implementation**

The following figure shows how the pattern’s roles were mapped to the application classes.

![img](https://www.f...svg)

These classes can be found in the following files:

- ...

**Consequences**

The use of the State Pattern in the current design allows the following benefits:

- ...
- ...
- ....

#### KNOWN CODE SMELLS AND REFACTORING SUGGESTIONS

> ...

**Example of such a subsection**:

------

#### DATA CLASS

...

### TESTING

- Screenshot of coverage report...
- Link to mutation testing report...

### SELF-EVALUATION

> ...

### Unit Tests

To ensure the correctness of your implementation, it is recommended to write unit tests for your code. Here are some examples of tests you can write for the implemented features:

- Test the `swapPosition` method to ensure that it correctly swaps the positions of two elements.
- Test the `turnLeft` and `turnRight` methods to verify that they correctly rotate the game piece.
- Test the GUI elements to ensure that they are displayed correctly and respond to user input.

You can use a testing framework like JUnit to write and run these tests. Make sure to cover different scenarios and edge cases to ensure the robustness of your code.

### Integration Tests

In addition to unit tests, it is also important to perform integration tests to verify the interaction between different components of your application. For example:

- Test the integration between the game logic and the GUI to ensure that the game state is correctly updated and displayed.
- Test the integration between user input and the game logic to ensure that the game responds correctly to user actions.

These tests can be written using a combination of unit testing frameworks and GUI testing frameworks like Selenium or Appium.

### Performance Testing

To ensure that your game runs smoothly and efficiently, it is recommended to perform performance testing. This involves measuring the performance of your code under different loads and stress conditions. Some performance tests you can consider:

- Test the game's performance with a large number of game pieces on the screen to ensure that it remains responsive.
- Test the game's performance on different hardware configurations to identify any performance bottlenecks.

You can use tools like JMeter or Apache Bench to perform performance testing.

Remember to regularly run your tests and update them as your code evolves. This will help you catch any regressions or issues introduced during development.