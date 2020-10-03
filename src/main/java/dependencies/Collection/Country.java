package dependencies.Collection;

import java.io.Serializable;

public enum Country implements Serializable {
    GERMANY("Germany"),
    FRANCE("France"),
    INDIA("India"),
    VATICAN("Vatican"),
    SOUTH_KOREA("South Korea");

    public String tittle;

    Country(String tittle) {
        this.tittle = tittle;
    }

    public String getTittle() {
        return tittle;
    }
}
