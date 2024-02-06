# Bricks Breaking Game
Welcome to the Bricks Breaking game! This project is a Java-based implementation of the classic Bricks Breaking game. The game challenges players to strategically clear a grid of bricks by breaking groups of adjacent bricks of the same color.

![breakingBricks](https://github.com/JanPeregrim/Breaking_Bricks/assets/130740859/dbe187f3-1c7b-4620-a20d-f728b58a00ee)

# Table of Contents
- Project Overview
- Project Structure
- How to Run
- Game Features
- Persistence and Data Management
- Server-Side Components
- RESTful Web Services
- Testing
- Contributing
## Project Overview
The Bricks Breaking game is a Java-based project aimed at providing an enjoyable gaming experience to users. It features a classic gameplay mechanic where players must strategically eliminate groups of adjacent =bricks to clear the game grid. The project includes both server-side and client-side components, offering a robust architecture for scalability and maintainability.

## Project Structure
The project structure is organized as follows:

- src/main/java: Contains the main Java source code for the project.
- src/test/java: Contains unit tests for testing the functionality of the game's services.
- src/main/resources: Contains static resources such as HTML, CSS, and game templates, as well as the application configuration properties.
## How to Run
To run the Bricks Breaking game, follow these steps:

- Ensure you have Java installed on your system.
- Navigate to the project root directory.
- Compile the source files using your preferred Java compiler.
- Run the Main class located in src/main/java/gamestudio/ to start the game.
- Access the game by navigating to http://localhost:8080/ and clicking on the "Breaking Bricks" button.
## Game Features
The Bricks Breaking game offers the following features:

- Interactive Console UI: The game features an interactive console user interface (ConsoleUI.java) for gameplay interaction.
- Game Logic: Core game logic is implemented in the core package, including classes for managing bricks, the game field, and game state.
- Persistence: Game data persistence is managed through entities (entity package), including comments, ratings, scores, and user information.
- Scalability: The project is designed with scalability in mind, allowing for future enhancements and additional features to be easily integrated.
## Persistence and Data Management
The project utilizes various techniques for persistence and data management:

- Entity Classes: The entity package contains entity classes representing game data, including Comment, Rating, Score, and User.
- Service Layer: The service package contains service classes responsible for managing game data, including CRUD operations, data validation, and business logic.
## Server-Side Components
The server-side components handle game requests, responses, and business logic:

- Controllers: The server/controller package contains controller classes for handling HTTP requests and routing them to the appropriate service methods.
- Web Services: The server/webservice package contains RESTful web service classes for managing comments, ratings, and scores via HTTP endpoints.
## RESTful Web Services
The Bricks Breaking game exposes RESTful web services for managing game data:

- CommentServiceRest: Provides endpoints for managing comments.
- RatingServiceRest: Provides endpoints for managing ratings.
- ScoreServiceRest: Provides endpoints for managing scores.
These services enable seamless integration with client applications and external systems.

## Testing
The project includes comprehensive unit tests for testing the functionality of the game's service layer:

- Service Tests: Located in the test/java/gamestudio/service/ directory, these tests ensure the reliability and correctness of the game's service components.
## Contributing
Contributions to the Bricks Breaking game project are welcome! If you have any suggestions, bug reports, or feature requests, please feel free to open an issue or submit a pull request on the project's GitHub repository.
