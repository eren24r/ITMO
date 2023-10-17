package сlasses;

/**
 * Кординаты
 */
public class Coordinates {
    private Long x; //Значение поля должно быть больше -811, Поле не может быть null
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

    public Long getX() {
        return this.x;
    }
    public float getY(){
        return this.y;
    }
}
