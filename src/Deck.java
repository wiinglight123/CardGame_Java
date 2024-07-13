import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck() {
        cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                String imagePath = "C:/Users/Radu/IdeaProjects/Card Game/Images/playing-cards-master/" + suit + "_" + rank + ".png";
                cards.add(new Card(rank, suit, imagePath));



            }
        }
        Collections.shuffle(cards);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("The deck is empty. No more cards to deal.");
            return null;
        }
        return cards.remove(0);
    }

    public int cardsRemaining() {
        return cards.size();
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();

        int numCardsToDeal = 5;
        for (int i = 0; i < numCardsToDeal; i++) {
            Card dealtCard = deck.dealCard();
            if (dealtCard != null) {
                System.out.println("Dealt card: " + dealtCard);
            }
        }

        System.out.println("Remaining cards in the deck: " + deck.cardsRemaining());
    }
}

enum Suit {
    hearts,
    diamonds,
    clubs,
    spades
}

enum Rank {
    A(20),
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    J(12), Q(13), K(14);
    private final int value;
    Rank(final int value) {
        this.value=value;
    }
    public int getValue() { return value; }
}

class Card {
    private Rank rank;
    private Suit suit;
    private String imagePath;

    public Card(Rank rank, Suit suit, String imagePath) {
        this.rank = rank;
        this.suit = suit;
        this.imagePath = imagePath;
    }
    public Suit getSuit(Card card){
        return card.suit;
    }
    public int getRank(){
        return rank.getValue();
    }
    public String getImagePath() {
        System.out.println("Card:" +imagePath);
        return imagePath;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}