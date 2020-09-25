package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class FilterContainsNameCommand extends Commands{

    String argument;

    public FilterContainsNameCommand(String name, String argument){
        this.name = name;
        this.argument = argument;
    }

    @Override
    public ArrayList<String> execute(){
        return manager.filter_contains_name(argument);
    }
}
