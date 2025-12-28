Hit-Based 2D Platformer (Java)

A precision 2D platformer built from scratch using Java and Object-Oriented Programming (OOP) principles. This project features a custom game engine(jvm), hit-box-based combat, and a dynamic level loading system.

**Core Technical Architecture**
### 1. Object-Oriented Design

The game is built on a modular architecture using core OOP concepts:

    Inheritance: A base Entity.java class is extended by Player.java and Enemy.java to share common attributes while allowing unique behaviors.

Encapsulation & Abstraction: Game logic is separated into distinct packages for entities, gamestates, levels, and UI.

Polymorphism: Used to manage different game states and entity updates efficiently.

### 2. Multi-Threaded Engine

To ensure a smooth user experience, the game utilizes Concurrency:

    Main Thread: Initializes the application.

Event Dispatch Thread (EDT): Handles GUI rendering and user inputs (Mouse/Keyboard).

Game Loop Thread: A dedicated thread for game logic, physics updates (UPS), and frame rendering (FPS).

### 3. Physics & Combat System

    Hitboxes: Uses geometric shapes to detect precise collisions between the player and the environment.

Collision Prediction: Implements specialized HelpMethods to check if a tile is solid before moving the player, preventing wall-clipping.

Attack Box Mechanism: An invisible area attached to attack animations. Damage is triggered when the Attack Box overlaps with an enemy's Hitbox during an active input.

### 4. Graphics & Animation

    Sprite Atlas Slicing: Automatically cuts 64x40 pixel frames from a single "atlas" image and stores them in 2D arrays for real-time animation playback.

Data-Driven Level Design: Levels are loaded from image blueprints where specific pixel colors correspond to unique tiles (e.g., grass or stone).

## Tech Stack

    Language: Java 

GUI Framework: Swing & AWT

Graphics: Java2D (Graphics class, Geom package)

Development Methodology: Agile & Iterative Development

## File Structure
Plaintext

src/
├── entities/    # Player, Enemy (Crabby), and Entity logic [cite: 71]
├── gamestates/  # Menu, Playing, Won, and Options management [cite: 71, 93]
├── inputs/      # Keyboard and Mouse listeners [cite: 71]
├── levels/      # Level data and Tile management [cite: 71]
├── ui/          # Overlays and Menu buttons [cite: 71]
└── utilz/       # Sprite loading and collision help methods [cite: 71]

## Controls

A / D: Move Left / Right 
Space: Jump
  
Mouse: Navigate Menus and Attack
