package Logic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 * <h2>Class describing players.</h2>
 * This class used to create different characters both for computer and the user.<br>
 * Instance of this object holds all the information about the character:its name,icons<br>
 * attack and defence factors,currently wearing items,health and desired attack and defend areas.<br>
 * User's character can be graphically seen in {@linkplain CharacterFrame}.
 * @author Dima Ryskin
 * @author Bogdan Kirylyuk
 * @see {@linkplain Armor} for more details.
 * @see {@linkplain Weapon} for more details.
 * @see {@linkplain CharacterFrame} for more details.
 * 
 */
public class Player implements Serializable
{
    private String loginToDatabase;
    /**
    * This variable holds an {@linkplain Icon} that displaying player in {@linkplain CharacterFrame}.<br>
    * Does not refer to bot.
    */
    private Icon shopIcon;
    /**
     * This variable holds an {@linkplain Icon} that is displayed in {@linkplain BattleForm}.
     */
    private Icon battleIcon;
    /**
     * This integer variable represents basic attack damage that each player have even without weapon.
     */
    private int strengthFactor;
    /**
     * This double variable represents basic defense rate that each player have even without armor.
     */
    private double defenceFactor;
    /**
     * Variable that holds array of {@linkplain Item}s that each character is currently wearing.
     */
    private Item[] item = new Item[5];
    private String name;
    /**
     * Integer variable represents character's health.
     */
    private int health;
    /**
     * {@linkplain Type} variable referring to player's defense area.
     */
    private Type defenceArea;
    /**
     * {@linkplain Type} variable referring to player's attack area.
     */
    private Type attackArea;
    /**
     * Console text generated for each played during the battle describing its progress.
     */
    private String consoleText;
    /**
     * Holds the current availability of the armor items in stock for the player.
     * @see (@linkplain Armor)
     */
    private boolean[] currentArmors;
    /**
     * Holds the current availability of the weapon items in stock for the player.
     * @see (@linkplain Weapon)
     */
    private boolean[] currentWeapons;
     
       
    /**
     * <h2>The first constructor used to create user characters.</h2><br>
     * This constructor instantiates player created by user.<br>
     * Not all the fields of the player are being initialized here but only the basic ones.<br>
     * All other properties are set by the user though {@linkplain CrahacterFrame} window.
     * @param name receives String variable representing player's desired name.
     */
    public Player(String name) //Player constructor
    {
        shopIcon = new ImageIcon(getClass().getResource("images/player-image-fixed.jpg"));
        battleIcon = new ImageIcon(getClass().getResource("images/player-battle-window.jpg"));
        int rndStrength = (int)(Math.random() * 3 + 1); //Generate strength factor
        setStrengthFactor(rndStrength);
        double rndDefence = (double)(Math.random() * 0.3); //Generate defence factor
        setDefenceFactor(rndDefence);
        setName(name);
        setHealth(100);
        currentArmors = new boolean[]{  true, 
                                        false,
                                        false,
                                        true, 
                                        false,
                                        false,
                                        true, 
                                        false,
                                        false,
                                        true, 
                                        false,
                                        false,

                                    };
         currentWeapons = new boolean[]{true, 
                                        true,
                                        true,
                                        false,
                                        false,
                                        true,
                                        false,
                                        false
                                        };
    }
    /**
     * <h2>The second constructor used to create bot players.</h2><br>
     * In this constructor all the properties of the object are filled randomally to make each bot different.<br>
     * @param wepArr array of type {@linkplain Weapon} that represents all the weapons in game that the bot can choose from.
     * @param armArr array of type {@linkplain Armor} that represents all the armors in game that the bot can choose from.
     */
    public Player( Weapon[] wepArr, Armor[] armArr ) //Bot constructor
    {
        battleIcon = new ImageIcon(getClass().getResource("images/bot-battle-window.jpg"));
        int rndStrength = (int)(Math.random() * 3 + 1);
        double rndDefence = (double)(Math.random() * 0.3);
        setStrengthFactor(rndStrength);
        setDefenceFactor(rndDefence);
        for(int area = 0 ; area < 4 ; )
        {
            Item randomItem = armArr[(int)(Math.random() * 12)];
            if(randomItem.getItemType().getIndex() == area  && randomItem.isAvalible())
            {
                item[area] = randomItem;
                area++;
            }
        }
        
        
        do //
        {
            item[4] = wepArr[(int)(Math.random() * 8)];
        }while(item[4].isAvalible() == false);
        
        String botNames[] = {"Destructor", "Garbage Collector", "Static Member", "Exception Handler"};
        setName(botNames[(int) (Math.random()*botNames.length)]);
        setHealth(100);
        
    }
    
