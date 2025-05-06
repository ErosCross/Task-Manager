package com;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class RemoveTaskMenu extends JPanel {



    private JDialog dialog; // <-- Reference to the popup
    private String title;

    public RemoveTaskMenu(JDialog dialog, JPanel container, JPanel taskItem, String title) {
        this.dialog = dialog;
        this.title = title;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel text = new JLabel("Are you sure you want to remove this task?", SwingConstants.CENTER);
        text.setForeground(Color.WHITE);
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/CalSans-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            text.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        JPanel topControlsPanel = new JPanel(new BorderLayout());
        // Top Controls Panel (Search + Buttons)
        topControlsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        topControlsPanel.setBackground(new Color(74, 72, 72));
        // Layout buttons
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(new Color(74, 72, 72));
        JButton yesButton = new JButton("Yes");
        yesButton.setBackground(new Color(34, 139, 34));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.addActionListener(e -> {
            Connector con = new Connector();
            con.removeTask(title);
            container.remove(taskItem);
            container.revalidate();
            container.repaint();
            // ✅ Close the dialog after task is added
            dialog.dispose();
        });

        JButton noButton = new JButton("No");
        noButton.setBackground(new Color(235,10,10));
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.addActionListener(e -> {
            // ✅ Close the dialog after task is added
            dialog.dispose();
        });

        topControlsPanel.add(yesButton, BorderLayout.WEST);
        topControlsPanel.add(noButton, BorderLayout.EAST);

        formPanel.add(text);
        formPanel.add(topControlsPanel);
        this.setBackground(new Color(74, 72, 72));
        add(formPanel);


    }
}
