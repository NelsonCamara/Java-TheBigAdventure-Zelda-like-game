# The Big Adventure - Jeu Zelda-like en Java

Jeu d'aventure en vue de dessus inspiré de Zelda, développé en **Java 21** avec rendu graphique temps réel. Le joueur explore une carte générée depuis un fichier `.map` personnalisé, combat des ennemis, collecte des objets et interagit avec l'environnement.

---

## À propos du projet

The Big Adventure est un jeu d'action-aventure 2D où le joueur évolue dans un monde composé de biomes variés (lave, eau, glace), d'obstacles (murs, arbres, rochers, portes verrouillées), d'ennemis avec IA de déplacement, et d'objets collectables (épées, clés, pizzas). Le jeu inclut un système de combat, un inventaire interactif, et une caméra qui suit le joueur avec zoom dynamique.

Le monde est entièrement défini par des fichiers `.map` avec un format custom (encodages, grille ASCII, entités), interprétés par un **lexer/parser** intégré.

---

## Architecture & Conception

### Architecture en couches

Le projet suit une séparation claire en 6 packages fonctionnels :

```
src/com/TheBigAdventure/
  Main/              → Point d'entrée
  characterEntities/ → Joueur, ennemis, alliés, inventaire, combat
  graphic/           → Rendu graphique et gestion des événements
  groundEntities/    → Biomes, obstacles, décorations
  mapBuilder/        → Lexer, parser, validation, chargement de carte
  usableEntities/    → Items (Sword, Key, Pizza)
```

**43 classes** | **~6000 lignes de code**

---

## Compétences techniques démontrées

### Programmation Orientée Objet avancée

- **Sealed interfaces** — `Character` (permits `Enemy`, `Player`, `Ally`) et `CanFight` (permits `Enemy`, `Player`) pour un contrôle strict de la hiérarchie de types
- **Records** (`Tile`, `Biomes`, `Obstacles`, `Sword`, `Pizza`, `Key`, `Result`, `MapSize`, `MapContainer`) pour l'immutabilité des données
- **Interfaces multiples** — `Player` implémente à la fois `Character` et `CanFight` ; les items implémentent `InsideInventory`
- **Enums avec comportement abstrait** — `ItemType` et `CharacterType` avec méthodes `createItem()` / `createCharacter()` via le pattern **Factory Method**
- **Pattern Strategy** — `ActionType` (enum implémentant `ExecuteAction`) pour gérer les interactions contextuelles (ramasser un item, combattre, ouvrir une porte, se soigner)
- **Immutabilité** — `Case` est immuable avec méthodes `withCharacterEntity()`, `withItem()`, `withEnvironnementEntity()` retournant de nouvelles instances
- **Interface fonctionnelle** — `CaseUpdater<T>` avec `@FunctionalInterface` et utilisation de method references (`Character::updateCharacterInCase`)

### Compilateur / Interpréteur de fichiers Map

- **Lexer** — Analyse lexicale complète avec expressions régulières et tokenisation (`Token` enum avec 12 types de tokens : `SECTION_HEADER`, `SIZE`, `DATA_BLOCK`, `POSITION`, `ZONE`, etc.)
- **Parser** — Extraction structurée des sections `encodings`, `size`, `data` et `[element]`
- **Validation exhaustive** — `MapValidate` vérifie les dimensions, les encodages (unicité clés/valeurs, format caractère unique), la conformité de chaque ligne de la grille
- **Chargement dynamique** — `LoadObjectsFromMap` instancie les objets via les patterns Factory selon leur type détecté (`isCharacter()`, `isEnvironnement()`, `isInsideInventory()`)

### Moteur de jeu

- **Game loop** temps réel avec rendu continu et gestion d'événements clavier
- **Caméra dynamique** — Zoom ×6 centré sur le joueur avec gestion des bordures de carte (`AffineTransform`)
- **Système de combat** — Interface `CanFight` avec méthode `default canAttack()` utilisant la distance de Manhattan
- **IA ennemie** — Déplacements aléatoires dans une zone définie, avec intervalle temporel (`Instant`)
- **Système d'inventaire** — Grille 3×3 navigable au clavier avec curseur, swap d'items, item en main, échange entre inventaires
- **Détection de collisions** — Vérification d'adjacence et de cases walkable avant chaque déplacement
- **Gestion des sprites** — Chargement dynamique d'images PNG par skin depuis les ressources

### Java moderne (Java 21)

- **Sealed interfaces** et **records** (fonctionnalités Java 17+)
- **`Optional<T>`** — Gestion systématique de la nullabilité pour les entités dans les cases
- **Stream API** — Filtrage de positions, collecte d'ennemis, traitement de données
- **Pattern matching** avec `switch` et `instanceof`
- **Generics** — `CaseUpdater<T>`, `Pair<T>` pour la flexibilité du code

### Bonnes pratiques

- **Javadoc complète** sur toutes les classes et méthodes publiques
- **Programmation défensive** — `Objects.requireNonNull()` systématique avec messages d'erreur
- **Gestion d'exceptions** — `IllegalArgumentException`, `IllegalStateException`, `NoSuchElementException`
- **Regex** pour le parsing de zones ennemies et de positions

---

## Lancement

### Prérequis
- **Java 21** ou supérieur

### Exécution rapide
```bash
java -jar thebigadventure.jar
```

### Avec une carte personnalisée
```bash
java -jar thebigadventure.jar --level nom_de_la_carte.map
```

### Compilation depuis les sources
```bash
javac -d out src/com/TheBigAdventure/**/*.java
java -cp out com.TheBigAdventure.Main.Main
```

### Contrôles
| Touche | Action |
|--------|--------|
| `↑` `↓` `←` `→` | Déplacer le joueur |
| `Espace` | Interagir (ramasser, combattre, ouvrir porte, utiliser item) |
| `I` | Ouvrir / fermer l'inventaire |
| `↑` `↓` `←` `→` (inventaire) | Naviguer dans l'inventaire |
| `Espace` (inventaire) | Équiper l'item sélectionné |

---

## Format des fichiers Map

Le jeu utilise un format de carte custom avec 4 sections :

```
[grid]
  encodings: GRASS(g) TREE(T) WALL(W) WATER(Z) ...
  size: (65 x 47)
  data: """
  EEEEEEEEEEEEEEE
  ET           TE
  E     g f     E
  EEEEEEEEEEEEEEE
  """

[element]
  name: Hero
  skin: BABA
  player: true
  position: (10, 5)
  health: 20

[element]
  name: Goblin
  skin: MONSTER
  position: (20, 10)
  health: 5
  damage: 2
  zone: (3 x 3)
```

---

## Documentation

Le dossier `docs/` contient :
- **dev.pdf** — Documentation technique développeur
- **user.pdf** — Guide utilisateur
- **api/** — Javadoc générée

---

## Technologies

| Technologie | Usage |
|-------------|-------|
| Java 21 | Langage principal |
| Java AWT / Graphics2D | Rendu graphique et sprites |
| Zen5 Library | Boucle applicative et événements |
| Regex / Lexer custom | Parsing des fichiers de carte |

---

## Auteur

**Nelson Camara** — Étudiant en Master Informatique

---

*Projet académique — Jeu d'aventure inspiré de Zelda à des fins éducatives.*
