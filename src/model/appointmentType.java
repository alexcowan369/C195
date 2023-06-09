package model;

/**
 * The class below creates the "appointmentType" objects.
 * @author Alexander Cowan
 */
public class appointmentType {
    private String Type;

    public appointmentType(String Type){
        this.Type = Type;
    }

    /**
     * @return the apponitmentType type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the apponitmentType type to set
     */
    public void setType(String Type) {
        Type = Type;
    }

    /**
     * @return the appointmentType String type
     */
    @Override
    public String toString(){return Type;}
}