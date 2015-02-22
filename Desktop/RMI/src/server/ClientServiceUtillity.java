package server;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Pattern;
import server.User.Priviliges;
import static server.User.USER_DIR;
import static server.Validator.*;

public class ClientServiceUtillity extends UnicastRemoteObject implements ServiceProvider {
    //The file that's store all the encrypted & decrypted credit cards
    private static final String DIR = "ENCRYPT_AND_DECRYPT_INFO.txt";
   
    //The offset when encryption is performed
    private static final int DEFAULT_OFFSET = 5;
    
    //User priviliges
    private Priviliges priviliges;
    
    //StringBuilder for appending different things during execution of the program
    private StringBuilder sb;
    
    //Use for reading a line from a file
    private String lineString;

    //Def. for preparing reading from a file
    public static InputStream inputStream;
    public static InputStreamReader inputStreamReader;
    public static BufferedReader reader;

    //Def. of preparing writing a file
    public static OutputStream outputStream;
    public static OutputStreamWriter outputStreamWriter;
    public static BufferedWriter writer;

    public ClientServiceUtillity() throws RemoteException {
        super();
    }
    
    //Release all of the resources that has been opened during execution of the program
    public static void closeResources() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    //Check whether the user privilide is enough for making some action
    public boolean isAuthorized(Priviliges priviliges) {
        return priviliges.equals(this.priviliges);
    }
    
    //Performing user login in the system
    @Override
    public boolean login(String username, char[] password) throws RemoteException {
        //Determines whether or not user with a specific username exist
        if (fileExist(USER_DIR + username + ".xml")) {
            try {
                //Make the password a string
                String strPassword = "";
                for (int i = 0; i < password.length; i++) {
                    strPassword += String.valueOf(password[i]);
                }
                //Open the file with the user information
                inputStream = new FileInputStream(USER_DIR + username + ".xml");
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                sb = new StringBuilder();

                while ((lineString = reader.readLine()) != null) {
                    sb.append(lineString);
                }
                
                //After that the @param userInfo contain a whole information about the user
                String userInfo = new String(sb);
               
                //Validate user form with a regular expresions
                if (!Pattern
                        .compile("<username>" + username + "</username>")
                        .matcher(userInfo)
                        .find()) {
                    return false;
                }

                if (!Pattern
                        .compile("<password>" + strPassword + "</password>")
                        .matcher(userInfo)
                        .find()) {
                    return false;
                }
                //Get the user priviliges
                if (Pattern
                        .compile("<privilige>ENCRYPT</privilige>")
                        .matcher(userInfo)
                        .find()) {
                    priviliges = User.Priviliges.ENCRYPT;
                } else if (Pattern
                        .compile("<privilige>DECRYPT</privilige>")
                        .matcher(userInfo)
                        .find()) {
                    priviliges = User.Priviliges.DECRYPT;
                } else if (Pattern
                        .compile("<privilige>ENCRYPT_AND_DECRYPT</privilige>")
                        .matcher(userInfo)
                        .find()) {
                    priviliges = User.Priviliges.ENCRYPT_AND_DECRYPT;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                closeResources();
            }
        } else {
            return false;
        }
        return true;
    }
    
    @Override
    public String encrypt(String creditCard) throws RemoteException, Exception {
        //Determines whether user is authorized for encrypt the credit card
        if (isAuthorized(Priviliges.ENCRYPT)
                || isAuthorized(Priviliges.ENCRYPT_AND_DECRYPT)) {
            /*  Determines whether the entered credit card meets the Luhn formula
                For more information about the Luhn formula visit: http://en.wikipedia.org/wiki/Luhn_algorithm
                If not, then the Exception will be thrown
            */
            if (isLuhnFormula(creditCard)) {
                int lines;
                try {
                    inputStream = new FileInputStream(DIR);
                    inputStreamReader = new InputStreamReader(inputStream);
                    reader = new BufferedReader(inputStreamReader);
                    if ((lines = (int) reader.lines().filter(x -> x.contains(creditCard)).count()) >= 0) {
                        /* 
                            @param lines holds the lines that contains credit card $ 
                            if lines are 12 then the encryption can be performed,
                            otherwise an Exception will be thrown
                        */ 
                        if (lines > 11) {
                            throw new Exception("You can't encrypt that credit card anymore!");
                        } else {
                            /*
                                Performing encryption.
                                If that credit card is previosly encrypted then
                                the default offset is increase by 1.
                            */
                            String encryptedCardInfo = performEncryption(creditCard,
                                    (DEFAULT_OFFSET + lines) % creditCard.length());
                            closeResources();
                            try {
                                writer = new BufferedWriter(new FileWriter(DIR, true));
                                writer.append(creditCard);
                                writer.append(" - ");
                                writer.append(encryptedCardInfo);
                                writer.newLine();
                                writer.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } finally {
                                closeResources();
                            }
                            return encryptedCardInfo;
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } finally {
                    closeResources();
                }
            } else {
                throw new Exception("You enter an invalid credit card number!");
            }
        }
        throw new Exception("You can't encrypt!");
    }

    @Override
    public String decrypt(String creditCard) throws RemoteException, Exception {
         //Determines whether user is authorized for encrypt the credit card
        if (isAuthorized(Priviliges.DECRYPT)
                || isAuthorized(Priviliges.ENCRYPT_AND_DECRYPT)) {
            try {
                //Read the file, that contains all information about encrypted credit cards
                inputStream = new FileInputStream(DIR);
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                /*
                    Get the credit card number that correspond with a given cryptogram
                    If no such a number exist an Exception will be thrown
                */
                while ((lineString = reader.readLine()) != null) {
                    if (lineString.split(" - ")[1].contains(creditCard)) {
                        return lineString.split(" - ")[0];
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                closeResources();
            }
        } else {
            throw new Exception("You can't decrypt!");
        }
        throw new Exception("This credit card can't be decrypted");
    }
    
    /*
        Perform the encryption. The method is Substitution cipher.
        @param creditCardNumber - holds a credit card number that is about to be
            encrypted
        @param offset - define the offset that will use during the encryption
            process
    */
    private String performEncryption(String creditCardNumber, int offset) {
        /*
            Calling the tokenize method from class Validator to perform the 
            tokenization
        */
        int[] digitArr = tokenize(creditCardNumber);
        sb = new StringBuilder();
        for (int i = 0; i < digitArr.length; i++) {
            digitArr[i] = (digitArr[i] + offset);
            sb.append(digitArr[i]);
            //Make the output more readable - XXXX XXXX XXXX XXXX
            if ((i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }
        return new String(sb);
    }

}
