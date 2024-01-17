import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
// LAB 1, Name: Christian Bird
public class schoolsearch
{
static ArrayList<StudentInfo> studentInfoList = new ArrayList<>();
private static final String FORMAT_SEARCH_STUDENT = "Format for searching Student command: S[tudent]: <lastname> [B[us]]";
private static final String FORMAT_TEACHER = "Format for Teacher command is: T[eacher]: <lastname>";
private static final String FORMAT_BUS = "Format for Bus command is: B[us]: <number>";
private static final String FORMAT_GRADE = "Format for Grade command: G[rade]: <number> [H[igh]|L[ow]]";
private static final String FORMAT_AVERAGE = "Format for Average command is: A[verage]: <number>";

public static void main(String[] args)
{
    String filename = "students.txt";
    parseFile(filename);
    
    Scanner keyboard = new Scanner(System.in);
    while (true)
    {
        runCommand(keyboard);
    }
}
public static void parseFile(String fileName)
{
    try
    {
        File file = new File(fileName);
        Scanner f = new Scanner(file);
        while (f.hasNextLine())
        {
            String[] line = f.nextLine().split(",");
            MakeNewStudent(line);
        }
    }
    catch (FileNotFoundException e)
    {
        throw new RuntimeException(e);
    }
}
private static void MakeNewStudent(String[] line)
{
    
    StudentInfo newStudent = new StudentInfo();
    newStudent.stLastName = line[0];
    newStudent.stFirstName = line[1];
    try
    {
        int grade = Integer.parseInt(line[2]);
        
        if (grade > 6 || grade < 0)
        {
            System.out.println("Student entry: " + newStudent.stLastName +
                                       " has grade entry of not in range of 0 to 6.");
        }
        else
        {
            newStudent.grade = grade;
        }
    }
    catch (NumberFormatException e)
    {
        System.out.println(Integer.parseInt(line[2]));
        System.out.println("Student entry: " + newStudent.stLastName +
                                   " has incorrect grade entry of not a number.");
    }
    try
    {
        int classRoom = Integer.parseInt(line[3]);
        newStudent.classRoom = classRoom;
    }
    catch (NumberFormatException e)
    {
        System.out.println("Student entry: " + newStudent.stLastName +
                                   " has incorrect classroom entry of not a number.");
    }
    try
    {
        int bus = Integer.parseInt(line[4]);
        newStudent.bus = bus;
    }
    catch (NumberFormatException e)
    {
        System.out.println("Student entry: " + newStudent.stLastName +
                                   " has incorrect bus entry of not a number.");
    }
    try
    {
        float gpa = Float.parseFloat(line[5]);
        if (gpa > 4.0 || gpa < 0)
        {
            System.out.println("Student entry: " + newStudent.stLastName +
                                       " has incorrect GPA entry of not in range of 0 to 4.");
        }
        else
        {
            newStudent.GPA = gpa;
        }
    }
    catch (NumberFormatException e)
    {
        System.out.println("Student entry: " + newStudent.stLastName +
                                   " has incorrect GPA entry of not a number.");
    }
    newStudent.teacherLastName = line[6];
    newStudent.teacherFirstName = line[7];
    studentInfoList.add(newStudent);
}
public static void runCommand(Scanner keyboard)
{
    String[] input = keyboard.nextLine().split(" ");
    
    String arg1 = null;
    String arg2 = null;
    String command = input[0];
    
    switch (command)
    {
        case "Student:", "S:":
        {
            if (input.length > 1)
            {
                arg1 = input[1];
                if (input.length > 2)
                {
                    if (input[2].charAt(0) == 'B' || input[2].equals("Bus"))
                    {
                        arg2 = input[2];
                    }
                    else
                    {
                        System.out.println(FORMAT_SEARCH_STUDENT);
                        return;
                    }
                }
                StudentSearch.searchForStudentByName(arg1, arg2);
            }
            else
            {
                System.out.println(FORMAT_SEARCH_STUDENT);
            }
            break;
        }
        case "T:", "Teacher:":
        {
            if (input.length > 1)
            {
                arg1 = input[1];
                StudentSearch.searchForStudentByTeacher(arg1);
            }
            else
            {
                System.out.println(FORMAT_TEACHER);
            }
            break;
        }
        
        case "B:", "Bus:":
        {
            try{
                if (input.length > 1)
                {
                    arg1 = input[1];
                    StudentSearch.searchForStudentByBus(arg1);
                }
                else
                {
                    System.out.println(FORMAT_BUS);
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println(FORMAT_BUS);
            }
            
            break;
        }
        case "G:", "Grade:":
        {
            if (input.length > 1)
            {
                try
                {
                    arg1 = String.valueOf(input[1]);
                    if (input.length > 2)
                    {
                        arg2 = input[2];
                        if (arg2.equals("H") || arg2.equals("High") ||
                                    arg2.equals("L") || arg2.equals("Low"))
                        {
                            StudentSearch.searchForStudentByGrade(String.valueOf(arg1), arg2);
                        }
                        else
                        {
                            System.out.println(FORMAT_GRADE);
                            return;
                        }
                    }
                    else
                    {
                        StudentSearch.searchForStudentByGrade(String.valueOf(arg1), arg2);
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println(FORMAT_GRADE);
                    return;
                }
            }
            else
            {
                System.out.println(FORMAT_GRADE);
            }
            break;
        }
        case "A:", "Average:":
        {
            if (input.length > 1)
            {
                try
                {
                    arg1 = input[1];
                    StudentSearch.calculateAverageGPAByGrade(arg1);
                }
                catch (NumberFormatException e)
                {
                    System.out.println(FORMAT_AVERAGE);
                    return;
                }
            }
            else
            {
                System.out.println(FORMAT_AVERAGE);
            }
            break;
        }
        case "I", "Info":
        {
            StudentSearch.findNumberOfStudentsInEachGrade();
            break;
        }
        case "Q", "Quit":
        {
            System.exit(0);
            break;
        }
        default:
        {
            System.out.println(String.join(",\n",FORMAT_SEARCH_STUDENT, FORMAT_TEACHER, FORMAT_GRADE, FORMAT_AVERAGE, FORMAT_BUS, "• I[nfo]\n" + "• Q[uit]"));
            break;
        }
    }
}
}

