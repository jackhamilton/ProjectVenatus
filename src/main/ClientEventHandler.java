/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import display.gui.MainGUI;
import display.DisplayHandler;
import homestead.MainBase;
import items.Consumeable;
import items.Item;
import items.Shield;
import items.Weapon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.Objects.GameObject;
import worldobjects.Chest;
import worldobjects.Pot;
import worldobjects.Tile;


public class ClientEventHandler {

    public static MainGUI mgui;
    
    public static void main(String[] args) {
        
        mgui = new MainGUI();
        mgui.setVisible(true);
        Player.mainchar = new Player("Jesus", Player.Classes.MAGE);
        MainBase.mainbase = new MainBase();
        DisplayHandler.initDisplay(); 
        HeadsUpDisplay.initializeHUD();
        
        List<Item> items = new ArrayList();
        items.add(new Shield(Shield.Shld.WOOD_SHIELD));
        Pot pot = new Pot(100, 100, items);
        Chest c = new Chest(400, 100,new ArrayList(Arrays.asList(new Consumeable(Consumeable.Cnsm.POTION), new Weapon(Weapon.MagicWeap.FIRE_ROD))));
        
        List<GameObject> objects = new ArrayList();
        
        objects.add(pot);
        objects.add(c);
        
        
        Tile mainTile = new Tile(0, 0, true, objects);
        mainTile.setCurrentTile();
    }
    
}
