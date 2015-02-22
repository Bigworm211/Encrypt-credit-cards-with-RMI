package server;

import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import static server.ClientServiceUtillity.*;
import static server.User.*;
import static server.Validator.fileExist;

public class ServerSide extends javax.swing.JPanel {
    //The name of file that contains the encrypted and decrypted cards info
    private final String DIR = "ENCRYPT_AND_DECRYPT_INFO.txt";
    
    //A references that keep track of all the user details. Works like session.
    private final User newUser = new User();
    /*
        A references from external library, that perofrm XML serializations over
        user information. For more information, please visit: http://xstream.codehaus.org/
    */
    private XStream xstream;

    public ServerSide() {
        initComponents();
    }
    
    //A method that create new user
    public boolean createNewUser() {
        /*
            If there is NOT a file with the specific name, then the username is
            free and the process can continue
        */
        if (!fileExist(USER_DIR + username.getText() + ".xml")) {
            xstream = new XStream();
            newUser.setPassword(password.getText());
            newUser.setUsername(username.getText());
            switch (privileges.getSelectedIndex()) {
                case 0:
                    newUser.setPrivilige(User.Priviliges.ENCRYPT);
                    break;
                case 1:
                    newUser.setPrivilige(User.Priviliges.DECRYPT);
                    break;
                case 2:
                    newUser.setPrivilige(User.Priviliges.ENCRYPT_AND_DECRYPT);
                    break;
            }
            return true;
        } else {
            messageNewUser.setText("The username is alredy taken");
            return false;
        }
    }
    //Add the new user
    public boolean addUser(User newUser) throws IOException {
        BufferedWriter bw = null;
        String user = newUser.getUsername();
        try {
            File file = new File(USER_DIR + user + ".xml");
            if (!fileExist(USER_DIR + user + ".xml")) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(xstream.toXML(newUser));
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
        return true;
    }
//Validate the submition form. Display error message if it needs.
    private boolean validateForm() {
        boolean isCorrect = true;
        if (username.getText().equals("")) {
            messageUsername.setText("Username must be provide!");
            isCorrect = false;
        }
        if (password.getText().equals("")) {
            messagePassword.setText("Password must be provide!");
            isCorrect = false;
        }
        return isCorrect;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCreateNewUser = new javax.swing.JButton();
        btnCheckFreeUsername = new javax.swing.JButton();
        privileges = new javax.swing.JComboBox();
        messageNewUser = new javax.swing.JLabel();
        messageUsername = new javax.swing.JLabel();
        messageCheckForUserName = new javax.swing.JLabel();
        messagePassword = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboTypeSorting = new javax.swing.JComboBox();
        btnPerformReport = new javax.swing.JButton();
        lblReportMessage = new javax.swing.JLabel();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel1.setText("Add new User");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        jLabel4.setText("Privileges");

        btnCreateNewUser.setText("Create new user");
        btnCreateNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewUserActionPerformed(evt);
            }
        });

        btnCheckFreeUsername.setText("Check for free username");
        btnCheckFreeUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckFreeUsernameActionPerformed(evt);
            }
        });

        privileges.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "User, that can encrypt", "User, that can decrypt", "User, that can do both encrypt and decrypt" }));

        messageNewUser.setForeground(new java.awt.Color(255, 51, 51));
        messageNewUser.setText(" ");

        messageUsername.setForeground(new java.awt.Color(255, 51, 51));
        messageUsername.setText(" ");

        messageCheckForUserName.setForeground(new java.awt.Color(255, 51, 51));
        messageCheckForUserName.setText(" ");

        messagePassword.setForeground(new java.awt.Color(251, 51, 51));
        messagePassword.setText(" ");

        jLabel5.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel5.setText("Get report");

        jLabel6.setText("Sort report by:");

        cboTypeSorting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Encrypt numbers", "Credit cards" }));

        btnPerformReport.setText("Perform report");
        btnPerformReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerformReportActionPerformed(evt);
            }
        });

        lblReportMessage.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btnCheckFreeUsername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(messageCheckForUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(messageUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(180, 180, 180)
                        .addComponent(btnPerformReport)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(messagePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(privileges, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnCreateNewUser)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                        .addComponent(messageNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblReportMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(31, 31, 31)
                                        .addComponent(cboTypeSorting, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5))
                                .addGap(150, 150, 150))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(messageNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5)))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(messageUsername)
                            .addComponent(jLabel6)
                            .addComponent(cboTypeSorting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCheckFreeUsername)
                            .addComponent(messageCheckForUserName)
                            .addComponent(btnPerformReport))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(messagePassword)
                            .addComponent(lblReportMessage))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(privileges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(44, 44, 44)
                        .addComponent(btnCreateNewUser)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewUserActionPerformed
        try {
            if (validateForm()) {
                if (createNewUser() && addUser(newUser)) {
                    messagePassword.setText("");
                    messageUsername.setText("");
                    messageCheckForUserName.setText("");
                    messageNewUser.setText("User was succsefuly created!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCreateNewUserActionPerformed

    private void btnCheckFreeUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckFreeUsernameActionPerformed
        if (username.getText().equals("")) {
            messageCheckForUserName.setText("Username must be provide!");
        } else if (fileExist(USER_DIR + username.getText() + ".xml")) {
            messageCheckForUserName.setText("The username has been alredy taken!");
        } else {
            messageCheckForUserName.setText("The username is available!");
        }
    }//GEN-LAST:event_btnCheckFreeUsernameActionPerformed

    private void btnPerformReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerformReportActionPerformed
        if(cboTypeSorting.getSelectedIndex() == 0)  
            sortByEncryptedNumbers();
        else
            sortByCreditCards();
    }//GEN-LAST:event_btnPerformReportActionPerformed
    //Perform the sorting by credit cards
    public void sortByCreditCards() {
        Map<String, List<String>> map = new TreeMap<>();
        //Get the UNIX time in order to set an unique name of the report
        long uniqueNumber = (new Date()).getTime();
        //The buffered list
        List<String> list;
        //A list that contains the readi line
        List<String> listLine;
        //A list with all keys
        List<String> listKey;

        try {
            inputStream = new FileInputStream(DIR);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            listLine = reader.lines().collect(Collectors.toList());
            listKey = listLine
                    .stream()
                    .map(x -> x.split(" - ")[0])
                    .collect(Collectors.toList());
            Iterator<String> itr = listKey.iterator();

            while (itr.hasNext()) {
                String next = itr.next();
                list = listLine
                        .stream()
                        .filter(x -> x.split(" - ")[0].equals(next))
                        .map(x -> x.split(" - ")[1])
                        .collect(Collectors.toList());
                map.put(next, list);
            }

            Iterator<String> keyIterator = map.keySet().iterator();
            Iterator<List<String>> valueIterator = map.values().iterator();
            
            String dir = "Report" + uniqueNumber + ".txt";
            File file = new File(dir);
            file.createNewFile();

            outputStream = new FileOutputStream(dir);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            writer = new BufferedWriter(outputStreamWriter);
            writer.append("-----------------***Report - ");
            writer.append(Long.toString(uniqueNumber));
            writer.append("***---------------------");
            writer.newLine();

            writer.append("Bank Card			|Encrypted bank cards");
            writer.newLine();
            writer.append("--------------------------------------------------------------------------");
            writer.newLine();

            while (keyIterator.hasNext()) {
                String next = keyIterator.next();
                Iterator<String> strIterator = valueIterator.next().iterator();
                while (strIterator.hasNext()) {
                    String next1 = strIterator.next();
                    writer.append(String.format("%1$-32s|%2$-20s", next, next1));
                    writer.newLine();
                    writer.flush();
                }
                writer.append("--------------------------------------------------------------------------");
                writer.newLine();
            }
            writer.append("====Note: The report is ordered by ");
            writer.append("Bank Cards");
            writer.append("!====");
            writer.flush();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        } finally {
            closeResources();
        }
        lblReportMessage.setText("Successful! The name of report is: Report" + uniqueNumber + ".txt");
    }

    public void sortByEncryptedNumbers() {
          long uniqueNumber = (new Date()).getTime(); 
          Map<String, String> map; 
          Map<String, String> sort = new TreeMap<>();
          try {
            inputStream = new FileInputStream(DIR);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            map = reader.lines().collect(Collectors.toMap((x) -> x.split(" - ")[1], (x) -> x.split(" - ")[0]));
            sort.putAll(map);
            Iterator<String> keyIterator = sort.keySet().iterator();
            Iterator<String> valueIterator = sort.values().iterator();
            String dir = "Report" + uniqueNumber + ".txt";
            File file = new File(dir);
            file.createNewFile();
            outputStream = new FileOutputStream(dir);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            writer = new BufferedWriter(outputStreamWriter);
            writer.append("-----------------***Report - ");
            writer.append(Long.toString(uniqueNumber));
            writer.append("***---------------------");
            writer.newLine();
            writer.append("Bank Card			|Encrypted bank cards");
            writer.newLine();
            writer.append("--------------------------------------------------------------------------");
            writer.newLine();
            while (keyIterator.hasNext()) {
                String next = keyIterator.next();
                String next1 = valueIterator.next();
                writer.append(String.format("%1$-32s|%2$-20s", next1, next));
                writer.newLine();
                writer.flush();
                writer.append("--------------------------------------------------------------------------");
                writer.newLine();
            }
            writer.append("====Note: The report is ordered by ");
            writer.append("Encrypted cards");
            writer.append("!====");
            writer.flush();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        } finally {
          closeResources();
        }
        lblReportMessage.setText("Successful! The name of report is: Report" + uniqueNumber + ".txt");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckFreeUsername;
    private javax.swing.JButton btnCreateNewUser;
    private javax.swing.JButton btnPerformReport;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cboTypeSorting;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblReportMessage;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JLabel messageCheckForUserName;
    private javax.swing.JLabel messageNewUser;
    private javax.swing.JLabel messagePassword;
    private javax.swing.JLabel messageUsername;
    private javax.swing.JTextField password;
    private javax.swing.JComboBox privileges;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}