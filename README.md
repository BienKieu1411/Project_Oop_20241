# 🃏🎲 Game Card 🎮🃏🔥

## 🎮 Introduction 🏆

- The game includes three popular card games: Tiến Lên Miền Bắc, Ba Cây, and Tiến Lên Miền Nam. Each game has unique rules and requires strategic thinking. The game is developed in Java with a JavaFX-based graphical interface.

## 🛠⚡ Installation ⚙️

### 1️⃣ System Requirements 💻:

- Windows/macOS/Linux

- Java 11 trở lên

- JavaFX SDK

- Maven or Gradle (optional)

### 2️⃣ Installation Steps 🛠:

- Clone repository:

```bash
git clone https://github.com/BienKieu1411/Project_Oop_20241/
cd Project_Oop_20241
```

- If using Maven:

```bash
mvn clean install
mvn javafx:run
```

- If using Gradle:

```bash
./gradlew build
./gradlew run
```

## 📜🎴 Game Rules 🃏

### 🃏 1. Tiến Lên Miền Bắc 🔥

- Played with a 52-card deck, up to 4 players.

- Each player is dealt 13 cards.

- The player with 3 of Spades starts first.

- Cards must be played in the same suit and be higher than the previous card.

- Valid combinations:

  - Single: Any single card.

  - Pair: Two cards of the same rank.

  - Triple: Three cards of the same rank.

  - Straight: Consecutive cards of the same suit.

  - Four of a Kind: Four cards of the same rank.

- The winner is the first to play all their cards.

### 🂡 2. Ba Cây 🎲

- Chơi với bài 36 lá (loại bỏ bài 10, J, Q, K).

- Played with a 36-card deck (cards 10, J, Q, K are removed).

- Each player is dealt 3 cards.

- The score is calculated as the sum of card values, using only the last digit.

  - A = 1, 2-9 keep their values, 10-K = 0 points.

  - The player with the highest total points wins.

- In case of a tie:

- Compare suits (Diamonds > Hearts > Spades > Clubs).

### 🃏 3. Tiến Lên Miền Nam ⚡

- Similar to Tiến Lên Miền Bắc, but with some differences:

  - No suit restrictions when playing a higher card.

  - Can play a higher-ranking card regardless of suit.

  - Four consecutive pairs (Four Double Sequence) can beat 2s.

## 🎯🚀 How to Play 🕹️

### 🎮 1. Run the game with the command:

```sh
java MainApplication
```

### 🎲 2. Select a game mode.

### 🕹 3. Follow the in-game instructions.

## 📩📨 Contact 📬

- ✍️ Authors: Kiều Giang Biên, Phạm Việt Cường, Bùi Lê Hoàng, Nguyễn Huy Kiệt, Nguyễn Ngọc Văn Quyến, Phạm Ngọc Tuyên.

- 🌍 GitHub: [BienKieu1411](https://github.com/BienKieu1411)

- 📧 Email: bienkieugiang@gmail.com
