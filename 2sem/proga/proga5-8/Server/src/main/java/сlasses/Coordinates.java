package сlasses;

/**
 * Кординаты
 */
public class Coordinates {
    private long x; //Значение поля должно быть больше -811, Поле не может быть null
    private float y; //Максимальное значение поля: 416

    public Coordinates(){

    }

    public Coordinates(Long x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return (String) ("(" + this.x + "," + this.y + ")");
    }

    public float getX() {
        return this.x;
    }
    public float getY(){
        return this.y;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
