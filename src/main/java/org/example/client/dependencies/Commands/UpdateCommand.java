package org.example.client.dependencies.Commands;

import org.example.client.dependencies.Collection.StudyGroup;

import java.util.ArrayList;

public class UpdateCommand extends Commands{
    private StudyGroup argument;

    public UpdateCommand(String name, StudyGroup argument){
        this.name = name;
        this.argument = argument;
    }
    @Override
    public ArrayList<String> execute(){
        return manager.update(argument);
    }
}
