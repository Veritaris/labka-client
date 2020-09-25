package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class RemoveByIdCommand extends Commands {

    Long argument;
    public RemoveByIdCommand(String name, Long argument){
        this.name = name;
        this.argument = argument;
    }

    @Override
    public ArrayList<String> execute(){
        return manager.remove_by_id(argument);
    }
}
