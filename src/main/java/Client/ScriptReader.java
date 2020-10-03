package Client;

import dependencies.CommandManager.CommandObject;
import dependencies.CommandManager.CommandObjectCreator;
import dependencies.UserAuthorization.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class ScriptReader {
    private final CommandObjectCreator commandObjectCreator = new CommandObjectCreator();
    private boolean scriptIsRecursive = false;
    private ArrayList<String[]> commandWithArgs;
    private ArrayList<String> commandArgs;
    private ArrayList<String> scriptNameStack = new ArrayList<>();

    private final InetAddress serverAddress;
    private String command;

    private final Receiver receiver;
    private final Sender sender;

    private Scanner in;
    private CommandObject commandObjectToSend;

    public ScriptReader(Receiver receiver, Sender sender, InetAddress serverAddress) {
        this.receiver = receiver;
        this.sender = sender;
        this.serverAddress = serverAddress;
    }

    public void executeScript(String file_path, User user) {
        File file = new File(file_path);
        try {
            if (this.scriptNameStack.contains(file.getName())) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Recursion cannot work with the same file!");
            return;
        }
        this.scriptNameStack.add(file.getName());

        try {
            commandWithArgs = new ArrayList<>();
            in = new Scanner(new File(file_path));

            while (in.hasNextLine()) {
                commandWithArgs.add(in.nextLine().split(" ", 2));
            }

            for (int j = 0; j < commandWithArgs.size(); j++) {
                commandArgs = new ArrayList<>();
                command = commandWithArgs.get(j)[0];

                switch(command) {
                    case "add":
                        for (int i = 1; i <= 11; i++) {
                            commandArgs.add(commandWithArgs.get(j + i)[0]);
                        }
                        j += 11;
                        break;

                    case "update":
                        commandArgs.add(commandWithArgs.get(j)[1]);
                        for (int i = 1; i <= 11; i++) {
                            commandArgs.add(commandWithArgs.get(j + i)[0]);
                        }
                        j += 12;
                        break;

                    case "filter_contains_name":
                    case "execute_script":
                    case "remove_by_id":
                        commandArgs.add(commandWithArgs.get(j)[1]);
                        j++;
                        break;
                }

                try {
                    switch (command) {
                        case "max_by_expelled_students":
                        case "remove_first":
                        case "history":
                        case "help":
                        case "info":
                        case "show":
                        case "clear":
                        case "head":
                        case "exit":
                            commandObjectToSend = commandObjectCreator.create(command, commandArgs);
                            commandObjectToSend.setSender(user);
                            this.sender.sendMessage(commandObjectToSend, this.serverAddress);
                            break;

                        case "add":
                        case "update":
                        case "remove_by_id":
                        case "filter_contains_name":
                            assert commandArgs != null;
                            commandObjectToSend = commandObjectCreator.create(command, commandArgs);
                            commandObjectToSend.setSender(user);
                            this.sender.sendMessage(commandObjectToSend, this.serverAddress);
                            break;

                        case "execute_script":
                            this.scriptIsRecursive = true;
                            executeScript(commandArgs.get(0), user);
                    }

                    try {
                        for (String line : this.receiver.handleMessage().getMessage()) {
                            System.out.println(line);
                        }
                    } catch (NullPointerException ignored) {
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException ignored) {

        }
    }

    public void clearScriptStack() {
        this.scriptNameStack.clear();
    }
}