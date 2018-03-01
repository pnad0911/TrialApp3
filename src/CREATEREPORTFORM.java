import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Duy Pham
 * @version 1.01 - 05/17/2014
 * @email pnad0911@gmail.com
 * @prgm.usage A19014
 * @assignment.number A19014
 * @see <a href='http://jcouture.net/cisc190/A19014.php'>Program Specification</a>
 * @see <br><a href='http://docs.oracle.com/javase/7/docs/technotes/guides/Javadoc/index.html'>Javadoc Documentation</a>
 */

public class CREATEREPORTFORM extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonReturn;
    private JCheckBox studentIDCheckBox;
    private JCheckBox lastnameTheFirstnameCheckBox;
    private JCheckBox firstnameTheLastnameCheckBox;
    private JCheckBox quizTotalCheckBox;
    private JCheckBox asgnTotalCheckBox;
    private JCheckBox pointsTotalCheckBox;
    private JCheckBox letterGradeWithrawnCheckBox;
    private JCheckBox fedStatusCheckBox;
    private JRadioButton commaRadioButton;
    private JRadioButton semiColonRadioButton;
    private JRadioButton tabRadioButton;
    private JRadioButton allStudentsRadioButton;
    private JRadioButton onlyCurrentStudentsRadioButton;
    private JRadioButton onlyFedStudentsRadioButton;
    private JButton createFileButton;
    private JRadioButton studentIDRadioButton;
    private JRadioButton lastnameFirstnameRadioButton;
    private JRadioButton pointsTotalRadioButton;
    private JButton fileNameButton;
    public JLabel lblFileName;
    private StringBuilder strOutput=new StringBuilder();
    public String strId="0";
    public String strFL="0";
    public String strLF="0";
    public String strQ="0";
    public String strA="0";
    public String strP="0";
    public String strLG="0";
    public String strF="0";
    public String strFieldDelimeter=";";
    public String strSelect="0";
    public  String strSortBy="0";
    public ArrayList<String> ary=new ArrayList<String>();
    public String[] ary2=new String[200];

    public CREATEREPORTFORM()
    {
        setContentPane(contentPane);
        setTitle("Create Report Form");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //the  "Return to Main Form" button will close the Create Report Form and bring back to the main form
        buttonReturn.addActionListener(new ActionListener() {
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
        //createFileButton.addActionListener(new test());
        studentIDCheckBox.addItemListener(new CheckBoxListener());
        lastnameTheFirstnameCheckBox.addItemListener(new CheckBoxListener());
        firstnameTheLastnameCheckBox.addItemListener(new CheckBoxListener());
        quizTotalCheckBox.addItemListener(new CheckBoxListener());
        asgnTotalCheckBox.addItemListener(new CheckBoxListener());
        pointsTotalCheckBox.addItemListener(new CheckBoxListener());
        letterGradeWithrawnCheckBox.addItemListener(new CheckBoxListener());
        fedStatusCheckBox.addItemListener(new CheckBoxListener());
        commaRadioButton.addActionListener(new RadioButtonListener());
        semiColonRadioButton.addActionListener(new RadioButtonListener());
        tabRadioButton.addActionListener(new RadioButtonListener());
        studentIDRadioButton.addActionListener(new RadioButtonListener());
        lastnameFirstnameRadioButton.addActionListener(new RadioButtonListener());
        pointsTotalRadioButton.addActionListener(new RadioButtonListener());
        allStudentsRadioButton.addActionListener(new RadioButtonListener());
        onlyCurrentStudentsRadioButton.addActionListener(new RadioButtonListener());
        onlyFedStudentsRadioButton.addActionListener(new RadioButtonListener());
        fileNameButton.addActionListener(new FileName());
        createFileButton.addActionListener(new createFile());
    }

    private void onCancel()
    {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args)
    {
        CREATEREPORTFORM dialog = new CREATEREPORTFORM();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Public inner class that handle the event when
     * the user clicks one of the check boxes
     */
    public class CheckBoxListener implements ItemListener
    {
        public void itemStateChanged(ItemEvent g)
        {
            if(g.getSource()==studentIDCheckBox)
            {
                //if studentIDCheckBox is selected
                if(g.getStateChange()==ItemEvent.SELECTED)
                {
                    strId="1";

                }
                else
                {
                    strId="0";

                }
            }
            if(g.getSource()==lastnameTheFirstnameCheckBox)
            {
                //if lastnameTheFirstnameCheckBox is selected
                if(lastnameTheFirstnameCheckBox.isSelected())
                {
                    firstnameTheLastnameCheckBox.setSelected(false);
                    strLF="1";
                    strFL="0";
                    ary.add(strLF);
                }
            }
            if (g.getSource()==firstnameTheLastnameCheckBox)
            {
                //if firstnameTheLastnameCheckBox is selected
                if(firstnameTheLastnameCheckBox.isSelected())
                {
                    lastnameTheFirstnameCheckBox.setSelected(false);
                    strFL="1";
                    strLF="0";
                    ary.add(strFL);
                }
            }
            if(g.getSource()==quizTotalCheckBox)
            {
                //if quizTotalCheckBox is selected
                if(quizTotalCheckBox.isSelected())
                {
                    strQ="1";
                    ary.add(strQ);
                }
                else
                {
                    strQ="0";
                }
            }
            if(g.getSource()==asgnTotalCheckBox)
            {
                //asgnTotalCheckBox is selected
                if(asgnTotalCheckBox.isSelected())
                {
                    strA="1";
                    ary.add(strA);
                }
                else
                {
                    strA="0";
                }
            }
            if(g.getSource()==pointsTotalCheckBox)
            {
                //pointsTotalCheckBox is selected
                if(pointsTotalCheckBox.isSelected())
                {
                    strP="1";
                    ary.add(strP);
                }
                else
                {
                    strP="0";
                }
            }
            if(g.getSource()==letterGradeWithrawnCheckBox)
            {
                //letterGradeWithrawnCheckBox is selected
                if(letterGradeWithrawnCheckBox.isSelected())
                {
                    strLG="1";
                    ary.add(strLG);
                }
                else
                {
                    strLG="0";
                }
            }
            if(g.getSource()==fedStatusCheckBox)
            {
                //fedStatusCheckBox is selected
                if(fedStatusCheckBox.isSelected())
                {
                    strF="1";
                    ary.add(strF);
                }
                else
                {
                    strF="0";
                }
            }
        }
    }

    /**
     * Public inner class that handle the event when
     * the user clicks one of the radio buttons
     */
    public class RadioButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==commaRadioButton)
            {
                //commaRadioButton is selected
                if(commaRadioButton.isSelected())
                {
                    semiColonRadioButton.setSelected(false);
                    tabRadioButton.setSelected(false);
                    strFieldDelimeter=",";
                }
            }
            if(e.getSource()==semiColonRadioButton)
            {
                //semiColonRadioButton is selected
                if(semiColonRadioButton.isSelected())
                {
                    commaRadioButton.setSelected(false);
                    tabRadioButton.setSelected(false);
                    strFieldDelimeter=";";
                }
            }
            if(e.getSource()==tabRadioButton)
            {
                //tabRadioButton is selected
                if(tabRadioButton.isSelected())
                {
                    commaRadioButton.setSelected(false);
                    semiColonRadioButton.setSelected(false);
                    strFieldDelimeter="\t";
                }
            }
            if(e.getSource()==allStudentsRadioButton)
            {
                //allStudentsRadioButton is selected
                if(allStudentsRadioButton.isSelected())
                {
                    onlyCurrentStudentsRadioButton.setSelected(false);
                    onlyFedStudentsRadioButton.setSelected(false);
                    strSelect="1";
                }
            }
            if(e.getSource()==onlyCurrentStudentsRadioButton)
            {
                //onlyCurrentStudentsRadioButton is selected
                if(onlyCurrentStudentsRadioButton.isSelected())
                {
                    allStudentsRadioButton.setSelected(false);
                    onlyFedStudentsRadioButton.setSelected(false);
                    strSelect="2";
                }
            }
            if(e.getSource()==onlyFedStudentsRadioButton)
            {
                //onlyFedStudentsRadioButton is selected
                if(onlyFedStudentsRadioButton.isSelected())
                {
                    allStudentsRadioButton.setSelected(false);
                    onlyCurrentStudentsRadioButton.setSelected(false);
                    strSelect="3";
                }
            }
            if(e.getSource()==studentIDRadioButton)
            {
                //studentIDRadioButton is selected
                if(studentIDRadioButton.isSelected())
                {
                    lastnameFirstnameRadioButton.setSelected(false);
                    pointsTotalRadioButton.setSelected(false);
                    strSortBy="1";
                }
            }
            if(e.getSource()==lastnameFirstnameRadioButton)
            {
                //lastnameFirstnameRadioButton is selected
                if(lastnameFirstnameRadioButton.isSelected())
                {
                    studentIDRadioButton.setSelected(false);
                    pointsTotalRadioButton.setSelected(false);
                    strSortBy="2";
                }
            }
            if(e.getSource()==pointsTotalRadioButton)
            {
                //pointsTotalRadioButton is selected
                if(pointsTotalRadioButton.isSelected())
                {
                    studentIDRadioButton.setSelected(false);
                    lastnameFirstnameRadioButton.setSelected(false);
                    strSortBy="3";
                }
            }
        }
    }

    /**
     * Public inner class that handle the event when
     * the user clicks a buttons
     */
    public class FileName implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            dialogBox dia=new dialogBox();
            dia.pack();
            dia.setVisible(true);

            lblFileName.setText(dia.strFileName1);
        }
    }

    /**
     * Public inner class that handle the event when
     * the user clicks a buttons
     */
    private class createFile implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String strFileName=lblFileName.getText();
            //if name is valid
            if(isFilenameValid(strFileName)==true)
            {
                A19014 a19014=new A19014();
                DBUpdt db = new DBUpdt();
                db.openConnection("GRADEBOOK");
                StringBuilder strSQL=new StringBuilder("SELECT roster.*, grades.* FROM Roster  INNER JOIN grades ON roster.stuid=grades.stuid ");
                //onlyCurrentStudentsRadioButton is selected
                if(strSelect.equals("2"))
                {
                    strSQL.append(" WHERE enrlstatus = '' ");
                }
                //onlyFedStudentsRadioButton is selected
                if(strSelect.equals("3"))
                {
                    strSQL.append(" WHERE fedstatus = 'FED' ");
                }
                //studentIDRadioButton is selected
                if(strSortBy.equals("1"))
                {
                    strSQL.append(" ");
                }
                //lastnameFirstnameRadioButton is selected
                if(strSortBy.equals("2"))
                {
                    strSQL.append(" ORDER BY lastname,firstname");
                }
                //pointsTotalRadioButton is selected
                if(strSortBy.equals("3"))
                {
                    strSQL.append(" ORDER BY total");
                }
                db.query(strSQL.toString());
                String strFile="data/"+strFileName+".txt";
                File file = new File(strFile);
                // write a report to a file
                PrintWriter output= null;
                try {
                    output = new PrintWriter(file);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                while (db.moreRecords())
                {
                    //if studentIDCheckBox is selected
                    if(strId.equals("1"))
                    {
                        output.print(db.getField("stuid").trim()+strFieldDelimeter);
                    }
                    //if lastnameTheFirstnameCheckBox is selected
                    if(strLF.equals("1"))
                    {
                        output.print(db.getField("lastname").trim() + strFieldDelimeter + db.getField("firstname").trim() + strFieldDelimeter);
                    }
                    //if firstnameTheLastnameCheckBox is selected
                    if(strFL.equals("1"))
                    {
                        output.print(db.getField("firstname").trim()+strFieldDelimeter +db.getField("lastname").trim()+strFieldDelimeter);
                    }
                    //if quizTotalCheckBox is selected
                    if(strQ.equals("1"))
                    {
                        output.print(String.valueOf(a19014.getQuizTotal(db.getField("stuid")))+strFieldDelimeter);
                    }
                    //if asgnTotalCheckBox is selected
                    if(strA.equals("1"))
                    {
                        output.print(String.valueOf(a19014.getAsgnTotal(db.getField("stuid")))+strFieldDelimeter);
                    }
                    //if pointsTotalCheckBox is selected
                    if(strP.equals("1"))
                    {
                        output.print(String.valueOf(a19014.getAsgnTotal(db.getField("stuid"))+a19014.getQuizTotal(db.getField("stuid")))+strFieldDelimeter);
                    }
                    //if letterGradeWithrawnCheckBox is selected
                    if(strLG.equals("1"))
                    {
                        if(db.getField("enrlstatus").trim().equals("Withdrawn") | db.getField("enrlstatus").trim().equals("Dropped"))
                        {
                            output.print(db.getField("enrlstatus").trim()+strFieldDelimeter);
                        }
                        else
                        {
                            output.print(a19014.getLetterGrade(a19014.getAsgnTotal(db.getField("stuid"))+a19014.getQuizTotal(db.getField("stuid")))+strFieldDelimeter);
                        }

                    }
                    //if fedStatusCheckBox is selected
                    if(strF.equals("1"))
                    {
                        output.print(db.getField("fedstatus").trim()+strFieldDelimeter);
                    }
                    output.println("");
                }
                db.close();
                output.close();
            }
        }
    }

    /**
     * check to see file name valid or not
     * @param file - file name
     * @return - boolean
     */
    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
