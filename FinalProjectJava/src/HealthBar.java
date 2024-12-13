import javax.swing.*;
import java.awt.*;
//Display of the health bar with the different conditions attached to it
public class HealthBar {
    private int health; //current health of the player
    private final int maxHealth; // maximum health of the player

    public HealthBar(int maxHealth) {
        //setting the maximum health
        this.maxHealth = maxHealth;
        this.health = maxHealth; //initializing current health to maximum
    }
//method to set the current health ensuring it stays within valid bounds
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth)); // Clamp between 0 and maxHealth
       // repaint(); // next state of the health bar
    }

    //@Override
    //method to draw the health bar on my screen
    public void paintComponent(Graphics g) {
     //   super.paintComponent(g);

        // Dimensions of my health bar
        int width = 200;
        int height = 20;

        // Colors and borders
        g.setColor(Color.RED);
        g.fillRect(10, 10, width, height);

        g.setColor(Color.GREEN);
        g.fillRect(10, 10, (int) (width * (health / (double) maxHealth)), height);

        g.setColor(Color.BLACK);
        g.drawRect(10, 10, width - 1, height - 1); // Border
    }
}
