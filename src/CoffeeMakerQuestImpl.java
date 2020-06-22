import java.util.*;

enum Item {
	NONE, COFFEE, CREAM, SUGAR
}

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	private Player player;
	private ArrayList<Room> rooms;
	private boolean gameOver;
	private int currentRoom;

	CoffeeMakerQuestImpl() {
		this.player = null;
		this.rooms = new ArrayList<Room>();
		this.gameOver = false;
		this.currentRoom = -1;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		// TODO
		return this.gameOver;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		this.player = p;
	}

	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		if (room == null || !rooms.isEmpty()) {
			return false;
		}

		rooms.add(room);
		return true;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the northern-most room
	 * @param northDoor string to label the south door of room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {

		if (room == null || northDoor == null || southDoor == null) {
			return false;
		}

		if (rooms.isEmpty()) {
			return false;
		}

		for (Room preExistingRoom : rooms) {
			if (preExistingRoom.getFurnishing().equals(room.getFurnishing())
					|| preExistingRoom.getAdjective().equals(room.getAdjective()))
				return false;
		}

		rooms.get(rooms.size() - 1).setNorthDoor(northDoor); // Setting northernmost room in list to northDoor
		room.setSouthDoor(southDoor);
		rooms.add(room);

		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */
	public Room getCurrentRoom() {
		if (currentRoom == -1)
			return null;

		return rooms.get(currentRoom);
	}

	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		if (room == null || !rooms.contains(room))
			return false;

		currentRoom = rooms.indexOf(room);
		return true;
	}

	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}

	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make sure you
	 * use Player.getInventoryString() whenever you need to display the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {

		cmd = cmd.toLowerCase();
		String responseString = "";
		switch (cmd) {
			case "n":
				if (rooms.size() > currentRoom + 1) {
					currentRoom++;
				} else {
					responseString = "A door in that direction does not exist.\n";
				}
				return responseString;
			case "s":
				if (currentRoom > 0) { // Ensuring a room actually exists
					currentRoom--;
				} else {
					responseString = "A door in that direction does not exist.\n";
				}
				return responseString;
			case "l":
				if (getCurrentRoom().getItem() != Item.NONE) {
					responseString = "There might be something here...\nYou found some ";
					switch (getCurrentRoom().getItem()) {
						case CREAM:
							responseString = responseString + "creamy cream!\n";
							player.addItem(Item.CREAM);
							break;
						case SUGAR:
							responseString = responseString + "sweet sugar!\n";
							player.addItem(Item.SUGAR);
							break;
						case COFFEE:
							responseString = responseString + "caffeinated coffee!\n";
							player.addItem(Item.COFFEE);
							break;
						case NONE:
							break;
					}

				} else {
					responseString = "You don't see anything out of the ordinary.\n";
				}
				return responseString;

			case "i":
				responseString = this.player.getInventoryString();
				return responseString;
			case "h":
			responseString = "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";
				return responseString;
			case "d":
				this.gameOver = true;
				responseString = player.getInventoryString() + "\n" + getDrinkString(player);
				return responseString;
			default:
				responseString = "What?";
				return responseString;
		}
		// TODO
	}

	private String getDrinkString(Player p){ //Private method
		if (p.checkCoffee() && p.checkCream() && p.checkSugar()){
			return "You drink the beverage and are ready to study!\nYou win!\n";
		}else if(p.checkCoffee() && p.checkCream()){
			return "Without sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
		}else if (p.checkSugar() && p.checkCream()){
			return "You drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
		}else if (p.checkCoffee() && p.checkSugar()){
			return "Without cream, you get an ulcer and cannot study.\nYou lose!\n";
		}else if (p.checkCoffee()){
			return "Without cream, you get an ulcer and cannot study.\nYou lose!\n";
		}else if (p.checkCream()){
			return "You drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
		}else if (p.checkSugar()){
			return "You eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";
		}else{
			return "You drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
		}
	}

}
