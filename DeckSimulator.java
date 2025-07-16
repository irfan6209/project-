import java.util.*;

class Card {
    private String suit;
    private String rank;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getSuit() { return suit; }
    public String getRank() { return rank; }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private List<Card> cards = new ArrayList<>();
    private final String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
    private final String[] ranks = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"
    };

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck() {
        cards.clear();
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
        System.out.println("Deck shuffled!");
    }

    public void sortDeck() {
        cards.sort((c1, c2) -> {
            int suitOrder = getSuitOrder(c1.getSuit()) - getSuitOrder(c2.getSuit());
            if (suitOrder != 0) return suitOrder;
            return getRankOrder(c1.getRank()) - getRankOrder(c2.getRank());
        });
        System.out.println("Deck sorted by suit and rank.");
    }

    private int getSuitOrder(String suit) {
        switch (suit) {
            case "Hearts": return 1;
            case "Diamonds": return 2;
            case "Spades": return 3;
            case "Clubs": return 4;
            default: return 5;
        }
    }

    private int getRankOrder(String rank) {
        List<String> orderedRanks = Arrays.asList(
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King", "Ace"
        );
        return orderedRanks.indexOf(rank);
    }

    public Card drawRandomCard() {
        if (cards.isEmpty()) {
            System.out.println("No more cards left in the deck.");
            return null;
        }
        Random rand = new Random();
        int index = rand.nextInt(cards.size());
        return cards.remove(index);
    }

    public List<Card> drawMultipleCards(int count) {
        List<Card> drawn = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Card card = drawRandomCard();
            if (card != null) drawn.add(card);
            else break;
        }
        return drawn;
    }

    public void printDeck() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty.");
        } else {
            System.out.println("Current cards in deck:");
            for (Card card : cards) {
                System.out.println("- " + card);
            }
        }
    }

    public void resetDeck() {
        initializeDeck();
        System.out.println("Deck reset to original 52 cards.");
    }

    public int remainingCards() {
        return cards.size();
    }
}

public class DeckSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();

        System.out.println("🃏 Welcome to Deck of Cards Simulator");
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Shuffle Deck");
            System.out.println("2. Sort Deck");
            System.out.println("3. Draw a Random Card");
            System.out.println("4. Draw Multiple Cards");
            System.out.println("5. Print Deck");
            System.out.println("6. Reset Deck");
            System.out.println("7. Exit");

            System.out.print("Your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    deck.shuffleDeck();
                    break;
                case 2:
                    deck.sortDeck();
                    break;
                case 3:
                    Card drawn = deck.drawRandomCard();
                    if (drawn != null)
                        System.out.println("You drew: " + drawn);
                    break;
                case 4:
                    System.out.print("How many cards to draw? ");
                    int count = scanner.nextInt();
                    List<Card> drawnCards = deck.drawMultipleCards(count);
                    System.out.println("You drew:");
                    for (Card c : drawnCards)
                        System.out.println("- " + c);
                    break;
                case 5:
                    deck.printDeck();
                    break;
                case 6:
                    deck.resetDeck();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting simulator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
