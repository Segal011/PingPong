package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Buffer<Player> {

    public Game.Player[] buffer;

    private int n, head, tail;

    boolean isFull;

    public Buffer(int n) {
        this.buffer = new Game.Player[ n ];
        this.n = n;
        this.head = this.tail = 0;
        this.isFull = false;
    }

    public boolean isEmpty() {
        return !this.isFull && (this.head == this.tail);
    }

    public void append(Game.Player s) {
        if(s == null){return;}
        if (this.isFull) this.head = (this.head + 1) % this.n;
        if(isInBuffer( s.Name )){return;}
        this.buffer[ this.tail ] = s;
        this.tail = (this.tail + 1) % this.n;
        if (this.tail == this.head) this.isFull = true;
    }

    public boolean isInBuffer(String s){
        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        for (int i = 0; i < numElements; i++) {
            if (s.equals( this.buffer[ this.head + i ].Name )) {
                return true;
            }
        }
        return false;
    }

    public void remove() {
        if (!this.isEmpty()) {
            this.head = (this.head + 1) % this.n;
            this.isFull = false;
        }
    }

    public Player getNext(Player object) {
        if (this.isEmpty() || object == null) return null;
        if (object.equals( this.buffer[ this.tail - 1 ] )) {
            return (Player) this.buffer[ this.head ];
        }
        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        for (int i = 0; i < numElements; i++) {
            if (this.buffer[ this.head + i ].equals( object )) {
                return (Player) this.buffer[ this.head + i + 1 ];
            }
        }
        return null;
    }

    public Player getPrevious(Player object) {
        if (this.isEmpty() || object == null) return null;
        if (object.equals( this.buffer[ this.head ] )) {
            return (Player) this.buffer[ this.tail - 1 ];
        }
        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        for (int i = 1; i < numElements; i++) {
            if (this.buffer[ this.head + i ].equals( object )) {
                return (Player) this.buffer[ this.head + i - 1 ];
            }
        }
        return null;
    }

    public Player getHead() {
        if (this.isEmpty()) return null;
        return (Player) this.buffer[ this.head ];
    }

    public Player getTail() {
        if (this.isEmpty()) return null;
        return (Player) this.buffer[ this.tail -1];
    }

    public List<Game.Player> sortAndToList() {
        List<Game.Player> playerObjects = new ArrayList<>();


        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        int i = 0;
        while(i < numElements && i < 10){
            if (i > 3){
                System.out.println(i  );
            }
            playerObjects.add( this.buffer[ (this.head + i) % this.n ] );
            i++;
        }
        Collections.sort( playerObjects );
        return playerObjects;
    }

    public String toString() {
        StringBuilder string = new StringBuilder(  );
        string.append( '\n' );
        Player player = this.getHead();
        while (player != this.getTail()){
            string.append( player.toString()+ '\n' );
            player = this.getNext( player );
        }
        string.append( player.toString()+ '\n' );
        return String.valueOf( string );
    }
}