package com.example.lunchbuddies;

public class DataInfo {

    private static DataInfo datainfo=new DataInfo();
    private int user_id=0;
    private int post_id=0;

    public int getReciever_user_id() {
        return reciever_user_id;
    }

    public void setReciever_user_id(int reciever_user_id) {
        this.reciever_user_id = reciever_user_id;
    }

    private int reciever_user_id;
    private String first_name,last_name,email_id,contact_number,password;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static DataInfo getInstance() {
        return datainfo;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public DataInfo() {
    }
}
