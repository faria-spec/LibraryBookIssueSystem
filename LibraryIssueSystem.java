import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryIssueSystem extends JFrame implements ActionListener {
    JLabel lblName, lblRoll, lblBookTitle, lblCategory, lblIssueDate, lblReturnDate, lblRemarks, lblType;
    JTextField txtName, txtRoll, txtBookTitle, txtIssueDate, txtReturnDate;
    JTextArea txtRemarks;
    JComboBox<String> comboCategory;
    JRadioButton rbNew, rbOld;
    ButtonGroup bgType;
    JButton btnIssue, btnReset, btnExit;

    public LibraryIssueSystem() {
        setTitle("Library Book Issue System");
        setSize(650, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font f = new Font("Arial", Font.BOLD, 16);

        lblName = new JLabel("Student Name:");
        lblName.setBounds(50, 40, 200, 30);
        lblName.setFont(f);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(250, 40, 250, 30);
        add(txtName);

        lblRoll = new JLabel("Roll Number:");
        lblRoll.setBounds(50, 90, 200, 30);
        lblRoll.setFont(f);
        add(lblRoll);

        txtRoll = new JTextField();
        txtRoll.setBounds(250, 90, 250, 30);
        add(txtRoll);

        lblBookTitle = new JLabel("Book Title:");
        lblBookTitle.setBounds(50, 140, 200, 30);
        lblBookTitle.setFont(f);
        add(lblBookTitle);

        txtBookTitle = new JTextField();
        txtBookTitle.setBounds(250, 140, 250, 30);
        add(txtBookTitle);

        lblCategory = new JLabel("Book Category:");
        lblCategory.setBounds(50, 190, 200, 30);
        lblCategory.setFont(f);
        add(lblCategory);

        String categories[] = {"Select Category", "Programming", "AI", "Databases", "Networking"};

        comboCategory = new JComboBox<>(categories);
        comboCategory.setBounds(250, 190, 250, 30);
        add(comboCategory);

        lblType = new JLabel("Book Type:");
        lblType.setBounds(50, 240, 200, 30);
        lblType.setFont(f);
        add(lblType);

        rbNew = new JRadioButton("New Edition");
        rbOld = new JRadioButton("Old Edition");

        rbNew.setBounds(250, 240, 120, 30);
        rbOld.setBounds(380, 240, 120, 30);

        bgType = new ButtonGroup();
        bgType.add(rbNew);
        bgType.add(rbOld);

        add(rbNew);
        add(rbOld);

        lblIssueDate = new JLabel("Issue Date (dd/MM/yyyy):");
        lblIssueDate.setBounds(50, 290, 250, 30);
        lblIssueDate.setFont(f);
        add(lblIssueDate);

        txtIssueDate = new JTextField();
        txtIssueDate.setBounds(300, 290, 200, 30);
        add(txtIssueDate);

        lblReturnDate = new JLabel("Return Date (dd/MM/yyyy):");
        lblReturnDate.setBounds(50, 340, 250, 30);
        lblReturnDate.setFont(f);
        add(lblReturnDate);

        txtReturnDate = new JTextField();
        txtReturnDate.setBounds(300, 340, 200, 30);
        add(txtReturnDate);

        lblRemarks = new JLabel("Remarks:");
        lblRemarks.setBounds(50, 390, 200, 30);
        lblRemarks.setFont(f);
        add(lblRemarks);

        txtRemarks = new JTextArea();

        JScrollPane sp = new JScrollPane(txtRemarks);
        sp.setBounds(250, 390, 250, 80);
        add(sp);

        btnIssue = new JButton("Issue Book");
        btnIssue.setBounds(80, 500, 150, 40);

        btnReset = new JButton("Reset");
        btnReset.setBounds(260, 500, 150, 40);

        btnExit = new JButton("Exit");
        btnExit.setBounds(440, 500, 150, 40);

        add(btnIssue);
        add(btnReset);
        add(btnExit);

        btnIssue.addActionListener(this);
        btnReset.addActionListener(this);
        btnExit.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnIssue) {

            try {

                String name = txtName.getText();
                String roll = txtRoll.getText();
                String title = txtBookTitle.getText();
                String issueDate = txtIssueDate.getText();
                String returnDate = txtReturnDate.getText();

                if (name.isEmpty() || roll.isEmpty() || title.isEmpty() ||
                        issueDate.isEmpty() || returnDate.isEmpty()) {

                    throw new EmptyFieldException("All fields are required!");
                }

                if (!roll.matches("\\d+")) {
                    throw new InvalidRollNumberException(
                            "Roll Number must contain numbers only!");
                }

                int numericRoll = Integer.parseInt(roll);

                if (comboCategory.getSelectedIndex() == 0) {
                    throw new NullSelectionException(
                            "Please select a Book Category!");
                }

                if (!rbNew.isSelected() && !rbOld.isSelected()) {
                    throw new NullSelectionException(
                            "Please select a Book Type!");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);

                Date issue = sdf.parse(issueDate);
                Date ret = sdf.parse(returnDate);

                if (ret.before(issue)) {
                    throw new InvalidDateException(
                            "Return Date cannot be earlier than Issue Date!");
                }

                throw new BookIssuedSuccessfullyException(
                        "Book Issued Successfully!");

            }

            catch (EmptyFieldException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            catch (InvalidRollNumberException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            catch (InvalidDateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            catch (NullSelectionException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid Numeric Format!");
            }

            catch (BookIssuedSuccessfullyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unexpected Error: " + ex.getMessage());
            }

            finally {
                JOptionPane.showMessageDialog(this,
                        "Operation Completed");
            }
        }

        if (e.getSource() == btnReset) {

            txtName.setText("");
            txtRoll.setText("");
            txtBookTitle.setText("");
            txtIssueDate.setText("");
            txtReturnDate.setText("");
            txtRemarks.setText("");

            comboCategory.setSelectedIndex(0);
            bgType.clearSelection();
        }

        if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new LibraryIssueSystem();
    }
}
