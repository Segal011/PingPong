package Game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Data {

    public  static Buffer<Player> players ;
    public static IconsCircularBuffer icons;
    public static IconsCircularBuffer colours;
    private static String file = "C:\\Users\\Indre\\Desktop\\PingPong\\src\\PlayersData.txt";
    private final int size;
    private int iconsCount = 10;


    public Data() {
        size = 20;
        if(players == null) {
            players = new Buffer<>(size);
        }
        icons = new IconsCircularBuffer( iconsCount );
        colours = new IconsCircularBuffer( iconsCount );
        loadData();
        if(players.isEmpty()){
            players.append( new Player("player", Color.WHITE, icons.get(1)));
        }
    }

    public void newPlayersBuffer(){
        players = new Buffer<>(size);
        try {
            Files.deleteIfExists( Paths.get( file ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInPlayersList(String name){
        return players.isInBuffer( name );
    }

    public void AddPlayer(String name, String icon, String color) {
        Color newColor = getColor(color);
        players.append( new Player( name, newColor, icons.get( new Integer(icon) ) ) );
    }

    public static void loadData(){
        try {
            System.out.println("Creating File/Object input stream...");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            System.out.println("Loading Hashtable Object...");
            loadPlayersData((String) in.readObject());
            System.out.println("Closing all input streams...\n");
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadPlayersData(String readObject) {
        String[] lines =  readObject.split("\\r?\\n", -1);
        for(String line : lines) {
            if (line != null && line.split( " " ).length == 5) {
                String[] parts = line.split( " " );
                String name = parts[ 0 ];
                Icon icon = icons.get( new Integer( parts[ 1 ].substring( parts[ 1 ].length() - 5, parts[ 1 ].length() - 4 ) ) );
                Color color = getColor(parts[2]);
                int score = Integer.parseInt( parts[ 3 ] );
                int round = Integer.parseInt( parts[ 4 ] );
                players.append( new Player( name, color,  icon, score, round) );
            }
        }
    }

    public static  void saveData(){
        try {
            System.out.println("Creating File/Object output stream...");
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            String s = players.toString();
            System.out.println(s);
            System.out.println("Writing Hashtable Object...");
            out.writeObject(s);
            System.out.println("Closing all output streams...\n");
            out.close();
            fileOut.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color getColor(String code){
        Color newColor;
        switch (code) {
            case "red":
                newColor = Color.RED;
                break;
            case "blue":
                newColor = Color.BLUE;
                break;
            case "green":
                newColor = Color.GREEN;
                break;
            case "white":
                newColor = Color.WHITE;
                break;
            case "yellow":
                newColor = Color.YELLOW;
                break;
            default:
                newColor = Color.WHITE;
        }
        return newColor;
    }

    public String setColor(Color color){
        if(color.equals( Color.RED )){
            return "red";
        }else if(color.equals( Color.BLUE )){
            return "blue";
        }else if(color.equals( Color.GREEN )){
            return "green";
        }else if(color.equals( Color.YELLOW )){
            return "yellow";
        }
        return "white";
    }
}
