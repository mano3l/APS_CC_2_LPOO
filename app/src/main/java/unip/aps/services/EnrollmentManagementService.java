package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Programs;
import unip.aps.utils.JSONUtility;

import java.util.List;
import java.util.Scanner;

public class EnrollmentManagementService {
    private final JSONUtility<Enrollment> jsonFile;
    Scanner sc = new Scanner(System.in);

    public EnrollmentManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Enrollment.class);
    }

    public List<Enrollment> getEnrollments() {return this.jsonFile.parseJSON();

    }
    }
