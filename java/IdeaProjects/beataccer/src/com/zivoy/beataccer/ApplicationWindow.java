package com.zivoy.beataccer;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

public class ApplicationWindow extends JFrame implements ActionListener, PropertyChangeListener {
    final DecimalFormat twoPlaces = new DecimalFormat("##.00");
    JMenu fileMenu;
    JMenu helpMenu;
    ScoresaberApi user;
    short state;
    int selectedNumber;
    float selectedAcc;
    Play[] selectedPlays;
    private JPanel mainPanel;
    private JPanel scoresaberUidPanel;
    private JPanel percentSelectorPanel;
    private JPanel generatePanel;
    private PTextField scoreSaberId;
    private JTextField percentSelectedFeild;
    private JButton backButton;
    private JButton searchButton;
    private JButton confirmButton;
    private JButton saveListButton;
    private JProgressBar progressBar;
    private JSlider percentSlider;
    private JPanel userDataPanel;
    private JLabel userNameLabel;
    private JLabel accuraccyLabel;
    private JTable table1;
    private JButton confirmPlayerButton;
    private JLabel percentSelected;

    public ApplicationWindow(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("error windows feel not available");
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((event) -> System.exit(0));

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem aMenuItem = new JMenuItem("About");
        aMenuItem.setMnemonic(KeyEvent.VK_A);
        aMenuItem.setToolTipText("Program information");
        aMenuItem.addActionListener((event) -> {
            About dialog = new About();
            dialog.pack();
            dialog.setVisible(true);
        });

        JMenuItem bMenuItem = new JMenuItem("Changelog");
        bMenuItem.setMnemonic(KeyEvent.VK_C);
        bMenuItem.setToolTipText("Log of changes");
        bMenuItem.addActionListener((event) -> {
            Changelog dialog = new Changelog();
            dialog.pack();
            dialog.setVisible(true);
        });

        fileMenu.add(eMenuItem);
        helpMenu.add(aMenuItem);
        helpMenu.add(bMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        this.setSize(new Dimension(500, 500));
        this.setPreferredSize(new Dimension(500, 500));
        this.setMinimumSize(new Dimension(400, 300));

        state = 1;

        this.pack();

        searchButton.addActionListener(e -> searchAction());
        scoreSaberId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchAction();
                }
            }

        });

        confirmPlayerButton.setActionCommand("confirm");
        confirmPlayerButton.addActionListener(this);

        confirmButton.addActionListener(e -> stage3());

        backButton.addActionListener(e -> {
            if (state == 2)
                stage1();
            else if (state == 3)
                stage2();
            else
                stage3();
        });

        stage1();
        percentSlider.addChangeListener(new SliderListener());
        saveListButton.addActionListener(e -> {
            PlayListCreator playListCreator;
            try {
                playListCreator = new PlayListCreator(user.name, selectedPlays);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                return;
            }
            File file = choseFile();
            if (file == null) return;

            try {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(playListCreator.generateJsonString());
                myWriter.close();
                JOptionPane.showMessageDialog(null, "Playlist created!\ninstall using mod assistant or by putting in the folder",
                        "Done!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException err) {
                System.out.println("An error occurred.");
                err.printStackTrace();
            }
        });
    }

    File choseFile() {
        String home = System.getProperty("user.home");
        home = home + "\\Downloads";
        if (!System.getProperty("os.name").toLowerCase().contains("windows"))
            home = home.replace('\\', '/');
        return choseFile(new File(home), new File("accuracy_improvement_plan.json"));
    }

    File choseFile(File path, File target) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(target);
        chooser.setCurrentDirectory(path);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json");
        chooser.setFileFilter(filter);
        if (chooser.showSaveDialog(mainPanel.getParent()) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file.getName().split(".*\\.").length < 2 || !file.getName().split(".*\\.")[1].equalsIgnoreCase("json")) {
                file = new File(file.toString() + ".json");
            }
            if (file.exists())
                if (JOptionPane.showConfirmDialog(null, "You are about to overwrite",
                        "File Exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.CANCEL_OPTION)
                    return choseFile(chooser.getCurrentDirectory(), file);
            return file;
        }
        return null;
    }

    private void updateSelectedField() {
        selectedAcc = (float) (percentSlider.getValue() / 10.);
        selectedNumber = user.accList.length;
        for (int i = 0; i < user.accList.length; i++)
            if (selectedAcc < user.accList[i]) {
                selectedNumber = i;
                break;
            }

        percentSelectedFeild.setText(selectedAcc + "% | " + selectedNumber + " selected");
    }

    private void searchAction() {
        scoreSaberId.setEditable(false);
        searchButton.setEnabled(false);

        if (scoreSaberId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter a scoresaber id",
                    "Error -- no input", JOptionPane.ERROR_MESSAGE);
            scoreSaberId.setEditable(true);
            searchButton.setEnabled(true);
            return;
        }
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                user = new ScoresaberApi(scoreSaberId.getText());
                return null;
            }

            @Override
            public void done() {
                setCursor(null);
                if (!user.isValid()) {
                    JOptionPane.showMessageDialog(null, "Invalid scoresaber id",
                            "Error -- scoresaberId is not valid", JOptionPane.ERROR_MESSAGE);
                    user = null;
                    scoreSaberId.setPropt();
                    scoreSaberId.setEditable(true);
                    searchButton.setEnabled(true);
                    return;
                }

                userNameLabel.setText(user.name);
                accuraccyLabel.setText(twoPlaces.format(user.averageAcc) + "%");

                userDataPanel.setVisible(true);
                confirmPlayerButton.setVisible(true);
                searchButton.setEnabled(true);
                searchButton.setVisible(false);
                backButton.setVisible(true);
                state = 2;
                confirmPlayerButton.requestFocus();
            }
        }.execute();
    }

    private void stage1() {
        scoresaberUidPanel.setVisible(true);
        percentSelectorPanel.setVisible(false);
        backButton.setVisible(false);
        progressBar.setVisible(false);
        generatePanel.setVisible(false);
        userDataPanel.setVisible(false);

        scoreSaberId.setPropt();
        confirmPlayerButton.setVisible(false);
        scoreSaberId.setEditable(true);
        searchButton.setVisible(true);
        searchButton.grabFocus();
        state = 1;
    }

    private void stage2() {
        scoresaberUidPanel.setVisible(false);
        percentSelectorPanel.setVisible(true);
        backButton.setVisible(true);
        progressBar.setVisible(false);
        generatePanel.setVisible(false);
        userDataPanel.setVisible(true);
        state = 2;
        confirmButton.requestFocus();
        updateSelectedField();
    }

    private void stage3() {
        scoresaberUidPanel.setVisible(false);
        percentSelectorPanel.setVisible(false);
        backButton.setVisible(true);
        progressBar.setVisible(false);
        generatePanel.setVisible(true);
        userDataPanel.setVisible(true);
        state = 3;
        saveListButton.requestFocus();

        TableModel model = new DefaultTableModel(new Vector<>(Arrays.asList("Map name", "Diffeculty", "Mapper", "Accuracy", "Mods")),
                selectedNumber);
        selectedPlays = new Play[selectedNumber];

        int loc = 0;
        for (int i = 0; i < user.accList.length; i++) {
            Play play = user.rankedPlays[i];
            if (play.accuracy < selectedAcc) {
                model.setValueAt(play.songAuthorName + " - " + play.songName + " " + play.songSubName, loc, 0);
                model.setValueAt(play.difficultyRaw.replace('_', ' '), loc, 1);
                model.setValueAt(play.levelAuthorName, loc, 2);
                model.setValueAt(twoPlaces.format(play.accuracy) + "%", loc, 3);
                model.setValueAt(play.mods, loc, 4);
                selectedPlays[loc] = play;
                loc++;
            }
        }
        percentSelected.setText(twoPlaces.format(selectedAcc) + "% -- " + selectedNumber + " items");
        table1.setModel(model);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("confirm")) {
            progressBar.setVisible(true);
            confirmPlayerButton.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            user.makePageDownloader(confirmPlayerButton, progressBar, this, () -> {
                this.stage2();
                return null;
            });
            user.downloadRanked.addPropertyChangeListener(this);
            user.downloadRanked.execute();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }

    private void createUIComponents() {
        scoreSaberId = new PTextField("Enter Scoresaber URL or ID");
    }

    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (source.equals(percentSlider)) {
                updateSelectedField();
            }
        }

    }
}
