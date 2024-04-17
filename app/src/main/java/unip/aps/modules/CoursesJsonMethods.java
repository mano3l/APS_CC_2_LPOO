package unip.aps.modules;
import unip.aps.objects.CoursesObject;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class CoursesJsonMethods {
    GetPath path = new GetPath();
    Gson gson = new Gson();
    private CoursesObject[] courseArray;
    Scanner sc = new Scanner(System.in);
    String courseJsonPath = path.getCurrentPath() + path.getDataJson() + "/Courses.json";


    public CoursesJsonMethods(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(courseJsonPath));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);

            }
            br.close();
            courseArray = gson.fromJson(sb.toString(), CoursesObject[].class);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        }

    public void ListCourses(){
        for (CoursesObject courses : courseArray){
            System.out.println(courses.toString());
        }
    }

    public CoursesObject SelectCourse(int course){
        return courseArray[course];
    }

    public void EditCourse(int course) {
        CoursesObject editedCourse = Edit(course);
        courseArray[course] = editedCourse;

        try {
            FileWriter writer = new FileWriter(courseJsonPath);
            gson.toJson(courseArray, writer);
            writer.close();
            System.out.println("Course Updated!");
        } catch (IOException e) {
            System.out.println("Error in Json: " + e.getMessage());
        }
    }

    private CoursesObject Edit(int course) {
        System.out.println("Change course: " + courseArray[course].getCourseName());
        System.out.print("\nChange Course Name: ");
        courseArray[course].setCourseName(sc.nextLine());

        System.out.print("Change Course Coordinator: ");
        courseArray[course].setCoordinator(sc.nextLine());

        System.out.print("Change Course Semester: ");
        courseArray[course].setSemester(Integer.parseInt(sc.nextLine()));

        return courseArray[course];
    }
    }


