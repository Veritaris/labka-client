package dependencies.Collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private long x;
    private long y;

    public Coordinates (long x, long y){
        this.x = x;
        this.y = y;
    }

    public long getX() { return x;}
    public long getY() { return y;}
}
