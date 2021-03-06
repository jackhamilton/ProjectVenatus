    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.gui.LevelMaker;
import display.gui.MainGUI;
import static display.gui.MainGUI.mainBaseImageDisplay;
import graphics.swing.BufferedJPanel;
import homestead.MainBase;
import items.Item;
import java.util.ArrayList;
import java.util.List;
import items.Armor;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import main.Player;
import homestead.AddOn;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.ClientEventHandler;
import main.HeadsUpDisplay;
import main.Tile;
import resources.Sprite;





public class DisplayHandler {
    
    public static List<Item> currentItemListDisplay = new ArrayList();
    public static List<AddOn> currentAddonListDisplay = new ArrayList();
    public static MainGUI mgui = ClientEventHandler.mgui;
    public static int charx = 200;
    public static int chary = 200;
    public static BufferedJPanel disp;
    public static KeyListen keylistener = new KeyListen();
    
    public static Sprite characterImage = null;
    public static Sprite weaponImage = null;
    public static int imgupID = -1;
    public static int imgdownID = -1;
    public static int imgrightID = -1;
    public static int imgleftID = -1;
    
    public static int selectedConsumeableSlot = 1;
    
    public static Image weapUp = new ImageRetriever().getImage("/res/Character Sprites/Weapon Sprites/Iron Sword Up.png");
    public static Image weapDown= new ImageRetriever().getImage("/res/Character Sprites/Weapon Sprites/Iron Sword Down.png");
    public static Image weapRight= new ImageRetriever().getImage("/res/Character Sprites/Weapon Sprites/Iron Sword Right.png");
    public static Image weapLeft= new ImageRetriever().getImage("/res/Character Sprites/Weapon Sprites/Iron Sword Left.png");
    
    public static Player.Perk currentPerk;
    public static int perkNum;
    public static Image weaponSprite;
    public static boolean isDev = false;
    
