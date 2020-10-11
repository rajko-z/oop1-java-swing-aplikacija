package main;

import java.awt.Font;

import javax.swing.UIManager;

import gui.login.MainFrame;
import repositories.RepositoryFactory;

public class Aplikacija {
	

	public static void main(String[] args) {
		
		System.out.println("-------------Glavna aplikacija-------------");
		System.out.println("Dobrodošli...");
		
		RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
		repositoryFactory.loadData();
		
		setUIFont (new javax.swing.plaf.FontUIResource(new Font(Font.SANS_SERIF,  Font.LAYOUT_LEFT_TO_RIGHT, 15)));
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		
	}
	private static void setUIFont (javax.swing.plaf.FontUIResource f){
	    @SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    } 

}
