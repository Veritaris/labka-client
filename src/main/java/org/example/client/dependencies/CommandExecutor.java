package org.example.client.dependencies;


import org.example.client.dependencies.Collection.*;
import java.util.*;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.*;
import org.json.simple.*;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "Convert2MethodRef", "unchecked", "SimplifyStreamApiCallChains"})
public class CommandExecutor {
    private static final Logger logger = LogManager.getLogger();

    private PriorityQueue<StudyGroup> groups = new PriorityQueue<>();
    private String[][] availableCommands = new String[16][1];
    private ArrayList<String> message = new ArrayList<>();
    public ArrayList<String> history = new ArrayList<>();
    private static CommandExecutor commandExecutor;
    private final String collectionsJSONFilePath;
    private File collectionsJSONFile;
    private Date creationDate;

    private ObjectMapper mapper = new ObjectMapper();
    private JSONObject groupJSONObject;
    private JSONArray jsonArray;
    private JSONParser parser;

    private Semester groupCurrentSemester;
    private Coordinates groupCoordinates;
    private int expelledStudentsAmount;
    private JSONObject coordinatesJson;
    private int studentsToExpelAmount;
    private Country adminNationality;
    private String semesterEnumValue;
    private String groupIdentifier;
    private String groupAdminName;
    private long studentsCount;
    private double adminHeight;
    private Person groupAdmin;
    private JSONObject admin;
    private long lastGroupID;
    private int adminWeight;
    private long groupID;
    private long xCord;
    private long yCord;

