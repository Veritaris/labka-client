package dependencies.Collection;

import java.io.Serializable;

public enum Semester implements Serializable {
    FIRST("First semester"),
    SECOND("Second semester"),
    FOURTH("Fourth semester"),
    FIFTH("Fifth semester"),
    EIGHTH("Eighth semester");

    public String tittle;
    Semester(String tittle) { this.tittle = tittle; }
    public String getTittle() { return tittle; }
}
