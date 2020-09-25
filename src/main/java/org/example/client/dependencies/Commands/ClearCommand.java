package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class ClearCommand extends Commands {

    public ClearCommand(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute(){
        return manager.clearCollection();
     }
}
