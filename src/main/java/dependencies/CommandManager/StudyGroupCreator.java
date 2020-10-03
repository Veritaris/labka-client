package dependencies.CommandManager;

import dependencies.Collection.*;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({"DuplicatedCode", "BooleanMethodIsAlwaysInverted"})
public class StudyGroupCreator {
    public StudyGroup constructor(Long id) {
        while (true) {
            try {
                Scanner field = new Scanner(System.in);
                System.out.println("Input count of students");
                boolean flag = true;
                String studentsCountString = field.nextLine();

                while (flag) {
                    if (studentsCountString.trim().length()==0) throw new InputMismatchException();
                    if (!isDigit(studentsCountString)) {
                        System.out.println("! Count of students must be a number !");
                        studentsCountString = field.nextLine();
                    } else {
                        if (Long.parseLong(studentsCountString)<=0) {
                            System.out.println("! Number must be > 0, try again !");
                            studentsCountString = field.nextLine();
                        } else flag = false;
                    }
                }
                int studentsCount = Integer.parseInt(studentsCountString);
                System.out.println("Input group's name");
                String name = field.nextLine();

                if (name.trim().length()==0) {
                    System.out.println("! Empty string entered !");
                    throw new InputMismatchException();
                }
                flag = true;
                System.out.println("Input x coordinate:");
                String xString = field.nextLine();

                while (flag) {
                    if (xString.trim().length() == 0) throw new InputMismatchException();
                    if (!isDigit(xString)) {
                        System.out.println("! x must be a number !");
                        xString = field.nextLine();
                    } else {
                        if (Integer.parseInt(xString) <= -18) {
                            System.out.println("! x must be > -18, try again !");
                            xString = field.nextLine();
                        } else flag = false;
                    }
                }
                flag = true;
                int x = Integer.parseInt(xString);
                System.out.println("Input y coordinate:");
                String yString = field.nextLine();

                while (flag) {
                    if (yString.trim().length()==0) throw new InputMismatchException();
                    if (!isDigit(yString)){
                        System.out.println("! y must be a number !");
                        yString = field.nextLine();
                    } else{
                        if (Long.parseLong(yString)>950){
                            System.out.println("! y must be <= 950, try again !");
                            yString = field.nextLine();
                        } else flag = false;
                    }
                }
                int y = Integer.parseInt(yString);
                System.out.println("Input amount of expelled students:");
                String expelledStudentsString = field.nextLine();
                flag = true;
                while (flag) {
                    if (expelledStudentsString.trim().length()==0) throw new InputMismatchException();
                    if (!isDigit(expelledStudentsString)){
                        System.out.println("! Amount of students must be a number !");
                        expelledStudentsString = field.nextLine();
                    } else{
                        if (Integer.parseInt(expelledStudentsString)<=0){
                            System.out.println("! Number must be > 0, try again !");
                            expelledStudentsString = field.nextLine();
                        } else flag = false;
                    }
                }
                Integer expelledStudents = Integer.parseInt(expelledStudentsString);
                flag = true;
                System.out.println("Input amount of should be expelled students:");
                String shouldBeExpelledStudentsString = field.nextLine();
                while (flag){
                    if (shouldBeExpelledStudentsString.trim().length()==0) throw new InputMismatchException();
                    if (!isDigit(shouldBeExpelledStudentsString)){
                        System.out.println("! Amount of students must be a number !");
                        shouldBeExpelledStudentsString = field.nextLine();
                    } else{
                        if (Integer.parseInt(shouldBeExpelledStudentsString)<=0){
                            System.out.println("! Number must be > 0, try again !");
                            shouldBeExpelledStudentsString = field.nextLine();
                        } else flag = false;
                    }
                }
                flag = true;
                Integer shouldBeExpelled = Integer.parseInt(shouldBeExpelledStudentsString);
                System.out.println("Choose and input semester of group: " + Arrays.toString(Semester.values()));
                String semesterEnumValue = field.nextLine();
                while (flag){
                    if (semesterEnumValue.trim().length()==0) throw new InputMismatchException();
                    if (!containsSemester(semesterEnumValue)){
                        System.out.println("! Choose one of these semesters !");
                        semesterEnumValue = field.nextLine();
                    } else flag = false;
                }
                Semester semester = Semester.valueOf(semesterEnumValue);
                System.out.println("!Input admin's name:");
                String nameGroupAdmin = field.nextLine();
                if (nameGroupAdmin.trim().length()==0){
                    System.out.println("!Empty string entered!");
                    throw new InputMismatchException();
                }
                flag = true;
                System.out.println("!Input admin's height:");
                String  heightString = field.nextLine();
                while (flag){
                    if (heightString.trim().length()==0) throw new InputMismatchException();
                    if (!isDigitFloat(heightString)){
                        System.out.println("! Input number !");
                        heightString = field.nextLine();
                    } else{
                        if (Float.parseFloat(heightString)<=0){
                            System.out.println("! height must be > 0 !");
                            heightString = field.nextLine();
                        } else flag = false;
                    }
                }
                float height = Float.parseFloat(heightString);
                System.out.println("!Input admin's weight:");
                flag = true;
                String weightString = field.nextLine();
                while (flag){
                    if (weightString.trim().length()==0) throw new InputMismatchException();
                    if (!isDigit(weightString)){
                        System.out.println("! Input number !");
                        weightString = field.nextLine();
                    } else if (Integer.parseInt(weightString)<=0){
                        System.out.println("! Weight must be > 0 !");
                        weightString = field.nextLine();
                    } else flag = false;
                }
                int weight = Integer.parseInt(weightString);
                System.out.println("!Choose and input admin's country: " + Arrays.toString(Country.values()));
                flag = true;
                String country = field.nextLine();
                while (flag){
                    if (country.trim().length()==0) throw new InputMismatchException();
                    if (!containsCountry(country)){
                        System.out.println("! Choose one of these country !");
                        country = field.nextLine();
                    } else flag = false;
                }
                Country nationality = Country.valueOf(country);
                Coordinates coordinates = new Coordinates(x, y);
                Person groupAdmin = new Person(nameGroupAdmin, height, weight, nationality);
                if (id != 0){
                    return new StudyGroup(id, name, semester, coordinates, studentsCount, groupAdmin, shouldBeExpelled, expelledStudents);
                } else {
                    return new StudyGroup(id, name, semester, coordinates, studentsCount, groupAdmin, shouldBeExpelled, expelledStudents);
                }
            } catch  (InputMismatchException e){
                System.out.println("! Input error !");
            }catch (IllegalArgumentException e){
                System.out.println("! Wrong format semester or country !");
            }
        }
    }

