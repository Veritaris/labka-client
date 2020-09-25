package org.example.client.dependencies;

import org.example.client.dependencies.Commands.*;
import org.example.client.Client.ScriptReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("FieldCanBeLocal")
public class CommandProcessor {
    private final CommandObjectCreator commandObjectCreator = new CommandObjectCreator();
    private final Scanner consoleScanner = new Scanner(System.in);
    private List<String> commandArguments = new ArrayList<>();
    private CommandObject commandObjectToSend;
    private CommandExecutor commandExecutor;
    private CommandObject commandObject;
    private ScriptReader scriptReader;
    private List<String> commandArray;
    private String collectionsPath;
    private String command;

    public CommandProcessor() {

    }

    public CommandObject readCommand() {
        System.out.print("> ");
        commandArray = new ArrayList<>(Arrays.asList(consoleScanner.nextLine().trim().split(" ")));

        if (commandArray.size() == 0) {
            System.out.println("No command passed!");
        }

        command = commandArray.get(0);
        commandArray.remove(0);
        commandArguments = commandArray;

        if (command.equals("execute_script")) {
            if (commandArguments.size() >= 1) {
                this.scriptReader.executeScript(commandArguments.get(0));
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

    public CommandObject processCommand(CommandObject receivedCommandObject) {
        receivedCommandObject.clearMessage();
        commandObjectToSend = new CommandObject(receivedCommandObject.getName());

        switch (receivedCommandObject.getName()) {
            case "add":
                commandObjectToSend.setMessage(
                        (new AddCommand(receivedCommandObject.getName(), receivedCommandObject.getStudyGroup())).execute()
                );
                break;

            case "clear":
                commandObjectToSend.setMessage(
                        (new ClearCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "exit":
                commandObjectToSend.setMessage(
                        (new ExitCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "filter_by_group_admin":
                commandObjectToSend.setMessage(
                        (new FilterByGroupAdminCommand(
                                receivedCommandObject.getName(),
                                receivedCommandObject.getStringArgument())
                        ).execute()
                );
                break;

            case "filter_contains_name":
                commandObjectToSend.setMessage(
                        (new FilterContainsNameCommand(
                                receivedCommandObject.getName(),
                                receivedCommandObject.getStringArgument())
                        ).execute()
                );
                break;

            case "head":
                commandObjectToSend.setMessage(
                        (new HeadCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "help":
                commandObjectToSend.setMessage(
                        (new HelpCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "history":
                commandObjectToSend.setMessage(
                        (new HistoryCommand(receivedCommandObject.getName(), this.commandExecutor)).execute()
                );
                break;

            case "info":
                commandObjectToSend.setMessage(
                        (new InfoCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "max_by_expelled_students":
                commandObjectToSend.setMessage(
                        (new MaxByExpelledStudentsCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "remove_by_id":
                commandObjectToSend.setMessage(
                        (new RemoveByIdCommand(receivedCommandObject.getName(), receivedCommandObject.getLongArgument())).execute()
                );
                break;

            case "remove_first":
                commandObjectToSend.setMessage(
                        (new RemoveFirstCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "show":
                commandObjectToSend.setMessage(
                        (new ShowCommand(receivedCommandObject.getName())).execute()
                );
                break;

            case "update":
                commandObjectToSend.setMessage(
                        (new UpdateCommand(receivedCommandObject.getName(), receivedCommandObject.getStudyGroup())).execute()
                );
                break;

            default:
                System.out.println("!Something went wrong in Commander!");
        }
        this.commandExecutor.addToHistory(receivedCommandObject.getName());
        return commandObjectToSend;
    }

    public void setCommandExecutor(String collectionsFilePath) {
        this.collectionsPath = collectionsFilePath;
        this.commandExecutor = new CommandExecutor(this.collectionsPath);
    }

    public void setScriptReader(ScriptReader scriptReader) {
        this.scriptReader = scriptReader;
    }

    public void close(){
        this.consoleScanner.close();
    }
}
