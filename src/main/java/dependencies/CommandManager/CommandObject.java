package dependencies.CommandManager;


import dependencies.Collection.StudyGroup;
import dependencies.UserAuthorization.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("UnusedReturnValue")
public class CommandObject implements Serializable {

    private ArrayList<String> message;
    private StudyGroup studyGroup;
    private String stringArgument;
    private Long groupID;

    private User user = new User("", "");

    private boolean isScripted = false;
    private boolean isFailed = false;
    private String failReason;
    private String name;
    private static final long serialVersionUID = 8599384652358861241L;

    public CommandObject(String name, StudyGroup studyGroup){
        this.name = name;
        this.studyGroup = studyGroup;
    }

    public CommandObject(String name, Long groupID){
        this.name = name;
        this.groupID = groupID;
    }

    public CommandObject(String name, String stringArgument){
        this.name = name;
        this.stringArgument = stringArgument;
    }

    public CommandObject(String name){
        this.name = name;
    }

    public CommandObject(String command, String username, String rawPassword) {
        this.name = command;
        this.user = new User(username, rawPassword);
    }

    public void setMessage(ArrayList<String> message){
        this.message = message;
    }

    public ArrayList<String> getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }

    public StudyGroup getStudyGroup() {
        return this.studyGroup;
    }

    public Long getGroupID() {
        return this.groupID;
    }

    public String getStringArgument() {
        return this.stringArgument;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFailed() {
        return this.isFailed;
    }

    public void setIsFailed(boolean isFailed) {
        this.isFailed = isFailed;
    }

    public String getFailReason() {
        return this.failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public void clearMessage() {
        if (this.message != null) {
            this.message = new ArrayList<>();
        }
    }

    public void setSender(User user) {
        this.user = user;
    }

    public User getSender() {
        return this.user;
    }

    @Override
    public String toString(){
        return String.format("CommandObject<" +
                "command=\"%s\"", getName()) +
                ((getStudyGroup() != null) ? String.format(", firstArgument='%s'", getStudyGroup()) : "") +
                ((getGroupID() != null) ? String.format(", secondArgument='%s'", getGroupID()) : "") +
                ((getStringArgument() != null) ? String.format(", thirdArgument='%s'", getStringArgument()) : "") +
                ((getMessage() != null) ? String.format(", message='%s'", getMessage()) : "") +
                (String.format(", %s", getSender().toString())) +
                ">";

    }

    public boolean isScripted() {
        return this.isScripted;
    }

    public void setScripted(boolean scripted) {
        this.isScripted = scripted;
    }

    public CommandObject setStringMessage(String message) {
        this.message = new ArrayList<>(Collections.singleton(message));
        return this;
    }
}
