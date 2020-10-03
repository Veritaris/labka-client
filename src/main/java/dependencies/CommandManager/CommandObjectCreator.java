package dependencies.CommandManager;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public class CommandObjectCreator {
    private final StudyGroupCreator studyGroupCreator = new StudyGroupCreator();
    private static CommandObject commandObject;
    private String creationError = "";
    private Long groupID;
    private final List<String > availableCommands = Arrays.asList(
            "clear", "exit", "head", "help", "info", "history", "show",
            "remove_first", "max_by_expelled_students", "add",
            "filter_contains_name", "filter_by_group_admin", "update",
            "remove_by_id", "script"
    );

    public CommandObjectCreator() {
    }

    public CommandObject create(String command, List<String> commandArgs) {
        switch (command) {
            case "clear":
            case "exit":
            case "head":
            case "help":
            case "info":
            case "history":
            case "show":
            case "remove_first":
            case "logout":
            case "max_by_expelled_students":
                return new CommandObject(command);

            case "register":
            case "login":
                if (commandArgs.size() >= 2) {
                    return new CommandObject(command, commandArgs.get(0), commandArgs.get(1));
                } else {
                    return createErrorObject("Error", String.format("Not enough arguments: 2 is required, %s got", commandArgs.size()));
                }

            case "add":
                if (commandArgs.size() == 11) {
                    commandObject = new CommandObject(command, studyGroupCreator.constructor(
                            0L, commandArgs.get(0), commandArgs.get(1), commandArgs.get(2), commandArgs.get(3), commandArgs.get(4),
                            commandArgs.get(5), commandArgs.get(6), commandArgs.get(7), commandArgs.get(8), commandArgs.get(9), commandArgs.get(10)
                        )
                    );
                    return (commandObject.getStudyGroup() != null) ? commandObject : createErrorObject(command, "Error, see errors above");
                }
                return new CommandObject(command, studyGroupCreator.constructor(0L));

            default:
                if (!availableCommands.contains(command)) {
                    return createErrorObject(
                            command,
                            String.format(
                                    "Unknown command: '%s', use 'help' to get all available commands",
                                    command
                            )
                    );
                }

                if (commandArgs.size() >= 1) {
                    switch (command) {
                        case "filter_contains_name":
                        case "filter_by_group_admin":
                            return new CommandObject(command, commandArgs.get(0));

                        case "update":
                            groupID = toLong(commandArgs.get(0));
                            if (groupID >= 0) {
                                if (commandArgs.size() == 12) {
                                    return new CommandObject(command, studyGroupCreator.constructor(
                                            groupID, commandArgs.get(1), commandArgs.get(2), commandArgs.get(3), commandArgs.get(4), commandArgs.get(5),
                                            commandArgs.get(6), commandArgs.get(7), commandArgs.get(8), commandArgs.get(9), commandArgs.get(10), commandArgs.get(11)
                                    )
                                    );
                                }
                                return new CommandObject(command, studyGroupCreator.constructor(0L));
                            } else {
                                return createErrorObject(command, String.format("groupID must be a long, got '%s'", commandArgs.get(0)));
                            }

                        case "remove_by_id":
                            groupID = toLong(commandArgs.get(0));
                            return (groupID != -1) ?
                                    (new CommandObject(command, groupID)) :
                                    createErrorObject(command, creationError);

                        case "execute_script":
                            return new CommandObject(command);

                        default:
                            return createErrorObject(command, "Unknown error, sorry");
                    }
                } else {
                    return createErrorObject(command, "Not enough arguments");
                }
        }
    }

    public static CommandObject createErrorObject(String objectName, String failReason) {
        commandObject = new CommandObject(objectName);
        commandObject.setIsFailed(true);
        commandObject.setFailReason(failReason);
        return commandObject;
    }

    private Long toLong(String arg) {
        try {
            return Long.parseLong(arg);
        } catch (NumberFormatException e) {
            System.out.printf("Expected int, got <%s>\n", arg);
            return -1L;
        }
    }

}
