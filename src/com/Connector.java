package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {

    public boolean addTasks(String title, String description, int status, Date date) {
        String[] statusOptions = {"pending", "in_progress", "completed"};
        if (status < 0 || status >= statusOptions.length) {
            System.out.println("Invalid status index");
            return false;
        }

        String sql = "INSERT INTO tasks (title, description, status, due_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taskmanager", "root", "1234");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, statusOptions[status]);
            pstmt.setDate(4, new java.sql.Date(date.getTime()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taskmanager", "root", "1234");
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                Date dueDate = resultSet.getDate("due_date");

                Task task = new Task(title, description, status, dueDate);
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
    public void removeTask(String title) {
        List<Task> tasks = this.getTasks();
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                // Correct way to handle SQL deletion with PreparedStatement
                String sql = "DELETE FROM tasks WHERE title = ?"; // Using '?' as placeholder
                try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taskmanager", "root", "1234");
                     PreparedStatement pstmt = con.prepareStatement(sql)) {

                    pstmt.setString(1, title); // Set the title in the prepared statement
                    int rowsAffected = pstmt.executeUpdate(); // Execute the query

                    if (rowsAffected > 0) {
                        System.out.println("Task successfully deleted!");
                    } else {
                        System.out.println("No task found with the title: " + title);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break; // Exit loop once task is found and deleted
            }
        }
    }




}
