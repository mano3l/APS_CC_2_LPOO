package unip.aps.services;

import unip.aps.models.Programs;
import unip.aps.utils.JSONUtility;

import java.util.List;
import java.util.Scanner;

public class ProgramManagementService {
    private final JSONUtility<Programs> jsonFile;
    Scanner sc = new Scanner(System.in);

    public ProgramManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Programs.class);
    }

    public List<Programs> getPrograms() {
        return this.jsonFile.parseJSON();
    }
}
