package vooga.user.view.gui.middleFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import vooga.user.controller.ILoginController;
import vooga.user.main.ResourceManager;
import vooga.user.model.LoginTemplate;
import vooga.user.view.actions.AbstractLoginAction;
import vooga.user.view.actions.AssignImageButton;
import vooga.user.view.actions.EditButton;
import vooga.user.view.actions.PasswordLogin;
import vooga.user.view.actions.RegisterButton;
import vooga.user.view.actions.SubmitButton;
import vooga.user.view.actions.ViewInputButton;

/**
 * @author Conrad Haynes
 * The generic field Panel class displays a specific "fill-in" panel for a Login sequence based on the appropriate calls
 * to builder methods.
 *
 */
public class FieldPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private List<JTextField> inputFields = new ArrayList<JTextField>();
	private List<String> promptText = new ArrayList<String>();
	private String[] inputText; 
	private static ISectionAddable mySectionType,currentSectionType;
	private ResourceManager guiResource = new ResourceManager("vooga.user.resources.GUIResource");
	
	/**
	 * This is the constructor for a Field Panel JPanel that is constructed section by section
	 */
	public FieldPanel(LoginTemplate[] log, ILoginController controller) {
		this.setLayout(new MigLayout());
		String[] buttonNames = guiResource.getStringArray("ButtonReferences");
		
		for (LoginTemplate login : log) {
			currentSectionType = login.getSectionType();
			mySectionType = login.getSectionType();
			addSection(login.getHeader(), login.getPrompts(), this);
			if (login.getImageURL() != null) {
				addImage(login.getImageURL());
			}
			AbstractLoginAction[] buttons = {
					new PasswordLogin(controller, this),
					new SubmitButton(controller, this),
					new RegisterButton(controller, this), 
					new ViewInputButton(controller,this),
					new EditButton(controller,this),
					new AssignImageButton(controller, this)
					};

			// accesses a specific button class action based on it's index in the array
			for (Integer i : login.getButtonListener()) {
				if (i >= 0) {
					addButton(buttons[i], buttonNames[i]);
				}
			}
			this.add(new JLabel(""), "wrap para, gap para");
		}
	}
	
	private void addSection(String header, String[] prompts,
			FieldPanel fieldPanel) {
		addSeparator(this, header);
		for(String r : prompts){
			fieldPanel.getPrompts().add(r);
		}	
		for (int i = 0; i < prompts.length; i++) {
			currentSectionType = mySectionType;	
			if(prompts[i].equals("Password") || prompts[i].equals("Passwordconfirm")){
				currentSectionType = new PasswordInputSection();}
			
				currentSectionType.addSection(header, prompts[i], this);
		}
	}
	
	
	public void paintComponent(Graphics g) {
		 String[] images = guiResource.getStringArray("LoginImageArray");
		 Random rand = new Random();
		 Image img = new ImageIcon("src/vooga/user/resources/" + 
				 guiResource.getString(images[rand.nextInt(images.length)])).getImage();
		    g.drawImage(img, 0, 0, 640,480, null);
		  }
	
	/**
	 * This method simply returns the promptText as an Arraylist
	 */
	public List<String> getPrompts(){
		return promptText;
	}
	
	/**
	 * This method simply returns the inputField as an Arraylist
	 */
	public List<JTextField> getInputs(){
		return inputFields;
	}
	
	/**
	 * This method adds a button to the fill-in section to advance us to the appropriate window
	 */
	public void addButton(ActionListener listener, String name) {
		JButton button = new JButton(name);
		this.add(button, "gap para"); //wrap para, 
		button.addActionListener(listener);
	}

	 /**
	  * This method adds images to a specific section
	  */
	public void addImage(String imagePath){
			JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
			imageLabel.setHorizontalAlignment(JLabel.CENTER);
			this.add(imageLabel);
		}
	 
	
	/**
	 * This method is utilized by the add Section method to create different sections to the game
	 */
	 private void addSeparator(JPanel panel, String text)
	   {
	      panel.add(new JLabel(text), "gapbottom 1, span, split 2, aligny center");
	      panel.add(new JSeparator(), "gapleft rel, growx");
	   }
	
	/**
	* This method retrieves the results of all the input fields as a String[]
	*/
	public String[] getInputFields(){
		inputText = new String[inputFields.size()];
		for(int j = 0; j < inputFields.size(); j++){
			inputText[j] = inputFields.get(j).getText();
		}
		return inputText;
		}

	/**
	* This method retrieves the appropriate prompt inputs from the log-in sequence as a String[]
	*/
	public String[] getPromptText(){
		String[] text = new String[promptText.size()];
		for(int i = 0; i < promptText.size(); i++){
			text[i] = promptText.get(i);
			}
			return text;
		}
	
	public void clear(){
		this.clear();
		this.removeAll();
	}
}
