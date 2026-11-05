# ⚔️ chaos_mode

Mod Minecraft pour **Fabric 1.21.11**, et mon premier projet en Java : de nouveaux minerais, des outils, un onglet créatif dédié et une **dimension Chaos** à laquelle on accède par un portail en losange posé au sol.

<p align="center">
  <img src="https://rafatns.vercel.app/assets/projets/chaos/portail.webp" width="32%" alt="Le portail Chaos activé dans l'Overworld"/>
  <img src="https://rafatns.vercel.app/assets/projets/chaos/dimension.webp" width="32%" alt="La dimension Chaos et son portail d'arrivée"/>
  <img src="https://rafatns.vercel.app/assets/projets/chaos/onglet-creatif.webp" width="32%" alt="L'onglet créatif Chaos"/>
</p>

## Contenu

### Ressources

| Étape | Comment l'obtenir |
|---|---|
| **Chaos Ore** | Minerai qui donne du **Raw Chaos** une fois miné |
| **Semi Chaos Ingot** | Raw Chaos cuit au four, ou en minant du **Semi Chaos Ore** |
| **Chaos Ingot** | Semi Chaos Ingot cuit au four |
| **Chaos Core** | Le bloc qui compose le terrain de la dimension Chaos |

### Outils

| Outil | Matériau | Durabilité |
|---|---|---|
| Sword / Pickaxe of semi chaos | Semi Chaos Ingot | 1 800 |
| Sword / Pickaxe of chaos | Chaos Ingot | 2 600 |

Tous les objets du mod sont regroupés dans l'onglet créatif **Chaos**.

### La dimension Chaos

1. Fabrique des **Chaos Portal Frame** : 4 lingots de netherite, 4 diamants et 1 Semi Chaos Ingot donnent 4 cadres.
2. Pose 12 cadres **à plat au sol, en losange** (7 blocs de large).
3. Allume le cadre au **briquet** : l'intérieur se remplit de portail.
4. Marche dedans : tu arrives dans la dimension Chaos, où un portail de retour est généré automatiquement à la surface.

## Lancer le mod

Il faut un **JDK 21 ou plus récent**.

```bash
./gradlew runClient   # lance Minecraft avec le mod
./gradlew build       # génère le .jar dans build/libs/
```

## Stack

Java 21 · Fabric Loader · Fabric API · Fabric Loom · mappings officiels Mojang

## Licence

CC0 1.0, héritée du modèle de mod Fabric : voir [LICENSE](LICENSE).

🔗 Portfolio : [rafatns.vercel.app](https://rafatns.vercel.app)
