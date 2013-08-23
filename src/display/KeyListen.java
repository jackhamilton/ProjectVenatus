
package display;

import java.awt.Point;
import main.Objects.ActivatableObject;
import java.awt.event.KeyEvent;
import main.Objects.AttackableObject;
import main.Player;
import main.Macro;
import main.Tile;


public class KeyListen implements java.awt.event.KeyListener {
    
    public static boolean isMoving = false;
    public static Mover mover = new Mover();
    public static Thread move = new Thread(mover); 
    public static boolean isAttacking = false;
    
    @Override
    public synchronized void keyTyped(KeyEvent e) {
        
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        isMoving = true;
        if (keyCode == KeyEvent.VK_UP) {
            main.Player.mainchar.currentDirection = "up";
            DisplayHandler.weaponSprite = DisplayHandler.weapUp;
            if(!move.isAlive()) {
                move.start();
            }
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            main.Player.mainchar.currentDirection = "down";
            DisplayHandler.weaponSprite = DisplayHandler.weapDown;
            if(!move.isAlive()) {
            move.start();
            }
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            main.Player.mainchar.currentDirection = "right";
            DisplayHandler.weaponSprite = DisplayHandler.weapRight;
            if(!move.isAlive()) {
            move.start();
            }
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            main.Player.mainchar.currentDirection = "left";
            DisplayHandler.weaponSprite = DisplayHandler.weapLeft;
            if(!move.isAlive()) {
            move.start();
            }
        } 
        if(keyCode == KeyEvent.VK_E){
            
            //Maximum and minimum x and y values for attacking something
            int maX = DisplayHandler.charx + Player.mainchar.imgup.getWidth(null);
            int maY = DisplayHandler.chary +Player.mainchar.imgup.getHeight(null);
            int miX = DisplayHandler.charx;
            int miY = DisplayHandler.chary;
            for(int i = 0; i<Tile.currentTile.objects.size(); i++){
                if(Tile.currentTile.objects.get(i) instanceof ActivatableObject){
                    ActivatableObject obj = (ActivatableObject)Tile.currentTile.objects.get(i);
                    
                    Point center = Macro.center(maX, miX, maY, miY);
                    if(Macro.within(center.x, obj.maX, obj.miX) && Macro.within(center.y, obj.maY, obj.miY)){
                        obj.activate();
                    }
                }
                    
            }
        }
        if(keyCode == KeyEvent.VK_A){
            isAttacking = true;
            //Maximum and minimum x and y values for attacking something
            int maX = -1;
            int maY = -1;
            int miX = -1;
            int miY = -1;
            //Establish the hitbox based on the player's direction.
            switch (Player.mainchar.currentDirection) {
                case "up" :
                    maX = DisplayHandler.charx + 40;
                    maY = DisplayHandler.chary + 40;
                    miX = DisplayHandler.charx;
                    miY = DisplayHandler.chary;
                    break;
                case "down" :
                    maX = DisplayHandler.charx + 40;
                    maY = DisplayHandler.chary + 40 + 15;
                    miX = DisplayHandler.charx;
                    miY = DisplayHandler.chary + 40;
                    break;
                case "right" :
                    maX = DisplayHandler.charx + 40 + 20;
                    maY = DisplayHandler.chary;
                    miX = DisplayHandler.charx + 40;
                    miY = DisplayHandler.chary + 40;
                    break;
                case "left" :
                    maX = DisplayHandler.charx;
                    maY = DisplayHandler.chary + 40;
                    miX = DisplayHandler.charx - 15;
                    miY = DisplayHandler.chary;
                    break;
            }
            //Loop through all attackable objects and test if any are within range of the player
            for(int i = 0; i<AttackableObject.attackableObjects.size(); i++){
                AttackableObject obj = AttackableObject.attackableObjects.get(i);
                //Test if the object's hitbox intersects with the player's hitbox or vice versa.
                //This works by testing if any of the corners of each hitbox are within the other box.
                boolean intersect = false;
                if (Macro.within(obj.maX, maX, miX) || Macro.within(obj.miX, maX, miX)) {
                    if (Macro.within(obj.maY, maY, miY) || Macro.within(obj.miY, maY, miY)) {
                        intersect = true;
                    }
                } 
                if (!intersect) {
                    if (Macro.within(maX, obj.maX, obj.miX) || Macro.within(miX, obj.maX, obj.miX)) {
                        if (Macro.within(maY, obj.maY, obj.miY) || Macro.within(miY, obj.maY, obj.miY)) {
                            intersect = true;
                        }
                    }
                } 
                if (!intersect) {
                    Point objCenter = Macro.center(obj.maX, obj.miX, obj.maY, obj.miY);
                    Point center = Macro.center(maX, miX, maY, miY);
                    if (Macro.within(objCenter.x, maX, miX) && Macro.within(objCenter.y, maY, miY)) {
                        intersect = true;
                    } else if (Macro.within(center.x, obj.maX, obj.miX) && Macro.within(center.y, obj.maY, obj.miY)) {
                        intersect = true;
                    }
                }
                if (intersect) {
                    obj.attacked();
                }
                //This code just tests if the top left corner of the object is within the player's attack hitbox. Whoops.
                /*if (obj.x >= miX && obj.x <= maX && obj.y >= miY && obj.y <= maY) {
                    obj.attacked();
                }*/
                
            }
        }
        
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        isMoving = false;
    }
    
}

class Mover implements Runnable {
    
    public void run() {
        while (KeyListen.isMoving) {
            DisplayHandler.moveCharacter(main.Player.mainchar.currentDirection);
            try{Thread.sleep(30);}catch(Exception e) {System.err.println("Broke");}
        }
        KeyListen.move = new Thread(KeyListen.mover);
    }
    
}