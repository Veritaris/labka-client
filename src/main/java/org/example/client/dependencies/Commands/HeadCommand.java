package org.example.client.dependencies.Commands;

import java.util.ArrayList;

public class HeadCommand extends Commands {

    public HeadCommand(String name){
        this.name = name;
    }
    @Override
    public ArrayList<String> execute(){
        return manager.head();
    }
}
