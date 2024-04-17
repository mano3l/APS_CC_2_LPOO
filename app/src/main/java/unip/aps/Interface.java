package unip.aps;

import unip.aps.modules.CoursesJsonMethods;

import java.util.Objects;
import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        CoursesJsonMethods coursesJson = new CoursesJsonMethods();
        Scanner sc = new Scanner(System.in);
        System.out.println("University 2024 - System");

        coursesJson.EditCourse(2);
        coursesJson.ListCourses();
    }
    }
