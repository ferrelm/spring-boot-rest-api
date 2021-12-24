package com.example.demo.boats;

//import java.util.ArrayList;
//import java.util.List;

public class Boat {

    private String id;
    private String color;
    private int size;

    public Boat(String id, String color, int size) {
        this.id = id;
        this.color = color;
        this.size = size;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    /*
     * public Boat(int id, String color, int size) { this.id = id; this.color =
     * color; this.size = size; }
     * 
     * public List<Object> getBoat() {
     * 
     * List<Object> list = new ArrayList<>(); list.add(id); list.add(color);
     * list.add(size);
     * 
     * return list; }
     * 
     * public int getId() { return id; }
     */
}
