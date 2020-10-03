package Client;

import dependencies.CommandManager.CommandObject;
import dependencies.CommandManager.CommandProcessor;
import dependencies.UserAuthorization.Session;
import dependencies.UserAuthorization.User;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

@SuppressWarnings({"FieldCanBeLocal"})
public class Client {
    private final InetAddress serverAddress;
    private final DatagramSocket socket;
    private final String serverHost;
    private final int serverPort;

    private final Receiver messageReceiver;
    private CommandObject receivedMessage;
    private final Sender messageSender;
    private Session session;

    private final CommandProcessor commandProcessor = new CommandProcessor();
    private final ScriptReader scriptReader;
    private CommandObject commandObject;

    public Client(String host, int port) throws IOException {
        System.out.printf("Creating client at '%s:%s'\n", host, port);
        this.serverHost = host;
        this.serverPort = port;

        this.serverAddress = InetAddress.getByName(this.serverHost);
        this.socket = new DatagramSocket();

        this.messageSender = new Sender(this.socket, this.serverPort);
        this.messageReceiver = new Receiver(this.socket, this.serverAddress, this.serverPort);

        this.scriptReader = new ScriptReader(this.messageReceiver, this.messageSender, this.serverAddress);
        this.commandProcessor.setScriptReader(this.scriptReader);


        System.out.println("Client created");
    }

    public void startInteractiveMode() throws IOException, ClassNotFoundException {
        System.out.println("You have entered into interactive mode");
        session = new Session();

        while (true) {
            commandObject = commandProcessor.readCommand(session);
            if (session.getUser().isLoggedIn()) {
                commandObject.setSender(session.getUser());
            } else {
                commandObject.setSender(commandObject.getSender());
            }

            if (!commandObject.isFailed()) {
                if (commandObject.isScripted()) {
                    this.scriptReader.clearScriptStack();
                    continue;
                }

                messageSender.sendMessage(commandObject, this.serverAddress);
                receivedMessage = messageReceiver.handleMessage();

                if (!receivedMessage.isFailed()) {
                    for (String line : receivedMessage.getMessage()) {
                        System.out.println(line);
                    }
                }

                switch (receivedMessage.getName()) {
                    case "401":
                        System.out.println("You need to authorize to use commands");
                        break;

                    case "login":
                        if (!receivedMessage.getMessage().get(0).equals("401")) {
                            this.session.setUser(commandObject.getSender());
                            this.session.getUser().setLoggedIn(true);
                        }
                        break;

                    case "ServerNotResponding":
                        System.out.println("Server not responding");
                        break;

                    case "logout":
                        this.session.setUser(new User("", ""));
                        break;

                    case "exit":
                        this.commandProcessor.close();
                        System.exit(0);
                        break;
                }
            } else {
                System.out.printf(
                        "Command object was not created, reason:\n\t%s\n",
                        commandObject.getFailReason()
                );
            }
        }
    }
}
