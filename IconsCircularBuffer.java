package Game;

import javax.swing.*;
import java.util.ArrayList;


public class IconsCircularBuffer {

    /**
     * Circular buffer
     */
    public Icon[] buffer;
    /**
     * n: Size of buffer, head: Index of first element in buffer, tail: Index of next
     * element to append
     */
    private int n, head, tail;
    /**
     * <tt>true</tt> if buffer is full to capacity, and <tt>false</tt> otherwise
     */
    boolean isFull;

    /**
     * Constructor
     *
     * @param n Size of buffer
     * @precondition n > 0
     */
    public IconsCircularBuffer(int n) {
        this.buffer = new Icon[ n ];
        this.n = n;
        this.head = this.tail = 0;
        this.isFull = false;
        readImages();
    }

    private void readImages(){
        for(int i = 1; i <= n; i++) {
            Icon image = new ImageIcon( "C:\\Users\\Indre\\Desktop\\Project\\icons\\" + i + ".png" );
            append( image );
        }
    }

    /**
     * Checks whether buffer is empty
     *
     * @return <tt>true</tt> if empty, <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return !this.isFull && (this.head == this.tail);
    }

    /**
     * Appends <tt>String</tt> item to buffer (If buffer is full, oldest entry is
     * replaced)
     *
     * @param s <tt>String</tt> item to append
     */
    public void append(Icon s) {
        if (this.isFull) this.head = (this.head + 1) % this.n;
        this.buffer[ this.tail ] = s;
        this.tail = (this.tail + 1) % this.n;
        if (this.tail == this.head) this.isFull = true;
    }

    /**
     * Removes oldest item from buffer (If buffer is empty, no action is taken)
     */
    public void remove() {
        if (!this.isEmpty()) {
            this.head = (this.head + 1) % this.n;
            this.isFull = false;
        }
    }

    public Icon getNext(Icon object) {
        if (this.isEmpty() || object == null) return null;
        if (object.equals( this.buffer[ this.tail - 1 ] )) {
            return (Icon) this.buffer[ this.head ];
        }
        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        for (int i = 0; i < numElements; i++) {
            if (this.buffer[ this.head + i ].equals( object )) {
                return (Icon) this.buffer[ this.head + i + 1 ];
            }
        }
        return null;
    }

    public Icon getPrevious(Icon object) {
        if (this.isEmpty() || object == null) return null;
        if (object.equals( this.buffer[ this.head ] )) {
            return (Icon) this.buffer[ this.tail - 1 ];
        }
        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        for (int i = 1; i < numElements; i++) {
            if (this.buffer[ this.head + i ].equals( object )) {
                return (Icon) this.buffer[ this.head + i - 1 ];
            }
        }
        return null;
    }

    public Icon getHead() {
        if (this.isEmpty()) return null;
        return (Icon) this.buffer[ this.head ];
    }

    public Icon get(int i) {
        if (this.isEmpty()) return null;
        return (Icon) this.buffer[ i ];
    }

    /**
     * Returns <tt>String</tt> of buffer items in order of their insertion time
     *
     * @return <tt>String</tt> of buffer items
     */
    public ArrayList<Icon> list() {
        ArrayList<Icon> icons = new ArrayList<>();
        int numElements = (this.isFull) ? this.n : ((this.head > this.tail) ? (this.tail + this.n) - this.head : this.tail - this.head);
        for (int i = 0; i < numElements; i++) icons.add( this.buffer[ (this.head + i) % this.n ] );
        return icons;
    }




}
