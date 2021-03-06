
package main;

import display.DisplayHandler;
import homestead.MainBase;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import main.Objects.GameObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Tile implements Serializable{
    
    public List<GameObject> objects = new ArrayList();
    public HashMap location = new HashMap();
    public static HashMap currentLocation = new HashMap();
    public boolean isMain = false;
    public boolean isDev = false;
    public static Tile currentTile;
    public static List<Tile> allTiles = new ArrayList();
    public Image background;
    public static Image currentTileBackground;
    
    public Tile(int xl, int yl, Image background){
        location.put("xCord", xl);
        location.put("yCord", yl);
        this.background = background;
        allTiles.add(this);
    }
    
    public Tile(int xl, int yl, boolean isMain, Image background){
        location.put("xCord", xl);
        location.put("yCord", yl);
        this.background = background;
        allTiles.add(this);
        this.isMain = isMain;
        objects.add(MainBase.mainbase);
    }
    
    public Tile(int xl, int yl, List<GameObject> objects, Image background){
        location.put("xCord", xl);
        location.put("yCord", yl);
        this.background = background;
        allTiles.add(this);
        this.objects = objects;
    }
    
    public Tile(int xl, int yl, boolean isMain, List<GameObject> objects, Image background){
        location.put("xCord", xl);
        location.put("yCord", yl);
        this.background = background;
        allTiles.add(this);
        this.isMain = isMain;
        objects.add(MainBase.mainbase);
        this.objects = objects;
    }
    
    public Tile(Image background){
        this.background = background;
    }
    
    public void addObject(GameObject o){
        objects.add(o);
    }
    
    public void removeObject(GameObject o){
        objects.remove(o);
    }
    
    public void setCurrentTile(){
        if (currentTile != null) {
            for (GameObject obj : currentTile.objects) {
                DisplayHandler.disp.removeImage(obj.image.id);
            }
        }
        currentTileBackground = background;
        DisplayHandler.disp.setBackground(currentTileBackground);
        for(GameObject obj : objects) {
            obj.display();
        }
        currentTile = this;
    }
    
    public static boolean setCurrentTile(String direction){
        HashMap loc = new HashMap();
        loc.put("yCord", (int)currentTile.location.get("yCord"));
        loc.put("xCord", (int)currentTile.location.get("xCord"));
        switch(direction){
            case "up":
                loc.put("yCord", (int)currentTile.location.get("yCord")+1);
                break;
            case "down":
                loc.put("yCord", (int)currentTile.location.get("yCord")-1);
                break;
            case "right":
                loc.put("xCord", (int)currentTile.location.get("xCord")+1);
                break;
            case "left":
                loc.put("xCord", (int)currentTile.location.get("xCord")-1);
                break;
            default:
                return false;
        }
        for(Tile t: allTiles){
            if(t.location.equals(loc)){
                t.setCurrentTile();
                return true;
            }
        }
        return false;
    }
    
    public static boolean setCurrentTile(int x, int y){
        HashMap hm = new HashMap();
        hm.put("xCord", x);
        hm.put("yCord", y);
        for(int i=0;i<allTiles.size();i++){
            if(allTiles.get(i).location.equals(hm)){
                allTiles.get(i).setCurrentTile();
                return true;
            }
        }
        return false;
    }
    
    public boolean serialize(){
        try {
            FileOutputStream fileout = new FileOutputStream("C:\\Users\\Austin\\Documents\\GitHub\\ProjectVenatus\\src\\main\\Tiles\\tile"+location.get("xCord")+location.get("yCord")+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(this);
            out.close();
            fileout.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static Tile deSerialize(int x, int y){
        
        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\Austin\\Documents\\GitHub\\ProjectVenatus\\src\\main\\Tiles\\tile"+x+y+".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Tile t = (Tile) in.readObject();
            in.close();
            fileIn.close();
            return t;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
}