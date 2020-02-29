package com.zivoy.classes;

import com.zivoy.keyHandlers.Key;
import com.zivoy.keyHandlers.PrivateKey;
import com.zivoy.keyHandlers.PublicKey;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// a class to handel the external contacts file
public class FileManeger {
    String Filename; // variable

    // constructor takes a filename
    public FileManeger(String Filename) {
        this.Filename = Filename;
        File tempFile = new File(this.Filename);  // if file does not exit make it
        if (!tempFile.exists()) {
            try {
                tempFile.createNewFile();
            } catch (IOException ignored) { } // ignore file error
        }
    }

    // read model and private/public key and save to file
    public void save(PrivateKey privateKey, PublicKey publicKey, DefaultListModel<Element> model) {
        try {
            PrintWriter writer = new PrintWriter(this.Filename, "UTF-8");  // create a new writer

            writer.println(privateKey);  // write the public and private key
            writer.println(publicKey);

            for (Object contact : model.toArray()) {   // iterate over all the contacts
                Element contactElement = (Element) contact;

                StringBuilder line = new StringBuilder();  // generate a string builder
                if (contactElement.getKeyValue() instanceof PrivateKey) line.append("private"); // make an entry describing if
                else line.append("public");                                                     // its a public or private key
                line.append(" ");
                line.append(contactElement.name);  // add the contact name
                line.append(" ");
                line.append(contactElement.getKeyValue().getKey());  // add the key

                writer.println(line.toString());  // write line to file
            }
            writer.close();  // close file
        } catch (java.io.IOException e) {  // ignore errors just print it
            e.printStackTrace();
        }
    }

    // load contacts from file
    public loadout load() {
        try {
            return load(Filename);  // try reading contacts and returns it
        } catch (IOException ignored) { } // ignore IO errors
        PrivateKey temp = new PrivateKey();  // if there was an error make a new profile
        return new loadout(temp, temp.makePublic(), new DefaultListModel<>());
    }

    // function that does the loading
    private loadout load(String filename) throws IOException {
        File file = new File(filename);

        // make a buffered reader to read from file
        BufferedReader br = new BufferedReader(new FileReader(file));

        // make some variables
        String st;
        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        DefaultListModel<Element> contacts = new DefaultListModel<>();
        // get the line count in file
        long lines = Files.lines(file.toPath()).count();
        // iterate over all lines
        for (int i = 0; i < Math.max(lines, 2); i++) {  // iterate over a minimum of 2 lines
            st = br.readLine();
            switch (i) {
                case 0:
                    if (st != null)
                        privateKey = PrivateKey.fromString(st); // if the first line is not null get the private key
                    else
                        privateKey = new PrivateKey();  // if it is then make a new key
                    break;
                case 1:
                    if (st != null)
                        publicKey = PublicKey.fromString(st); // if the second line is not null then get the public key
                    else
                        publicKey = privateKey.makePublic(); // otherwise get make a new public key
                    break;
                default:  // otherwise read contacts
                    if (st == null) break; // if the line doesnt exist exit
                    Element item;
                    try {
                        item = StringElement(st, i - 1); // get the line from file
                    } catch (BadLine e) {
                        continue; // if its a bad line then skip it
                    }
                    contacts.addElement(item);  // add to the list model
            }
        }
        return new loadout(privateKey, publicKey, contacts); //return the keys and contacts
    }

    // function to extract elements from string
    Element StringElement(String line, int curridx) throws BadLine {
        final Pattern lineRegex = Pattern.compile("(private|public) (.+) (.+)"); // define regex pattern
        Matcher lineComponents = lineRegex.matcher(line); // match it against the string
        if (!lineComponents.matches()) {
            throw new BadLine();  // if it doesnt match throw a bad line error
        }
        String name = lineComponents.group(2);  // get the name
        String key = lineComponents.group(3);  // get the key
        Key keyObj;
        switch (lineComponents.group(1)) {
            case "private":
                keyObj = PrivateKey.fromString(key);  // if the first part says private then extract a private key from the key
                break;
            case "public":
                keyObj = PublicKey.fromString(key); // if its public then extract a public key
                break;
            default:
                throw new BadLine(); // otherwise throw a bad line
        }
        return new Element(name, keyObj, curridx);  // return a element

    }

    // loadout class for returning data
    public static class loadout {
        // some variables
        public PrivateKey privateKey;
        public PublicKey publicKey;
        public DefaultListModel<Element> model;

        // constructor to fill variables
        loadout(PrivateKey privateKey, PublicKey publicKey, DefaultListModel<Element> model) {
            this.privateKey = privateKey;
            this.publicKey = publicKey;
            this.model = model;
        }
    }
}
