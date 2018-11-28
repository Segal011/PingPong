package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddPlayer extends  JPanel  implements ActionListener {

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    ArrayList<JRadioButton> coloursButtons;
    ArrayList<JRadioButton> iconsButtons;
    public String name, colour, icon;
    private Renderer renderer;
    private JFrame frame;
    private Graphics2D g;
    private Graphics2D graphics2D;
    private  int width = 1000;
    private  int height = 600;
    ButtonGroup colourGroup =new ButtonGroup();
    ButtonGroup iconsGroup =new ButtonGroup();

    Data data;

    public AddPlayer() {
        data = new Data();
        AddColours();
        AddIcons();
        addNewPlayer();
    }

    public void addNewPlayer(){
        Pong.pong.addNewPlayer = false;
        JTextField nameFeld = new JTextField(5);
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        c.gridy = 0;
        panel.add(new JLabel( "Name:" ), c);
        c.gridy = 1;
        panel.add(nameFeld, c);
        c.gridy = 2;
        panel.add(new JLabel( "Colour:"  ), c);
        c.gridy = 3;
        for(JRadioButton button : coloursButtons){
            colourGroup.add(button);
            panel.add(button, c);
        }
        c.gridy = 4;
        panel.add(new JLabel( "Icon:"  ), c);
        c.gridy = 5;
        for(JRadioButton icon : iconsButtons){
            iconsGroup.add(icon);
            panel.add(icon, c);
        }
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Create New Player", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for(JRadioButton button : coloursButtons){
                if(button.isSelected()){
                    colour = button.getText();
                    button.setLayout(new GridLayout(0, 2, 10, 0));
                    break;
                }
            }
            for(JRadioButton button : iconsButtons){
                if(button.isSelected()){
                    icon = button.getText();
                    button.setBounds(150, 80, 50, 20);
                    break;
                }
            }
            if(data.isInPlayersList( nameFeld.getText() )){
                JOptionPane.showMessageDialog(null, "Player with the same name is already existing");
            }else if(nameFeld != null && nameFeld.getText() != ""  && icon != null && colour != null){
                data.AddPlayer(nameFeld.getText(), icon, colour);
                Pong.pong.gameStatus = 5;
                Pong.data.saveData();
                Pong.pong.addNewPlayer = true;

            }else{
                JOptionPane.showMessageDialog(null, "Wrong inputs");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Any New Payer");
        }
    }

    private void AddColours() {
        coloursButtons =new ArrayList<>(  );
        coloursButtons.add(new JRadioButton("red"));
        coloursButtons.add(new JRadioButton("blue"));
        coloursButtons.add(new JRadioButton("green"));
        coloursButtons.add(new JRadioButton("white"));
        coloursButtons.add(new JRadioButton("yellow"));
        coloursButtons.get( 0).updateUI();
    }

    private void AddIcons() {
        iconsButtons =new ArrayList<>(  );
        ArrayList<Icon> icons = data.icons.list();
        for(int i = 0; i < icons.size(); i++){
            iconsButtons.add( new JRadioButton( String.valueOf( i ), icons.get( i ) ) );
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor( Color.BLACK );
        g.fillRect( 0, 0,width +30, height +30 );
        g.setColor( Color.WHITE );
        g.drawRect( 5, 5, width-10, height-10 );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
