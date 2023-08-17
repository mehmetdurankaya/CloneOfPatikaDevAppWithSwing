package com.patikadev.model;

import com.patikadev.helper.MyDbConnector;
import com.patikadev.helper.MyHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Path {
    private int id;
    private String name;

    public Path() {
    }

    public Path(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Path> getList(){
        ArrayList<Path> paths = new ArrayList<>();

        String query = "SELECT * FROM " + MyHelper.DB_TABLE_NAME_PATH;
        Path path;

        try {
            Statement statement = MyDbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));

                paths.add(path);
            }

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return paths;
    }

    public static boolean add(String pathName){
        String query = "INSERT INTO " + MyHelper.DB_TABLE_NAME_PATH + " (name) VALUES (?)";
        boolean result = false;

        try {
            PreparedStatement preparedStatement = MyDbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,pathName);
            result = (preparedStatement.executeUpdate() != -1);
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if(!result) MyHelper.showMessage("Operation failed! There was an error adding a path.", "Error");
        return result;
    }

    public static boolean update(int id, String name){
        String query = "UPDATE "+ MyHelper.DB_TABLE_NAME_PATH + " SET name = ? WHERE id = ?";
        boolean result = false;
        try {
            PreparedStatement preparedStatement = MyDbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            result = (preparedStatement.executeUpdate() > 0);
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if(!result) MyHelper.showMessage("Operation failed! There was an error updating the path name.", "Updating Error");
        return result;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM " + MyHelper.DB_TABLE_NAME_PATH + " WHERE id = ?";
        boolean result = false;

        ArrayList<Course> pathCourseList = Course.getListByPathID(id);
        if(pathCourseList.size() > 0){
            StringBuilder message = new StringBuilder("Attention!\nRelated courses will be deleted along with the path.");
            for (Course c : pathCourseList){
                message.append("\n").append(c.getCourseName());
            }
            message.append("\nDo you approve?");

            if(MyHelper.confirm(message.toString(), "Confirm Message!")){
                for (Course c : pathCourseList){
                    Course.delete(c.getCourseId());
                }
            }
            else {
                MyHelper.showMessage("The transaction was canceled.", "Cancel Message");
                return false;
            }
        }

        try {
            PreparedStatement preparedStatement = MyDbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            result = (preparedStatement.executeUpdate() > 0);
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static Path getPathByID(int id){
        Path path = null;
        String query = "SELECT * FROM " + MyHelper.DB_TABLE_NAME_PATH + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = MyDbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return path;
    }
}