    public static void initDisplay(){
        if (!isDev) {
            disp = MainGUI.display;
            mgui = ClientEventHandler.mgui;
            weaponImage = disp.drawImage(weapUp, charx+12, chary-12);
            characterImage = disp.drawImage(Player.mainchar.imgup, charx, chary);
            Player.mainchar.currentDirection = "up";
            Player.mainchar.currentDirectionDupe = "up";
            MainGUI.MainTabbedPane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("LEFT"), "none");
            MainGUI.MainTabbedPane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("RIGHT"), "none");
            //DisplayHandler.disp.changeKeyListener(keylistener);
            MainGUI.MainTabbedPane.addKeyListener(keylistener);
            resourceInfoDisplayer();
            DisplayHandler.displayAllInvinImages();
            HeadsUpDisplay.initializeHUD();
            DisplayHandler.displayStats();
            DisplayHandler.displayPerks();
            Image finalImage = null;
        } else {
            disp = LevelMaker.display;
        }
    }
    
    public static void displayStats() {
         mgui.Level.setText("" + Player.mainchar.level);
         mgui.Dexterity.setText("" + Player.mainchar.dexterity);
         mgui.Intell.setText("" + Player.mainchar.intelligence);
         mgui.Vitality.setText("" + Player.mainchar.vitality);
         mgui.Strength.setText("" + Player.mainchar.strength);
         
         mgui.pointsRemaining.setText("" + Player.mainchar.skillPoints);
         mgui.skillsDexterity.setText("" + Player.mainchar.dexterity);
         mgui.skillsIntelligence.setText("" + Player.mainchar.intelligence);
         mgui.skillsVitality.setText("" + Player.mainchar.vitality);
         mgui.skillsStrength.setText("" + Player.mainchar.strength);
    }
    
    public static void moveWeapon(int x, int y) {
        disp.moveImage(weaponImage.id, x, y);
    }
    
    public static void moveCharacter(String direction){
        if (!direction.equals(Player.mainchar.currentDirectionDupe)) {
        disp.removeImage(characterImage.id);
        disp.removeImage(weaponImage.id);
        switch(direction){
            case "up":
                Player.mainchar.currentDirectionDupe = "up";
                chary -=5;
                if (chary < 0) {
                    if (!MainBase.mainbase.inBase){
                        if(!Tile.setCurrentTile("up")) {
                            chary = 0;
                        } else {
                            chary = 478;
                            weaponImage = disp.drawImage(weapUp, charx+12, chary-12);
                            characterImage = disp.drawImage(Player.mainchar.imgup, charx, chary);
                        }
                    } else {
                        MainBase.mainbase.exitBase();
                        charx = 315;
                        chary = 340;
                        weaponImage = disp.drawImage(weapUp, charx+12, chary-12);
                        characterImage = disp.drawImage(Player.mainchar.imgup, charx, chary);
                    }
                } else {
                    weaponImage = disp.drawImage(weapUp, charx+12, chary-12);
                    characterImage = disp.drawImage(Player.mainchar.imgup, charx, chary);
                }
                break;
            case "down":
                Player.mainchar.currentDirectionDupe = "down";
                chary +=5;
                if (chary > 478) {
                    if (!MainBase.mainbase.inBase){
                        if(!Tile.setCurrentTile("down")) {
                            chary = 478;
                        } else {
                            chary = 0;
                            characterImage = disp.drawImage(Player.mainchar.imgdown, charx, chary);
                            weaponImage = disp.drawImage(weapDown, charx+12, chary+20);
                        }
                    } else {
                        MainBase.mainbase.exitBase();
                        charx = 315;
                        chary = 340;
                        characterImage = disp.drawImage(Player.mainchar.imgdown, charx, chary);
                        weaponImage = disp.drawImage(weapDown, charx+12, chary+20);
                    }
                } else {
                    characterImage = disp.drawImage(Player.mainchar.imgdown, charx, chary);
                    weaponImage = disp.drawImage(weapDown, charx+12, chary+20);
                }
                break;
            case "right":
                Player.mainchar.currentDirectionDupe = "right";
                charx+=5;
                if (charx > 732) {
                    if (!MainBase.mainbase.inBase){
                        if(!Tile.setCurrentTile("right")) {
                            charx = 732;
                        } else {
                            charx = 0;
                            weaponImage = disp.drawImage(weapRight, charx+12, chary+12);
                            characterImage = disp.drawImage(Player.mainchar.imgright, charx, chary);
                        }
                    } else {
                        MainBase.mainbase.exitBase();
                        charx = 315;
                        chary = 340;
                        weaponImage = disp.drawImage(weapRight, charx+12, chary+12);
                        characterImage = disp.drawImage(Player.mainchar.imgright, charx, chary);
                    }
                } else {
                    weaponImage = disp.drawImage(weapRight, charx+12, chary+12);           
                    characterImage = disp.drawImage(Player.mainchar.imgright, charx, chary);         
                }
                break;
           case "left":
                Player.mainchar.currentDirectionDupe = "left";
                charx-=5;
                if (charx < 0) {
                    if (!MainBase.mainbase.inBase){
                        if(!Tile.setCurrentTile("left")) {
                            charx = 0;
                        } else {
                            charx = 738;
                            characterImage = disp.drawImage(Player.mainchar.imgleft, charx, chary);
                            weaponImage = disp.drawImage(weapLeft, charx-20, chary+12);
                        }
                    } else {
                        MainBase.mainbase.exitBase();
                        charx = 315;
                        chary = 340;
                        characterImage = disp.drawImage(Player.mainchar.imgleft, charx, chary);
                        weaponImage = disp.drawImage(weapLeft, charx-20, chary+12);
                    }
                } else {
                    characterImage = disp.drawImage(Player.mainchar.imgleft, charx, chary);
                    weaponImage = disp.drawImage(weapLeft, charx-20, chary+12);
                }
                break;
        }
        } else {
            switch(direction){
                case "up":
                    chary -=5;
                    if (chary < 0) {
                        if (!MainBase.mainbase.inBase){
                            if(!Tile.setCurrentTile("up")) {
                                chary = 0;
                            } else {
                                chary = 478;
                                disp.moveImage(weaponImage.id, charx+12, chary-12);
                                disp.moveImage(characterImage.id, charx, chary);
                            }
                        } else {
                            MainBase.mainbase.exitBase();
                            charx = 315;
                            chary = 340;
                            disp.moveImage(weaponImage.id, charx+12, chary-12);
                            disp.moveImage(characterImage.id, charx, chary);
                        }
                        
                    } else {
                        disp.moveImage(weaponImage.id, charx+12, chary-12);
                        disp.moveImage(characterImage.id, charx, chary);
                    }
                    break;
                case "down":
                    chary +=5;
                    if (chary > 478) {
                        if (!MainBase.mainbase.inBase){
                            if(!Tile.setCurrentTile("down")) {
                                chary = 478;
                            } else {
                                chary = 0;
                                disp.moveImage(characterImage.id, charx, chary);
                                disp.moveImage(weaponImage.id, charx+12, chary+20);
                            }
                        } else {
                            MainBase.mainbase.exitBase();
                            charx = 315;
                            chary = 340;
                            disp.moveImage(characterImage.id, charx, chary);
                            disp.moveImage(weaponImage.id, charx+12, chary+20);
                        }
                    } else {
                        disp.moveImage(characterImage.id, charx, chary);
                        disp.moveImage(weaponImage.id, charx+12, chary+20);
                    }
                    break;
                case "right":
                    charx+=5;
                    if (charx > 732) {
                        if (!MainBase.mainbase.inBase){
                            if(!Tile.setCurrentTile("right")) {
                                charx = 732;
                            } else {
                                charx = 0;
                                disp.moveImage(weaponImage.id, charx+12, chary+12);
                                disp.moveImage(characterImage.id, charx, chary);
                            }
                        } else {
                            MainBase.mainbase.exitBase();
                            charx = 315;
                            chary = 340;
                            disp.moveImage(weaponImage.id, charx+12, chary+12);
                            disp.moveImage(characterImage.id, charx, chary);
                        }
                    } else {
                        disp.moveImage(weaponImage.id, charx+12, chary+12);
                        disp.moveImage(characterImage.id, charx, chary);
                    }
                    break;
               case "left":
                    charx-=5;
                    if (charx < 0) {
                        if (!MainBase.mainbase.inBase){
                            if(!Tile.setCurrentTile("left")) {
                                charx = 0;
                            } else {
                                charx = 732;
                                disp.moveImage(characterImage.id, charx, chary);
                                disp.moveImage(weaponImage.id, charx-20, chary+12);
                            }
                        } else {
                            MainBase.mainbase.exitBase();
                            charx = 315;
                            chary = 340;
                            disp.moveImage(characterImage.id, charx, chary);
                            disp.moveImage(weaponImage.id, charx-20, chary+12);
                        }
                    } else {
                        disp.moveImage(characterImage.id, charx, chary);
                        disp.moveImage(weaponImage.id, charx-20, chary+12);
                    }
                    break;
            }
        }
    }
    
    public static void itemListPrinter(Item.Type t){
        
        currentItemListDisplay.clear();
        MainGUI.itemListModel.removeAllElements();
        for(int i = 0; i<Player.mainchar.items.size(); i++){
            if(Player.mainchar.items.get(i).itemtype == t){
                
                MainGUI.itemListModel.addElement(Player.mainchar.items.get(i).name);
                currentItemListDisplay.add(Player.mainchar.items.get(i));
                
            }
            
        }     
        
    }
    
    public static void itemListPrinter(Armor.Type at){
        currentItemListDisplay.clear();
        MainGUI.itemListModel.removeAllElements();
        for(int i = 0; i<Player.mainchar.items.size(); i++){
            if(Player.mainchar.items.get(i).itemtype == Item.Type.ARMOR){
                Armor armor = (Armor)Player.mainchar.items.get(i);
                if(armor.type == at){
                    MainGUI.itemListModel.addElement(Player.mainchar.items.get(i).name);
                    currentItemListDisplay.add(Player.mainchar.items.get(i));
                }
            
                
            }
        } 
    }
    
    public static void itemListPrinter(int consumeableslot){
        currentItemListDisplay.clear();
        MainGUI.itemListModel.removeAllElements();
        selectedConsumeableSlot = consumeableslot;
        
        for(int i = 0; i<Player.mainchar.items.size(); i++){
            if(Player.mainchar.items.get(i).itemtype == Item.Type.CONSUMEABLE){
                
            MainGUI.itemListModel.addElement(Player.mainchar.items.get(i).name);
            currentItemListDisplay.add(Player.mainchar.items.get(i));
                
            }
        }
    }
    
    public static void addonListPrinter(){
        currentAddonListDisplay.clear();
        MainGUI.addonListModel.removeAllElements();
        MainGUI.addonListModel.addElement("Main Base");
        for(int i =0;i<MainBase.mainbase.addons.size();i++){
            MainGUI.addonListModel.addElement(MainBase.mainbase.addons.get(i).name);
            currentAddonListDisplay.add(MainBase.mainbase.addons.get(i));
        }
        mainBaseImageDisplay.setIcon(new ImageIcon(MainBase.mainbase.image.image));
    }
    
    public static void displayAllInvinImages() {
        MainGUI.lHandPic.setText("");
        MainGUI.rHandPic.setText("");
        MainGUI.lBootPic.setText("");
        MainGUI.rBootPic.setText("");
        MainGUI.leggingsPic.setText("");
        MainGUI.headPic.setText("");
        MainGUI.torsoPic.setText("");
        MainGUI.Con1Pic.setText("");
        MainGUI.Con3Pic.setText("");
        MainGUI.Con2Pic.setText("");
        MainGUI.Con4Pic.setText("");
        
        
        MainGUI.lHandPic.setIcon(Player.mainchar.left_hand_item.img);
        MainGUI.rHandPic.setIcon(Player.mainchar.right_hand_item.img);
        MainGUI.lBootPic.setIcon(Player.mainchar.boots.img);
        MainGUI.rBootPic.setIcon(Player.mainchar.boots.img);
        MainGUI.leggingsPic.setIcon(Player.mainchar.pants.img);
        MainGUI.torsoPic.setIcon(Player.mainchar.chest_piece.img);
        MainGUI.headPic.setIcon(Player.mainchar.head_piece.img);
        if(Player.mainchar.con1!=null){
            MainGUI.Con1Pic.setIcon(Player.mainchar.con1.img);
        }
        if(Player.mainchar.con2!=null){
            MainGUI.Con3Pic.setIcon(Player.mainchar.con2.img);
        }
        if(Player.mainchar.con2!=null){
            MainGUI.Con2Pic.setIcon(Player.mainchar.con3.img);
        }
        if(Player.mainchar.con4!=null){
            MainGUI.Con4Pic.setIcon(Player.mainchar.con4.img);
        }
        
    }
    
    public static void itemInfoPrinter(){
        int index = MainGUI.itemList.getSelectedIndex();
        if(index!= -1){
             MainGUI.itemDisplay.setText(currentItemListDisplay.get(index).display());
             Player.mainchar.equip(currentItemListDisplay.get(index));
             Item.Type t = currentItemListDisplay.get(index).itemtype;
             switch(t){
                case WEAPON:
                    MainGUI.lHandPic.setText("");
                    MainGUI.lHandPic.setIcon(currentItemListDisplay.get(index).img);
                    break;
                case SHIELD:
                    MainGUI.rHandPic.setText("");
                    MainGUI.rHandPic.setIcon(currentItemListDisplay.get(index).img);
                    break;
                case ARMOR:
                    Armor a = (Armor)currentItemListDisplay.get(index);
                    switch(a.type){
                        case BOOTS:
                            MainGUI.lBootPic.setText("");
                            MainGUI.lBootPic.setIcon(currentItemListDisplay.get(index).img);
                            MainGUI.rBootPic.setText("");
                            MainGUI.rBootPic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                        case LEGS:
                            MainGUI.leggingsPic.setText("");
                            MainGUI.leggingsPic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                        case HEAD:
                            MainGUI.headPic.setText("");
                            MainGUI.headPic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                        case TORSO:
                            MainGUI.torsoPic.setText("");
                            MainGUI.torsoPic.setIcon(currentItemListDisplay.get(index).img);
                            break;

                    }
                    break;
                case CONSUMEABLE:
                    switch(selectedConsumeableSlot){
                        case 1:
                            MainGUI.Con1Pic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                        case 2:
                            MainGUI.Con2Pic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                        case 3:
                            MainGUI.Con3Pic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                        case 4:
                            MainGUI.Con4Pic.setIcon(currentItemListDisplay.get(index).img);
                            break;
                    }
                    break;
            }
             
        }  
    }
    
    public static void addonInfoPrinter(){
        int index = MainGUI.AddOnList.getSelectedIndex();
        if(index ==0){
             MainGUI.addonDisplay.setText(MainBase.mainbase.getUpgradeInfo());
             MainGUI.UpgradeDisplayImage.setIcon(null);
        }else if(index!=-1){
             MainGUI.addonDisplay.setText(currentAddonListDisplay.get(index-1).display());
             MainGUI.UpgradeDisplayImage.setIcon(new ImageIcon(currentAddonListDisplay.get(index-1).image.image));
        }
    }
    
    public static void resourceInfoDisplayer(){
        MainGUI.WoodAmount.setText(Integer.toString(MainBase.mainbase.currentwood));
        MainGUI.IronAmount.setText(Integer.toString(MainBase.mainbase.currentiron));
        MainGUI.LeatherAmount.setText(Integer.toString(MainBase.mainbase.currentleather));
        MainGUI.StoneAmount.setText(Integer.toString(MainBase.mainbase.currentstone));
        MainGUI.IronAmount.setText(Integer.toString(MainBase.mainbase.currentiron));
        MainGUI.SilkAmount.setText(Integer.toString(MainBase.mainbase.currentsilk));
        
    }
    
    public static void displayPerks(){
        MainGUI.perkPoints.setText(Integer.toString(Player.mainchar.perkPoints));
        for(int i = 0;i<Player.mainchar.classSkills.length;i++){
            switch(i){
                case 0:
                    MainGUI.perkButton0.setText(Player.mainchar.classSkills[0].name);
                    break;
                case 1:
                    MainGUI.perkButton1.setText(Player.mainchar.classSkills[1].name);
                    break;
                case 2:
                    MainGUI.perkButton2.setText(Player.mainchar.classSkills[2].name);
                    break;
                case 3:
                    MainGUI.perkButton3.setText(Player.mainchar.classSkills[3].name);
                    break;
                case 4:
                    MainGUI.perkButton4.setText(Player.mainchar.classSkills[4].name);
                    break;
                case 5:
                    MainGUI.perkButton5.setText(Player.mainchar.classSkills[5].name);
                    break;
                case 6:
                    MainGUI.perkButton6.setText(Player.mainchar.classSkills[6].name);
                    break;
                case 7:
                    MainGUI.perkButton7.setText(Player.mainchar.classSkills[7].name);
                    break;
                case 8:
                    MainGUI.perkButton8.setText(Player.mainchar.classSkills[8].name);
                    break;
                case 9:
                    MainGUI.perkButton9.setText(Player.mainchar.classSkills[9].name);
                    break;
                case 10:
                    MainGUI.perkButton10.setText(Player.mainchar.classSkills[10].name);
                    break;
                case 11:
                    MainGUI.perkButton11.setText(Player.mainchar.classSkills[11].name);
                    break;
                    
            }
        }
    }
    
    public static void displayPerk(int num){
        currentPerk = Player.mainchar.classSkills[num];
        if(Player.mainchar.classSkills.length>num){
            String s = "";
            if(Player.mainchar.skills.contains(currentPerk)){
                s += "\n\n[Obtained]";
            }
            MainGUI.perkNameDis.setText(Player.mainchar.classSkills[num].name);
            MainGUI.perkDescription.setText(Player.mainchar.classSkills[num].description+s);
            perkNum = num;
        }
    }
    
    public static void buyPerk(){
        if(Player.mainchar.perkPoints>1){
            if(!Player.mainchar.skills.contains(currentPerk)){
                Player.mainchar.addPerk(currentPerk);
                Player.mainchar.perkPoints-=1;
                MainGUI.perkPoints.setText(Integer.toString(Player.mainchar.perkPoints));
                displayStats();
                displayPerk(perkNum);
            }
        }else {
            JOptionPane.showMessageDialog(mgui, "You do not have any perk points");
        }
    }
    
}