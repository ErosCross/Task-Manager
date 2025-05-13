package com;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Calendar;

public class AddTaskMenu extends JPanel {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusBox;
    private JSpinner dueDateSpinner;
    private JButton addButton;

    private JDialog dialog; // <-- Reference to the popup

    public AddTaskMenu(JDialog dialog) {
        this.dialog = dialog;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title Field
        titleField = new JTextField(20);

        // Description Area
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        // Status Dropdown
        statusBox = new JComboBox<>(new String[]{"pending", "in progress", "completed"});

        // Date Picker (using Spinner)
        SpinnerDateModel dateModel = new SpinnerDateModel(new java.util.Date(), null, null, Calendar.DAY_OF_MONTH);
        dueDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd");
        dueDateSpinner.setEditor(dateEditor);

        // Add Button
        addButton = new JButton("Add Task");

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

        add(formPanel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        // Add button logic
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            String statusText = (String) statusBox.getSelectedItem();
            int status = switch (statusText) {
                case "In Progress" -> 1;
                case "Completed" -> 2;
                default -> 0; // Pending
            };

            java.util.Date utilDate = (java.util.Date) dueDateSpinner.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            System.out.println("Adding Task:");
            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Status: " + status);
            System.out.println("Due Date: " + sqlDate);

            Connector con = new Connector();
            con.addTasks(title, description, status, sqlDate);

            // ✅ Close the dialog after task is added
            dialog.dispose();
        });
    }
}
