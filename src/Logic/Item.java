package Logic;


import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.Icon;
/**
 * <h2>Abstract class that defines item objects in this game.</h2>
 * This class describes basic parameters and methods of each wearable item in the game.
 * @author Dima Ryskin
 * @author Bogdan Kirylyuk
 *
 */
public abstract class Item implements Serializable,Comparable
{
	/**
	 * String variable used as item name.
	 */
    private String name;
    /**
     * {@linkplain Icon} variable that holds reference to small colorful icon of the current item.
     */
    private Icon smallColor;
    /**
     * {@linkplain Icon} variable that holds reference to small black-white icon of the current item.
     */
    private Icon smallBW;
    /**
     * {@linkplain Icon} variable that holds reference to big colorful icon of the current item.
     */
    private Icon big;
    /**
     * Boolean variable defines whether this item is currently unlocked and available to wear.
     */
    private boolean available;
    /**
     * Boolean variable defines whether current item is already in use or not.
     */
    public boolean inUse = false;
    /**
     * <h2>Abstract method used to get current item {@linkplain Type}</h2>Because {@linkplain Item} is abstract
     * and can't have defined type this method will be implemented in derived classes.
     * @return instance of class {@linkplain Type} that is a enum type that represents item type.
     */
    public abstract Type getItemType();
    /**
     * <h2>Abstract method used to set current item {@linkplain Type}</h2>Because {@linkplain Item} is abstract
     * and can't have defined type this method will be implemented in derived classes.
     * @param instance of class {@linkplain Type} that is a enum type that represents item type.
     */
    public abstract void setItemType(Type value);
    /**
     * The constructor of the class used to initialize all the fields of the object.
     * @param name receives String variable represents item's name.
     * @param available receives boolean variable defining whether current item is unlocked.
     * @param itemFileName is a String variable represents current item's icon file name.
     */
    public Item(String name,boolean available,String itemFileName) //disabled PRICE
    {
        String smallColorIcon = "images/"+itemFileName+"_Small_Col.jpg";
        String smallBWIcon= "images/"+itemFileName+"_Small_BW.jpg";
        String bigIcon= "images/"+itemFileName+"_Big.jpg";
        //setPrice(price);  //disabled PRICE
        setName(name);
        setSmallColorIcon(new ImageIcon(getClass().getResource(smallColorIcon)));
        setSmallBWIcon(new ImageIcon(getClass().getResource(smallBWIcon)));
        setBigIcon(new ImageIcon(getClass().getResource(bigIcon)));
        setAvalible(available);
    }

    @Override
    public int compareTo(Object t)
    {
        return this.getItemType().getIndex()-((Item) t).getItemType().getIndex();
    }
    /**
     * Method used to get small icon of the item whether its unlocked or not.
     * @return {@linkplain Icon} object that holds item's image.
     */
    public Icon getSmallIcon()
    {
        if ( available )
        {
            return getSmallColorIcon();
        } 
        else
        {
            return getSmallBWIcon();
        }
    }
    /**
     * Method used to get item's availability.
     * @return boolean expression defining whether the item is available or not.
     */
    public boolean isAvalible()
    {
        return available;
    }
    /**
     * Method used to set item's availability.
     * @param val boolean variable defines whether the item is available or not.
     */
    public void setAvalible(boolean val)
    {
        this.available = val;
    }
   
    private Icon getSmallColorIcon()
    {
        Icon redIcon = new ImageIcon(getClass().getResource("images/default_small_image.jpg"));
        if (inUse)
        {
            return redIcon;
        }
        else
        return smallColor;
    }
    
    private Icon getSmallBWIcon()
    {
        return smallBW;
    }
    /**
     * Method used to get item's big icon.
     * @return {@linkplain Icon} object containing item's image.
     */
    public Icon getBigIcon()
    {

            return big;
    }
    /**
     * Method used to set item's small color icon.
     * @param val receives instance of {@linkplain Icon} containing item's image.
     */
    public void setSmallColorIcon(Icon val)
    {
        this.smallColor = val;
    }
    /**
     * Method used to set item's small black-white icon.
     * @param val receives instance of {@linkplain Icon} containing item's image.
     */
    public void setSmallBWIcon(Icon val)
    {
        this.smallBW = val;
    }
    /**
     * Method used to set item's big icon.
     * @param val receives instance of {@linkplain Icon} containing item's image.
     */    
    public void setBigIcon(Icon val)
    {
        
        this.big = val;
    }
    /**
     * Method used to get item's name.
     * @return String parameter containing item's name. 
     */
    public String getName()
    {
        return name;
    }
    /**
     * Method used to set item's name.
     * @param val receives String variable containing items desired name.
     */
    public void setName(String val)
    {
        this.name = val;
    }
    
    /**
     * Method same as getName()
     * @return string variable holding item's name.
     */
    public String toString()
    {
        return getName();
    }
}
