import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
//trap playground class to manage a playground that contains traps
public class TrapPlayground {
    private ArrayList<Sprite> environment = new ArrayList<>();

    //constructor that takes a file path to load the traps
    public TrapPlayground (String pathName){
        try{
            //loading images for the environment
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageTrap = ImageIO.read(new File("./img/trap.png"));

            //getting the dimensions of the trap image
            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);

            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
 //reading the level file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line=bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line!= null){
                for (byte element : line.getBytes(StandardCharsets.UTF_8)){
                    switch (element){
                        //adding the traps to our playground
                        case 'K' //k represents our trap
                                : environment.add(new SolidSprite(columnNumber*imageTrapWidth,
                                lineNumber*imageTrapHeight, imageTrap, imageTrapWidth, imageTrapHeight));
                            break;
                    }
                    columnNumber++;
                }
                columnNumber =0;
                lineNumber++;
                line=bufferedReader.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace(); //printing the stack trace for any exceptions
        }
    }

    public ArrayList<Sprite> getSolidSpriteList(){
        ArrayList <Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList(){
        ArrayList <Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }
}
