package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class ExitCommand extends Commands{

    public ExitCommand(String name){
        this.name =name;
    }

    @Override
    public ArrayList<String> execute(){
        return manager.exit();
    }
}
