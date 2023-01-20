import java.sql.*;
import java.util.Scanner;

class User {
    public void loginMenu() {
        System.out.println("=================================================");
        System.out.println("| Login menu:                                   |");
        System.out.println("=================================================");
        System.out.println("| [1] Login As Manager                          |");
        System.out.println("| [2] Login As Student                          |");
        System.out.println("| [3] Exit                                      |");
        System.out.println("=================================================");
    }
}

class Manager extends User {
    public void managerLogin(int id, String pass) {
        Scanner readInput = new Scanner(System.in);

        boolean detect = false;
        int input = 0;
        int mainId;
        int newId;
        String name;
        String mainPass;
        String confirmPass;
        String newPass;
        String quizName;
        String lessonName;
        String professorName;
        String newLessonName;
        String newProfessorName;
        String newQuestion;
        String newAnswer;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from manager;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                mainId = result.getInt("managerId");
                mainPass = result.getString("password");

                // check if user exists
                if (mainId == id) {
                    detect = true;
                    if (mainPass.equals(pass)) {
                        // manager logged in
                        // using Management class
                        Management manageObj = new Management();

                        while (true) {
                            manageObj.managementMenu();
                            input = readInput.nextInt();

                            // select from managementMenu
                            switch (input) {
                                // Add, Edit or Delete Professor
                                case 1:
                                    do {
                                        manageObj.professorCRUDMenu();
                                        input = readInput.nextInt();

                                        // select from professorCRUDMenu
                                        switch (input) {
                                            // add
                                            case 1:
                                                System.out.print("Enter name of the professor: ");
                                                name = readInput.next();
                                                System.out.print("Enter Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter Password: ");
                                                pass = readInput.next();
                                                System.out.print("Confirm Password: ");
                                                confirmPass = readInput.next();

                                                manageObj.add(name, id, pass, confirmPass, "professorinfo", "professorId");
                                                break;
                                            // edit
                                            case 2:
                                                System.out.print("Enter Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter Password: ");
                                                pass = readInput.next();
                                                System.out.print("Enter new Name: ");
                                                name = readInput.next();
                                                System.out.print("Enter new Id: ");
                                                newId = readInput.nextInt();
                                                System.out.print("Enter new Password: ");
                                                newPass = readInput.next();

                                                manageObj.edit(id, pass, name, newId, newPass, "professorinfo", "professorId");
                                                break;
                                            // delete
                                            case 3:
                                                System.out.print("Enter Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter Password: ");
                                                pass = readInput.next();

                                                manageObj.delete(id, pass, "professorinfo", "professorId");
                                                break;
                                        }

                                        // back
                                    } while (input != 4);

                                    break;
                                // Add, Edit or Delete Lesson
                                case 2:
                                    do {
                                        manageObj.lessonCRUDMenu();
                                        input = readInput.nextInt();

                                        // select from lessonCRUDMenu
                                        switch (input) {
                                            // add
                                            case 1:
                                                // available lessons
                                                manageObj.showLesson();

                                                // available professors
                                                manageObj.showProfessor();

                                                System.out.print("Enter Lesson Name: ");
                                                lessonName = readInput.next();
                                                System.out.print("Enter Professor Name: ");
                                                professorName = readInput.next();
                                                System.out.print("Enter ProfessorId: ");
                                                id = readInput.nextInt();

                                                manageObj.add(id, lessonName, professorName);
                                                break;
                                            // edit
                                            case 2:
                                                // available lessons
                                                manageObj.showLesson();

                                                // available professors
                                                manageObj.showProfessor();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter new Lesson Name: ");
                                                newLessonName = readInput.next();
                                                System.out.print("Enter new Professor Name: ");
                                                newProfessorName = readInput.next();
                                                System.out.print("Enter new Professor Id: ");
                                                newId = readInput.nextInt();

                                                manageObj.edit(id, newLessonName, newProfessorName, newId);
                                                break;
                                            // delete
                                            case 3:
                                                // available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // delete lesson from lesson table
                                                manageObj.delete(id);
                                                manageObj.deleteAllQuizzes(id);
                                                break;
                                        }

                                        // back
                                    } while (input != 4);

                                    break;
                                // Add, Edit or Delete Student
                                case 3:
                                    do {
                                        manageObj.studentCRUDMenu();
                                        input = readInput.nextInt();

                                        // select from studentCRUDMenu
                                        switch (input) {
                                            // add
                                            case 1:
                                                System.out.print("Enter name of the student: ");
                                                name = readInput.next();
                                                System.out.print("Enter Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter Password: ");
                                                pass = readInput.next();
                                                System.out.print("Confirm Password: ");
                                                confirmPass = readInput.next();

                                                manageObj.add(name, id, pass, confirmPass, "studentinfo", "studentId");
                                                break;
                                            // edit
                                            case 2:
                                                System.out.print("Enter Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter Password: ");
                                                pass = readInput.next();
                                                System.out.print("Enter new Name: ");
                                                name = readInput.next();
                                                System.out.print("Enter new Id: ");
                                                newId = readInput.nextInt();
                                                System.out.print("Enter new Password: ");
                                                newPass = readInput.next();

                                                manageObj.edit(id, pass, name, newId, newPass, "studentinfo", "studentId");
                                                break;
                                            // delete
                                            case 3:
                                                System.out.print("Enter Id: ");
                                                id = readInput.nextInt();
                                                System.out.print("Enter Password: ");
                                                pass = readInput.next();

                                                manageObj.delete(id, pass, "studentinfo", "studentId");
                                                break;
                                        }

                                        // back
                                    } while (input != 4);

                                    break;
                                // Add or Delete Student/Lesson
                                case 4:
                                    do {
                                        manageObj.studentLessonCRUDMenu();
                                        input = readInput.nextInt();

                                        // select from studentLessonCRUDMenu
                                        switch (input) {
                                            // add
                                            case 1:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter lessonId: ");
                                                id = readInput.nextInt();

                                                // show available students in selected lesson
                                                manageObj.showStudentFromLesson(id);

                                                System.out.print("Enter studentId: ");
                                                newId = readInput.nextInt();




                                                break;
                                            // delete
                                            case 2:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter lessonId: ");
                                                id = readInput.nextInt();

                                                // show available students in selected lesson
                                                manageObj.showStudentFromLesson(id);

                                                System.out.print("Enter studentId: ");
                                                newId = readInput.nextInt();

                                                manageObj.deleteStudentFromLesson(id, newId);
                                                break;
                                        }
                                        // back
                                    } while (input != 3);
                                    break;
                                // Add, Edit or Delete Quiz
                                case 5:
                                    do {
                                        manageObj.quizCRUDMenu();
                                        input = readInput.nextInt();

                                        // select from quizCRUDMenu
                                        switch (input) {
                                            // add
                                            case 1:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // show available quizzes
                                                manageObj.showQuizzes(id);

                                                System.out.print("Enter Quiz Name: ");
                                                quizName = readInput.next();

                                                System.out.print("Enter the number of questions: ");
                                                input = readInput.nextInt();
                                                String[] question = new String[input];
                                                String[] answer = new String[input];

                                                // read questions and answers
                                                for (int i = 0; i < input; i++) {
                                                    System.out.print("Enter question: ");
                                                    question[i] = readInput.next();
                                                    System.out.print("Enter answer: ");
                                                    answer[i] = readInput.next();
                                                }

                                                manageObj.addQuiz(id, quizName, question, answer);
                                                // this is for solving an error (if the user sets input = 4,
                                                // then it will cause jumping out from the do-while loop
                                                input = 0;
                                                break;
                                            // edit
                                            case 2:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // show available quizzes
                                                manageObj.showQuizzes(id);

                                                System.out.print("Enter Quiz Id: ");
                                                mainId = readInput.nextInt();

                                                // set new name for the quiz
                                                System.out.print("Enter new name for quiz: ");
                                                newPass = readInput.next();

                                                manageObj.editQuiz(id, mainId, newPass);
                                                break;
                                            // delete
                                            case 3:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // show available quizzes
                                                manageObj.showQuizzes(id);

                                                System.out.print("Enter Quiz Id: ");
                                                mainId = readInput.nextInt();

                                                manageObj.deleteQuiz(id, mainId);
                                                break;
                                        }

                                        // back
                                    } while (input != 4);

                                    break;
                                // Add, Edit or Delete Questions
                                case 6:
                                    do {
                                        manageObj.questionCRUDMenu();
                                        input = readInput.nextInt();

                                        switch (input) {
                                            // add
                                            case 1:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // show available quizzes
                                                manageObj.showQuizzes(id);

                                                System.out.print("Enter Quiz Id: ");
                                                mainId = readInput.nextInt();

                                                // show available questions
                                                manageObj.showQuestion(id, mainId);

                                                System.out.print("Enter the number of questions you want to add: ");
                                                input = readInput.nextInt();
                                                String[] question = new String[input];
                                                String[] answer = new String[input];

                                                // read questions and answers
                                                for (int i = 0; i < input; i++) {
                                                    System.out.print("Enter question: ");
                                                    question[i] = readInput.next();
                                                    System.out.print("Enter answer: ");
                                                    answer[i] = readInput.next();
                                                }

                                                // adding new questions
                                                manageObj.addQuestion(id, mainId, question, answer);
                                                break;

                                            // edit
                                            case 2:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // show available quizzes
                                                manageObj.showQuizzes(id);

                                                System.out.print("Enter Quiz Id: ");
                                                mainId = readInput.nextInt();

                                                // show available questions
                                                manageObj.showQuestion(id, mainId);

                                                System.out.print("Enter Question Id: ");
                                                newId = readInput.nextInt();

                                                // read new question and answer
                                                System.out.print("Enter new question: ");
                                                newQuestion = readInput.next();
                                                System.out.print("Enter new answer: ");
                                                newAnswer = readInput.next();

                                                manageObj.editQuestion(id, mainId, newId, newQuestion, newAnswer);
                                                break;
                                            // delete
                                            case 3:
                                                // show available lessons
                                                manageObj.showLesson();

                                                System.out.print("Enter Lesson Id: ");
                                                id = readInput.nextInt();

                                                // show available quizzes
                                                manageObj.showQuizzes(id);

                                                System.out.print("Enter Quiz Id: ");
                                                mainId = readInput.nextInt();

                                                // show available questions
                                                manageObj.showQuestion(id, mainId);

                                                System.out.print("Enter Question Id: ");
                                                newId = readInput.nextInt();

                                                manageObj.deleteQuestion(id, mainId, newId);
                                                break;
                                        }

                                        // back
                                    } while (input != 4);

                                    break;
                                // Inquiry about Quizzes
                                case 7:
                                    input = readInput.nextInt();

                                    break;
                                // Inquiry about Lessons
                                case 8:
                                    input = readInput.nextInt();
                                    break;
                                // exit
                                case 9:
                                    System.exit(0);
                            }


                        }

                    } else {
                        System.out.println("Wrong password...");
                    }
                }
            }

