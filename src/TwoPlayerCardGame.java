import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class TwoPlayerCardGame extends JFrame {
    private Player player1;
    private Player player2;
    private String player1Class;
    private String player2Class;
    private Boss evilBoss;
    private boolean player1Turn;

    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel bossLabel;

    private JProgressBar player1HealthBar;
    private JProgressBar player2HealthBar;
    private JProgressBar bossHealthBar;

    private JLabel player1CardLabel;

    private JPanel panel;
    private JLabel player2CardLabel;
    private JLabel bossCardLabel;

    private int turnsCount;

    public TwoPlayerCardGame(String player1Class, String player2Class) {
        this.player1Class = player1Class;
        this.player2Class = player2Class;
        player1 = new Player("Aragorn", 100, player1Class, new Deck());
        player2 = new Player("Legolas", 100, player2Class, new Deck());
        evilBoss = new Boss("Evil Boss", 20);
        player1Turn = true; // Player 1 starts the game



        ImageIcon player1CardIcon = new ImageIcon("C:/Users/Radu/IdeaProjects/Card Game/Images/" + player1Class + ".png");
        ImageIcon player2CardIcon = new ImageIcon("C:/Users/Radu/IdeaProjects/Card Game/Images/" + player2Class + ".png");
        ImageIcon bossCardIcon = new ImageIcon("C:/Users/Radu/IdeaProjects/Card Game/Images/Boss.png");



        player1Label = new JLabel("Player 1: " + player1.getName() + " (" + player1Class + ")");
        player2Label = new JLabel("Player 2: " + player2.getName() + " (" + player2Class + ")");
        bossLabel = new JLabel("Boss: " + evilBoss.getName());

        player1HealthBar = new JProgressBar(0, player1.getMaxHealth());
        player1HealthBar.setValue(player1.getHealth());

        player2HealthBar = new JProgressBar(0, player2.getMaxHealth());
        player2HealthBar.setValue(player2.getHealth());

        bossHealthBar = new JProgressBar(0, evilBoss.getMaxHealth());
        bossHealthBar.setValue(evilBoss.getHealth());

        player1CardLabel = new JLabel(player1CardIcon);
        player2CardLabel = new JLabel(player2CardIcon);
        bossCardLabel = new JLabel(bossCardIcon);

        // Set up layout
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(player1Label);
        panel.add(player1HealthBar);
        panel.add(player1CardLabel);

        panel.add(player2Label);
        panel.add(player2HealthBar);
        panel.add(player2CardLabel);

        panel.add(bossLabel);
        panel.add(bossHealthBar);
        panel.add(bossCardLabel);

        add(panel);

        setKeyBindings();
        updateLabels();

        setTitle("Two-Player Card Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        handleDrawCard(player1, player1CardLabel);
        handleDrawCard(player2, player2CardLabel);
        handleDrawCard(player1, player1CardLabel);
        handleDrawCard(player2, player2CardLabel);
        refreshPlayerCards(player2);
        refreshPlayerCards(player1);

        updateHealthBars();
    }

    private void displayPlayerCards(List<String> cardImages, Player player) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new FlowLayout());

        for (String imagePath : cardImages) {
            ImageIcon cardIcon = new ImageIcon(imagePath);


            Image scaledImage = cardIcon.getImage().getScaledInstance(50, 75, Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(scaledImage);

            JLabel cardLabel = new JLabel(cardIcon);
            cardPanel.add(cardLabel);
        }


        if (player == player1) {

            JPanel player1CardPanel = new JPanel();
            player1CardPanel.setLayout(new FlowLayout());
            for (String imagePath : cardImages) {
                ImageIcon cardIcon = new ImageIcon(imagePath);
                JLabel cardLabel = new JLabel(cardIcon);
                player1CardPanel.add(cardLabel);
            }
            panel.add(player1CardPanel);
        } else if (player == player2) {

            JPanel player2CardPanel = new JPanel();
            player2CardPanel.setLayout(new FlowLayout());
            for (String imagePath : cardImages) {
                ImageIcon cardIcon = new ImageIcon(imagePath);
                JLabel cardLabel = new JLabel(cardIcon);
                player2CardPanel.add(cardLabel);
            }
            panel.add(player2CardPanel);
        }

        panel.revalidate();
        panel.repaint();


    }

    private void refreshPlayerCards(Player player) {
        List<String> cardImages = player.getCardImages();


        if (player == player1) {
            removePlayerCardPanel(player2);
        } else if (player == player2) {
            removePlayerCardPanel(player1);
        }


        displayPlayerCards(cardImages, player);
    }

    private void removePlayerCardPanel(Player player) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (player == player1 && component instanceof JPanel && ((JPanel) component).getComponentCount() > 0) {
                panel.remove(component);
            }
            if (player == player2 && component instanceof JPanel && ((JPanel) component).getComponentCount() > 0) {
                panel.remove(component);
            }
        }
    }

    private void setKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();


        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "specialAttackPlayer1");
        actionMap.put("specialAttackPlayer1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1Turn) {
                    try {

                        Card card1 = player1.hand.get(0);
                        Card card2 = player1.hand.get(1);

                        if (card1.getSuit(card1) == card2.getSuit(card2)) {
                            switch (card1.getSuit(card1)) {
                                case diamonds: {
                                    Card playedCard1 = player1.playCard(0);
                                    handleAttackBoss(player1, bossCardLabel, playedCard1.getRank() * 2);
                                    Card playedCard2 = player1.playCard(0);
                                    handleAttackBoss(player1, bossCardLabel, playedCard2.getRank() * 2);
                                    handleDrawCard(player1, player1CardLabel);
                                    handleDrawCard(player1, player1CardLabel);
                                    updateHealthBars();
                                    player1Turn = false;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player2);
                                }
                                break;
                                case clubs: {

                                    Card playedCard1 = player1.playCard(0);
                                    Card playedCard2 = player1.playCard(1);
                                    handleHealSelf(player2, playedCard1.getRank());
                                    handleHealSelf(player2, playedCard2.getRank());
                                    handleHealSelf(player1, playedCard1.getRank());
                                    handleHealSelf(player1, playedCard2.getRank());
                                    handleDrawCard(player1, player1CardLabel);
                                    handleDrawCard(player1, player1CardLabel);
                                    updateHealthBars();
                                    player1Turn = false;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player2);
                                }
                                break;
                                case hearts: {
                                    Card playedCard1 = player1.playCard(0);
                                    handleAttackBoss(player1, bossCardLabel, playedCard1.getRank() * 2);
                                    Card playedCard2 = player1.playCard(0);
                                    handleAttackBoss(player1, bossCardLabel, playedCard2.getRank() * 2);
                                    handleDrawCard(player1, player1CardLabel);
                                    handleDrawCard(player1, player1CardLabel);
                                    updateHealthBars();
                                    player1Turn = false;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player2);
                                }
                                break;
                                case spades: {

                                    Card playedCard1 = player1.playCard(0);
                                    Card playedCard2 = player1.playCard(1);
                                    handleHealSelf(player2, playedCard1.getRank());
                                    handleHealSelf(player2, playedCard2.getRank());
                                    handleHealSelf(player1, playedCard1.getRank());
                                    handleHealSelf(player1, playedCard2.getRank());
                                    handleDrawCard(player1, player1CardLabel);
                                    handleDrawCard(player1, player1CardLabel);
                                    updateHealthBars();
                                    player1Turn = false;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player2);
                                }
                                break;
                                default:
                                    System.out.println("Unknown suit");
                                    break;
                            }
                        }
                    } catch (IndexOutOfBoundsException E) {

                        System.out.println("Player does not have enough cards in hand.");
                    }
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "attackPlayer1");
        actionMap.put("attackPlayer1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1Turn) {

                    int index = 0;
                    Card playedCard = player1.playCard(index);
                    handleAttackBoss(player1, bossCardLabel, playedCard.getRank());
                    handleDrawCard(player1, player1CardLabel);
                    updateHealthBars();
                    player1Turn = false;
                    updateLabels();
                    turnsCount++;
                    if (turnsCount % 2 == 0) {
                        bossTurn();
                    }
                    refreshPlayerCards(player2);
                }
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "healPlayer2");
        actionMap.put("healPlayer2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1Turn) {

                    int index = 0;
                    Card playedCard = player1.playCard(index);
                    handleHealSelf(player2, playedCard.getRank());
                    handleDrawCard(player1, player1CardLabel);
                    updateHealthBars();
                    player1Turn = false;
                    updateLabels();
                    turnsCount++;
                    if (turnsCount % 2 == 0) {
                        bossTurn();
                    }
                    refreshPlayerCards(player2);
                }
            }
        });
        // Set up keyboard input for player 1
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "selfHealPlayer1");
        actionMap.put("selfHealPlayer1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1Turn) {

                    int index = 0;
                    Card playedCard = player1.playCard(index);
                    handleHealSelf(player1, playedCard.getRank());
                    handleDrawCard(player1, player1CardLabel);
                    updateHealthBars();
                    player1Turn = false;
                    updateLabels();
                    turnsCount++;
                    if (turnsCount % 2 == 0) {
                        bossTurn();
                    }
                    refreshPlayerCards(player2);
                }
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "specialAttackPlayer2");
        actionMap.put("specialAttackPlayer2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player1Turn) {
                    try {

                        Card card1 = player2.hand.get(0);
                        Card card2 = player2.hand.get(1);

                        if (card1.getSuit(card1) == card2.getSuit(card2)) {
                            switch (card1.getSuit(card1)) {
                                case diamonds: {
                                    Card playedCard1 = player2.playCard(0);
                                    handleAttackBoss(player2, bossCardLabel, playedCard1.getRank() * 2);
                                    Card playedCard2 = player2.playCard(0);
                                    handleAttackBoss(player2, bossCardLabel, playedCard2.getRank() * 2);
                                    handleDrawCard(player2, player2CardLabel);
                                    handleDrawCard(player2, player2CardLabel);
                                    updateHealthBars();
                                    player1Turn = true;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player1);
                                }
                                break;
                                case clubs: {

                                    Card playedCard1 = player2.playCard(0);
                                    Card playedCard2 = player2.playCard(1);
                                    handleHealSelf(player2, playedCard1.getRank());
                                    handleHealSelf(player2, playedCard2.getRank());
                                    handleHealSelf(player1, playedCard1.getRank());
                                    handleHealSelf(player1, playedCard2.getRank());
                                    handleDrawCard(player2, player2CardLabel);
                                    handleDrawCard(player2, player2CardLabel);
                                    updateHealthBars();
                                    player1Turn = true;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player1);
                                }
                                break;
                                case hearts: {
                                    Card playedCard1 = player2.playCard(0);
                                    handleAttackBoss(player2, bossCardLabel, playedCard1.getRank() * 2);
                                    Card playedCard2 = player2.playCard(0);
                                    handleAttackBoss(player2, bossCardLabel, playedCard2.getRank() * 2);
                                    handleDrawCard(player2, player2CardLabel);
                                    handleDrawCard(player2, player2CardLabel);
                                    updateHealthBars();
                                    player1Turn = true;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player1);
                                }
                                break;
                                case spades: {

                                    Card playedCard1 = player2.playCard(0);
                                    Card playedCard2 = player2.playCard(1);
                                    handleHealSelf(player2, playedCard1.getRank());
                                    handleHealSelf(player2, playedCard2.getRank());
                                    handleHealSelf(player1, playedCard1.getRank());
                                    handleHealSelf(player1, playedCard2.getRank());
                                    handleDrawCard(player2, player2CardLabel);
                                    handleDrawCard(player2, player2CardLabel);
                                    updateHealthBars();
                                    player1Turn = true;
                                    updateLabels();
                                    turnsCount++;
                                    if (turnsCount % 2 == 0) {
                                        bossTurn();
                                    }
                                    refreshPlayerCards(player1);
                                }
                                break;
                                default:
                                    System.out.println("Unknown suit");
                                    break;
                            }
                        }
                    } catch (IndexOutOfBoundsException E) {

                        System.out.println("Player does not have enough cards in hand.");
                    }
                }
            }
        });
        // Set up keyboard input for player 2
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0), "attackPlayer2");
        actionMap.put("attackPlayer2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player1Turn) {

                    int index = 0;
                    Card playedCard = player2.playCard(index);
                    handleAttackBoss(player2, bossCardLabel, playedCard.getRank());
                    handleDrawCard(player2, player2CardLabel);
                    updateHealthBars();
                    player1Turn = true;
                }
                updateLabels();
                turnsCount++;
                if (turnsCount % 2 == 0) {
                    bossTurn();
                }
                refreshPlayerCards(player1);
            }

        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "healPlayer1");
        actionMap.put("healPlayer1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player1Turn) {

                    int index = 0;
                    Card playedCard = player2.playCard(index);
                    handleHealSelf(player1, playedCard.getRank());
                    handleDrawCard(player2, player2CardLabel);
                    updateHealthBars();
                    player1Turn = true;
                    updateLabels();
                    turnsCount++;
                    if (turnsCount % 2 == 0) {
                        bossTurn();
                    }
                    refreshPlayerCards(player1);
                }
            }
        });
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "selfHealPlayer2");
        actionMap.put("selfHealPlayer2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player1Turn) {

                    int index = 0;
                    Card playedCard = player2.playCard(index);
                    handleHealSelf(player2, playedCard.getRank());
                    handleDrawCard(player2, player2CardLabel);
                    updateHealthBars();
                    player1Turn = true;
                    updateLabels();
                    turnsCount++;
                    if (turnsCount % 2 == 0) {
                        bossTurn();
                    }
                    refreshPlayerCards(player1);
                }
            }
        });


    }

    private void handleAttackBoss(Player player, JLabel bossCardLabel, int damage) {
        int rank = damage;
        player.attackBoss(evilBoss, rank);

    }

    private void handleHealSelf(Player player, int heal) {
        int rank = heal;
        player.healSelf(player, rank);

    }

    private void handleDrawCard(Player player, JLabel playerCardLabel) {
        player.drawCard();

    }

    private void bossTurn() {
        if (evilBoss.isAlive()) {
            int damageToPlayer1 = evilBoss.attackPlayer();
            int damageToPlayer2 = evilBoss.attackPlayer();

            System.out.println("Boss attacks Player 1, dealing " + damageToPlayer1 + " damage.");
            System.out.println("Boss attacks Player 2, dealing " + damageToPlayer2 + " damage.");

            player1.takeDamage(damageToPlayer1);
            player2.takeDamage(damageToPlayer2);

            updateHealthBars();
            if (!player1.isAlive() && !player2.isAlive()) {
                showGameOverDialog("Both players have been defeated. Evil Boss wins!");
            } else if (!player2.isAlive()) {
                showGameOverDialog(player2.getName() + " the " + player2.getHeroType() + " has been defeated. Evil Boss wins!");
            } else if (!player1.isAlive()) {
                showGameOverDialog(player1.getName() + " the " + player1.getHeroType() + " has been defeated. Evil Boss wins!");
            }
        }
        else showGameOverDialog("Evil Boss has been defeated. Players won!");
    }


    private void updateLabels() {
        // Update player labels
        player1Label.setText("Player 1: " + player1.getName() + " (" + player1.getHeroType() + ") Health: " + player1.getHealth());
        player2Label.setText("Player 2: " + player2.getName() + " (" + player2.getHeroType() + ") Health: " + player2.getHealth());

        // Update boss label
        bossLabel.setText("Boss: " + evilBoss.getName() + " Health: " + evilBoss.getHealth());
    }

    private void updateHealthBars() {

        player1HealthBar.setValue(player1.getHealth());
        player2HealthBar.setValue(player2.getHealth());
        bossHealthBar.setValue(evilBoss.getHealth());


        repaint();
    }

    private void showGameOverDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

}