    private boolean isDigit(String s) throws NumberFormatException{
        try{
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isDigitFloat(String s) throws NumberFormatException{
        try{
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }


    private boolean containsSemester(String str) {

        for (Semester sem : Semester.values()) {
            if (sem.name().equals(str)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsCountry(String str) {

        for (Country country : Country.values()) {
            if (country.name().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public StudyGroup constructor(Long id, String groupName, String groupCurrentSemester, String xCoordinate, String yCoordinate, String studentsAmount, String adminName,
                                  String adminHeight, String adminWeight, String adminNation, String studentsToExpelAmount, String expelledStudentsAmount) {
        boolean errorFlag = false;
        
        Country adminNationality = null;
        Coordinates coordinates;
        float adminHeightFloat = 0;
        int shouldBeExpelled = 0;
        int expelledStudents = 0;
        int adminWeightFloat = 0;
        int studentsCount = 0;
        Semester semester = null;
        Person groupAdmin;
        int x = 0;
        int y = 0;

        if (!isDigit(studentsAmount)) {
            System.out.printf("\t[ScriptExecutorError] Students amount must be a number, got '%s'\n", studentsAmount);
            errorFlag = true;
        } else {
            if (Long.parseLong(studentsAmount) <= 0) {
                System.out.printf("\t[ScriptExecutorError] Students amount must be > 0, got '%s'\n", studentsAmount);
                errorFlag = true;
            } else {
                studentsCount = Integer.parseInt(studentsAmount);
            }
        }

        if (!isDigit(xCoordinate)) {
            System.out.printf("\t[ScriptExecutorError] xCoordinate must be a number, got '%s'\n", xCoordinate);
            errorFlag = true;
        } else {
            if (Integer.parseInt(xCoordinate) <= -18) {
                System.out.printf("\t[ScriptExecutorError] xCoordinate must be > -18, got '%s'\n", xCoordinate);
                errorFlag = true;
            } else {
                x = Integer.parseInt(xCoordinate);
            }
        }

        if (!isDigit(yCoordinate)){
            System.out.printf("\t[ScriptExecutorError] yCoordinate must be a number, got '%s'\n", yCoordinate);
            errorFlag = true;
        } else{
            if (Long.parseLong(yCoordinate) > 950){
                System.out.printf("\t[ScriptExecutorError] yCoordinate must be <= 950, got '%s'\n", yCoordinate);
                errorFlag = true;
            } else {
                y = Integer.parseInt(yCoordinate);
            }
        }

        if (expelledStudentsAmount.trim().length() == 0) {
            throw new InputMismatchException();
        }

        if (!isDigit(expelledStudentsAmount)){
            System.out.printf("\t[ScriptExecutorError] Students amount must be a number, got '%s'\n", expelledStudentsAmount);
            errorFlag = true;
        } else{
            if (Integer.parseInt(expelledStudentsAmount)<=0){
                System.out.printf("\t[ScriptExecutorError] Students amount must be > 0, got '%s'\n", expelledStudentsAmount);
                errorFlag = true;
            } else {
                expelledStudents = Integer.parseInt(expelledStudentsAmount);
            }
        }

        if (!isDigit(studentsToExpelAmount)){
            System.out.printf("\t[ScriptExecutorError] Students amount must be a number, got '%s'\n", studentsToExpelAmount);
            errorFlag = true;
        } else {
            if (Integer.parseInt(studentsToExpelAmount) <= 0){
                System.out.printf("\t[ScriptExecutorError] Students amount must be > 0, got '%s'\n", studentsToExpelAmount);
                errorFlag = true;
            } else {
                shouldBeExpelled = Integer.parseInt(studentsToExpelAmount);
            }
        }

        if (!containsSemester(groupCurrentSemester)){
            System.out.printf("\t[ScriptExecutorError] Semester must be one of this values: [FIRST, SECOND, FOURTH, FIFTH, EIGHTH], got '%s'\n", groupCurrentSemester);
            errorFlag = true;
        } else {
            semester = Semester.valueOf(groupCurrentSemester);
        }

        if (adminName.trim().length() == 0){
            System.out.printf("\t[ScriptExecutorError] Admin name cannot be empty, got '%s'\n", adminName);
            throw new InputMismatchException();
        }

            if (!isDigitFloat(adminHeight)){
                System.out.printf("\t[ScriptExecutorError] Admin height must be a number, got '%s'\n", adminHeight);
                errorFlag = true;
            } else{
                if (Float.parseFloat(adminHeight) <= 0){
                    System.out.printf("\t[ScriptExecutorError] Admin height must be > 0, got '%s'\n", adminHeight);
                    errorFlag = true;
                } else {
                    adminHeightFloat = Float.parseFloat(adminHeight);
                }
            }

        if (adminWeight.trim().length() == 0) {
            throw new InputMismatchException();
        }
        if (!isDigit(adminWeight)){
            System.out.printf("\t[ScriptExecutorError] Admin weight must be a number, got '%s'\n", adminWeight);
            errorFlag = true;
        } else if (Integer.parseInt(adminWeight) <= 0){
            System.out.printf("\t[ScriptExecutorError] Admin weight must be > 0, got '%s'\n", adminWeight);
            errorFlag = true;
        } else {
            adminWeightFloat = Integer.parseInt(adminWeight);
        }


        if (!containsCountry(adminNation)){
            System.out.printf("[ScriptExecutorError] Country must be one of this values: [GERMANY, FRANCE, INDIA, VATICAN, SOUTH_KOREA], got '%s'\n", adminNation);
            errorFlag = true;
        } else {
            adminNationality = Country.valueOf(adminNation);
        }

        if (!errorFlag) {
            coordinates = new Coordinates(x, y);
            groupAdmin = new Person(adminName, adminHeightFloat, adminWeightFloat, adminNationality);

            if (id != 0) {
                StudyGroup studyGroup = new StudyGroup(id, groupName, semester, coordinates, studentsCount, groupAdmin, shouldBeExpelled, expelledStudents);
                studyGroup.setId(id);
                return studyGroup;
            } else {
                return new StudyGroup(id, groupName, semester, coordinates, studentsCount, groupAdmin, shouldBeExpelled, expelledStudents);
            }
        } else {
            return null;
        }
    }
}
