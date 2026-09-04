# Wolf3 (Werewolf)

Spigot 1.16.5+ Werewolf minigame plugin.

## Build
```bash
mvn clean package -DskipTests
```
Output: `target/Werewolf.jar`

## Maps
Place world folders in any of:
- `plugins/Werewolf/World/<mapname>/`
- `plugins/Werewolf/maps/<mapname>/`
- `plugins/Werewolf/World/maps/<mapname>/`

Each folder should be a valid Minecraft world (`level.dat` recommended).

## Config
Item **names** are defined in `messages/message_*.yml` under `items.*`.
`config.yml` only sets `material` and `slot` per item.

## Recent changes
- Entertaining settings (sheriff election, skill brawl)
- Skill brawl: extra night abilities for any role; God Sword rare drop
- Skip clock skips 1/3 of remaining **day or night** time
- Map selector uses MAP item (not blank FILLED_MAP)
- Multi-language message system
