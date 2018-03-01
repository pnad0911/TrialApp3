import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Duy Pham
 * @version 1.01 - 05/17/2014
 * @email pnad0911@gmail.com
 * @prgm.usage A19014
 * @assignment.number A19014
 * @see <a href='http://jcouture.net/cisc190/A19014.php'>Program Specification</a>
 * @see <br><a href='http://docs.oracle.com/javase/7/docs/technotes/guides/Javadoc/index.html'>Javadoc Documentation</a>
 */

public class A19014 extends JDialog {
    private static String strData = "GRADEBOOK";
    private static String strTableGrades = "Grades";
    private static String strTableRoster = "Roster";
    private static ArrayList<String> aryGrades = new ArrayList<String>();
    private static ArrayList<String> aryRoster = new ArrayList<String>();
    private JPanel contentPane;
    private JComboBox comboBox1;
    private JButton createDBAndTablesButton;
    private JButton populateGradesButton;
    private JButton populateRosterButton;
    private JButton populateComboBoxButton;
    private JButton createReportButton;
    private JLabel lblEmail;
    private JLabel lblStuID;
    private JLabel lblQuiz;
    private JLabel lblAsgn;
    private JLabel lblTotal;
    private JLabel lblLetter;
    private JButton buttonOK;
    private JButton buttonCancel;

