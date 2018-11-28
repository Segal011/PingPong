package Game;


import java.awt.*;

public class Paddle
{

    public int paddleNumber;

    public int x, y, width = 40, height = 250;

    public int score;

    public Player playerObject;

    public Paddle(Pong pong, int paddleNumber)
    {
        this.paddleNumber = paddleNumber;

        if (paddleNumber == 1)
        {
            this.x = 30;
        }

        if (paddleNumber == 2)
        {
            this.x = pong.width - width - 20  ;
        }

        this.y = pong.height / 2 - this.height / 2;
    }

    public void render(Graphics g)
    {
        if(playerObject == null) {
            g.setColor( Color.WHITE );
        }else{
            g.setColor( playerObject.getColor());
        }
        g.fillRect(x, y, width, height);
    }

    public void move(boolean up)
    {
        int speed = 30;

        if (up)
        {
            if (y - speed > 5)
            {
                y -= speed;
            }
            else
            {
                y = 5;
            }
        }
        else
        {
            if (y + height + speed < Pong.pong.height)
            {
                y += speed;
            }
            else
            {
                y = Pong.pong.height - height;
            }
        }
    }
}