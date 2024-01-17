import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class StudentSearch
{
private static void joinStudentList(List<StudentInfo> studentList, StudentQueryFormatter formatter)
{
    if (studentList.toArray().length > 0)
    {
        StringJoiner output = new StringJoiner("\n");
        
        for (StudentInfo student : studentList) {
            output.add(formatter.format(student));
        }
        System.out.println(output);
    }
    else
    {
        System.out.println("no such student exists");
    }
}
static void searchForStudentByGrade(String grade, String arg2)
{
    int gradeValue = Integer.parseInt(grade);
    
    ArrayList<StudentInfo> studentMatchList = new ArrayList<>();
    for (StudentInfo student : schoolsearch.studentInfoList)
    {
        if (student.grade == gradeValue)
        {
            studentMatchList.add(student);
        }
    }
    if (arg2 != null)
    {
        if (!studentMatchList.isEmpty())
        {
            searchForStudentByGPA(studentMatchList, arg2);
        }
        else
        {
            System.out.println("No such student exists");
        }
    }
    else
    {
        joinStudentList(studentMatchList, new StudentFirstLastNameFormatter());
    }
}

private static void searchForStudentByGPA(List<StudentInfo> studentList, String sort)
{
    ArrayList<StudentInfo> studentFinalList = new ArrayList<>();
    float significantGPA = studentList.get(0).GPA;
    if (sort.charAt(0) == 'H')
    {
        for (StudentInfo student : studentList)
        {
            if (student.GPA > significantGPA)
            {
                significantGPA = student.GPA;
                studentFinalList.clear();
                studentFinalList.add(student);
            }
            else if (student.GPA == significantGPA)
            {
                studentFinalList.add(student);
            }
        }
    }
    else // Low
    {
        for (StudentInfo student : studentList)
        {
            if (student.GPA < significantGPA)
            {
                significantGPA = student.GPA;
                studentFinalList.clear();
                studentFinalList.add(student);
            }
            else if (student.GPA == significantGPA)
            {
                studentFinalList.add(student);
            }
        }
    }
    joinStudentList(studentFinalList, new StudentGPAFormatter());
}

static void searchForStudentByBus(String bus)
{
    int busRouteNumber = Integer.parseInt(bus);
    ArrayList<StudentInfo> studentMatchList = new ArrayList<>();
    for (StudentInfo student : schoolsearch.studentInfoList)
    {
        if (student.bus == busRouteNumber)
        {
            studentMatchList.add(student);
        }
    }
    joinStudentList(studentMatchList, new BusRouteFormatter());
}

static void calculateAverageGPAByGrade(String gradeLevel)
{
    int grade = Integer.parseInt(gradeLevel);
    float averageGPA = 0;
    int counter = 0;
    ArrayList<StudentInfo> studentMatchList = new ArrayList<>();
    for (StudentInfo student : schoolsearch.studentInfoList)
    {
        if (student.grade == grade)
        {
            studentMatchList.add(student);
        }
    }
    for (StudentInfo student : studentMatchList)
    {
        averageGPA += student.GPA;
        counter++;
    }
    if (counter > 0)
    {
        averageGPA = averageGPA / counter;
        System.out.println(gradeLevel + ", " + averageGPA);
    }
    else
    {
        System.out.println(grade + ", 0");
    }
}

static void searchForStudentByTeacher(String teacherLastname)
{
    ArrayList<StudentInfo> studentMatchList = new ArrayList<>();
    for (StudentInfo student : schoolsearch.studentInfoList)
    {
        if (student.teacherLastName.equalsIgnoreCase(teacherLastname))
        {
            studentMatchList.add(student);
        }
    }
    joinStudentList(studentMatchList, new StudentFirstLastNameFormatter());
}

static void searchForStudentByName(String lastName, String bus)
{
    ArrayList<StudentInfo> studentMatchList = new ArrayList<>();
    for (StudentInfo student : schoolsearch.studentInfoList)
    {
        if (student.stLastName.equalsIgnoreCase(lastName))
        {
            studentMatchList.add(student);
        }
    }
    if (bus != null)
    {
        joinStudentList(studentMatchList, new BusRouteStudentFormatter());
    }
    else
    {
        joinStudentList(studentMatchList, new BasicStudentFormatter());
    }
}

public static void findNumberOfStudentsInEachGrade()
{
 int grade0Students = 0;
 int grade1Students = 0;
 int grade2Students = 0;
 int grade3Students = 0;
 int grade4Students = 0;
 int grade5Students = 0;
 int grade6Students = 0;
 
    for (StudentInfo student: schoolsearch.studentInfoList)
    {
        switch (student.grade)
        {
            case 0:
            {
                grade0Students++;
                break;
            }
            case 1:
            {
                grade1Students++;
                break;
            }
            case 2:
            {
                grade2Students++;
                break;
            }
            case 3:
            {
                grade3Students++;
                break;
            }
            case 4:
            {
                grade4Students++;
                break;
            }
            case 5:
            {
                grade5Students++;
                break;
            }
            case 6:
            {
                grade6Students++;
                break;
            }
        }
    }
    System.out.println("6: " + grade6Students);
    System.out.println("5: " + grade5Students);
    System.out.println("4: " + grade4Students);
    System.out.println("3: " + grade3Students);
    System.out.println("2: " + grade2Students);
    System.out.println("1: " + grade1Students);
    System.out.println("0: " + grade0Students);
}

private static class BasicStudentFormatter implements StudentQueryFormatter
{
    @Override
    public String format(StudentInfo student)
    {
        return String.join(", ", student.stLastName, student.stFirstName,
                String.valueOf(student.grade), String.valueOf(student.classRoom),
                student.teacherLastName, student.teacherFirstName);
    }
}
private static class BusRouteStudentFormatter implements StudentQueryFormatter
{
    @Override
    public String format(StudentInfo student) {
        return String.join(", ", student.stLastName, student.stFirstName,
                String.valueOf(student.bus));
    }
}

private static class StudentFirstLastNameFormatter implements StudentQueryFormatter
{
    @Override
    public String format(StudentInfo student)
    {
        return String.join(", ", student.stLastName, student.stFirstName);
    }
}

private static class StudentGPAFormatter implements StudentQueryFormatter
{
    @Override
    public String format(StudentInfo student)
    {
        return String.join(", ", student.stLastName, student.stFirstName,
                String.valueOf(student.GPA), student.teacherLastName,
                student.teacherFirstName, String.valueOf(student.bus));
    }
}

private static class BusRouteFormatter implements StudentQueryFormatter
{
    @Override
    public String format(StudentInfo student)
    {
        return String.join(", ", student.stLastName, student.stFirstName, String.valueOf(student.grade),
                String.valueOf(student.classRoom));
    }
}
}
