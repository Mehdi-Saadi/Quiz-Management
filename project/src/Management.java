import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;

class Management {
    public void managementMenu() {
        System.out.println("=================================================");
        System.out.println("| Management menu:                              |");
        System.out.println("=================================================");
        System.out.println("| [1] Add, Edit or Delete Professor             |");
        System.out.println("| [2] Add, Edit or Delete Lesson                |");
        System.out.println("| [3] Add, Edit or Delete Student               |");
        System.out.println("| [4] Add or Delete Student/Lesson              |");
        System.out.println("| [5] Add, Edit or Delete Quiz                  |");
        System.out.println("| [6] Add, Edit or Delete Questions             |");
        System.out.println("| [7] Inquiry about Quizzes                     |");
        System.out.println("| [8] Inquiry about Lessons                     |");
        System.out.println("| [9] Exit                                      |");
        System.out.println("=================================================");
    }

    public void professorCRUDMenu() {
        System.out.println("=================================================");
        System.out.println("| Professor CRUD menu:                          |");
        System.out.println("=================================================");
        System.out.println("| [1] Add                                       |");
        System.out.println("| [2] Edit                                      |");
        System.out.println("| [3] Delete                                    |");
        System.out.println("| [4] Back                                      |");
        System.out.println("=================================================");
    }

    public void lessonCRUDMenu() {
        System.out.println("=================================================");
        System.out.println("| Lesson CRUD menu:                             |");
        System.out.println("=================================================");
        System.out.println("| [1] Add                                       |");
        System.out.println("| [2] Edit                                      |");
        System.out.println("| [3] Delete                                    |");
        System.out.println("| [4] Back                                      |");
        System.out.println("=================================================");
    }

    public void studentCRUDMenu() {
        System.out.println("=================================================");
        System.out.println("| Student CRUD menu:                            |");
        System.out.println("=================================================");
        System.out.println("| [1] Add                                       |");
        System.out.println("| [2] Edit                                      |");
        System.out.println("| [3] Delete                                    |");
        System.out.println("| [4] Back                                      |");
        System.out.println("=================================================");
    }

    public void studentLessonCRUDMenu() {
        System.out.println("=================================================");
        System.out.println("| Student/Lesson CRUD menu:                     |");
        System.out.println("=================================================");
        System.out.println("| [1] Add Student to a Lesson                   |");
        System.out.println("| [2] Delete Student from a lesson              |");
        System.out.println("| [3] Back                                      |");
        System.out.println("=================================================");
    }

    public void quizCRUDMenu() {
        System.out.println("=================================================");
        System.out.println("| Quiz CRUD menu:                               |");
        System.out.println("=================================================");
        System.out.println("| [1] Add                                       |");
        System.out.println("| [2] Edit                                      |");
        System.out.println("| [3] Delete                                    |");
        System.out.println("| [4] Back                                      |");
        System.out.println("=================================================");
    }

    public void questionCRUDMenu() {
        System.out.println("=================================================");
        System.out.println("| Question CRUD menu:                           |");
        System.out.println("=================================================");
        System.out.println("| [1] Add                                       |");
        System.out.println("| [2] Edit                                      |");
        System.out.println("| [3] Delete                                    |");
        System.out.println("| [4] Back                                      |");
        System.out.println("=================================================");
    }




