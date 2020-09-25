package org.example.client.dependencies.Collection;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Random;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "SillyAssignment", "ConstantConditions", "SameParameterValue"})
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Long studentsCount;
    private Integer expelledStudentsAmount;
    private Integer toExpelAmount;
    private Semester semester;
    private Person groupAdmin;

    public StudyGroup (Long groupID, String name, Semester semester, Coordinates coordinates, Long studentsCount, Person groupAdmin, Integer toExpelAmount, Integer expelledStudentsAmount){
        this.id = groupID;
        this.name = name;
        this.semester = semester;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.groupAdmin = groupAdmin;
        this.toExpelAmount = toExpelAmount;
        this.expelledStudentsAmount = expelledStudentsAmount;
        creationDate = LocalDateTime.now();

    }

    @Override
    public int compareTo(StudyGroup studyGroup) {
        return Long.compare(this.id, studyGroup.id);
//        int result = this.name.compareTo(studyGroup.name);
//        if (result == 0){
//            if (this.studentsCount - studyGroup.studentsCount > 0) {
//                return 1;
//            } else if (this.studentsCount - studyGroup.studentsCount < 0) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//        else return result;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) {this.id = id;}
    public String getName() {return this.name;}
    public Coordinates getCoordinates() {return this.coordinates;}
    public Long getStudentsCount() {return  this.studentsCount;}
    public Integer getExpelledStudentsAmount() {return expelledStudentsAmount;}
    public Integer getToExpelAmount() {return toExpelAmount;}
    public Person getGroupAdmin() {return groupAdmin;}
    public Semester getSemester() {return semester;}
    public void setGroupAdmin() {this.groupAdmin = groupAdmin;}

    @Override
    public String toString() {
        return String.format(
                "%s (%s), semester: '%s', coordinates: (%s, %s), students amount: %s, students to expel amount: %s, " +
                "expelled students amount: %s, group admin: %s, admin's height: %s, admin's weight: %s, admin's motherland: %s. " +
                "Creation date: %s",
                this.name, this.id, this.semester.getTittle(), this.coordinates.getX(), this.coordinates.getY(),
                this.studentsCount, this.toExpelAmount, this.expelledStudentsAmount, groupAdmin.getName(),
                groupAdmin.getHeight(), groupAdmin.getWeight(), groupAdmin.getNationality().getTittle(), this.creationDate
        );
    }

    private long nextLong(Random rng, long n) {
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }
}
