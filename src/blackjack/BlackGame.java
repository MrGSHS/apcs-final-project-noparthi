package blackjack;
import java.util.ArrayList;
import holdem.Computer;
import holdem.Display;
import holdem.Player;
import holdem.Round;
import holdem.Table;
public class BlackGame {
	



	public class Game {

		private Display display;
		
		private ArrayList<Player> players = new ArrayList<>();

		
		private Round round;
		private Table table;
		private int dealerIndex = 0;

		public ArrayList<int[]> playerPositions = new ArrayList<>();

		
		public Display getDisplay() {
			return display;
		}

		public Table getTable() {
			return table;
		}

		public Round getRound() {
			return round;
		}



		// Index 0 Is A Player, The Rest Are Computers
		public ArrayList<Player> getPlayers() {
			return players;
		}





		public Game() {
			int pos = 0;
			table = new Table();
			Player dealer = new dealer();
			players.add(dealer);
			round = new Round(this);
			display = new Display(this);
			display.setRoundTitle();
		}



		// Pays Out Money In Pot To Winner
		public void payout() {
			
		}


		// Creates New Round
		public void newRound() {
			for (Player p : players) {
				p.unFold();
				p.resetPointsInvested();
			}
			resetPlayerBetAmount();
			setDealerIndex(dealerIndex += 1);
			table = new Table();
			round = new Round(this);
			takeBlinds();
			takeAnte();
			display.setRoundTitle();
			round.preFlop();
		}
	}

}
