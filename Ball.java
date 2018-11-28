

package Game;

import java.awt.*;
import java.util.Random;

public class Ball
{

    public int x, y, width = 35, height = 35;

    public int motionX, motionY;

    private int ballColour; // 0 - white; 1 - first playerObject; 2 - second playerObject

    public Random random;

    private Pong pong;

    public int amountOfHits;

    public Ball(Pong pong)
    {
        ballColour = 0;
        this.pong = pong;
        this.random = new Random();
        spawn();
    }


    public void update(Paddle paddle1, Paddle paddle2)
    {
        int speed = 7;
        this.x += motionX * speed;
        this.y += motionY * speed;
        if (this.y + height - motionY > pong.height  || this.y + motionY < 5)
        {
            if (this.motionY < 0)
            {
                this.y = 5;
                this.motionY = random.nextInt(4);

                if (motionY == 0)
                {
                    motionY = 1;
                }
            }
            else
            {
                this.motionY = -random.nextInt(4);
                this.y = pong.height - height;
                if (motionY == 0)
                {
                    motionY = -1;
                }
            }
        }
        if (checkCollision(paddle1) == 1)
        {
            this.motionX = 1 + (amountOfHits / 2);
            //  this.motionY = -2 + random.nextInt(4);
            this.motionY = -2;
            if (motionY == 0)
            {
                motionY = 1;
            }
            ballColour = 1;
            amountOfHits++;
        }
        else if (checkCollision(paddle2) == 1)
        {
            this.motionX = -1 - (amountOfHits / 2);
            //this.motionY = -2 + random.nextInt(4);
            this.motionY = -2;
            if (motionY == 0)
            {
                motionY = 1;
            }
            ballColour = 2;
            amountOfHits++;
        }
        if (checkCollision(paddle1) == 2)
        {
            paddle2.score++;
            spawn();
        }
        else if (checkCollision(paddle2) == 2)
        {
            paddle1.score++;
            spawn();
        }
    }

    public void spawn()
    {
        this.amountOfHits = 0;
        this.x = pong.width / 2 - this.width / 2;
        this.y = pong.height / 2 - this.height / 2;
        this.motionY = -2 + random.nextInt(4);
        if (motionY == 0)
        {
            motionY = 1;
        }
        if (random.nextBoolean())
        {
            motionX = 1;
        }
        else
        {
            motionX = -1;
        }
    }

    public int checkCollision(Paddle paddle)
    {
        if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
        {
            return 1; //bounce
        }
        else if ((paddle.x > this.x && this.x <= width/2  && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2&& this.x >= width/2 + pong.width ))
        {
            return 2; //score
        }
        return 0; //nothing
    }

    public void render(Graphics g)
    {
        if(ballColour == 0) {
            g.setColor( Color.WHITE );
        } else if (ballColour == 1){
            g.setColor( pong.currentPlayer1.getColor());
        } else{
            g.setColor( pong.currentPlayer2.getColor());
        }
        g.fillOval(x, y, width, height);
    }

}