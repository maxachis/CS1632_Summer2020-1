import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class CoffeeMakerQuestTest {

	CoffeeMakerQuest cmq;
	Player player;
	Room room1;	// Small room
	Room room2;	// Funny room
	Room room3;	// Refinanced room
	Room room4;	// Dumb room
	Room room5;	// Bloodthirsty room
	Room room6;	// Rough room

	@Before
	public void setup() {
		// 0. Turn on bug injection for Player and Room.
		// Config.setBuggyPlayer(true);
		// Config.setBuggyRoom(true);
		
		// 1. Create the Coffee Maker Quest object and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance();

		// TODO: 2. Create a mock Player and assign to player and call cmq.setPlayer(player). 
		// Player should not have any items (no coffee, no cream, no sugar)
		player = Mockito.mock(Player.class);
		cmq.setPlayer(player);

		// TODO: 3. Create mock Rooms and assign to room1, room2, ..., room6.
		room1 = Mockito.mock(Room.class);
		room2 = Mockito.mock(Room.class);
		room3 = Mockito.mock(Room.class);
		room4 = Mockito.mock(Room.class);
		room5 = Mockito.mock(Room.class);
		room6 = Mockito.mock(Room.class);
		
		//MockItems
		
		
		// Mimic the furnishings / adjectives / items of the rooms in the original Coffee Maker Quest.
		Mockito.when(room1.getFurnishing()).thenReturn("Quaint sofa");
		Mockito.when(room2.getFurnishing()).thenReturn("Sad record player");
		Mockito.when(room3.getFurnishing()).thenReturn("Tight pizza");
		Mockito.when(room4.getFurnishing()).thenReturn("Flat energy drink");
		Mockito.when(room5.getFurnishing()).thenReturn("Beautiful bag of money");
		Mockito.when(room6.getFurnishing()).thenReturn("Perfect air hockey table");
		// Adjectives		
		Mockito.when(room1.getAdjective()).thenReturn("Small");
		Mockito.when(room2.getAdjective()).thenReturn("Funny");
		Mockito.when(room3.getAdjective()).thenReturn("Refinanced");
		Mockito.when(room4.getAdjective()).thenReturn("Dumb");
		Mockito.when(room5.getAdjective()).thenReturn("Bloodthirsty");
		Mockito.when(room6.getAdjective()).thenReturn("Rough");
		//Items
		Mockito.when(room1.getItem()).thenReturn(Item.CREAM);
		Mockito.when(room2.getItem()).thenReturn(Item.NONE);
		Mockito.when(room3.getItem()).thenReturn(Item.COFFEE);
		Mockito.when(room4.getItem()).thenReturn(Item.NONE);
		Mockito.when(room5.getItem()).thenReturn(Item.NONE);
		Mockito.when(room6.getItem()).thenReturn(Item.SUGAR);
		
		// TODO: 4. Add the rooms created above to mimic the layout of the original Coffee Maker Quest.
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Magenta", "Massive");
		cmq.addRoomAtNorth(room3, "Beige", "Smart");
		cmq.addRoomAtNorth(room4, "Dead", "Slim");
		cmq.addRoomAtNorth(room5, "Vivacious", "Sandy");
		cmq.addRoomAtNorth(room6, "Purple", "Minimalist");
	}

	@After
	public void tearDown() {
		cmq = null;
	}
	
	/**
	 * Test case for String getInstructionsString().
	 * Preconditions: None.
	 * Execution steps: Call cmq.getInstructionsString().
	 * Postconditions: Return value is " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 */
	@Test
	public void testGetInstructionsString() {
		assertEquals(" INSTRUCTIONS (N,S,L,I,D,H) > ", cmq.getInstructionsString());
	}
	
	/**
	 * Test case for boolean addFirstRoom(Room room).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.addFirstRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testAddFirstRoom() {
		Room myRoom = Mockito.mock(Room.class);
		assertEquals(cmq.addFirstRoom(myRoom), false);
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Fake bed" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is true.
	 *                 room6.setNorthDoor("North") is called.
	 *                 myRoom.setSouthDoor("South") is called.
	 */
	@Test
	public void testAddRoomAtNorthUnique() {
		Room myRoom = Mockito.mock(Room.class);
		Mockito.when(myRoom.getFurnishing()).thenReturn("Fake bed");
		assertTrue(cmq.addRoomAtNorth(myRoom, "North", "South"));
		Mockito.verify(room6).setNorthDoor("North");
		Mockito.verify(myRoom).setSouthDoor("South");
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Flat energy drink" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddRoomAtNorthDuplicate() {
		Room myRoom = Mockito.mock(Room.class);
		Mockito.when(myRoom.getFurnishing()).thenReturn("Flat energy drink");
		assertFalse(cmq.addRoomAtNorth(myRoom, "North", "South"));
		Mockito.verify(myRoom, times(0)).setNorthDoor("North");
		Mockito.verify(myRoom, times(0)).setSouthDoor("South");
	}
	
	/**
	 * Test case for Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room) has not yet been called.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is null.
	 */
	@Test
	public void testGetCurrentRoom() {
		assertNull(cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room room) has not yet been called.
	 * Execution steps: Call cmq.setCurrentRoom(room3).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(room3) is true. 
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testSetCurrentRoom() {
		assertTrue(cmq.setCurrentRoom(room3));
		assertEquals(cmq.getCurrentRoom(), room3);
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: None.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandI() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		assertEquals("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n", cmq.processCommand("I"));
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some creamy cream!\n".
	 *                 player.addItem(Item.CREAM) is called.
	 */
	@Test
	public void testProcessCommandLCream() {
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("l"),"There might be something here...\nYou found some creamy cream!\n");
		Mockito.verify(player).addItem(Item.CREAM);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandN() {
		cmq.setCurrentRoom(room4);
		assertEquals(cmq.processCommand("n"), "");
		assertEquals(cmq.getCurrentRoom(), room5);
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandSNoDoor() {
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("s"),"A door in that direction does not exist.\n");
		assertEquals(cmq.getCurrentRoom(), room1);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLose() {
		Mockito.when(player.checkCoffee()).thenReturn(false);
		Mockito.when(player.checkCream()).thenReturn(false);
		Mockito.when(player.checkSugar()).thenReturn(false);
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		assertEquals("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n",cmq.processCommand("D") );
		assertTrue(cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWin() {
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(true);
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n");
		assertEquals(cmq.processCommand("D"), "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n");
		assertTrue(cmq.isGameOver());
	}
	
	// TODO: Put in more unit tests of your own making to improve coverage!
	
	/**
	 * Test case for String processCommand("d").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWinLower() {
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(true);
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n");
		assertEquals(cmq.processCommand("d"), "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n");
		assertTrue(cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("d").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLoseLower() {
		assertEquals(cmq.processCommand("D"),"YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n" );
		assertTrue(cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("S").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("S").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("S") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandSUpper() {
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("S"),"A door in that direction does not exist.\n");
		assertEquals(cmq.getCurrentRoom(), room1);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandNUpper() {
		cmq.setCurrentRoom(room4);
		assertEquals(cmq.processCommand("N"), "");
		assertEquals(cmq.getCurrentRoom(), room5);		
	}
	
	
	
	@Test
	public void testProcessCommandLUpper() {
		cmq.setCurrentRoom(room3);
		assertEquals(cmq.processCommand("L"),"There might be something here...\nYou found some coffee!\n");
		Mockito.verify(player).addItem(Item.COFFEE);
	}
	
	/**
	 * Test case for String processCommand("i").
	 * Preconditions: None.
	 * Execution steps: Call cmq.processCommand("i").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandILower() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		assertEquals(cmq.processCommand("I"),"YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
	}
	
	//tests if player can go south when door exists to south
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testProcessCommandSDoor() {
		cmq.setCurrentRoom(room4);
		assertEquals(cmq.processCommand("s"), "");
		assertEquals(cmq.getCurrentRoom(), room3);		
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room3) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some coffee!\n".
	 *                 player.addItem(Item.COFFEE) is called.
	 */
	@Test
	public void testProcessCommandLCoffee() {
		cmq.setCurrentRoom(room3);
		assertEquals(cmq.processCommand("l"),"There might be something here...\nYou found some coffee!\n");
		Mockito.verify(this.player).addItem(Item.COFFEE);
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room6) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some sugar!\n".
	 *                 player.addItem(Item.SUGAR) is called.
	 */
	@Test
	public void testProcessCommandLSugar() {
		cmq.setCurrentRoom(room6);
		assertEquals(cmq.processCommand("l"),"There might be something here...\nYou found some sugar!\n");
		Mockito.verify(player).addItem(Item.SUGAR);
	}
	
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room5) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nThere's nothing here :( \n".
	 *                 player.addItem(Item.SUGAR) is not called.
	 *                 player.addItem(Item.CREAM) is not called.
	 *                 player.addItem(Item.COFFEE) is not called.
	 */
	@Test
	public void testProcessCommandLNothing() {
		cmq.setCurrentRoom(room5);
		assertEquals(cmq.processCommand("l"),"There might be something here...\nThere's nothing here :( \n");
		Mockito.verify(player, times(0)).addItem(Item.SUGAR);
		Mockito.verify(player, times(0)).addItem(Item.CREAM);
		Mockito.verify(player, times(0)).addItem(Item.COFFEE);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room6) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "There is no door there.".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandNNoDoor() {
		cmq.setCurrentRoom(room6);
		assertEquals(cmq.processCommand("n"), "A door in that direction does not exist.\n");
		assertEquals(cmq.getCurrentRoom(), room6);
	}
	
	/**
	 * Test case for String processCommand("H").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 * Execution steps: Call cmq.processCommand("H").
	 * Postconditions: Return value of cmq.processCommand("H") is "L: Look for items\n N: Go North\n S: Go South\n D: Drink coffee\n I: Check inventory\n".
	 */
	@Test
	public void testProcessCommandHUpper() {
		assertEquals(cmq.processCommand("H"), "L: Look for items\n N: Go North\n S: Go South\n D: Drink coffee\n I: Check inventory\n");
	}
	
	/**
	 * Test case for String processCommand("h").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 * Execution steps: Call cmq.processCommand("h").
	 * Postconditions: Return value of cmq.processCommand("h") is "L: Look for items\n N: Go North\n S: Go South\n D: Drink coffee\n I: Check inventory\n".
	 */
	@Test
	public void testProcessCommandHLower() {
		assertEquals(cmq.processCommand("h"), "L: Look for items\n N: Go North\n S: Go South\n D: Drink coffee\n I: Check inventory\n");
	}
	
	/**
	 * Test case for String processCommand("g").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 * Execution steps: Call cmq.processCommand("g").
	 * Postconditions: Return value of cmq.processCommand("g") is "What?".
	 */
	@Test
	public void testProcessCommandInvalidLetter() {
		assertEquals(cmq.processCommand("g"), "What?");
	}
	
	/**
	 * Test case for String processCommand("5").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 * Execution steps: Call cmq.processCommand("5").
	 * Postconditions: Return value of cmq.processCommand("5") is "What?".
	 */
	@Test
	public void testProcessCommandInvalidNum() {
		assertEquals(cmq.processCommand("5"), "What?");		
	}
	
	/**
	 * Test case for String processCommand(" ").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 * Execution steps: Call cmq.processCommand(" ").
	 * Postconditions: Return value of cmq.processCommand(" ") is "What?".
	 */
	@Test
	public void testProcessCommandInvalidSpace() {
		assertEquals(cmq.processCommand(" "), "What?");				
	}
	
	/**
	 * Test case for String processCommand("!").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 * Execution steps: Call cmq.processCommand("!").
	 * Postconditions: Return value of cmq.processCommand("!") is "What?".
	 */
	@Test
	public void testProcessCommandInvalidSpecialChar() {
		assertEquals(cmq.processCommand("!"), "What?");		
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandIAllItems() {
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(true);
		assertEquals(cmq.processCommand("I"), "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n");
	}


	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * 					Call cmq.processCommand("l").
	 * Postconditions: 1st Return value is "There might be something here...\nYou found some coffee!\n".
	 * 				   2nd Return value is "There might be something here...\nThere's nothing here :( \n".
	 *                 player.addItem(Item.CREAM) is called, only once.
	 */
	@Test
	public void testProcessCommandLCreamRepeated() {
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("l"),"There might be something here...\nYou found some creamy cream!\n");
		assertEquals(cmq.processCommand("l"),"There might be something here...\\nThere's nothing here :( \\n");
		Mockito.verify(player, times(1)).addItem(Item.CREAM);
	}
	
	@Test
	public void testProcessUniqueDoors() {
		
	}
	
	@Test
	public void testProcessUniqueFurnishings() {
		
	}
	
	public void testProcessUniqueAdjectives() {
		
	}
	
	
}
