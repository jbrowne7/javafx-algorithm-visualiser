# JavaFX Algorithm Visualiser

Used JavaFX to create a visual graph which shows the steps of dijkstras algorithm, turns dijkstra into UI state transition

## Video Demo

[![Algorithm Visualiser Demo](https://img.youtube.com/vi/5Gv4n_MWxY8/0.jpg)](https://jamesbrowne.dev/posts/javafx-algorithm-visualiser/#demo-video)

Click the image above to watch a demonstration of the algorithm visualiser.

## Table of Contents

- [Requirements](#requirements)
- [Setup](#setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Future Improvements](#future-improvements)

## Requirements
- Java 17-21
- Git

## Setup  

**1. Clone the repository**
```bash
git clone https://github.com/jbrowne7/javafx-algorithm-visualiser.git
cd javafx-algorithm-visualiser
```  

**2. Run using gradle wrapper**

On **Linux/macOS**:
```bash
./gradlew run --args dijkstra
```

On **Windows**:
```cmd
gradlew.bat run --args dijkstra
```

## Usage

Run the application:

```bash
./gradlew run --args dijkstra
```

## Project Structure

### `algorithm` package

This package contains implementations of the different algorithms that this project can visualise
All algorithms implement the Algorithm interface. Classes in this package:
- Dijkstra
- DFS (not yet implemented)

### `model` package
This package contains the custom data structures used in the project. Classes in this package:
- Edge
- Graph
- Node
- StepResult (used to store the result of each step of the algorithm)
- NodeEntry (used in the priority queue for Dijkstra)

### `ui` package
This package contains the user interface components of the project. Classes in this package:
- GraphVisualiser (places UI components for the graph visualisation)
- GraphPane (builds the graph UI (nodes, edges, weights))
- DistancesPane (builds the table with the distances to certain nodes)
- StepButtonPane (builds the step button)

### `util` package
This package contains utility classes and methods used throughout the project. Classes in this package:
- AlgorithmFactory (creates instances of algorithms based on user input)
- GraphBuilder (builds graphs from user input or predefined structures)
- AppConfig (record for configuration settings)
- AppContext (stored configuration settings as AppConfig)
- ArgsParser (parses cmdline arguments)

## Future improvement for this project:
- Adding more algorithms (e.g. DFS, BFS)
- Highlight the entry in the table that was just updated
