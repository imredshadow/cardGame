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
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private Solitaire game;
    public static String IMG_PATH = System.getProperty("user.dir") + "/src/main/java/com/cardGame/resources/";

    public GUI(Solitaire game) {
        this.game = game;
        // Create and set up the window.
        setTitle("Solitaire");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this supplies the background
        try {
            Image blackImg = ImageIO.read(new File(IMG_PATH + "background.jpg"));

            setContentPane(new ImagePanel(blackImg));

        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel gameArea = new JPanel(new GridLayout(3, 1, 10, 10));
        gameArea.setOpaque(false);
        gameArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));

        JPanel topArea = new JPanel(new GridLayout(1, 2, 10, 10));
        topArea.setOpaque(false);

        JPanel deckArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deckArea.setOpaque(false);
        deckArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Deck"));

        JPanel discardArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        discardArea.setOpaque(false);
        discardArea.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), "Discard Pile"));

        topArea.add(deckArea);
        topArea.add(discardArea);

        JPanel dealerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dealerArea.setOpaque(false);
        dealerArea.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), "Dealer's Cards"));

        JPanel playerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerArea.setOpaque(false);
        playerArea.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW), "Your Cards"));

        gameArea.add(topArea);
        gameArea.add(dealerArea);
        gameArea.add(playerArea);

        this.setLayout(new BorderLayout());
        this.add(gameArea, BorderLayout.CENTER);

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
        



        //Testing if the method below works for step 2
        Stack<Card> deckCards = new Stack<Card>();

        Card deckCard1 = new Card(2, Card.Suit.Diamonds);
        deckCard1.hide();
        deckCards.push(deckCard1);

        Card deckCard2 = new Card(5, Card.Suit.Hearts);
        deckCard2.hide();
        deckCards.push(deckCard2);

        Card deckCard3 = new Card(13, Card.Suit.Spades);
        deckCard3.hide();
        deckCards.push(deckCard3);

        Stack<Card> discardedCards = new Stack<Card>();
        discardedCards.push(new Card(4, Card.Suit.Hearts));
        discardedCards.push(new Card(8, Card.Suit.Clubs));

        Stack<Card> dealerCards = new Stack<Card>();
        dealerCards.push(new Card(13, Card.Suit.Spades));

        Card dealerHiddenCard = new Card(7, Card.Suit.Hearts);
        dealerHiddenCard.hide();
        dealerCards.push(dealerHiddenCard);

        Stack<Card> playerCards = new Stack<Card>();
        playerCards.push(new Card(1, Card.Suit.Diamonds));
        playerCards.push(new Card(9, Card.Suit.Clubs));

        deckArea.add(drawPile(deckCards));
        discardArea.add(drawPile(discardedCards));
        dealerArea.add(drawPile(dealerCards));
        playerArea.add(drawPile(playerCards));





        this.setVisible(true);
    }

    //Precondition: stackIn is not null and stack had valid Card objects
    //Postcondition: a JLayeredPane is returned containing the cards from the stack
    public JLayeredPane drawPile(Stack<Card> stackIn) {
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
