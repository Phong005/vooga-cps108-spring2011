package vooga.user.view.actions;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import vooga.user.controller.ILoginController;
import vooga.user.model.database.SQLite;
import vooga.user.model.database.UserDatabase;
import vooga.user.view.gui.middleFrame.FieldPanel;


public class EditButton extends AbstractLoginAction {

	FieldPanel panel;
	SQLite database;
	ILoginController pc;
	public EditButton(ILoginController p, FieldPanel fieldPanel) {
		super(p);
		pc = p;
		panel = fieldPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		pc.updateWithInput();
		
		try {
			database = new UserDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}