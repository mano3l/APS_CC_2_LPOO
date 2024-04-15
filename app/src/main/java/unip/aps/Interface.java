package unip.aps;

import unip.aps.modules.CoursesJsonR;

public class Interface {
    public static void main(String[] args){

        System.out.print("Courses University 2023 " + "\n");

        CoursesJsonR coursesJson = new CoursesJsonR();

//        coursesJson.GetCourses();
        System.out.println(coursesJson.SelectCourse(2));

    }
}
