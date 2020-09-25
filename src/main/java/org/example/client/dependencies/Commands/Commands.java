package org.example.client.dependencies.Commands;

import org.example.client.dependencies.CommandExecutor;

import java.nio.file.Paths;

public abstract class Commands implements CommandInterface{

    String name;
    protected CommandExecutor manager = CommandExecutor.getInstance(Paths.get(".").toAbsolutePath().normalize().toString() + String.format("/%s", "users.json"));
}
