package dependencies.Collection;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

public class Person implements Serializable {
    private final Country nationality;
    private final double height;
    private final String name;
    private final int weight;

    public Person (String name, double height, int weight, Country nationality){
        this.name = name;
        this.height = Math.round(height * 10) / 10.0;
        this.weight = weight;
        this.nationality = nationality;
    }

    public String getName() { return name; }
    public double getHeight() { return height; }
    public int getWeight() { return weight; }
    public Country getNationality() { return nationality; }

    public String getPersonHash() {
        return DigestUtils.md5Hex(String.format("%s%s%s%s", this.nationality, this.height, this.weight, this.name));
    }

    @Override
    public String toString() {
        return String.format(
                "Person<name='%s', height='%s', weight='%s', nationality='%s'>",
                this.getName(), this.getHeight(), this.getWeight(), this.getNationality()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Person groupAdmin = (Person) object;
        return name.equals(groupAdmin.name) &&
                height == groupAdmin.height &&
                weight == groupAdmin.weight &&
                nationality.equals(groupAdmin.nationality);
    }
}
