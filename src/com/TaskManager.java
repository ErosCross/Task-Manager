package com;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class TaskManager extends JFrame {
    public TaskManager() {
        this.setTitle("Amit's Task Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(800, 600);
        this.setIconImage(new ImageIcon("src/resources/images/icon.png").getImage());
        this.getContentPane().setBackground(new Color(74, 72, 72));
        this.setLayout(new BorderLayout());

        // Panels
        JPanel mainPanel = new JPanel();
        JPanel tasksPanel = new JPanel();
        JPanel topControlsPanel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        // Top title
        JLabel title = new JLabel("Amit's Task Manager", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/CalSans-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            title.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        this.add(title, BorderLayout.NORTH);

        // Layout setup
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(58, 58, 58));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Controls Panel (Search + Buttons)
        topControlsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        topControlsPanel.setBackground(new Color(58, 58, 58));

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setToolTipText("Search tasks...");
        topControlsPanel.add(searchField, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(60, 30));
        addButton.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Add New Task", true);
            dialog.setContentPane(new AddTaskMenu(dialog));
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadTasks(tasksPanel);
        });

        buttonsPanel.setOpaque(false);
        buttonsPanel.add(addButton);
        topControlsPanel.add(buttonsPanel, BorderLayout.EAST);

        // Add topControlsPanel to mainPanel
        mainPanel.add(topControlsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Tasks display panel inside a scroll pane
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBackground(new Color(74, 74, 74));

        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setPreferredSize(new Dimension(480, 400));
        mainPanel.add(scrollPane);

        loadTasks(tasksPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void addTask(JPanel container, String title, String description, String status, Date dueDate) {
        JPanel taskItem = new JPanel(new BorderLayout());
        taskItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        taskItem.setBackground(new Color(94, 94, 94));
        taskItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Create main label with title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Additional info (description, status, date)
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(dueDate);
        JLabel infoLabel = new JLabel("<html><i>" + status + "</i> | Due: " + formattedDate + "<br/>" + description + "</html>");
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Panel to stack labels
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(infoLabel);

        // Done button
        JButton doneButton = new JButton("✔");
        doneButton.setPreferredSize(new Dimension(50, 30));
        doneButton.setFocusPainted(false);
        doneButton.setBackground(new Color(0, 153, 102));
        doneButton.setForeground(Color.WHITE);

        // Remove button
        JButton removeButton = new JButton("✖");
        removeButton.setPreferredSize(new Dimension(50, 30));
        removeButton.setFocusPainted(false);
        removeButton.setBackground(new Color(235, 10, 10));
        removeButton.setForeground(Color.WHITE);
        removeButton.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Delete a Task", true);
            dialog.setContentPane(new RemoveTaskMenu(dialog,container,taskItem,title));
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

        });

        // Adding done and remove button to the same panel (right side)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(doneButton);
        buttonPanel.add(removeButton);

        taskItem.add(textPanel, BorderLayout.CENTER);
        taskItem.add(buttonPanel, BorderLayout.EAST);

        container.add(taskItem);
        container.revalidate();
        container.repaint();
    }
    public void loadTasks(JPanel container) {
        Connector con = new Connector();
        for (Task task : con.getTasks()) {
            if (!task.getAdded()) {
                addTask(container, task.getTitle(), task.getDescription(), task.getStatus(), task.getDueDate());
                task.setAdded();
            }

        }
    }



}