    public A19014() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        createDBAndTablesButton.addActionListener(new createDBAndTablesButton1());
        populateGradesButton.addActionListener(new populateGrades());
        populateRosterButton.addActionListener(new populateRoster());
        populateComboBoxButton.addActionListener(new populateCombo());
        comboBox1.addActionListener(new Comboseclect());
        createReportButton.addActionListener(new create());
    }

    public static void main(String[] args) throws Exception {
        A19014 dialog = new A19014();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Create DB and Tables
     * @throws Exception
     */
    public static void createDBAndTablesButton() throws Exception
    {
        DBUpdt db = new DBUpdt();
        //Create a database called GRADEBOOK.
        db.openConnection(strData);
        //Drop any existing tables named GRADES or ROSTER
        db.deleteAll(strData, strTableGrades);
        db.deleteAll(strData, strTableRoster);
        //Create a table called GRADES
        db.execute(stringSQL(strTableGrades, "data/Grades.txt"));
        //Create a table called ROSTER
        db.execute(stringSQL(strTableRoster, "data/Roster.txt"));
        db.close();
    }

    /**
     * Create stringSQL
     * @param strTable - table name
     * @param strFileName - file name
     * @return - String SQL
     * @throws Exception
     */
    public static String stringSQL(String strTable, String strFileName) throws Exception {
        INET net = new INET();
        String strPage;
        //check to see if the file exists on disk
        Boolean boTest = net.fileExist(strFileName);
        if (boTest == false) {
            if (strFileName.equals("data/Grades.txt")) {
                strPage = net.getURLRaw("http://faculty.sdmiramar.edu/jcouture/2014sp/cisc190/webct/manual/schema-grades.txt");
                //strTest = net.getPREData(strPage);
                net.saveToFile(strFileName, strPage);
            } else if (strFileName.equals("data/Roster.txt")) {
                //download the Winds Aloft data from the NWS and save it
                strPage = net.getURLRaw("http://faculty.sdmiramar.edu/jcouture/2014sp/cisc190/webct/manual/schema-roster.txt");
                //strTest = net.getPREData(strPage);
                net.saveToFile(strFileName, strPage);
            }
        }
        String str = "";
        File myFile = new File(strFileName);
        if (myFile.exists()) {
            int intCount = 0;
            Scanner inputFile = new Scanner(myFile);
            StringBuilder strTGD = new StringBuilder("CREATE TABLE " + strTable + " (");
            while (inputFile.hasNext()) {
                String strLine;
                strLine = inputFile.nextLine();
                if (intCount > 9) {
                    String[] tokens = strLine.split("\t");
                    strTGD.append(tokens[0] + " ");
                    if (strTable.equals(strTableGrades)) {
                        aryGrades.add(tokens[0]);
                    } else {
                        aryRoster.add(tokens[0]);
                    }
                    String strL=tokens[1].substring(3, tokens[1].length());
                    StringBuilder strL1=new StringBuilder(strL);
                    if(strL.indexOf("l")>-1)
                    {
                        strL1.deleteCharAt(strL.indexOf("l"));
                    }
                    strTGD.append(strL1.toString().toUpperCase());
                    if (tokens[2].trim().equals("NO") & intCount == 10) {
                        strTGD.append(" NOT NULL");
                        if (tokens[3].trim().equals("PRI")) {
                            strTGD.append(" PRIMARY KEY, ");
                        } else {
                            strTGD.append(", ");
                        }
                    } else {
                        strTGD.append(", ");
                    }
                }
                intCount++;
            }
            strTGD.replace(strTGD.length() - 2, strTGD.length() - 1, ")");
            str = strTGD.toString();
        }

        return str;
    }

    /**
     * populate your GRADES table with the data from the grades file
     * @throws Exception
     */
    public static void popGrades() throws Exception {
        String strFileName = "data/dataGrades.txt";
        INET net = new INET();
        String strTest;
        String strPage;
        //check to see if the file exists on disk
        Boolean boTest = net.fileExist(strFileName);
        if (boTest == false) {
            //download the Winds Aloft data from the NWS and save it
            strPage = net.getURLRaw("http://faculty.sdmiramar.edu/jcouture/2014sp/cisc190/webct/manual/data-grades.txt");
            //strTest = net.getPREData(strPage);
            net.saveToFile(strFileName, strPage);
        }
        DBUpdt db = new DBUpdt();
        File myFile = new File(strFileName);
        if (myFile.exists()) {
            String strLine;
            Scanner inputFile = new Scanner(myFile);
            db.openConnection(strData);
            while (inputFile.hasNext()) {
                strLine = inputFile.nextLine();
                String[] tokens = strLine.split(";");
                //add stuid to database
                db.addRecord(strTableGrades, aryGrades.get(0), tokens[0]);
                int intCount = 0;
                //add the rest of data
                for (String s : tokens) {
                    if (intCount > 0) {
                        db.setField(strTableGrades, aryGrades.get(0), tokens[0], aryGrades.get(intCount), tokens[intCount]);
                    }
                    intCount++;
                }
                db.close();
            }
        }
    }

    /**
     * populate your ROSTER table with the data from the roster file
     * @throws Exception
     */
    public static void popRoster() throws Exception {
        String strFileName = "data/dataRoster.txt";
        INET net = new INET();
        String strTest;
        String strPage;
        //check to see if the file exists on disk
        Boolean boTest = net.fileExist(strFileName);
        if (boTest == false) {
            //download the Winds Aloft data from the NWS and save it
            strPage = net.getURLRaw("http://faculty.sdmiramar.edu/jcouture/2014sp/cisc190/webct/manual/data-roster.txt");
            //strTest = net.getPREData(strPage);
            net.saveToFile(strFileName, strPage);
        }
        DBUpdt db = new DBUpdt();
        File myFile = new File(strFileName);
        if (myFile.exists()) {
            String strLine;
            Scanner inputFile = new Scanner(myFile);
            db.openConnection(strData);
            while (inputFile.hasNext()) {
                strLine = inputFile.nextLine();
                String[] tokens = strLine.split(";");
                //add stuid to database
                db.addRecord(strTableRoster, aryRoster.get(0), tokens[0]);
                int intCount = 0;
                for (String s : tokens) {
                    if (intCount > 0) {
                        db.setField(strTableRoster, aryRoster.get(0), tokens[0], aryRoster.get(intCount), tokens[intCount]);
                    }
                    intCount++;
                }
                db.close();
            }
        }
    }

    /**
     * getQuizTotal(strStuID) that will return a "float" representing the total points of the student's quizzes
     * @param strStuID - student ID
     * @return - Total points of student's quiz
     */
    public static float getQuizTotal(String strStuID) {
        DBUpdt db = new DBUpdt();
        db.openConnection(strData);
        //find stuid
        db.query("SELECT * FROM " + strTableGrades + " WHERE stuid = '" + strStuID + "'");
        int intCou;
        float fl;
        float tt = 0000;
        while (db.moreRecords()) {
            for (intCou = 21; intCou < 36; intCou++) {
                String strS = db.getField(aryGrades.get(intCou));
                //Divide that score by 100 and multiply it by 2
                fl = (Float.valueOf(strS) / 100) * 2;
                tt = tt + fl;
            }
        }
        DecimalFormat format = new DecimalFormat("###.##");
        float fltt=Float.valueOf(format.format(tt));
        return fltt;
    }

    /**
     * getAsgnTotal(strStuID) that will return a "float"  representing the total points of the student's assignments
     * @param strStuID - student ID
     * @return -  total points of the student's assignments
     */
    public static float getAsgnTotal(String strStuID) {
        int intCou;
        int intCou1 = 0;
        float fl;
        float tt = 0000;
        //Initialize an array of point values for each assignment using the JCouture.net course overview page as your guide
        String[] ary = new String[15];
        String strValueAsgn = "22233344455679";
        int intC;
        for (intC = 0; intC < strValueAsgn.length(); intC++) {
            ary[intC] = String.valueOf(strValueAsgn.charAt(intC));
        }
        ary[14] = "11";
        DBUpdt db = new DBUpdt();
        db.openConnection(strData);
        //Look up the student grades in the GRADES table
        db.query("SELECT * FROM " + strTableGrades + " WHERE stuid = '" + strStuID + "'");
        while (db.moreRecords()) {
            for (intCou = 6; intCou < 21; intCou++) {
                //Calculate the number points the student earned by taking the assignment score and dividing it by 100 then
                // multiply it by the number of points for that assignment
                String strS = db.getField(aryGrades.get(intCou));
                fl = (Float.valueOf(ary[intCou1]) / 100) * (Float.valueOf(strS));
                tt = tt + fl;
                intCou1++;
            }
        }
        DecimalFormat format = new DecimalFormat("###.##");
        float fltt=Float.valueOf(format.format(tt));
        return fltt;
    }

    /**
     * getLetterGrade(fltTotal) that accepts the total point value as a float and
     * returns a single letter string representing the ordinal grade
     * @param fltTotal - total points
     * @return - letter grade
     */
    public static String getLetterGrade(float fltTotal) {
        String strLetterGrade = "";
        if (fltTotal < 60) {
            strLetterGrade = "F";
        } else if (fltTotal >= 60 & fltTotal < 70) {
            strLetterGrade = "D";
        } else if (fltTotal >= 70 & fltTotal < 80) {
            strLetterGrade = "C";
        } else if (fltTotal >= 80 & fltTotal < 90) {
            strLetterGrade = "B";
        } else if (fltTotal >= 90) {
            strLetterGrade = "A";
        }
        return strLetterGrade;
    }

    /**
     * Create Report
     */
    public static void CreateReport() {
        DBUpdt db = new DBUpdt();
        db.openConnection(strData);
        db.query("SELECT roster.*, grades.* FROM " + strTableRoster + " INNER JOIN grades ON roster.stuid=grades.stuid ORDER BY lastname,firstname");
        //write a report to a file called SEMESTER.TXT
        File file = new File("data/SEMESTER.TXT");
        PrintWriter output = null;
        try {
            // write a file to disk using the filename
            output = new PrintWriter(file);
        } catch (Exception egg) {
            egg.printStackTrace();
        }
        while (db.moreRecords()) {
            DecimalFormat format = new DecimalFormat("#0.00");
            float flAsgn = getAsgnTotal(db.getField("stuid"));
            float flQuiz = getQuizTotal(db.getField("stuid"));
            output.println(db.getField("lastname").trim() + ";" + db.getField("firstname").trim()
                    + ";" + db.getField("stuid").trim() + ";" + db.getField("email").trim() + ";" +
                    String.valueOf(format.format(flQuiz)) + ";" + String.valueOf(format.format(flAsgn)) + ";" +
                    String.valueOf(format.format((flAsgn + flQuiz))) + ";" + getLetterGrade(flAsgn + flQuiz));
        }
        db.close();
        output.close();
    }

    public void onCancel() {
// add your code here if necessary
        dispose();
    }

    /**
     * only acute when user choose
     * @param strT - selection String
     * @return - boolean
     */
    public Boolean blnChange(String strT) {
        Boolean boChange;
        //only acute when user choose
        if (strT.equals(" ")) {
            boChange = false;
        } else {
            boChange = true;
        }
        return boChange;
    }

    /**
     * populate combo box
     */
    public void PopCombo() {
        DBUpdt db = new DBUpdt();
        db.openConnection(strData);
        db.query("SELECT * FROM Roster ORDER BY lastname");
        comboBox1.addItem(" ");
        while (db.moreRecords()) {
            String strS = db.getField("lastname");
            comboBox1.addItem(strS.trim() + "," + db.getField("firstname").trim());
        }
    }

    public class createDBAndTablesButton1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                createDBAndTablesButton();
            } catch (Exception p) {

            }
        }
    }

    public class populateGrades implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                popGrades();
            } catch (Exception y) {

            }
        }
    }

    private class populateRoster implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                popRoster();
            } catch (Exception y) {

            }
        }
    }

    public class populateCombo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                PopCombo();
            } catch (Exception y) {

            }
        }
    }

    public class Comboseclect implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selection13;
            selection13 = (String) comboBox1.getSelectedItem();
            String[] tokens = selection13.split(",");
            if (blnChange(selection13) == true) {
                DBUpdt db = new DBUpdt();
                db.openConnection(strData);
                db.query("SELECT * FROM " + strTableRoster + " WHERE lastname = '" + tokens[0] + "' AND firstname ='" + tokens[1] + "'");
                while (db.moreRecords()) {
                    String strID13 = db.getField("stuid");
                    lblStuID.setText(strID13);
                    DecimalFormat format = new DecimalFormat("#0.00");
                    float flAsgn = getAsgnTotal(strID13);
                    float flQuiz = getQuizTotal(strID13);
                    lblAsgn.setText(String.valueOf(format.format(flAsgn)));
                    lblQuiz.setText(String.valueOf(format.format(flQuiz)));
                    lblTotal.setText(String.valueOf(format.format(flAsgn + flQuiz)));
                    lblLetter.setText(getLetterGrade((flAsgn + flQuiz)));
                    lblEmail.setText(db.getField("email"));
                }
            }
        }
    }

    private class create implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //bring up a second form that we will call "Create Report Form".
            CREATEREPORTFORM cr=new CREATEREPORTFORM();
            cr.pack();
            cr.setVisible(true);
        }
    }
}
