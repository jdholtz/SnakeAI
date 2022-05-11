# SnakeAI
A recreation of snake that uses machine learning by utilizing a neural network and a genetic algorithm implemented from scratch.

Head over to the [wiki][0] for information on how it works.

# Dependencies
- [Java 8+][1]

# Installation
First, clone the repository
```bash
$ git clone https://github.com/jdholtz/SnakeAI.git
$ cd SnakeAI
```
Then, compile the code to make it runnable
```bash
$ javac src/Main.java
```

# Using the program
Run the program by executing the following command
```bash
$ java src.Main
```

## Controls

### Moving
- Up arrow or w - Moves the snake up
- Right arrow or d - Moves the snake right
- Down arrow or s - Moves the snake down
- Left arrow or a - Moves the snake right

### Controlling the game
- p - pauses the game/simulation
- k - kills the current snake on the screen

You can control the speed of the game with the button at the top of the screen.

## Using the AI
On the first screen, select the "Start Evolution" button. From there, you have the option to either \
load data into the evolution from previous runs or start a new evolution. Then, you can adjust the simulation \
variables to test different conditions. 

Depending on the variables, it takes anywhere from about 50 to 500 generations for a snake to get over 30 \
apples (the average score is about 3-8 at that time). The best snake from each simulation is automatically \
saved into a file, so it could be loaded at a later time.

[0]: https://github.com/jdholtz/SnakeAI/wiki
[1]: https://www.oracle.com/java/technologies/downloads