package vooga.leveleditor.gui;


import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;


public class SpriteProperties extends JTable{
	public SpriteProperties(){
		super(setData(),setColumnNames());
		
	}
	
	private static Object[][] setData(){
		Object[][] data = {
			    {"X Velocity", "",},
			    {"Y Velocity", "",},
			    {"Sprite Group", "",}};
		return data;
	}
	private static String[] setColumnNames(){
		String[] columnNames = {"Sprite Properties", "Sprite Values",};
		return columnNames;
	}

}