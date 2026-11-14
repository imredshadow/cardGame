//Name: Jian Acol
//PD: 6
//Descrition: Creates the blackjack game's graphical interface, organizes the deck, discard pile, dealer's cards, and player's cards, and displays overlapping cards using a JLayeredPane

package com.cardGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private Solitaire game;
    public static String IMG_PATH = System.getProperty("user.dir") + "/src/main/java/com/cardGame/resources/";

    // GUI panels
    private JPanel gameArea;
    private JPanel topArea;
    private JPanel deckArea;
    private JPanel discardArea;
    private JPanel dealerArea;
    private JPanel playerArea;

    // Game buttons
    private JButton hitButton;
    private JButton standButton;

    private JLabel resultLabel;

    private JButton bet25Button;
    private JButton bet50Button;
    private JButton bet75Button;
    private JButton bet100Button;

    private JLabel moneyLabel;
    private JLabel betLabel;

    private boolean canBet = true;

    private JLabel dealerValueLabel;
    private JLabel playerValueLabel;

    public GUI(Solitaire game) {
        this.game = game;
        // Create and set up the window.
        setTitle("Blackjack");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this supplies the background
        try {
            Image blackImg = ImageIO.read(new File(IMG_PATH + "background.jpg"));

            setContentPane(new ImagePanel(blackImg));

        } catch (IOException e) {
            e.printStackTrace();
        }

        gameArea = new JPanel(new GridLayout(3, 1, 10, 10));
        gameArea.setOpaque(false);
        gameArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));

        topArea = new JPanel(new GridLayout(1, 2, 10, 10));
        topArea.setOpaque(false);

        deckArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deckArea.setOpaque(false);
        deckArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Deck"));

        discardArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        discardArea.setOpaque(false);
        discardArea.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), "Discard Pile"));

        topArea.add(deckArea);
        topArea.add(discardArea);

        dealerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dealerArea.setOpaque(false);
        dealerArea.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), "Dealer's Cards"));

        playerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerArea.setOpaque(false);
        playerArea.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW), "Your Cards"));

        gameArea.add(topArea);
        gameArea.add(dealerArea);
        gameArea.add(playerArea);

        this.setLayout(new BorderLayout());
        this.add(gameArea, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        hitButton.addActionListener(e -> {
            if (canBet == false && game.roundOver == false) {
                game.hit();

                if (game.roundOver == true) {
                    finishRound();
                }

                updateDisplay();
            }
        });

        standButton.addActionListener(e -> {
            if (canBet == false && game.roundOver == false) {
                game.stand();

                if (game.roundOver == true) {
                    finishRound();
                }

                updateDisplay();
            }
        });

        this.add(buttonPanel, BorderLayout.SOUTH);

        bet25Button = new JButton("Bet 25");
        bet50Button = new JButton("Bet 50");
        bet75Button = new JButton("Bet 75");
        bet100Button = new JButton("Bet 100");

        moneyLabel = new JLabel("Chips: 500");
        betLabel = new JLabel("Current Bet: 0");
        resultLabel = new JLabel("Place your bet!");

        buttonPanel.add(moneyLabel);
        buttonPanel.add(betLabel);

        buttonPanel.add(bet25Button);
        buttonPanel.add(bet50Button);
        buttonPanel.add(bet75Button);
        buttonPanel.add(bet100Button);

        buttonPanel.add(resultLabel);

        bet25Button.addActionListener(e -> {
            placeBet(25);
        });

        bet50Button.addActionListener(e -> {
            placeBet(50);
        });

        bet75Button.addActionListener(e -> {
            placeBet(75);
        });

        bet100Button.addActionListener(e -> {
            placeBet(100);
        });

        dealerValueLabel = new JLabel("Dealer Value: --");
        playerValueLabel = new JLabel("Your Value: --");

        dealerValueLabel.setForeground(Color.WHITE);
        playerValueLabel.setForeground(Color.WHITE);

        /*******
         * This is just a test to make sure images are being read correctly on your
         * machine. Please replace
         * once you have confirmed that the card shows up properly. The code below
         * should allow you to play the solitare
         * game once it's fully created.
         */
        // Card card = new Card(2, Card.Suit.Diamonds);
        // System.out.println(card);
        // this.add(card);

        // Testing if the method below works for step 2
        // Stack<Card> deckCards = new Stack<Card>();
        //
        // Card deckCard1 = new Card(2, Card.Suit.Diamonds);
        // deckCard1.hide();
        // deckCards.push(deckCard1);
        //
        // Card deckCard2 = new Card(5, Card.Suit.Hearts);
        // deckCard2.hide();
        // deckCards.push(deckCard2);
        //
        // Card deckCard3 = new Card(13, Card.Suit.Spades);
        // deckCard3.hide();
        // deckCards.push(deckCard3);
        //
        // Stack<Card> discardedCards = new Stack<Card>();
        // discardedCards.push(new Card(4, Card.Suit.Hearts));
        // discardedCards.push(new Card(8, Card.Suit.Clubs));
        //
        // Stack<Card> dealerCards = new Stack<Card>();
        // dealerCards.push(new Card(13, Card.Suit.Spades));
        //
        // Card dealerHiddenCard = new Card(7, Card.Suit.Hearts);
        // dealerHiddenCard.hide();
        // dealerCards.push(dealerHiddenCard);
        //
        // Stack<Card> playerCards = new Stack<Card>();
        // playerCards.push(new Card(1, Card.Suit.Diamonds));
        // playerCards.push(new Card(9, Card.Suit.Clubs));
        //
        // deckArea.add(drawPile(deckCards));
        // discardArea.add(drawPile(discardedCards));
        // dealerArea.add(drawPile(dealerCards));
        // playerArea.add(drawPile(playerCards));

        this.setVisible(true);
    }

    // Precondition: stackIn is not null and stack had valid Card objects
    // Postcondition: a JLayeredPane is returned containing the cards from the stack
    public JLayeredPane drawPile(Stack<Card> stackIn) {
        if (stackIn == null) {
            stackIn = new Stack<Card>();
        }
        Object cards[] = stackIn.toArray();

        int cardWidth = 70;
        int cardHeight = 102;
        int overlap = 25;

        int pileWidth = cardWidth;

        if (cards.length > 1) {
            pileWidth = cardWidth + overlap * (cards.length - 1);
        }

        JLayeredPane pile = new JLayeredPane();
        pile.setPreferredSize(new Dimension(pileWidth, cardHeight));
        pile.setSize(pileWidth, cardHeight);

        for (int i = cards.length - 1; i >= 0; i--) {
            Card card = (Card) cards[i];

            card.setPreferredSize(new Dimension(cardWidth, cardHeight));
            card.setMinimumSize(new Dimension(cardWidth, cardHeight));
            card.setMaximumSize(new Dimension(cardWidth, cardHeight));

            card.setBounds(i * overlap, 0, cardWidth, cardHeight);

            pile.add(card, Integer.valueOf(i));
        }

        return pile;
    }

    // Precondition: The game areas and value labels have been created.
    // Postcondition: The dealer's and player's cards and hand values are displayed.
    public void updateDisplay() {
        dealerArea.removeAll();
        playerArea.removeAll();

        dealerArea.add(drawPile(game.dealerHand));

        if (game.roundOver == false) {
            dealerValueLabel.setText("Dealer Value: ?");
        } else if (game.dealerHand != null) {
            dealerValueLabel.setText("Dealer Value: " + game.calculateHandValue(game.dealerHand));
        }

        dealerArea.add(dealerValueLabel);

        playerArea.add(drawPile(game.playerHand));

        if (game.playerHand != null) {
            playerValueLabel.setText("Your Value: " + game.calculateHandValue(game.playerHand));
        }

        playerArea.add(playerValueLabel);

        revalidate();
        repaint();
    }

    // Precondition: The player has selected a betting amount.
    // Postcondition: If the bet is valid, a new round starts and the display is updated.
    public void placeBet(int amount) {
        if (canBet == true && game.placeBet(amount)) {

            canBet = false;

            game.startNewRound();

            moneyLabel.setText("Chips: " + game.playerMoney);
            betLabel.setText("Current Bet: " + game.currentBet);
            resultLabel.setText("Your turn!");

            updateDisplay();

        } else {
            resultLabel.setText("Invalid bet!");
        }
    }

    // Precondition: The current round has ended.
    // Postcondition: The dealer's card is revealed, the winner is paid, and the game labels are updated.
    public void finishRound() {
        String winner = game.determineWinner();

        // Reveal the dealer's hidden card
        game.dealerHand.get(1).show();

        game.payWinner();

        String status = game.checkGameStatus();

        if (status.equals("")) {
            resultLabel.setText(winner);
            canBet = true;
        } else {
            resultLabel.setText(status);
            canBet = false;
        }

        moneyLabel.setText("Chips: " + game.playerMoney);
        betLabel.setText("Current Bet: 0");
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }
}
