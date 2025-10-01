# JavaFX Algorithm Visualiser

Used JavaFX to create a visual graph which shows the steps of dijkstras algorithm, turns dijkstra into UI state transition


# Requirements
- Java 23
- Git

# Run locally using gradle  

**1. Clone the repository**
```bash
git clone git@github.com:jbrowne7/javafx-algorithm-visualiser.git
cd javafx-algorithm-visualiser
```  

**2. Run using gradle wrapper**
```bash
./gradlew run --args dijkstra
```


## `algorithm` package

This package contains implementations of the different algorithms that this project can visualise
All algorithms implement the Algorithm interface. Classes in this package:
- Dijkstra
- DFS (not yet implemented)

## `model` package
This package contains the custom data structures used in the project. Classes in this package:
- Edge
- Graph
- Node
- StepResult (used to store the result of each step of the algorithm)
- NodeEntry (used in the priority queue for Dijkstra)

## `ui` package
This package contains the user interface components of the project. Classes in this package:
- GraphVisualiser (places UI components for the graph visualisation)
- GraphPane (builds the graph UI (nodes, edges, weights))
- DistancesPane (builds the table with the distances to certain nodes)
- StepButtonPane (builds the step button)

## `util` package
This package contains utility classes and methods used throughout the project. Classes in this package:
- AlgorithmFactory (creates instances of algorithms based on user input)
- GraphBuilder (builds graphs from user input or predefined structures)
- AppConfig (record for configuration settings)
- AppContext (stored configuration settings as AppConfig)
- ArgsParser (parses cmdline arguments)

## Future improvement for this project:
- Adding more algorithms (e.g. DFS, BFS, A*)
- Highlight the entry in the table that was just updated
