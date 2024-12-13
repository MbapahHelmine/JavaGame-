import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
//instance variables for gae components
    JFrame displayZoneFrame;//main window for displaying the game
    

    RenderEngine renderEngine;
    GameEngine gameEngine;//managing the game logic and states
    PhysicEngine physicEngine;
  //  HealthBar healthBar;

    Timer renderTimer;
    Timer gameTimer; //timer for game logic updates
    Timer physicTimer;

    public Main() throws Exception{
        displayZoneFrame = new JFrame("Java Labs");// creating a new JFrame with the title "Java Labs"
        displayZoneFrame.setSize(560,610);// setting my window size
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(200,350,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine(); //for handling movements and collisions
        gameEngine = new GameEngine(hero);

        //setting up timers for game updates
        renderTimer = new Timer(50,(time)-> renderEngine.update()); //timer for rendering objects every 50ms
        gameTimer = new Timer(50,(time)-> {
            System.out.println(hero.getHealth()); //print Hero's health to console
            gameEngine.update(); //updating game logic (health bar)
           // healthBar.setHealth(hero.getHealth());
            if (hero.getHealth() <= 0) {
                endGame(); // call method to end the game
            }
        });
        physicTimer = new Timer(50,(time)-> {
            physicEngine.update();
        });


        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

      //  healthBar = new HealthBar(100);
       // healthBar.setBounds(10, 10, 200, 20);
       // displayZoneFrame.add(healthBar);


        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true); //making the new window visible

        //initializing playground with level data
        Playground level = new Playground("./data/level1.txt");

        //SolidSprite testSprite = new DynamicSprite(100,100,test,0,0);
        renderEngine.addToRenderList(level.getSpriteList()); //adding sprites from level to render list
        renderEngine.addToRenderList(hero); //adding hero sprite to render list
        physicEngine.addToMovingSpriteList(hero);
        //setting the environment with solid sprites
        physicEngine.setEnvironment(level.getSolidSpriteList());
        //adding traps to the environment
        physicEngine.addAllToEnvironmentList(level.getTrapSpriteList());

        displayZoneFrame.addKeyListener(gameEngine); //allows the game engine to handle key events


    }
    private void endGame() {  //method used to handle game ending
        // stopping all timers
        for (Timer timer : new Timer[]{renderTimer, gameTimer, physicTimer}) {
            timer.stop();// stopping each timer
        }

        // displaying the message 'Game Over'
        JOptionPane.showMessageDialog(displayZoneFrame,
                "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);

        System.exit(0); // leave the application
    }


    public static void main (String[] args) throws Exception { //entry point of the application or gamr
	// write your code here

        Main main = new Main();// creating an instance of main that will set up and start the game

    }
}
