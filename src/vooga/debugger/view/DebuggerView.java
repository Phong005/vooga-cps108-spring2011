package vooga.debugger.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import vooga.debugger.Debugger;
import vooga.debugger.model.DebuggerModel;
import vooga.debugger.model.GameField;
import vooga.debugger.view.grapher.DebuggerGrapher;
import vooga.debugger.view.grapher.GraphGameField;

/**
 * Class that initializes and manages all view components systems
 * 
 * @author Troy Ferrell
 * @author Austin Benesh
 */
public class DebuggerView extends JFrame 
{		
	public DebuggerToolbar myToolbar;
	
	public GameTreePanel myGameTreePanel;
	public LiveGameFieldsPanel myLiveFieldsPanel;
	
	public DebuggerConsole myHistoryPanel;
	
	public DebuggerGrapher myGrapher;
	
	private Debugger myDebugger;
	private DebuggerModel myModel;
	
	public DebuggerView(Debugger debug, DebuggerModel model)
	{
		myDebugger = debug;
		myModel = model;
		
		myGrapher = new DebuggerGrapher();
		
		addGUIComponents();
		
		this.setSize(new Dimension(600, 600));
		this.setTitle("VOOGA Debugger");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Init & add gui components into layout
	 */
	private void addGUIComponents()
	{
		myToolbar = new DebuggerToolbar(myDebugger);
		this.add(myToolbar, BorderLayout.NORTH);
		

		myGameTreePanel = new GameTreePanel(myDebugger);
		myLiveFieldsPanel = new LiveGameFieldsPanel();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
			myGameTreePanel.getPane(), myLiveFieldsPanel);
		this.add(splitPane, BorderLayout.CENTER);
		
		
		myHistoryPanel = new DebuggerConsole();
		this.add(myHistoryPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Add Game Field to Debugger View
	 * 
	 * @param field - gamefield to add
	 */
	public void addFieldToView(GameField field)
	{
		if(field instanceof ScopeGameField)
			myLiveFieldsPanel.addField((ScopeGameField)field);
		else if(field instanceof GraphGameField)
			myGrapher.addField((GraphGameField)field);
	}
	
	/**
	 * Remove Game Field from Debugger View & redisplay GUI
	 * 
	 * @param field - gamefield to remove
	 */
	public void removeFieldFromView(GameField field)
	{
		if(field instanceof ScopeGameField)
			myLiveFieldsPanel.removeField((ScopeGameField)field);
		else if(field instanceof GraphGameField)
			myGrapher.removeField((GraphGameField)field);
	}
	
}
