package dependencies.Collection;

import java.time.LocalDate;
import java.io.Serializable;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "SillyAssignment", "ConstantConditions", "SameParameterValue"})
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private int studentsCount;
    private Integer expelledStudentsAmount;
    private Integer toExpelAmount;
    private Semester semester;
    private Person groupAdmin;

    public StudyGroup (Long groupID, String name, Semester semester, Coordinates coordinates, int studentsCount, Person groupAdmin, Integer toExpelAmount, Integer expelledStudentsAmount){
        this.id = groupID;
        this.name = name;
        this.semester = semester;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.groupAdmin = groupAdmin;
        this.toExpelAmount = toExpelAmount;
        this.expelledStudentsAmount = expelledStudentsAmount;
        this.creationDate = LocalDate.now();
    }

    public StudyGroup (Long groupID, String name, Semester semester, Coordinates coordinates, int studentsCount, Person groupAdmin, Integer toExpelAmount, Integer expelledStudentsAmount, LocalDate creationDate){
        this.id = groupID;
        this.name = name;
        this.semester = semester;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.groupAdmin = groupAdmin;
        this.toExpelAmount = toExpelAmount;
        this.expelledStudentsAmount = expelledStudentsAmount;
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(StudyGroup studyGroup) {
        return Long.compare(this.id, studyGroup.id);
    }

    public boolean equals(StudyGroup studyGroup) {
        return (this.name.equals(studyGroup.name) &&
                this.semester.equals(studyGroup.semester) &&
                this.coordinates.equals(studyGroup.coordinates) &&
                (this.studentsCount == studyGroup.studentsCount) &&
                (this.groupAdmin == studyGroup.groupAdmin) &&
                this.toExpelAmount.equals(studyGroup.toExpelAmount) &&
                this.expelledStudentsAmount.equals(studyGroup.expelledStudentsAmount));
    }

    public Long getId() { return this.id; }
    public void setId(Long id) {this.id = id;}
    public String getName() {return this.name;}
    public Coordinates getCoordinates() {return this.coordinates;}
    public int getStudentsCount() {return  this.studentsCount;}
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
}
