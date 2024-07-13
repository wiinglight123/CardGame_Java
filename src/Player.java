import java.util.ArrayList;
import java.util.List;


    public class Player {
        private String name;
        private int health;
        private int maxHealth;
        private String heroType;
        private Deck deck;
        public List<Card> hand;
        public Player(String name, int maxHealth, String heroType, Deck deck) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.health = maxHealth;
            this.heroType = heroType;
            this.deck = deck;
            this.hand = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }


        public List<String> getCardImages() {
            List<String> cardImages = new ArrayList<>();
            for (Card card : hand) {
                cardImages.add(card.getImagePath());
                System.out.println(cardImages);
            }

            return cardImages;
        }


        public void drawCard() {
            Card drawnCard = deck.dealCard();

            if (drawnCard != null) {
                System.out.println(name + " draws a card: " + drawnCard);
                hand.add(drawnCard);
            } else {
                System.out.println(name + " has no more cards to draw.");
            }
        }
        public Card playCard(int index) {
            if (index >= 0 && index < hand.size()) {
                return hand.remove(index);

            }
            else
                return hand.remove(0);
        }

        public int getMaxHealth() {
            return maxHealth;
        }

        public String getHeroType() {
            return heroType;
        }


       public void displayHealthBar()  {
            int healthPercentage = (int) (((double) health / maxHealth) * 100);
            System.out.println(name + " (" + heroType + ")'s Health: " + health + "/" + maxHealth + " (" + healthPercentage + "%)");
        }
        public void healSelf(Player player, int rank) {
            int heal= (int) (Math.random() * 5) + rank;
            setHealth(health+heal);
            System.out.println(name + " (" + heroType + ") heals for: " + heal + ".");

        }
        public void attackBoss(Boss boss, int rank) {
            int damage =  rank;
            System.out.println(name + " (" + heroType + ") attacks! Deals " + damage + " damage to " + boss.getName() + ".");
            boss.takeDamage(damage);
        }

        public void takeDamage(int damage) {
            health = Math.max(0, health - damage);
            System.out.println(name + " (" + heroType + ") takes " + damage + " damage!");
        }

        public boolean isAlive() {
            return health > 0;
        }
    }

