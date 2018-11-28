package Game;

import javax.swing.*;
import java.awt.*;

public class Player implements Comparable <Player> {

    public String Name;
    public String ID;
    public static int No;
    public Icon Icon;
    public Color Color;
    public int Score;
    public int Round;

    public Player(String name, Color color, Icon icon){
        Name = name;
        Icon = icon;
        Color = color;
        ID = String.valueOf( No++ );
        Score = 0;
        Round = 0;
    }
    public Player(String name, Color color, Icon icon, int score, int round){
        Name = name;
        Icon = icon;
        Color = color;
        ID = String.valueOf( No++ );
        Score = score;
        Round = round;
    }

    public String getID() {
        return ID;
    }

    public Icon getIcon() {
        return Icon;
    }

    public java.awt.Color getColor() {
        return Color;
    }


    public int getScore() {
        return Score;
    }

    public void setScore() {
        Score ++;
    }

    public void setRound() {
        Round ++;
    }


    @Override
    public String toString() {
        Data data = new Data();
        return (this.Name + " " + this.Icon + " " + data.setColor( this.Color )  + " " + this.Score + " " + this.Round);
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(o.getScore(), this.getScore());
    }
}
