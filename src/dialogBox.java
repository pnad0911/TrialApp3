import javax.swing.*;
import java.awt.event.*;

/**
 * @author Duy Pham
 * @version 1.01 - 05/17/2014
 * @email pnad0911@gmail.com
 * @prgm.usage A19014
 * @assignment.number A19014
 * @see <a href='http://jcouture.net/cisc190/A19014.php'>Program Specification</a>
 * @see <br><a href='http://docs.oracle.com/javase/7/docs/technotes/guides/Javadoc/index.html'>Javadoc Documentation</a>
 */

public class dialogBox extends JDialog
{
    private JPanel contentPane;
    private JTextArea textArea1;
    private JButton OKbutton;
    private JButton buttonOK;
    public String strFileName1;

    public dialogBox()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        OKbutton.addActionListener(new test1());
    }

    public static void main(String[] args)
    {
        dialogBox dialog = new dialogBox();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * Public inner class that handle the event when
     * the user clicks a button
     */
    public class test1 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            strFileName1=textArea1.getText();
            onCancel();
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
        }
    }
    private void onCancel()
    {
// add your code here if necessary
        dispose();
    }
}
