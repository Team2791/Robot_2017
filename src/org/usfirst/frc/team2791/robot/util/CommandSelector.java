package org.usfirst.frc.team2791.robot.util;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Allows user to list a group of Commands and then select from them based on a key </br>
 * Also has team color choosing capabilities</br>
 * It's basically just an ArrayList for Commands, and some methods to help access them
 */
public class CommandSelector {
	
	/**
	 * The key for the Command that is currently being selected
	 * The key can be changed/incremented to change which 
	 * command you would like to select
	 */
	private int selectedKey = 0;
	
	/**
	 * This Command Selector also has a team color.
	 * This is especially helpful for commands that changed 
	 * depending on which side of the field you are playing on
	 * (i.e., side gear autos)
	 */
	private String teamColor = "RED";
	
	/**
	 * An array list that has all of the commands 
	 * that you would like to select from in a list
	 */
	private ArrayList<Command> cList = new ArrayList<Command>();
	
		/**
	 * The name of the Command type you are selecting.
	 */
	private String name = "";
	
	private ArrayList<String> cListNames = new ArrayList<String>();
	


	/**
	 * Allows user to list a group of Commands and then select from them based on a key </br>
	 * Also has team color choosing capabilities</br>
	 * It's basically just an ArrayList for Commands, and some methods to help access them
	 * @param name the name of what you are selecting (ex: 'Auto Mode')
	 */
	public CommandSelector(String name){
		this.name = name;
	}
	
	/**
	 * 	 * Adds a Command to the end of the list
	 * <strong><i>Make sure the Command's name has been added.</i></strong>
	 * @param command a command to add to the end of the list
	 */
	public void addCommand(Command command) {
		cList.add(command);
	}

	/**
	 * Adds a Command to the list at a certain point and shifts all commands after down by one.
	 * <strong><i>Make sure the added Command's name has been added.</i></strong>
	 * @param command a command to add to the end of the list
	 * @param index the desired index that the command should be added to
	 * 
	 */
	public void addCommand(Command command, int index){
		cList.add(index, command);
	}

	public void addName(String str){
		cListNames.add(str);
	}
	
	public void addName(String str, int index){
		cListNames.add(index, str);
	}
	
	//***Setters***
	
	/**
	 * Increase the Selected Key by 1
	 * or reset it to 0 to stay in the ArrayList bounds
	 */
	public void incrementKey(){
		selectedKey = ((selectedKey + 1) % cListNames.size());
	}

	/**
	 * Decrease the Selected Key by 1
	 * or reset it to max value to stay in the ArrayList bounds
	 */
	public void decrementKey(){
		selectedKey = ((selectedKey - 1) + cListNames.size()) % cListNames.size();
	}

	/**
	 * @param key the desired int key, which corresponds to a 
	 * desired Command in the array list
	 * What ever the key is set to when {@link getSelected()} is called
	 */
	public void setKey(int key){
		this.selectedKey = key;
	}

	/**
	 * Sets the team color to Red
	 */
	public void setColorToRed(){
		this.teamColor = "RED";
	}

	/**
	 * Sets the team color to Blue
	 */
	public void setColorToBlue(){
		this.teamColor = "BLUE";
	}

	/**
	 * Use the specific color setters  [ setColorToRed() or setColorToBlue() ] if possible 
	 * @param color the desired team color for the command to operate on, should be "RED" or "BLUE"
	 */
	public void setColor(String color){
		this.teamColor = color;
	}

	//***Getters***
	
	/**
	 * @return the selected team color
	 */
	public String getColor(){
		return this.teamColor;
	}

	/**
	 * @return the int key that corresponds to the position of the desired Command in the ArrayList
	 */
	public int getKey(){
		return this.selectedKey;
	}
	
	/**
	 * @return the name of the selector
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return the Command that has been selected based on what the int key is currently set to
	 */
	public Command getSelected() {
		return cList.get(selectedKey);
	}
	
	/**
	 * @return the name of the Command that has been selected based on what the int key is currently set to.
	 * </br><i>This uses an array because the ArrayList can only be filled once we know what color the team is.
	 *  But SmartDashboard needs to know what the selected command is before that is finalized</i>
	 */
	public String getSelectedName(){
		String name = "";
		try{
			name = cListNames.get(selectedKey);
		}catch(ArrayIndexOutOfBoundsException e){
			name = this.name +" Selector Name Array is not Up To Date"; 
		}
		return name;
	}

	/**
	 * @return the ArrayList full of all added Commands
	 */
	public ArrayList<Command> getCommandList(){
		return this.cList;
	}

	
	/**
	 * Sends the Selected Command, Command Key, and Color to Smart Dashboard
	 */
	public void debug(){
		String commandName = "";
		try{
			commandName = getSelectedName();
		}
		catch(NullPointerException e){
			commandName = "Selected Command Name Unavailable";
		}
		
		SmartDashboard.putString("Selected " + name + " Command", commandName);
		SmartDashboard.putString("Selected Team Color", getColor());
		SmartDashboard.putNumber("Selected " + name + " KEY", getKey());

	}

}