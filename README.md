# Bowling Game Application

## Overview
This is a console-based **Bowling Game** built using **Spring Boot**. The game simulates a 10-frame bowling match, keeping track of scores, strikes, spares, and extra rolls. It follows **Clean Architecture** principles and leverages **Spring Events** for an event-driven architecture.

## How to Run

### Prerequisites
Ensure you have:
- **Java 17** or higher
- **Gradle** for building the app

### Steps to Start

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd bowling-game
   
2. **Build The Application**
```
./gradlew clean build
```
3. **Run the Application**
```
java -jar build/libs/bowling-0.0.1-SNAPSHOT.jar
```

### Example Output
```text
Welcome to the Bowling Game!
Press ENTER to roll the ball. Type 'exit' to finish the game.
Rolled: 7 pins.
Current Score: 7
```
