import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;
    private int health = 100;
    private boolean isInvincible = false;  // is use to make the hero invincible

    private long invincibleStartTime = -1; // -1 signifie qu'il n'a pas été activé

    private static final long INVINCIBLE_DURATION = 2000;

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
    public int getHealth() {
        return health;
    }
    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if (s!=this){
                if ((s instanceof SolidSprite) ){
                    if (((SolidSprite) s).intersect(moved)){
                        return false;
                    }
                }
                // ajout de la logique des trapes
                if ((s instanceof TrapSprite) ){
                    if (((TrapSprite) s).intersect(moved)){
                        if (!isInvincible){
                            health-=10;
                            setInvincible(true);
                            invincibleStartTime = System.currentTimeMillis();
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }
    public void update() {
        // Vérifie si l'invincibilité a duré assez longtemps
        if (invincibleStartTime != -1 && System.currentTimeMillis() - invincibleStartTime >= INVINCIBLE_DURATION) {
            setInvincible(false);  // Rétablir l'état invincible à false
            invincibleStartTime = -1;  // Réinitialiser la variable de temps
        }
    }

    @Override
    public void draw(Graphics g) {
        update();
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);
    }
}