    // add, edit, delete for professor and student
    public void add(String name, int id, String pass, String confirmPass, String tableName, String idType) {
        boolean detect = false;
        int mainId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        if (pass.equals(confirmPass)) {

            try {
                Connection Conn = DriverManager.getConnection(url, user, password);
                String sql = "select * from " + tableName + ";";

                Statement readStmt = Conn.createStatement();
                ResultSet result = readStmt.executeQuery(sql);

                // check the unique id
                while (result.next()) {
                    mainId = result.getInt(idType);

                    if (mainId == id)
                        detect = true;
                }

                // if the id used before
                if (detect) {
                    System.out.println("Id used...");
                } else {
                    // id is unique
                    sql = "insert into " + tableName +" (name, " + idType + ", password) values (?, ?, ?);";

                    PreparedStatement writeStmt = Conn.prepareStatement(sql);
                    writeStmt.setString(1, name);
                    writeStmt.setInt(2, id);
                    writeStmt.setString(3, pass);

                    int rows = writeStmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("A row has been inserted.");
                    }

                    writeStmt.close();
                }

                readStmt.close();
                Conn.close();
            } catch (Exception e) {
                System.out.println("Can't connect to database...");
                e.printStackTrace();
            }

        } else {
            System.out.println("Passwords are not the same..!");
        }
    }

    public void edit(int id, String pass, String newName, int newId, String newPass, String tableName, String idType) {
        boolean detect = false;
        int tableId = 0;
        int mainId;
        String mainPass;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from " + tableName + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the id
            while (result.next()) {
                tableId = result.getInt("id");
                mainId = result.getInt(idType);
                mainPass = result.getString("password");

                if (mainId == id) {
                    if (mainPass.equals(pass)) {
                        detect = true;
                    } else {
                        System.out.println("Wrong password...");
                    }
                    break;
                }
            }

            // if user exists
            if (detect) {
                sql = "update " + tableName + " set name = ?, " + idType + " = ?, password = ? where id = " + tableId + ";";

                PreparedStatement updateStmt = Conn.prepareStatement(sql);

                updateStmt.setString(1, newName);
                updateStmt.setInt(2, newId);
                updateStmt.setString(3, newPass);

                int rows = updateStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("A row has been inserted.");
                }

                updateStmt.close();
            } else {
                System.out.println("The user was not identified...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }

    }

    public void delete(int id, String pass, String tableName, String idType) {
        boolean detect = false;
        int rowId = 0;
        int mainId;
        String mainPass;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from " + tableName + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the id
            while (result.next()) {
                rowId = result.getInt("id");
                mainId = result.getInt(idType);
                mainPass = result.getString("password");

                if (mainId == id) {
                    if (mainPass.equals(pass)) {
                        detect = true;
                    } else {
                        System.out.println("Wrong password...");
                    }
                    break;
                }
            }

            // if user exists
            if (detect) {
                sql = "update " + tableName + " set " + idType + " = ?, password = ? where id = " + rowId + ";";

                PreparedStatement deleteStmt = Conn.prepareStatement(sql);
                deleteStmt.setInt(1, 0);
                deleteStmt.setString(2, "");

                int rows = deleteStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("The information was completely removed...");
                }

                deleteStmt.close();
            } else {
                System.out.println("The user was not identified...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }

    }

    // add, edit, delete for lesson
    public void add(int professorId, String professorName, String lessonName) {
        boolean detect = false;
        int mainProId;
        String mainName;
        String mainProfessorName;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from professorinfo;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                mainProId = result.getInt("professorId");

                if (mainProId == professorId) {
                    detect = true;
                    break;
                }
            }

            if (!detect) {
                System.out.println("Professor name does not exits...");
                System.out.println("Please add professor information first...");
            } else {
                detect = false;

                sql = "select * from lesson;";

                readStmt = Conn.createStatement();
                result = readStmt.executeQuery(sql);

                // check the unique name
                while (result.next()) {
                    mainName = result.getString("lessonName");
                    mainProfessorName = result.getString("professorName");

                    if (mainName.equals(lessonName))
                        if (mainProfessorName.equals(professorName)) {
                            detect = true;
                            break;
                        }
                }

                // if the lesson created before
                if (detect) {
                    System.out.println("Lesson exists...");
                } else {
                    sql = "insert into lesson (lessonName, professorName, professorId) values (?, ?, ?);";

                    PreparedStatement writeStmt = Conn.prepareStatement(sql);
                    writeStmt.setString(1, lessonName);
                    writeStmt.setString(2, professorName);
                    writeStmt.setInt(3, professorId);

                    int rows = writeStmt.executeUpdate();
                    if (rows > 0)
                        System.out.println("A row has been inserted.");

                    writeStmt.close();
                }
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }

    }

    public void edit(int lessonId, String newLessonName, String newProfessorName, int newProId) {
        boolean detect = false;
        int mainLessonId;
        int mainProId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from professorinfo;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the id
            while (result.next()) {
                mainProId = result.getInt("professorId");

                if (mainProId == newProId) {
                    detect = true;
                    break;
                }
            }

            if (!detect) {
                System.out.println("Professor name does not exits...");
                System.out.println("Please add professor information first...");
            } else {
                detect = false;

                sql = "select * from lesson;";

                readStmt = Conn.createStatement();
                result = readStmt.executeQuery(sql);

                // check the id
                while (result.next()) {
                    mainLessonId = result.getInt("id");

                    if (mainLessonId == lessonId) {
                        detect = true;
                        break;
                    }
                }

                // if lesson exists
                if (detect) {
                    sql = "update lesson set lessonName = ?, professorName = ?, professorId = ? where id = " + lessonId + ";";

                    PreparedStatement updateStmt = Conn.prepareStatement(sql);
                    updateStmt.setString(1, newLessonName);
                    updateStmt.setString(2, newProfessorName);
                    updateStmt.setInt(3, newProId);

                    int rows = updateStmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("A row has been updated...");
                    }

                    updateStmt.close();
                } else {
                    System.out.println("Lesson does not exist...");
                }
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void delete(int lessonId) {
        boolean detect = false;
        int rowId = 0;
        int mainLessonId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from lesson;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the lesson
            while (result.next()) {
                mainLessonId = result.getInt("id");


                if (mainLessonId == lessonId) {
                    detect = true;
                    break;
                }
            }

            // if lesson exists
            if (detect) {
                sql = "update lesson set lessonName = ?, professorName = ? where id = " + rowId + ";";

                PreparedStatement deleteStmt = Conn.prepareStatement(sql);
                deleteStmt.setInt(1, 0);
                deleteStmt.setString(2, "");

                int rows = deleteStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("The information was completely removed...");
                }

                deleteStmt.close();
            } else {
                System.out.println("Lesson does not exist...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    // delete all quizzes and question tables when the lesson deleted
    public void deleteAllQuizzes(int lessonId) {
        int i = 0;
        int count = 0;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from quizzes where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // count of quizzes
            while (result.next())
                count++;

            int[] quizIds = new int[count];

            // get quiz ids for deleting all question tables
            while (result.next()) {
                quizIds[i] = result.getInt("quizId");
                i++;
            }

            // delete quizzes
            sql = "delete from quizzes where id = " + lessonId + ";";
            PreparedStatement deleteStmt = Conn.prepareStatement(sql);

            int rows = deleteStmt.executeUpdate();
            if (rows > 0) {
                System.out.println(rows + "Quiz removed...");
            }

            // delete all question tables
            for (int k = 0; k < count; k++) {
                sql = "drop table `" + lessonId + "-" + quizIds[k] + "`;";
                readStmt.executeUpdate(sql);
            }
            System.out.println("The information was completely removed...");

            deleteStmt.close();
            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void showProfessor() {
        int count = 0;
        int professorId;
        String professorName;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        System.out.println("Available professors:");
        System.out.println();

        // making a delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from professorinfo;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                professorId = result.getInt("professorId");
                professorName = result.getString("name");
                System.out.println("Professor Id: " + professorId + "      Professor Name: " + professorName);
                count++;
            }

            System.out.println(count + " result found...");
            System.out.println();

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }

    }

    public void showStudentFromLesson(int lessonId) {
        int count = 0;
        int studentId;
        String studentName;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        System.out.println("Available students:");
        System.out.println();

        // making a delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from student where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                studentId = result.getInt("studentId");
                studentName = result.getString("name");
                System.out.println("Student Id: " + studentId + "      Student Name: " + studentName);
                count++;
            }

            System.out.println(count + " result found...");
            System.out.println();

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void addStudentToLesson(int lessonId, int studentId) {
        boolean detect = false;
        int mainStudentId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from student where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);


            if (!detect) {
                System.out.println("Student does not exists...");
            } else {
                sql = "delete from student where studentId = " + studentId + ";";
                PreparedStatement deleteStmt = Conn.prepareStatement(sql);

                int rows = deleteStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println(rows + "Student removed...");
                }

                System.out.println("The information was completely removed...");
                deleteStmt.close();
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void deleteStudentFromLesson(int lessonId, int studentId) {
        boolean detect = false;
        int mainStudentId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from student where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);


            while (result.next()) {
                mainStudentId = result.getInt("studentId");

                if (mainStudentId == studentId) {
                    detect = true;
                    break;
                }
            }

            if (!detect) {
                System.out.println("Student does not exists...");
            } else {
                sql = "delete from student where studentId = " + studentId + ";";
                PreparedStatement deleteStmt = Conn.prepareStatement(sql);

                int rows = deleteStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println(rows + "Student removed...");
                }

                System.out.println("The information was completely removed...");
                deleteStmt.close();
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    // add, edit, delete for quiz
    // show lessons
    public void showLesson() {
        int count = 0;
        int lessonId;
        String lessonName;
        String professorName;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        System.out.println("Available lessons:");
        System.out.println();

        // making a delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from lesson;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                lessonId = result.getInt("id");
                lessonName = result.getString("lessonName");
                professorName = result.getString("professorName");
                System.out.println("Lesson Id: " + lessonId + "      Lesson Name: " + lessonName + "      Professor Name: " + professorName);
                count++;
            }

            System.out.println(count + " result found...");
            System.out.println();

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    // show quizzes
    public void showQuizzes(int lessonId) {
        int count = 0;
        int quizId;
        String quizName;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        System.out.println("Available quizzes:");
        System.out.println();

        // making a delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from quizzes where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                quizId = result.getInt("quizId");
                quizName = result.getString("quizName");

                System.out.println("Quiz Id: " + quizId + "      Quiz Name: " + quizName);
                count++;
            }

            System.out.println(count + " result found...");
            System.out.println();

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    // show questions
    public void showQuestion(int lessonId, int quizId) {
        int count = 0;
        int questionId;
        String question;
        String answer;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        System.out.println("Available questions:");
        System.out.println();

        // making a delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from `" + lessonId + "-" + quizId + "`;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                questionId = result.getInt("questionId");
                question = result.getString("question");
                answer = result.getString("answer");

                System.out.println("Question Id: " + questionId + "      Question: " + question + "      Answer: " + answer);
                count++;
            }

            System.out.println(count + " result found...");
            System.out.println();

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void addStudentToAnswerTable(int lessonId, int quizId) {
        int questionId;
        int studentId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            // selecting students of a lesson
            String sql = "select * from student where id = " + lessonId + ";";

            Statement readStudentTableStmt = Conn.createStatement();
            ResultSet resultStudentOfLesson = readStudentTableStmt.executeQuery(sql);

            // selecting questionIds from question table
            sql = "select questionId from `" + lessonId + "-" + quizId + "`;";

            Statement readQuestionIdStmt = Conn.createStatement();
            ResultSet resultQuestionId = readQuestionIdStmt.executeQuery(sql);

            // writing studentIds for each question into answer table
            while (resultQuestionId.next()) {
                questionId = resultQuestionId.getInt("questionId");
                while (resultStudentOfLesson.next()) {
                    studentId = resultStudentOfLesson.getInt("studentId");
                    PreparedStatement writeStmt = Conn.prepareStatement("insert into `a-" + lessonId + "-" + quizId + "` (questionId, studentId) values (?, ?);");
                    writeStmt.setInt(1, questionId);
                    writeStmt.setInt(2, studentId);
                    int j = writeStmt.executeUpdate();
                    System.out.println(j + "record inserted..");
                }
            }

            readQuestionIdStmt.close();
            readStudentTableStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void addQuestion(int lessonId, int quizId, String[] question, String[] answer) {
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);

            for (int i = 0; i < question.length; i++) {
                PreparedStatement writeStmt = Conn.prepareStatement("insert into `" + lessonId + "-" + quizId + "` (question, answer) values (?, ?);");
                writeStmt.setString(1, question[i]);
                writeStmt.setString(2, answer[i]);
                int j = writeStmt.executeUpdate();
                System.out.println(j + "record inserted..");
            }

            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void editQuestion(int lessonId, int quizId, int questionId, String newQuestion, String newAnswer) {
        boolean detect = false;
        int mainQuestionId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from `" + lessonId + "-" + quizId + "`;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the id
            while (result.next()) {
                mainQuestionId = result.getInt("questionId");

                if (mainQuestionId == questionId) {
                    detect = true;
                    break;
                }
            }

            // if lesson exists
            if (detect) {
                sql = "update `" + lessonId + "-" + quizId + "` set question = ?, answer = ? where questionId = " + questionId + ";";

                PreparedStatement updateStmt = Conn.prepareStatement(sql);
                updateStmt.setString(1, newQuestion);
                updateStmt.setString(2, newAnswer);

                int rows = updateStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("A row has been updated...");
                }

                updateStmt.close();
            } else {
                System.out.println("Question does not exist...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int lessonId, int quizId, int questionId) {
        boolean detect = false;
        int mainQuestionId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from `" + lessonId + "-" + quizId + "`;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the id
            while (result.next()) {
                mainQuestionId = result.getInt("questionId");

                if (mainQuestionId == questionId) {
                    detect = true;
                    break;
                }
            }

            // if question exists
            if (detect) {
                sql = "delete from `" + lessonId + "-" + quizId + "` where questionId = " + questionId + ";";

                Statement deleteStmt = Conn.createStatement();

                int rows = deleteStmt.executeUpdate(sql);
                System.out.println(rows + "rows effected...");
            } else {
                System.out.println("Question does not exist...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }

    }

    public void addQuiz(int lessonId, String quizName, String[] question, String[] answer) {
        boolean detect = false;
        int quizId = 0;
        String mainQuizName;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from quizzes where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the unique name
            while (result.next()) {
                mainQuizName = result.getString("quizName");
                quizId = result.getInt("quizId");

                if (mainQuizName.equals(quizName)) {
                    detect = true;
                    break;
                }
            }

            // if the quiz created before
            if (detect) {
                System.out.println("Quiz exists...");
            } else {
                sql = "insert into quizzes (id, quizName) values (?, ?);";

                PreparedStatement writeStmt = Conn.prepareStatement(sql);
                writeStmt.setInt(1, lessonId);
                writeStmt.setString(2, quizName);

                int rows = writeStmt.executeUpdate();
                if (rows > 0)
                    System.out.println("Quiz created...");

                sql = "select * from quizzes where id = " + lessonId + ";";

                Statement readStmtAgain = Conn.createStatement();
                ResultSet secondResult = readStmtAgain.executeQuery(sql);


                // find the quizId for creating a unique table for questions
                while (secondResult.next()) {
                    mainQuizName = secondResult.getString("quizName");
                    quizId = secondResult.getInt("quizId");

                    if (mainQuizName.equals(quizName))
                        break;
                }

                // add question table (create table for a quiz)
                sql = "create table `" + lessonId + "-" + quizId + "` (" +
                        "questionId int(11) auto_increment primary key not null," +
                        " question varchar(255) not null," +
                        " answer varchar(255) not null" +
                        ");";

                Statement createStmt = Conn.createStatement();
                createStmt.executeUpdate(sql);
                System.out.println("The question table created...");
                System.out.println();

                // add answer table
                sql = "create table `a-" + lessonId + "-" + quizId + "` (" +
                        "questionId int(11) not null," +
                        " studentId int(11) not null," +
                        " studentAnswer varchar(255)," +
                        " foreign key (questionId) references `" + lessonId + "-" + quizId + "`(questionId)" +
                        ");";

                createStmt = Conn.createStatement();
                createStmt.executeUpdate(sql);
                System.out.println("The answer table created...");
                System.out.println();

                // adding questions and answers
                addQuestion(lessonId, quizId, question, answer);
                System.out.println("Questions were added to the question table...");
                System.out.println();

                // adding students to answer table
                addStudentToAnswerTable(lessonId, quizId);
                System.out.println("StudentIds were added to the answer table...");
                System.out.println();

                createStmt.close();
                writeStmt.close();
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void editQuiz(int lessonId, int quizId, String newName) {
        boolean detect = false;
        int mainQuizId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from quizzes where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the unique name
            while (result.next()) {
                mainQuizId = result.getInt("quizId");

                if (mainQuizId == quizId) {
                    detect = true;
                    break;
                }
            }

            // if the quiz exists
            if (detect) {
                sql = "update quizzes set quizName = ? where quizId = " + quizId + ";";

                PreparedStatement writeStmt = Conn.prepareStatement(sql);
                writeStmt.setString(1, newName);

                int rows = writeStmt.executeUpdate();
                if (rows > 0)
                    System.out.println("Quiz updated...");

                writeStmt.close();
            } else {
                System.out.println("Quiz not exists...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

    public void deleteQuiz(int lessonId, int quizId) {
        boolean detect = false;
        int mainQuizId;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from quizzes where id = " + lessonId + ";";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            // check the id
            while (result.next()) {
                mainQuizId = result.getInt("quizId");

                if (mainQuizId == quizId) {
                    detect = true;
                    break;
                }
            }

            // if the quiz exists
            if (detect) {
                // deleting quiz from quizzes table
                sql = "delete from quizzes where quizId = " + quizId + ";";

                Statement deleteStmt = Conn.createStatement();

                int rows = deleteStmt.executeUpdate(sql);
                System.out.println(rows + " Quiz removed...");

                // dropping question table
                sql = "drop table `" + lessonId + "-" + quizId + "`;";
                readStmt.executeUpdate(sql);

                // dropping answer table
                sql = "drop table `a-" + lessonId + "-" + quizId + "`;";
                readStmt.executeUpdate(sql);

                System.out.println("The information was completely removed...");

                deleteStmt.close();
            } else {
                System.out.println("Quiz not exists...");
            }

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

}
