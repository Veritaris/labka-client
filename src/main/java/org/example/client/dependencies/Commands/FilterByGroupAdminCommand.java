package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class FilterByGroupAdminCommand extends Commands {
    String argument;

    public FilterByGroupAdminCommand(String name, String argument){
        this.name = name;
        this.argument = argument;
    }
    @Override
    public ArrayList<String> execute(){
        return manager.filter_by_group_admin(argument);
    }
}
