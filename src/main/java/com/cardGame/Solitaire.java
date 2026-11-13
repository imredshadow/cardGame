package com.cardGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solitaire {
	ArrayList<Stack<Card>> columns;
	Queue<Card> deck;

	Stack<Card> playerHand;
	Stack<Card> dealerHand;
	
	int playerMoney = 500;
	int currentBet = 0;


	// the part of your program that's in charge of game rules goes here.
	public void createDeck() {
		if (deck == null) {
			deck = new LinkedList();
		}
		deck.clear();
		ArrayList<Card> temp = new ArrayList();
		for (Card.Suit suit : Card.Suit.values()) {
			for (int value = 1; value <= 13; value++) {
				Card card = new Card(value, suit);

				temp.add(card);
			}
		}
		Collections.shuffle(temp);
		deck.addAll(temp);

	}

	public Card drawCard() {
		if (deck == null || deck.isEmpty()) {
			return null;
		}

		return deck.remove();
	}

	public void startNewRound() {
	    createDeck();

	    playerHand = new Stack<Card>();
	    dealerHand = new Stack<Card>();

	    playerHand.push(drawCard());
	    dealerHand.push(drawCard());

	    playerHand.push(drawCard());
	    dealerHand.push(drawCard());

	    dealerHand.get(1).hide();
	   
	    //Test:
	    System.out.println("Player: " + playerHand);
	    System.out.println("Dealer: " + dealerHand);
	    System.out.println("Cards remaining: " + deck.size());
	    
	    System.out.println("Player value: " + calculateHandValue(playerHand));
	    System.out.println("Dealer value: " + calculateHandValue(dealerHand));

	}
	
	public int calculateHandValue(Stack<Card> hand) {
	    int total = 0;
	    int aceCount = 0;

	    for (Card card : hand) {
	        if (card.value == 1) {
	            total += 11;
	            aceCount++;
	        }
	        else if (card.value >= 11) {
	            total += 10;
	        }
	        else {
	            total += card.value;
	        }
	    }

	    // Change an Ace from 11 to 1 if necessary
	    while (total > 21 && aceCount > 0) {
	        total -= 10;
	        aceCount--;
	    }
	    


	    return total;
	}
	
	public void hit() {
		playerHand.push(drawCard());

	    if (calculateHandValue(playerHand) > 21) {
	        System.out.println("Player busts!");
	        System.out.println(determineWinner());
	    }

	}
	
	public void stand() {
	    dealerHand.get(1).show();

	    DealerTurn();
	    
	    System.out.println(determineWinner());
	}
	
	public void DealerTurn() {
	    while (calculateHandValue(dealerHand) < 17) {
	        dealerHand.push(drawCard());
	    }
	}

	public String determineWinner() {
	    int playerValue = calculateHandValue(playerHand);
	    int dealerValue = calculateHandValue(dealerHand);

	    if (playerValue > 21) {
	        return "Dealer wins!";
	    }
	    else if (dealerValue > 21) {
	        return "Player wins!";
	    }
	    else if (playerValue > dealerValue) {
	        return "Player wins!";
	    }
	    else if (dealerValue > playerValue) {
	        return "Dealer wins!";
	    }
	    else {
	        return "It's a tie!";
	    }
	}
	
	public boolean placeBet(int bet) {
	    if (bet < 25 || bet > 100) {
	        return false;
	    }

	    if (bet > playerMoney) {
	        return false;
	    }

	    currentBet = bet;
	    playerMoney -= bet;

	    return true;
	}
	
	public void payWinner() {
	    String winner = determineWinner();

	    if (winner.equals("Player wins!")) {
	        playerMoney += currentBet * 2;
	    }
	    else if (winner.equals("It's a tie!")) {
	        playerMoney += currentBet;
	    }

	    currentBet = 0;
	}
	
	public String checkGameStatus() {
	    if (playerMoney >= 1000) {
	        return "You won the game!";
	    }
	    else if (playerMoney <= 0) {
	        return "Game over! You ran out of chips.";
	    }

	    return "";
	}

	
	public void testDeck() {

		createDeck();

		System.out.println("Starting deck size: " + deck.size());

		for (int i = 0; i < 5; i++) {

			Card card = drawCard();

			System.out.println("Card drawn: " + card);
		}

		System.out.println("Remaining cards: " + deck.size());
	}
}
