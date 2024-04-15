package unip.aps.modules;
import unip.aps.objects.CoursesObject;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CoursesJsonR {
    GetPath path = new GetPath();
    Gson gson = new Gson();
    private CoursesObject[] courseArray;


    public CoursesJsonR(){
        try {
            String courseJsonPath = path.getCurrentPath() + path.getDataJson() + "/Courses.json";
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

    public void GetCourses(){
        for (CoursesObject courses : courseArray){
            System.out.println(courses.toString());
        }
    }
}