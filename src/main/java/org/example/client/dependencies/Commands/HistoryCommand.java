package org.example.client.dependencies.Commands;

import org.example.client.dependencies.CommandExecutor;

import java.util.ArrayList;

public class HistoryCommand extends Commands{
    private final CommandExecutor commandExecutor;

    public HistoryCommand(String name, CommandExecutor commandExecutor){
        this.name = name;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public ArrayList<String> execute(){
        return commandExecutor.getHistory();
    }
}
