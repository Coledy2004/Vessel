# Vessel

A dungeon crawler and deck builder written in Java with JavaFX.

The hero fights through the dungeon, possesses defeated enemies to steal their abilities as cards, and mutates along the way, losing a little more humanity on the way to the exit.

By Cole Younoszai and Sam Kutcipal.

---

## How to play (current prototype)

1. You fight three enemies in a row: a Skeleton, a Grave Knight, then the Lich. The intent line shows what the enemy will do on its turn.
2. Each turn you get 3 energy and draw 5 cards. Click a card to play it. The number in brackets is its energy cost.
3. Click **End Turn**. The enemy acts, your hand goes to the discard pile, and you draw a new hand.
4. When an enemy dies, click **Possess** to add its signature card to your deck, or **Leave the body** to move on unchanged.
5. The Lich is a boss. Once it drops to half health it becomes enraged and switches tactics.
6. Beat all three and you escape. Drop to 0 HP and you die.

---

## Project structure

```
src/main/java/com/vessel/
├── Launcher.java              Entry point (starts JavaFX)
├── ui/VesselApp.java          JavaFX window: displays state, forwards clicks to the game rules
├── model/                     Entity (abstract), Hero, Enemy, Boss
├── cards/                     Card, AttackCard, BlockCard, DecoratedCard, Deck
├── strategy/                  EnemyBehavior, AggressiveBehavior, DefensiveBehavior
├── factory/                   EnemyFactory, CryptEnemyFactory, CavernEnemyFactory
└── combat/CombatEncounter.java   Turn rules for one fight (no UI code)
src/test/java/com/vessel/      JUnit 5 tests for cards, deck, enemies, bosses and combat
```

The game rules (`model`, `cards`, `combat` and the rest) know nothing about JavaFX. The UI reads state and calls methods like `playCard`, `endTurn` and `possess`. Nothing else.

---

## Design patterns

| Pattern | Kind | Status | Where |
|---|---|---|---|
| Abstract Factory | Creational | Done | `factory/EnemyFactory` → `CryptEnemyFactory`, `CavernEnemyFactory`. Each region makes its own family of enemies with two product types: regular `Enemy` objects (grunt, elite) and a `Boss` that changes behavior partway through a fight. A region's grunt, elite and boss always match. |
| Strategy | Behavioral | Done | `strategy/EnemyBehavior` → `AggressiveBehavior`, `DefensiveBehavior`. Each enemy gets its fighting style as an object, so swapping the object changes how it fights. |
| Builder | Creational | Not started | `DungeonBuilder` puts a run together one region at a time, pulling enemies from each region's factory, and returns a finished `Dungeon`. |
| Decorator | Structural | Started | `cards/DecoratedCard` is the abstract base: it wraps a card and passes every call through. Mutations, and later secondary effects on stronger cards, will extend it and change one thing each. No concrete decorators yet. |

---

## What has been done

1. Gradle project with JavaFX 21, JUnit 5 and the Gradle wrapper. It opens directly in IntelliJ and runs with `gradlew run`.
2. Core model. `Entity` has HP and block, `Hero` has a deck, and `Enemy` has a behavior and a card to possess. `Boss` extends `Enemy` and switches to a second behavior when it drops to half health.
3. Cards and deck. `AttackCard` and `BlockCard`. `Deck` draws, discards and reshuffles. The starter deck is 5 Strikes and 4 Guards.
4. Strategy and Abstract Factory, described in Design patterns.
5. Combat. `CombatEncounter` runs energy, card plays, the end of the turn, the enemy's turn, win and loss, and possession (gain the enemy's card).
6. A basic JavaFX window that plays three fights in a row.
7. 13 unit tests covering damage, block, drawing cards, energy, enemy turns, both behaviors, the factories, possession and boss phase changes.
8. `DecoratedCard`, the abstract base class for the Decorator pattern.

---

## What's next

### The dungeon (Builder)

Right now `VesselApp` builds the run by hand: a list of three Crypt enemies. A `DungeonBuilder` will replace that. You start a region by handing the builder an `EnemyFactory`, add fights from that region one call at a time, and finish with `build()`:

```java
Dungeon dungeon = new DungeonBuilder()
        .region(new CryptEnemyFactory())
            .grunt().elite().boss()
        .region(new CavernEnemyFactory())
            .grunt().grunt().elite().boss()
        .exit()
        .build();
```

The builder never names an enemy. It asks the current region's factory, so the Builder and the Abstract Factory work together. `build()` also checks the layout before returning it: every region has to end with a boss, and the dungeon has to end with an exit, so a broken layout fails at startup instead of halfway through a run. Branching paths, where the player picks between two routes, would get added here later.

### Mutations (Decorator)

The base class, `DecoratedCard`, is in. It implements `Card`, holds the card it wraps and passes every call through. Next comes the first real mutation, something simple like extra damage on an attack. Every time the hero possesses an enemy, one of their cards mutates. The same base class will also carry secondary effects for stronger cards later in the game.

### Humanity

The hero gets a humanity stat that drops with each possession. At 0 the run ends and the hero has lost themselves.

### Deck building

Possessing should let you either add the new card or swap it in for one you already have. We also want a screen where you can look through your deck.

### More enemies and behaviors

We want new behaviors, such as one that cycles through a fixed pattern of moves or one that hits harder at low HP. Bosses that switch behavior partway through a fight are already in (`Boss` enrages at half health).

### UI and polish

The layout moves to FXML and CSS, then gets art, health bars and a title screen. A GitHub Actions workflow will run `./gradlew build` on every push.
