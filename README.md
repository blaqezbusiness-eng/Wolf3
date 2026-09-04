# Wolf3 / Werewolf

A Werewolf (Mafia-style) game plugin for Spigot 1.16+.

## Multi-language

Messages live in `plugins/Werewolf/messages/`:

- `message_en.yml` — English
- `message_ch.yml` — Traditional Chinese
- Add more: `message_<code>.yml` (e.g. `message_jp.yml`)

Config (`config.yml`):

```yaml
default-language: en
```

Player language is stored in `language.yml`.

Change language:

- `/ww lang` — list languages + open GUI
- `/ww lang en` / `/ww lang ch` — set language
- Lobby item **Language** (book) — select from available languages

## Other recent changes

- Map selector `{player}` placeholder fixed
- Lobby scoreboard: WEREWOLF title, win leaderboard, players, IP
- Game scoreboard: phase timer removed
- Player-head GUIs auto-size by player count
- Vanilla join/leave messages disabled

## Build

```bash
mvn clean package
```

Output: `target/Werewolf.jar` (Java 17+, Spigot API 1.16.5)
