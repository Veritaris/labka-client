package dependencies.CommandManager;

import dependencies.Collection.StudyGroup;

import java.util.ArrayList;

public interface CommandExecutor {

    static CommandExecutor getInstance() {
        return null;
    }

    ArrayList<String> help();

    ArrayList<String> info();

    ArrayList<String> show();

    ArrayList<String> login(String username, String rawPassword);

    ArrayList<String> logout(String username, String rawPassword);

    ArrayList<String> register(String username, String rawPassword);

    ArrayList<String> addStudyGroup(StudyGroup group, String author);

    ArrayList<String> updateStudyGroup(StudyGroup group);

    ArrayList<String> remove_by_id(Long id);

    ArrayList<String> clearCollection();

    ArrayList<String> exit();

    ArrayList<String> remove_first();

    ArrayList<String> head();

    ArrayList<String> getHistory();

    ArrayList<String> filter_contains_name(String str);

    ArrayList<String> max_by_expelled_students();

    ArrayList<String> filter_by_group_admin(String fieldsGroupAdmin);

}

