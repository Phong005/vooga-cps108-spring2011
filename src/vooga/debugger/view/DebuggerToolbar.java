
package vooga.debugger.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import vooga.debugger.Debugger;
import vooga.debugger.util.MethodAction;



/**
 * The toolbar for Debugger system
 * 
 * @author Troy Ferrell
 */

public class DebuggerToolbar extends JPanel 
{
	private static final String SCANNER_DELIMITTER = "\\s+";

	private Debugger myDebugger;
	
	// Toolbar buttons
	public JButton playButton;
	public JButton stopButton;
	
	public DebuggerToolbar(Debugger debug )
	{
		myDebugger = debug;
		
		initToolbar();
		
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void initToolbar()
	{
		JToolBar toolBar = new JToolBar();
		
		playButton = new JButton(new ImageIcon("src/vooga/debugger/resources/Debugger_PlayButton.png"));
		playButton.addActionListener(new MethodAction(myDebugger, "playGame"));
		playButton.setEnabled(false);
		toolBar.add(playButton );
		
		stopButton = new JButton(new ImageIcon("src/vooga/debugger/resources/Debugger_StopButton.png"));
		stopButton.addActionListener(new MethodAction(myDebugger, "stopGame"));
		toolBar.add(stopButton );
		
		JButton restartButton = new JButton(new ImageIcon("src/vooga/debugger/resources/Debugger_RestartButton.png"));
		restartButton.addActionListener(new MethodAction(myDebugger, "restartGame"));
		toolBar.add(restartButton );
		
		JButton showAllButton = new JButton(new ImageIcon("src/vooga/debugger/resources/Debugger_ShowAllButton.png"));
		showAllButton.addActionListener(new MethodAction(myDebugger,"showAllFields"));
		toolBar.add(showAllButton );
		
		JButton showGrapherButton = new JButton(new ImageIcon("src/vooga/debugger/resources/Debugger_ShowGrapherButton.png"));
		showGrapherButton.addActionListener(new MethodAction(myDebugger,"showGrapher"));
		toolBar.add(showGrapherButton );
		
		this.add(toolBar);
	}
	
	
	/*
	 *  Create toolbar items using resourcefile
	 * - toolbar button actions point to methods in controller classes using MethodAction class
	 * 	 and reflection, resourceFile also has image location info 
	 * 
	 * @param resourceFile - file that has toolbar information
	 */
	/*private void initToolbar(String resourceFile)
	{	
		JToolBar toolbar = new JToolBar();
		
		try
		{
			Scanner reader = new Scanner(new File(resourceFile)).useDelimiter(SCANNER_DELIMITTER);
			
			while(reader.hasNext())
			{
				String buttonImg = reader.next();
				String actionMethod = reader.next();
				
				//String controllerClassName = reader.next();
				//String targetComponentName = reader.next();
				//AbstractController controller = myController.getControllerFor(controllerClassName);
				//JComponent target = myGUI.getSLogoComponent(targetComponentName);
				
				JButton newToolbarButton = new JButton(new ImageIcon(buttonImg));
				newToolbarButton.setFocusable(false);
				
				newToolbarButton.addActionListener(new MethodAction(myDebugger, actionMethod));
				
				toolbar.add(newToolbarButton);
			}
		}catch(FileNotFoundException e)
		{
			// TODO: notify prompt
		}
		//catch(ClassNotFoundException e) {}
		
		this.add(toolbar);
	}*/

	
}