    public void setLoginToDatabase(String login)
    {
        this.loginToDatabase = login;
    }
    
    public String getLoginToDatabase()
    {
        return loginToDatabase;
    }
    
    public boolean[] getCurrentWeaponsArray()
    {
        return this.currentWeapons;
    }
    
    public boolean[] getCurrentArmorsArray()
    {
        return this.currentArmors;
    }
    
    /**
     * Updates the current array of items according to the (@linkplain Item)s array located
     * in the (@linkplain GameInitialize) object
     * @param armArray
     * @param wepArray 
     */
    public void updatePlayerItemsByCurrentGame(Armor[] armArray, Weapon[] wepArray)
    {
        int i=0;
        for (Armor armorSingleItem : armArray)
        {
            currentArmors[i++] = armorSingleItem.isAvalible();
        }
        i=0;
        for (Weapon weaponSingleItem : wepArray)
        {
            currentWeapons[i++] = weaponSingleItem.isAvalible();
        }
    }
    
    /**
     * Method used to get current time stamp used in the console text.
     * @return String containing the current time in the format HH:mm:ss.
     */
    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("HH:mm:ss").format(new Date()); 
    }
    /**
     * Method used to get console text of each player.
     * @return String variable containing the console text.
     */
    public String getConsoleText()
    {  
        return consoleText;
    }
    /**
     * <h2>Method used to generate user's player statistics.</h2>Based on currently wearing {@linkplain Item}s<br>
     * and basic strength and defense factor.<br>
     * Displayed in {@linkplain CharacterFrame}
     * @return String variable containing all the information about player's statistics.
     */
    public String getStats()
    {
        
        Armor[] arms = new Armor[4];
        
        String output = String.format("%s\n\nDEFENCE:\n", this.getName());
        
        
            for(short i = 0 ; i < 4 ; i++)
            {   
                arms[i] = (Armor) item[i];
                
                if(arms[i] != null)
                {
                    output += String.format("%s: %d%%\n", Type.getType(i).toString(),  (int) (arms[i].getDefenceValue()*100) );
                }
                
                
            }
            
            output += String.format("\nATTACK: ");
            
            Weapon wep = (Weapon) item[Type.WEAPON.getIndex()];
            if (wep != null)
            {
                output += String.format("%d\n", wep.getDamageValue() + getStrengthFactor());
            }

            return output;
 
    }
    /**
     * Method used only by the bot character to generate random attack and defence area.
     * @return {@linkplain Type} object containing information about specific body part.
     */
    public Type getRandomAttackArea()
    {
        
        Type type = Type.getType( (short) (Math.random() * 4) );
        return type;
    }
    
    /**
     * Method used to get an {@linkplain Item} from specific body part.
     * @param area receives {@linkplain Type} defining the body part we looking for items on.
     * @return {@linkplain Item} that is currently on the desired body part if any.
     */
    public Item getItem(Type area)
    {
        return item[area.getIndex()];
    }
    /**
     * Method used to wear an item.
     * @param item receives an {@linkplain Item} to wear.
     */
    public void setItem(Item item)
    {
        item.inUse = true;
        this.item[item.getItemType().getIndex()] = item;
    }
    /**
     * Method used to take item off.
     * @param itemType receives a {@linkplain Type} which defines body part where we want to take the item off.
     */
    public void setItemNull(Type itemType)
    {
        this.item[itemType.getIndex()] = null;
    }
    /**
     * Method used to get defense factor of the player.
     * @return double value representing the defense factor.
     */
    public double getDefenceFactor()
    {
        return defenceFactor;
    }
    
    
    /**
     * Method used to set the defense factor of a player.
     * @param val receives double value to be set as defense factor.
     */
    public void setDefenceFactor(double val)
    {
        this.defenceFactor = val;
    }

    /**
     * Method used to get current player's health.
     * @return integer value representing current health of a player.
     */
    public int getHealth()
    {
        return health;
    }
    /**
     * Method used to set player's current health.
     * @param val receives integer value.
     */
    public void setHealth(int val)
    {
        this.health = val;
    }
    /**
     * Method used to get player's name.
     * @return String variable containing player's name.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Method used to set player's Name
     * @param val receives String variable to be set as player's name.
     */
    public void setName(String val)
    {
        this.name = val;
    }
    /**
     * Method used to get player's strength factor.
     * @return integer value representing player's strength factor.
     */
    public int getStrengthFactor()
    {
        return strengthFactor;
    }
    /**
     * Method used to set player's strength factor.
     * @param val receives integer value.
     */
    public void setStrengthFactor(int val)
    {
        this.strengthFactor = val;
    }
    /**
     * Method used to get player's full information.
     * @return String variable.
     */
    public String toString()
    {
        return String.format("player name is %s\nplayer strength factor is %d\nplayer def factor is %.1f\n"
                + "player helmet is %s\n"
                + "player armor is %s\n"
                + "player belt is %s\n"
                + "player legs is %s\n"
                + "player weapon is %s\n"
                + "player health is %d\n",getName(),getStrengthFactor(),getDefenceFactor(),item[0],item[1],item[2],item[3],item[4],getHealth());
    }
    /**
     * Method used to throw hit on your opponent.
     * @param weapon receives instance of class {@linkplain Weapon} defining with which weapon to perform the hit.
     * @param strFactor receives player's strength factor as part of the damage calculation.
     * @param attackArea receives {@linkplain Type} with defines desired attack area.
     * @param target receives instance of class {@linkplain Player} which is the target of the attack.
     */
    public void throwHit(Weapon weapon,int strFactor,Type attackArea,Player target)
    {        
        int damage = ( weapon != null )? weapon.getDamageValue() + strFactor : strFactor ;
        System.err.printf("Thrown Damage: %d\n",damage);
        target.getHit(damage,attackArea, weapon);
        
    }

    /**
     * Method called automatically when player is getting a hit.
     * @param damage receives integer value representing the damage calculated by opponent's weapon and strength factor.
     * @param attackArea receives {@linkplain Type} which is the area where you are getting hit.
     * @param weapon receives instance of {@linkplain Weapon} which is the weapon you are being hit with.
     */
    public void getHit(int damage,Type attackArea, Weapon weapon)
    {
        double rawDamage; // damage thrown by the attacker
        rawDamage = damage - damage*getDefenceFactor(); //subtract the defence factor (random for each match) from the damage received
        System.err.printf("Got Damage: %.1f\n",rawDamage);
        
        if (this.item[attackArea.getIndex()] != null)
                rawDamage -= rawDamage * ( (Armor) this.item[attackArea.getIndex()] ).getDefenceValue();
        
        
        if(attackArea == getDefenceArea()) //is area attacked the same as defended?
        {
            if((int)rawDamage/2 > getHealth()) 
                health = 0;
            else
                health -= (int)rawDamage/2;
            
            consoleText = String.format("[" + getCurrentTimeStamp() + "] %s has been hit with %s in the %s and successfully blocked the attack. Got %d points of damage!\n",
                this.getName(), ((weapon != null)? weapon.getName(): "bare hands") , attackArea.toString() ,(int) (rawDamage/2) );
            
            
        }else
        {
            if((int)rawDamage > getHealth()) 
                health = 0;
            else
                health -= (int)rawDamage;
            
            consoleText = String.format("[" + getCurrentTimeStamp() + "] %s has been hit with %s in the %s and couldn't properly block it. Got %d points of damage!\n",
                this.getName(), ((weapon != null)? weapon.getName(): "bare hands") , attackArea.toString() ,(int) rawDamage );
        }
        
        
        
    }
    /**
     * Method used to get player's icon displayed in shop.
     * @return {@linkplain Icon} object containing players image.
     */
    public Icon getPlayerIcon()
    {
        return shopIcon;
    }
    /**
     * Method used to get player's icon displayed in battle window.
     * @return {@linkplain Icon} object containing players image.
     */
    public Icon getBattleIcon()
    {
        return battleIcon;
    }
    /**
     *Method used to get player's desired attack area.
     *@return object of class {@linkplain Type}.
     */
     public Type getAttackArea()
    {
        return this.attackArea;
    }
    /**
     * Method used to set player's attack area.
     * @param area instance of {@linkplain Type}.
     */
    public void setAttackArea(Type area)
    {
        attackArea = area;
    }
    /**
     * Method used to get player's defense area.
     * @return {@linkplain Type} object representing player's defence area.
     */
    public Type getDefenceArea()
    {
        return defenceArea;
    }
    /**
     * Method used to set player's defense area.
     * @param area receives {@linkplain Type} that will be set as player's defense area.
     */
    public void setDefenceArea(Type area)
    {
        defenceArea = area;
    }

}
