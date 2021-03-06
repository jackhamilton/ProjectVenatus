

package worldobjects.Enemies;

import display.DisplayHandler;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import main.Objects.AttackableObject;
import main.Macro.Direction;


public class Enemy extends AttackableObject implements Runnable{
    
    public int health;
    public String name;
    public Type type;
    public boolean alive;
    
    public static List<Enemy> enemies = new ArrayList();
    
    public static void main(String[] args){
        Cactus c = new Cactus(1, 1);
    }
    
    public Enemy(){
        alive = true;
        Thread ai = new Thread(this);
        ai.start();
    }
    
    //AI Thread
    @Override
    public void run() {
        System.out.println("This is Enemy class");
    }
    
    public void move(Direction d){
        switch(d){
            case UP:
                y+=20;
                DisplayHandler.disp.moveImage(image.id, x, y);
                break;
            case DOWN:
                y-=20;
                DisplayHandler.disp.moveImage(image.id, x, y);
                break;
            case RIGHT:
                x+=20;
                DisplayHandler.disp.moveImage(image.id, x, y);
                break;
            case LEFT:
                x-=20;
                DisplayHandler.disp.moveImage(image.id, x, y);
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        alive = false;
    }
    
    
    
    
    public enum Type{
        STAGNANT
    }
    
}
