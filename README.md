
## `algorithm` package

This package contains implementations of the different algorithms that this project can visualise
All algorithms implement the Algorithm interface. Classes in this package:
- Dijkstra

## `model` package
This package contains the custom data structures used in the project. Classes in this package:
- Edge
- Graph
- Node
- StepResult (used to store the result of each step of the algorithm)
- NodeEntry (used in the priority queue for Dijkstra)
- AlgorithmTableRow (used to store the data for each row in the table visualisation)

## `ui` package
This package contains the user interface components of the project. Classes in this package:
- GraphVisualiser (visualises the graph)

## `util` package
This package contains utility classes and methods used throughout the project. Classes in this package:
- AlgorithmFactory (creates instances of algorithms based on user input)
- GraphBuilder (builds graphs from user input or predefined structures)

## Future improvement for this project:
- Adding more algorithms (e.g. DFS, BFS, A*)
- Making ui package more modular, (e.g. seperate visualisation for graph and table)
