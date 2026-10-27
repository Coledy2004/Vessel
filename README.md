# Vessel

A dungeon-crawling, deck-building game written in Java with JavaFX.

The hero fights through the dungeon, **possesses** defeated enemies to steal their abilities as cards, and **mutates** as they go, slowly losing their humanity on the way to the exit.

**Developers:** Cole Younoszai, Sam Kutcipal

---

## Getting started

### Requirements
- JDK 21 or newer (developed on Temurin 25)
- IntelliJ IDEA (any recent version). You don't need to install Gradle or JavaFX: the Gradle wrapper downloads both.

### Open in IntelliJ
1. **File → Open…** and pick the `Vessel` folder (the one with `build.gradle` in it).
2. If IntelliJ asks, choose **Trust Project** and let the Gradle sync finish.
3. Make sure the project SDK is JDK 21+ (**File → Project Structure → Project → SDK**).

### Run the game
- In the **Gradle** tool window: **Vessel → Tasks → application → run**, or
- From a terminal in the project folder:

```bash
./gradlew run
```

(On Windows `cmd`/PowerShell, use `gradlew.bat run`.)

### Run the tests
- Right-click `src/test/java` → **Run 'All Tests'**, or:

```bash
./gradlew test
```

---

## How to play (current prototype)

1. You fight three enemies in a row: a Skeleton, a Grave Knight, then the Lich. The **intent** line shows what the enemy will do on its turn.
2. You get **3 energy** per turn and draw **5 cards**. Click a card to play it (the number in brackets is its energy cost).
3. Click **End Turn**. The enemy acts, your hand is discarded, and you draw a new hand.
4. When an enemy dies you choose:
   - **Possess**: add the enemy's signature card to your deck.
   - **Leave the body**: move on unchanged.
5. Beat all three and you **escape**. Drop to 0 HP and you **die**.

---

## Project structure

```
src/main/java/com/vessel/
├── Launcher.java              Entry point (starts JavaFX)
├── ui/VesselApp.java          JavaFX window: displays state, forwards clicks to the game rules
├── model/                     Entity (abstract), Hero, Enemy, Boss
├── cards/                     Card, AttackCard, BlockCard, Deck
├── strategy/                  EnemyBehavior, AggressiveBehavior, DefensiveBehavior
├── factory/                   EnemyFactory, CryptEnemyFactory, CavernEnemyFactory
└── combat/CombatEncounter.java   Turn rules for one fight (no UI code)
src/test/java/com/vessel/      JUnit 5 tests for cards, deck, enemies and combat
```

The game rules (`model`, `cards`, `combat`, ...) know nothing about JavaFX. The UI only reads state and calls methods like `playCard`, `endTurn` and `possess`.

---

## Design patterns

| Pattern | Kind | Status | Where |
|---|---|---|---|
| **Abstract Factory** | Creational | Done | `factory/EnemyFactory` → `CryptEnemyFactory`, `CavernEnemyFactory`. Each region makes its own family of enemies with two product types: regular `Enemy` objects (grunt, elite) and a `Boss` that changes behavior mid-fight. A region's grunt, elite and boss always match. |
| **Strategy** | Behavioral | Done | `strategy/EnemyBehavior` → `AggressiveBehavior`, `DefensiveBehavior`. How an enemy fights is a swappable object. |
| **Composite** | Structural | Not started | Planned for the dungeon map (regions containing rooms). |
| **Decorator** | Structural | Not started | Planned for mutations that wrap cards and change what they do. |

---

## What has been done

1. **Project setup:** a Gradle project with JavaFX 21, JUnit 5 and the Gradle wrapper. It opens directly in IntelliJ and runs with `gradlew run`.
2. **Core model:** `Entity` has HP and block, `Hero` has a deck, and `Enemy` has a behavior and a card to possess. `Boss` extends `Enemy` and switches to a second behavior ("enraged") at half health.
3. **Cards and deck:** `AttackCard` and `BlockCard`. `Deck` handles drawing, discarding and reshuffling. The starter deck is 5 Strikes and 4 Guards.
4. **Strategy and Abstract Factory**, as in the table above.
5. **Combat:** `CombatEncounter` handles energy, playing cards, ending the turn, the enemy's turn, win/lose, and possession (gain the enemy's card).
6. **A basic JavaFX window** to play three fights in a row.
7. **Unit tests (13)** for damage, block, drawing cards, energy, enemy turns, both behaviors, the factories, possession and boss phase changes.

---

## What's next

### 1. The dungeon (Composite)
- [ ] Build a `DungeonComponent` interface with `Room` (leaf) and `Region` (holds rooms or other regions).
- [ ] Use both factories: a Crypt region, then a Caverns region, ending in an exit room.
- [ ] Later, let the player choose between branching paths.

### 2. Mutations (Decorator)
- [ ] Add a `CardDecorator` base class and a first decorator (for example a card that deals extra damage).
- [ ] Mutate one of the hero's cards each time they possess an enemy.

### 3. Humanity
- [ ] Give the hero a humanity stat that drops with each possession.
- [ ] End the run if humanity reaches 0 ("lost themselves").

### 4. Deck building
- [ ] When possessing, choose to **add** the card or **replace** an existing one.
- [ ] A screen to view your deck.

### 5. More enemies and behaviors
- [ ] New behaviors (for example one that cycles through moves, or hits harder at low HP).
- [x] Bosses that switch behavior mid-fight (`Boss` enrages at half health).

### 6. UI and polish
- [ ] Move the layout to FXML + CSS, then add art and health bars.
- [ ] A title screen.
- [ ] A GitHub Actions workflow that runs `./gradlew build` on every push.