            if (!detect)
                System.out.println("No manager was identified...");

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

}

class Stud extends User {

    public void studentLogin(int id, String pass) {
        Scanner readInput = new Scanner(System.in);

        boolean detect = false;
        int input;
        int mainId;
        String mainPass;
        String url = "jdbc:mysql://localhost:3306/quizmanagement";
        String user = "root";
        String password = "mehdi200782";

        try {
            Connection Conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from studentinfo;";

            Statement readStmt = Conn.createStatement();
            ResultSet result = readStmt.executeQuery(sql);

            while (result.next()) {
                mainId = result.getInt("studentId");
                mainPass = result.getString("password");

                // check if user exists
                if (mainId == id) {
                    detect = true;
                    if (mainPass.equals(pass)) {
                        // student logged in
                        // using Student class
                        Student studObj = new Student();

                        while (true){
                            studObj.studentMenu();
                            input = readInput.nextInt();

                            // select from studentMenu
                            switch (input) {
                                // quizzes
                                case 1:
                                    break;
                                // history of passed quizzes
                                case 2:
                                    break;
                                case 3:
                                    System.exit(0);
                            }
                        }

                    } else {
                        System.out.println("Wrong password...");
                    }
                }
            }

            // if no user detected
            if (!detect)
                System.out.println("No student was identified...");

            readStmt.close();
            Conn.close();
        } catch (Exception e) {
            System.out.println("Can't connect to database...");
            e.printStackTrace();
        }
    }

}
