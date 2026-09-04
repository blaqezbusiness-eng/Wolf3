# Fix: Map selector `{player}` placeholder

## Problem
When selecting a map, the broadcast showed:
`{player} selected map <world>`

instead of the actual player name.

## Root cause
In `Arena.selectMap()` and `Arena.selectRandomMap()`, only the `world` placeholder was passed:

```java
// BEFORE (broken)
this.broadcast(this.msg().get("game.map-selected-broadcast", MessageUtil.ph("world", worldName)));
```

Message template:
```yaml
map-selected-broadcast: "&e{player} selected map &6{world}"
```

## Fix
```java
// AFTER (fixed)
this.broadcast(this.msg().get("game.map-selected-broadcast",
    MessageUtil.ph("player", player.getName(), "world", worldName)));

// and for random:
this.broadcast(this.msg().get("game.map-selected-broadcast",
    MessageUtil.ph("player", player.getName(), "world", "Random")));
```

## Files changed
- `src/main/java/com/werewolf/game/arena/Arena.java` (methods `selectMap` and `selectRandomMap`)

## Binary
Use `Werewolf-fixed.jar` (built with this fix applied).
