import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelectionInterface extends JFrame {
    private JButton startGameButton;
    private JComboBox<String> player1ClassComboBox;
    private JComboBox<String> player2ClassComboBox;

    public CharacterSelectionInterface() {

        startGameButton = new JButton("Start Game");


        setLayout(new GridLayout(3, 2));


        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });


        String[] classOptions = {"Select Class", "Warrior", "Mage", "Rogue", "Archer", "Paladin"};
        player1ClassComboBox = new JComboBox<>(classOptions);
        player2ClassComboBox = new JComboBox<>(classOptions);


        add(new JLabel("Player 1 Class:"));
        add(player1ClassComboBox);
        add(new JLabel("Player 2 Class:"));
        add(player2ClassComboBox);
        add(new JLabel());
        add(startGameButton);


        setTitle("Character Selection");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {

        if (player1ClassComboBox.getSelectedIndex() == 0 || player2ClassComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Both players must select a class to start the game.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if(player1ClassComboBox.getSelectedIndex() == player2ClassComboBox.getSelectedIndex())
        JOptionPane.showMessageDialog(this, "Players must choose different classes", "Error", JOptionPane.ERROR_MESSAGE);
            else{

            String player1Class = (String) player1ClassComboBox.getSelectedItem();
            String player2Class = (String) player2ClassComboBox.getSelectedItem();


            String message = "Game started!\n";
            message += "Player 1 selected: " + player1Class + "\n";
            message += "Player 2 selected: " + player2Class;
            new TwoPlayerCardGame(player1Class, player2Class);
            dispose();

            JOptionPane.showMessageDialog(this, message, "Game Started", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CharacterSelectionInterface());
    }
}
