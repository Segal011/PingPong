
package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Pong extends JComponent  implements ActionListener, KeyListener {

    public static Data data;

    public Player currentPlayer1;

    public Player currentPlayer2;

    public static Pong pong;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public int width = screenSize.width - 15, height = screenSize.height - 15   ;

    private boolean selectScoreLimit = false, selectPlayer1 = false, selectPlayer2 = false, selectDifficulty = false, selectName = false, selectIcon = false, selectColour = false;

    public boolean addNewPlayer = false;

    private Renderer renderer;

    private Paddle player1;

    private Paddle player2;

    private Ball ball;

    private boolean w, s, up, down;

    public int gameStatus = 0, scoreLimit = 7, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over, 4 = ChoosePlayer, 5 = AddNewPlayer  7 = results

    private Random random;

    private JFrame jframe;

    public Pong() {
        addNewPlayer = false;
        Timer timer = new Timer( 20, this );
        random = new Random();
        jframe = new JFrame( "Pong" );
        renderer = new Renderer();
        jframe.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        jframe.setLocationRelativeTo( null );
        jframe.setSize( screenSize );
        jframe.setPreferredSize( screenSize );
        jframe.setMinimumSize( screenSize );
        jframe.setMaximumSize( screenSize );
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setUndecorated(true);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.pack();
        jframe.add( renderer );
        jframe.addKeyListener( this );
        timer.start();
    }

    public void start() {
        gameStatus = 2;
        player1 = new Paddle( this, 1 );
        player2 = new Paddle( this, 2 );
        ball = new Ball( this );
    }

    public void update() {
        if (player1.score >= scoreLimit) {
            playerWon = 1;
            player1.playerObject.setRound();
            player1.playerObject.setScore();
            player2.playerObject.setRound();
            gameStatus = 3;
            Pong.data.saveData();
        }
        if (player2.score >= scoreLimit) {
            gameStatus = 3;
            player2.playerObject.setRound();
            player2.playerObject.setScore();
            player1.playerObject.setRound();
            playerWon = 2;
            Pong.data.saveData();
        }
        if (w) {
            player1.move( true );
        }
        if (s) {
            player1.move( false );
        }
        if (up) {
            player2.move( true );
        }
        if (down) {
            player2.move( false );
        }
        ball.update( player1, player2 );
    }


    public void render(Graphics2D g)  {
        g.setColor( Color.BLACK );
        g.fillRect( 0, 0,width +30, height +30 );
        g.setColor( Color.WHITE );
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(5));
        g.drawRect(5, 5, width-5, height-5);
        g.setStroke(oldStroke);
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setColor( Color.WHITE );
        switch (gameStatus) {
            case 0:
                g.setFont( new Font( "Arial", 1, 50 ) );
                g.drawString( "PONG", width / 2 - 75, 100 );
                g.setFont( new Font( "Arial", 1, 35 ) );
                g.drawString( "Press SPACE to Play", width / 2 - 150, height / 2 - 100 );
                g.drawString( "Press SPACE to See The Score Board", width / 2 - 270, height / 2 - 20 );
                g.drawString( "Press DELETE to Remove All Players", width / 2 - 270, height / 2 + 60 );
                break;
            case 1:
                g.setColor( Color.WHITE );
                g.setFont( new Font( "Arial", 1, 50 ) );
                g.drawString( "PAUSED", width / 2 - 103, height / 2 - 25 );
                break;
            case 2:
                g.setColor( Color.WHITE );
                g.setStroke( new BasicStroke( 5f ) );
                g.drawLine( width / 2, 5, width / 2, height - 5 );
                g.setStroke( new BasicStroke( 2f ) );
                g.drawOval( width / 2 - 150, height / 2 - 150, 300, 300 );
                g.setFont( new Font( "Arial", 1, 50 ) );
                g.drawString( String.valueOf( player1.score ), width / 2 - 90, 50 );
                g.drawString( String.valueOf( player2.score ), width / 2 + 65, 50 );
                player1.render( g );
                player2.render( g );
                ball.render( g );
                break;
            case 3:
                g.setFont( new Font( "Arial", 1, 50 ) );
                g.drawString( "PONG", width / 2 - 75, 100 );
                if (playerWon == 1) {
                    g.drawString( "Player " + player1.playerObject.Name + " Wins!", width / 2 - 240, height / 2 - 180 );
                    player1.playerObject.Icon.paintIcon( this, g, width / 2 - 20, height / 2 - 100 );
                } else if (playerWon == 2) {
                    g.drawString( "Player " + player2.playerObject.Name + " Wins!", width / 2 - 240, height / 2 - 110 );
                    player1.playerObject.Icon.paintIcon( this, g, width / 2 - 20, height / 2 - 100 );
                }
                data.loadData();
                g.setFont( new Font( "Arial", 1, 30 ) );
                g.drawString( "Press SPACE to Play Again", width / 2 - 185, height / 2 + 30 );
                g.drawString( "Press ESCAPE for Menu", width / 2 - 175, height / 2 + 70 );
                break;
            case 4:
                g.setFont( new Font( "Arial", 1, 50 ) );
                g.drawString( "PONG", width / 2 - 75, 100 );
                g.setFont( new Font( "Arial", 1, 30 ) );
                g.drawString( "<< Score Limit: " + scoreLimit + " >>", width / 2 - 150, height / 2 - 100 );
                if(currentPlayer1.equals( currentPlayer2 )){
                    g.setColor( Color.GRAY );
                    g.drawString( "<< Player1: " + currentPlayer1.Name + " >>", width / 2 - 150, height / 2 - 40 );
                    g.setColor( Color.WHITE );
                }
                else {
                    g.drawString( "<< Player1: " + currentPlayer1.Name + " >>", width / 2 - 150, height / 2 - 40 );
                }
                if(currentPlayer1.equals( currentPlayer2 )){
                    g.setColor( Color.GRAY );
                    g.drawString( "<< Player2: " + currentPlayer2.Name + " >>", width / 2 - 150, height / 2 + 20 );
                    g.setColor( Color.WHITE );
                }
                else {
                    g.drawString( "<< Player2: " + currentPlayer2.Name + " >>", width / 2 - 150, height / 2 + 20 );
                }
                g.drawString( "Press SHIFT to Add New Player", width / 2 - 230, height / 2 + 80 );
                g.drawString( "Press SPACE to Play", width / 2 - 150, height / 2 + 140 );
                break;
            case 5:
                if (addNewPlayer) {
                    g.setFont( new Font( "Arial", 1, 50 ) );
                    g.drawString( "PONG", width / 2 - 75, 100 );
                    g.setFont( new Font( "Arial", 1, 30 ) );
                    g.drawString( " New Player:", width / 2 - 180, height / 2 - 60 );
                    g.drawString( data.players.getTail().Name, width / 2 - 60, height / 2 + 0 );
                    data.players.getTail().Icon.paintIcon( this, g, width / 2 - 220, height / 2 - 30 );
                    g.setColor( data.players.getTail().getColor() );
                    g.fillRect( width / 2 - 120, height / 2 - 30, 40, 40 );
                }
                break;
            case 7:
                g.setFont( new Font( "Arial", 1, 50 ) );
                g.drawString( "PONG", width / 2 - 75, 100 );
                g.setFont( new Font( "Arial", 1, 30 ) );
                g.drawString( "The Best Players", width / 2 - 150, height / 2 - 200 );
                java.util.List<Player> playerObjects = data.players.sortAndToList();
                for (int i = 0; i < playerObjects.size(); i++) {
                    g.drawString( i + ".", width / 2 - 260, height / 2 - 100 + i * 60 );
                    playerObjects.get( i ).Icon.paintIcon( this, g, width / 2 - 220, height / 2 - 160 + i * 60 );
                    g.drawString( playerObjects.get( i ).Name, width / 2 - 120, height / 2 - 100 + i * 60 );
                    g.drawString( String.valueOf( playerObjects.get( i ).Score ), width / 2 + 80, height / 2 - 100 + i * 60 );
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStatus == 2) {
            update();
        }
        renderer.repaint();
    }

    public static void main(String[] args) {
        data = new Data();
        pong = new Pong();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W) {
            w = true;
        } else if (id == KeyEvent.VK_S) {
            s = true;
        } else if (id == KeyEvent.VK_UP) {
            if(gameStatus == 2) {
                up = true;
            }else if(gameStatus  == 4 && selectPlayer2) {
                selectPlayer2 = false;
                selectPlayer1 = true;
            }else if(gameStatus  == 4 && selectPlayer1) {
                selectPlayer1 = false;
                selectScoreLimit = true;
            }else if(gameStatus  == 4 && selectScoreLimit) {
                selectScoreLimit = false;
                selectPlayer2 = true;
            }else if(selectColour) {
                selectColour = false;
                selectIcon = true;
            }else if(selectIcon) {
                selectIcon = false;
                selectName = true;
            }else if(selectName) {
                selectName = false;
                selectColour = true;
            }
        } else if (id == KeyEvent.VK_DOWN) {
            if(gameStatus == 2){
                down = true;
            }else if(gameStatus  == 4 && selectScoreLimit){
                selectScoreLimit = false;
                selectPlayer1 = true;
            }else if(gameStatus  == 4 && selectPlayer1){
                selectPlayer1 = false;
                selectPlayer2 = true;
            }else if(gameStatus  == 4 && selectPlayer2) {
                selectPlayer2 = false;
                selectScoreLimit = true;
            }else  if (selectName) {
                selectName = false;
                selectIcon = true;
            } else if (selectIcon) {
                selectIcon = false;
                selectColour = true;
            } else if (selectColour) {
                selectColour = false;
                selectName = true;
            }
        } else if (id == KeyEvent.VK_RIGHT) {
            if (selectScoreLimit) {
                scoreLimit++;
            } else if (selectPlayer1) {
               currentPlayer1 = data.players.getNext( currentPlayer1 );
            }else if (selectPlayer2) {
                currentPlayer2 = data.players.getNext( currentPlayer2 );
            }
        } else if (id == KeyEvent.VK_LEFT) {

            if (selectScoreLimit) {
                scoreLimit--;
                if(scoreLimit < 0) {
                    scoreLimit = 0;
                }
            } else if (selectPlayer1) {
                currentPlayer1 = data.players.getPrevious( currentPlayer1 );
            }else if (selectPlayer2) {
                currentPlayer2 = data.players.getPrevious( currentPlayer2 );
            }
        } else if (id == KeyEvent.VK_ESCAPE ) {
            if(gameStatus == 1){
                gameStatus = 2;
            }else {
                gameStatus = 0;
            }
        } else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 5 )) {
             gameStatus = 4;
        } else if (id == KeyEvent.VK_SHIFT&& ( gameStatus == 4)) {
            AddPlayer addPlayer = new AddPlayer();
              gameStatus = 5;
        }else if (id == KeyEvent.VK_SHIFT&& gameStatus == 0) {
                gameStatus = 7;
        } else if (id == KeyEvent.VK_SPACE) {
            if(gameStatus == 0 ){
                selectScoreLimit = true;
                currentPlayer1 = data.players.getHead();
                currentPlayer2 = data.players.getHead();
                gameStatus = 4;
            }else if (gameStatus == 4){
                gameStatus = 2;
                start();
                player1.playerObject = currentPlayer1;
                player2.playerObject = currentPlayer2;
            } else if ( gameStatus == 3) {
                gameStatus = 0;
            } else if (gameStatus == 1) {
                gameStatus = 2;
            } else if (gameStatus == 2) {
                gameStatus = 1;
            } else if (gameStatus == 5) {
                gameStatus = 4;
            }
        } else if( id == KeyEvent.VK_DELETE && gameStatus == 0){
            data.newPlayersBuffer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W) {
            w = false;
        } else if (id == KeyEvent.VK_S) {
            s = false;
        } else if (id == KeyEvent.VK_UP) {
            up = false;
        } else if (id == KeyEvent.VK_DOWN) {
            down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
