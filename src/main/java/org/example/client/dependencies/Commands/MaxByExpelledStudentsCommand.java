package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class MaxByExpelledStudentsCommand extends Commands{

    public MaxByExpelledStudentsCommand(String name){

        this.name = name;
    }
    @Override
    public ArrayList<String> execute(){
        return manager.max_by_expelled_students();
    }
}
