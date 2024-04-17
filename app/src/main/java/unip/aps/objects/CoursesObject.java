package unip.aps.objects;

public class CoursesObject {
    String courseName;
    String coordinator;
    int semester;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }


    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return  "\n" +
                "Course Name: " + courseName + ", "+ "\n" +
                "Coordinator: " + coordinator + ", "+ "\n" +
                "Semester: " + semester;
    }
}


