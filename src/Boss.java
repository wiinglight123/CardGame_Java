import java.util.Random;

public class Boss {


    private String name;
    private int health;
    private int maxHealth;
    private Random random;


    public Boss(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.random = new Random();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void displayHealthBar() {
        int healthPercentage = (int) (((double) health / maxHealth) * 100);
        System.out.println(name + "'s Health: " + health + "/" + maxHealth + " (" + healthPercentage + "%)");
    }

    public int attackPlayer() {
        int damage = random.nextInt(15) + 10; // Random damage between 10 and 29

        System.out.println(name + " attacks! Deals " + damage + " damage to the player.");
        return damage;
    }
    /*Remember to code this */
    public void specialAttack() {
        int damage = random.nextInt(30) + 20; // Random damage between 20 and 49
        System.out.println(name + " uses a special attack! Deals " + damage + " damage to the player.");
    }
    /*Remember to code this */
    public void heal() {
        int healing = random.nextInt(20) + 10; // Random healing between 10 and 29
        health = Math.min(maxHealth, health + healing);
        System.out.println(name + " heals for " + healing + " health.");
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        if (health > 0) {
            System.out.println(name + " takes " + damage + " damage!");
        } else {
            System.out.println("You win!");
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

}
