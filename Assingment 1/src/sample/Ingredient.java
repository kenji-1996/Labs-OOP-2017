package sample;
/**
 * Created by krogers on 18/08/2017.
 */

public class Ingredient {

    private int ID;
    private String name;
    private double quantity;
    private String unitsAndStyle;
    private boolean deleted = false;

    public Ingredient()
    {
        this.ID = -1;
        this.name = "no name";
        this.quantity = 0;
        this.unitsAndStyle = "";
        this.deleted = false;
    }

    public Ingredient(int ID, String name, double quantity, String unitsAndStyle)
    {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.unitsAndStyle = unitsAndStyle;
        this.deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public void setUnitsAndStyle(String unitsAndStyle) {
        this.unitsAndStyle = unitsAndStyle;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public double getQuantity() {
        return quantity;
    }
    public String getUnitsAndStyle() {
        return unitsAndStyle;
    }

    @Override
    public String toString() {
        return quantity + " " + unitsAndStyle + " " + name;
    }
    public String toString(String indent) {
        return indent + quantity + " " + unitsAndStyle + " " + name;
    }
}
