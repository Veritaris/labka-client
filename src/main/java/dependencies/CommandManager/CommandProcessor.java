package dependencies.CommandManager;

import Client.ScriptReader;
import dependencies.UserAuthorization.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("FieldCanBeLocal")
public class CommandProcessor {
    private final CommandObjectCreator commandObjectCreator = new CommandObjectCreator();
    private final Scanner consoleScanner = new Scanner(System.in);
    private List<String> commandArguments = new ArrayList<>();
    private CommandObject commandObject;
    private ScriptReader scriptReader;
    private List<String> commandArray;
    private String command;

    public CommandProcessor() {

    }

    public CommandObject readCommand(Session session) {
        System.out.print("> ");
        commandArray = new ArrayList<>(Arrays.asList(consoleScanner.nextLine().trim().split(" ")));

        if (commandArray.size() == 0) {
            System.out.println("No command passed!");
        }

        command = commandArray.get(0);
        commandArray.remove(0);
        commandArguments = commandArray;

        if (command.equals("execute_script")) {
            if (!session.getUser().isLoggedIn()) {
                return CommandObjectCreator.createErrorObject("AuthError", "You need to be authorized to use script command");
            }
            if (commandArguments.size() >= 1) {
                this.scriptReader.executeScript(commandArguments.get(0), session.getUser());
                commandObject = new CommandObject("scripted");
                commandObject.setScripted(true);
                return commandObject;
            } else {
                return CommandObjectCreator.createErrorObject(command, "Not enough arguments");
            }
        } else {
            commandObject = commandObjectCreator.create(command, commandArguments);
            return commandObject;
        }
    }

    public void setScriptReader(ScriptReader scriptReader) {
        this.scriptReader = scriptReader;
    }

    public void close(){
        this.consoleScanner.close();
    }
}
