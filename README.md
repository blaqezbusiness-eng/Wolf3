# Wolf3 / Werewolf

A Werewolf (Mafia-style) game plugin for Spigot 1.16+.

## Fix in this commit

**Map selector `{player}` placeholder bug**

Previously, when a player selected a map, the broadcast message showed the literal text `{player} selected map ...` instead of the player's name.

Fixed in `Arena.selectMap()` and `Arena.selectRandomMap()` by correctly passing the `player` placeholder:

```java
MessageUtil.ph("player", player.getName(), "world", worldName)
```

## Build

```bash
mvn clean package
```

Output: `target/Werewolf.jar`

## Requirements

- Spigot / Paper 1.16.5+
- Java 17+
