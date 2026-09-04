# Wolf3 / Werewolf

A Werewolf (Mafia-style) game plugin for Spigot 1.16+.

## Recent changes

### Map selector `{player}` placeholder
Fixed broadcast to show the real player name.

### Lobby scoreboard
- Title: **WEREWOLF**
- Win count leaderboard (top 5)
- Players: `count/max`
- IP: `play.werewolf.net`

### Game scoreboard
- Phase timer removed (phase + day + alive/dead + votes remain)

### Player-head GUIs (Seer, Sheriff, Cupid, Spectator)
- Inventory size auto-scales with alive player count:
  - 1–9 → 9 slots
  - 10–18 → 18 slots
  - up to 54 max

### Join / leave
- Vanilla join/leave messages disabled
- Only in-game messages: `{player} joined the game! (count/max)`

## Build

```bash
mvn clean package
```

Output: `target/Werewolf.jar`

Requires Java 17+ and Spigot API 1.16.5.
