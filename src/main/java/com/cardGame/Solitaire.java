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

	int playerMoney = 900;
	int currentBet = 0;

	boolean roundOver = true;

	// the part of your program that's in charge of game rules goes here.

	// Precondition: The deck may be null or may already contain cards.
    // Postcondition: The deck contains 52 shuffled cards.
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

	// Precondition: The deck has been created and may contain cards.
    // Postcondition: The top card is removed and returned, or null is returned if empty.
	public Card drawCard() {
		if (deck == null || deck.isEmpty()) {
			return null;
		}

		return deck.remove();
	}

	// Precondition: The player has placed a valid bet and the round is not active.
    // Postcondition: A new deck and two-card hands are created, with the dealer's second card hidden.
	public void startNewRound() {
		if (currentBet == 0 || roundOver == true) {
			return;
		}
		createDeck();

		playerHand = new Stack<Card>();
		dealerHand = new Stack<Card>();

		playerHand.push(drawCard());
		dealerHand.push(drawCard());

		playerHand.push(drawCard());
		dealerHand.push(drawCard());

		dealerHand.get(1).hide();

		// Test:
		System.out.println("Player: " + playerHand);
		System.out.println("Dealer: " + dealerHand);
		System.out.println("Cards remaining: " + deck.size());

		System.out.println("Player value: " + calculateHandValue(playerHand));
		System.out.println("Dealer value: " + calculateHandValue(dealerHand));

	}

	// Precondition: The hand contains valid Card objects.
    // Postcondition: The total Blackjack value of the hand is returned, counting Aces as 1 or 11 when needed.
	public int calculateHandValue(Stack<Card> hand) {
		int total = 0;
		int aceCount = 0;

		for (Card card : hand) {
			if (card.value == 1) {
				total += 11;
				aceCount++;
			} else if (card.value >= 11) {
				total += 10;
			} else {
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

	// Precondition: A round is active and the player's hand has been created.
    // Postcondition: One card is added to the player's hand; if the value exceeds 21, the round ends.
	public void hit() {
		if (roundOver == true) {
			return;
		}

		Card card = drawCard();

		if (card == null) {
			return;
		}

		playerHand.push(card);

		if (calculateHandValue(playerHand) > 21) {
			System.out.println("Player busts!");
			System.out.println(determineWinner());
			roundOver = true;
		}

	}

	// Precondition: A round is active and the dealer's hand has been created.
    // Postcondition: The dealer's hidden card is revealed, the dealer takes their turn, and the round ends.
	public void stand() {
		if (roundOver == true) {
			return;
		}
		dealerHand.get(1).show();

		DealerTurn();

		System.out.println(determineWinner());

		roundOver = true;
	}

	// Precondition: The dealer's hand has been created and contains valid cards.
    // Postcondition: The dealer draws cards until their hand value is at least 17 or the deck is empty.
	public void DealerTurn() {
		while (calculateHandValue(dealerHand) < 17) {
			Card card = drawCard();

			if (card == null) {
				break;
			}

			dealerHand.push(card);

		}
	}

	// Precondition: Both the player's and dealer's hands have been created.
    // Postcondition: A message declaring the winner or a tie is returned.
	public String determineWinner() {
		int playerValue = calculateHandValue(playerHand);
		int dealerValue = calculateHandValue(dealerHand);

		if (playerValue > 21) {
			return "Dealer wins!";
		} else if (dealerValue > 21) {
			return "Player wins!";
		} else if (playerValue > dealerValue) {
			return "Player wins!";
		} else if (dealerValue > playerValue) {
			return "Dealer wins!";
		} else {
			return "It's a tie!";
		}
	}

	 // Precondition: The round is over and the player has enough money for the requested bet.
    // Postcondition: If the bet is valid, the bet is stored, removed from the player's money, and the round becomes active.
	public boolean placeBet(int bet) {
		if (roundOver == false || currentBet != 0) {
			return false;
		}

		if (bet < 25 || bet > 100) {
			return false;
		}

		if (bet > playerMoney) {
			return false;
		}

		currentBet = bet;
		playerMoney -= bet;
		roundOver = false;

		return true;
	}

	// Precondition: A round has ended and a valid bet is still stored.
    // Postcondition: The player's money is updated according to the result, and the current bet is reset to 0.
	public void payWinner() {
		if (currentBet == 0 || roundOver == false) {
			return;
		}

		String winner = determineWinner();

		if (winner.equals("Player wins!")) {
			playerMoney += currentBet * 2;
		} else if (winner.equals("It's a tie!")) {
			playerMoney += currentBet;
		}

		currentBet = 0;
	}

	// Precondition: The player's current money has been updated after a round.
    // Postcondition: A win message, game-over message, or empty string is returned depending on the player's money.
	public String checkGameStatus() {
		if (playerMoney >= 1000) {
			return "You won the game!";
		} else if (playerMoney <= 0) {
			return "Game over! You ran out of chips.";
		}

		return "";
	}

	// public void testDeck() {

	// 	createDeck();

	// 	System.out.println("Starting deck size: " + deck.size());

	// 	for (int i = 0; i < 5; i++) {

	// 		Card card = drawCard();

	// 		System.out.println("Card drawn: " + card);
	// 	}

	// 	System.out.println("Remaining cards: " + deck.size());
	// }
}
