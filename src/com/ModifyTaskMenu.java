package com;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class ModifyTaskMenu extends JPanel {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusBox;
    private JSpinner dueDateSpinner;
    private JButton saveButton;
    private JButton exitButton;
    private JDialog dialog;


    public ModifyTaskMenu(JDialog dialog, String title, String description, String status, Date dueDate) {

            this.dialog = dialog;

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Title Field
            titleField = new JTextField(20);
            titleField.setText(title);

            // Description Area
            descriptionArea = new JTextArea(4, 20);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setText(description);

            // Status Dropdown
            statusBox = new JComboBox<>(new String[]{"pending", "in progress", "completed"});
            System.out.println(status);
            statusBox.setSelectedItem(status);

            // Date Picker (using Spinner)
            SpinnerDateModel dateModel = new SpinnerDateModel(dueDate, null, null, Calendar.DAY_OF_MONTH);
            dueDateSpinner = new JSpinner(dateModel);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd");
            dueDateSpinner.setEditor(dateEditor);


            // save modifications and exit Buttons
            saveButton = new JButton("\uD83D\uDCBE");
            exitButton = new JButton("Exit");


        // Layout Inputs
            JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            formPanel.add(new JLabel("Title:"));
            formPanel.add(titleField);
            formPanel.add(new JLabel("Description:"));
            formPanel.add(new JScrollPane(descriptionArea));
            formPanel.add(new JLabel("Status:"));
            formPanel.add(statusBox);
            formPanel.add(new JLabel("Due Date:"));
            formPanel.add(dueDateSpinner);

            add(saveButton, BorderLayout.EAST);
            add(exitButton,BorderLayout.SOUTH);

            add(formPanel, BorderLayout.CENTER);
            saveButton.addActionListener(e -> {





            System.out.println("Modifying Task:");
            System.out.println("Title: " + titleField.getText());
            System.out.println("Description: " + description);
            System.out.println("Status: " + status);


            Connector con = new Connector();

            java.util.Date utilDate = (java.util.Date) dueDateSpinner.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());


            con.editTask(title,titleField.getText(),descriptionArea.getText(),statusBox.getSelectedIndex(), sqlDate);


            // ✅ Close the dialog after task is added
            dialog.dispose();
        });


        }


}