    public CommandExecutor(String collectionsJSONFilePath) {
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveCollection));

        try {
            if (collectionsJSONFilePath == null) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found (environment variable is empty!)");
            System.exit(1);
        }
        this.collectionsJSONFilePath = collectionsJSONFilePath;

        try {
            if ((new File(this.collectionsJSONFilePath)).exists()) {
                this.collectionsJSONFile = new File(this.collectionsJSONFilePath);
                loadCollections(this.collectionsJSONFile);
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            logger.error("File  not found!");
            System.exit(1);
        }

        creationDate = new Date();
        loadAvailableCommands();
    }

    public static CommandExecutor getInstance(String filepath){
        if (commandExecutor == null) {
            commandExecutor = new CommandExecutor(filepath);
        }
        return commandExecutor;
    }

    public void loadCollections(File collectionsJSONFile) {
        try {
            if (!collectionsJSONFile.canRead() || !collectionsJSONFile.canWrite()) {
                throw new SecurityException();
            }
        } catch (SecurityException e) {
            logger.error("File access denied");
            System.exit(1);
        }

        if (collectionsJSONFile.length() == 0) {
            logger.error("File is empty");
            return;
        }

        parser = new JSONParser();

        try (FileReader fileReader = new FileReader((this.collectionsJSONFilePath))){
            jsonArray = (JSONArray) parser.parse(fileReader);

            for (Object obj : jsonArray) {
                groupJSONObject = (JSONObject) obj;

                if (groupJSONObject != null) {
                    this.groups.add(constructStudyGroup(groupJSONObject));
                }
            }
            lastGroupID = (groups.size() > 1) ? groups.peek().getId() : 1;

        } catch (FileNotFoundException e) {
            logger.error("!File not found!");
            System.exit(1);

        } catch (NumberFormatException e) {
            logger.error("!Invalid argument format in file!");
            System.exit(1);

        } catch (IllegalArgumentException e) {
            logger.error("!Invalid string argument in file!");
            System.exit(1);

        } catch (IOException e){
            logger.error("!Input error!");
            System.exit(1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        sortGroups();
        logger.info("Collection uploaded.");
    }

    private void loadAvailableCommands() {
        try (Scanner in = new Scanner(new FileReader("file_commands.txt"))) {
            String str;
            int i = 0;
            while (in.hasNextLine()) {
                str = in.nextLine();
                availableCommands[i] = str.split(":");
                i++;
            }
        } catch (FileNotFoundException e) {
            logger.error("File with commands not found!");
            System.exit(1);
        }
    }

    private StudyGroup constructStudyGroup(JSONObject groupJSONObject) {
        studentsToExpelAmount = ((Long) groupJSONObject.get("shouldBeExpelled")).intValue();
        expelledStudentsAmount = ((Long) groupJSONObject.get("expelledStudents")).intValue();
        coordinatesJson = (JSONObject) groupJSONObject.get("coordinates");
        semesterEnumValue = (String) groupJSONObject.get("semester");
        studentsCount = (Long) groupJSONObject.get("studentsCount");
        groupIdentifier = (String) groupJSONObject.get("name");
        admin = (JSONObject) groupJSONObject.get("admin");
        groupID = (Long) groupJSONObject.get("id");

        xCord = (long) coordinatesJson.get("x");
        yCord = (long) coordinatesJson.get("y");

        adminNationality = Country.valueOf((String) admin.get("country"));
        groupAdminName = (String) admin.get("nameGroupAdmin");
        adminHeight = (double) admin.get("height");
        adminWeight = ((Long) admin.get("weight")).intValue();

        groupCurrentSemester = Semester.valueOf(semesterEnumValue);
        groupCoordinates = new Coordinates(xCord, yCord);

        groupAdmin = new Person(groupAdminName, adminHeight, adminWeight, adminNationality);

        return new StudyGroup(groupID, groupIdentifier, groupCurrentSemester, groupCoordinates, studentsCount, groupAdmin, studentsToExpelAmount, expelledStudentsAmount);
    }

    public void clearMessage() {
        message.clear();
    }

    public void sortGroups() {
        PriorityQueue<StudyGroup> groupPriorityQueue = new PriorityQueue<>();
        groups.stream().sorted().forEachOrdered(r -> groupPriorityQueue.add(r));
        groups = groupPriorityQueue;
    }

    public ArrayList<String> help() {
        message.clear();
        for (int i = 0; i<15; i++) {
            message.add(String.format("* %s - %s", availableCommands[i][0], availableCommands[i][1]));
        }
        return message;
    }

    public ArrayList<String> info() {
        message.clear();
        message.add(
                String.format("Collection type: %s\n", groups.getClass().getName()) +
                String.format("Creation date: %s\n", creationDate) +
                String.format("Amount of elements: %s", groups.size())
        );
        return message;
    }

    public ArrayList<String> show() {
        message.clear();
        groups.stream().forEachOrdered((p) -> message.add(p.toString()));
        return message;
    }

    public ArrayList<String> add(StudyGroup studyGroup) {
        studyGroup.setId(this.lastGroupID++);
        groups.add(studyGroup);
        sortGroups();
        message.add("Element added.");
        return message;
    }

    public ArrayList<String> update(StudyGroup group) {
        message.clear();
        PriorityQueue<StudyGroup> buf = new PriorityQueue<>();

        if (groups.stream().anyMatch(r -> r.getId().equals(group.getId()))){
            for (StudyGroup studyGroup : groups) {
                if (!studyGroup.getId().equals(group.getId())) {
                    buf.add(studyGroup);
                } else {
                    buf.add(group);
                }
            }

            groups = buf;
            sortGroups();
            message.add("Element updated (or added)");
        } else {
            message.add("Element with given id doesn't exist!");
        }
        return message;
    }

    public ArrayList<String> remove_by_id(Long id) {
        message.clear();
        boolean foundFlag = false;
        for (StudyGroup studyGroup : groups) {
            if (studyGroup.getId().equals(id)) {
                groups.remove(studyGroup);
                foundFlag = true;
                message.add("Element removed.");
                break;
            }
        }
        if (!foundFlag) {
            message.add("Element with given id doesn't exist!");
        }
        return message;
    }

    public ArrayList<String> clearCollection() {
        message.clear();
        groups.clear();
        message.add("Collection cleared.");
        return message;
    }

    private void saveCollection() {
        try {

            JSONArray groupCollection = new JSONArray();

            for (StudyGroup studyGroup : groups) {
                JSONObject group = new JSONObject();
                JSONObject coordinatesCollections = new JSONObject();
                JSONObject adminCollection = new JSONObject();

                group.put("id", studyGroup.getId());

                group.put("studentsCount", studyGroup.getStudentsCount());
                group.put("name", studyGroup.getName());

                coordinatesCollections.put("x", studyGroup.getCoordinates().getX());
                coordinatesCollections.put("y", studyGroup.getCoordinates().getY());

                group.put("coordinates", coordinatesCollections);
                group.put("expelledStudents", studyGroup.getExpelledStudentsAmount());
                group.put("shouldBeExpelled", studyGroup.getToExpelAmount());
                group.put("semester", String.format("%s", studyGroup.getSemester()));

                adminCollection.put("nameGroupAdmin", studyGroup.getGroupAdmin().getName());
                adminCollection.put("height", studyGroup.getGroupAdmin().getHeight());
                adminCollection.put("weight", studyGroup.getGroupAdmin().getWeight());
                adminCollection.put("country", String.format("%s", studyGroup.getGroupAdmin().getNationality()));

                group.put("admin", adminCollection);

                groupCollection.add(group);
            }

            String prettifiedJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groupCollection);

            FileWriter fileWriter = new FileWriter(this.collectionsJSONFilePath);
            fileWriter.write(prettifiedJSON);
            fileWriter.flush();

        } catch (FileNotFoundException e){
            logger.error("File not found");
        } catch (IOException e) {
            logger.error("Output error");
        }
        logger.info("Collection saved.");
    }

    public ArrayList<String> exit() {
        message.clear();
        saveCollection();
        history.clear();
        message.add("Completion of work...");

        return message;
    }

    public ArrayList<String> remove_first() {
        message.clear();
        try {
            if (groups.isEmpty()) {
                throw new NoSuchElementException();
            }
            groups.poll();
            message.add("Element removed.");
        } catch (NoSuchElementException e) {
            message.add("Collection is empty!");
        }
        return message;
    }

    public ArrayList<String> head() {
        message.clear();
        try {
            if (groups.isEmpty()) {
                throw new NoSuchElementException();
            }
            message.add(String.format("Element is output: %s", groups.peek()));
        } catch (NoSuchElementException e) {
            message.add("Collection is empty!");
        }
        return message;
    }

    public ArrayList<String> getHistory() {
        return this.history;
    }

    public void addToHistory(String commandName) {
        if (this.history.size() >= 7) {
            this.history.remove(0);
        }
        this.history.add(commandName);
    }

    public ArrayList<String> filter_contains_name(String str) {
        message.clear();
        groups.stream().filter(
                studyGroup -> studyGroup.getName().contains(str)).forEachOrdered(
                        studyGroup -> message.add(studyGroup.toString())
        );

        if (message.isEmpty()) {
            message.add("No matches found!");
        }
        return message;
    }

    public ArrayList<String> max_by_expelled_students() {
        message.clear();
        try {
            if (groups.isEmpty()) {
                throw new NoSuchElementException();
            }

            Integer max0 = 0;
            StudyGroup studyGroupMax = null;

            for (StudyGroup studyGroup: groups) {
                if (studyGroup.getExpelledStudentsAmount()>max0) {
                    max0 = studyGroup.getExpelledStudentsAmount();
                    studyGroupMax = studyGroup;
                }
            }
            assert studyGroupMax != null;
            message.add(studyGroupMax.toString());
        } catch (NoSuchElementException e) {
            message.add("Collection is empty!");
        }
        return message;
    }

    public ArrayList<String> filter_by_group_admin(String fieldsGroupAdmin) {
        message.clear();

        boolean foundFlag = false;
        String[] adminArray = fieldsGroupAdmin.split(";");

        String nameA = adminArray[0];
        double heightA = Double.parseDouble(adminArray[1]);
        int weightA = Integer.parseInt(adminArray[2]);
        Country countryA = Country.valueOf(adminArray[3]);

        Person groupAdmin = new Person(nameA, heightA, weightA, countryA);

        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getGroupAdmin().equals(groupAdmin)) {
                message.add(studyGroup.toString());
                foundFlag = true;
            }
        }

        if (!foundFlag) {
            message.add("No matches found!");
        }

        return message;
    }
}
