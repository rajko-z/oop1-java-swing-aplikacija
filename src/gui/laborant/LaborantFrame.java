package gui.laborant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import entity.korisnici.Laborant;
import gui.login.MainFrame;
import net.miginfocom.swing.MigLayout;
import res.ResourceLoader;

public class LaborantFrame extends JFrame{

	private static final long serialVersionUID = -5316167247182884148L;
	protected Laborant laborant;
	
	protected JButton btnObradaNalaza = new JButton("Obrada nalaza");
	protected JButton btnObradaRezultata = new JButton("Obrada rezultata");
	protected JButton btnOdjava = new JButton("Odjava");
	
	
	public LaborantFrame(Laborant laborant) {
		this.laborant = laborant;
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setTitle("Laborant: " + laborant.getIme() + " " + laborant.getPrezime());
		setPreferredSize(new Dimension(950,600));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
		
	}
	
	private void initGUI() {
		setButtons();
		initZapad();
		initCentar();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initCentar() {
		JPanel panelCentar = new JPanel(new MigLayout());
		panelCentar.setBackground(Color.white);
		
		JLabel userIconLabel = kreirajLabeluSaSlikom("profile.png", 130, 130);
		
		panelCentar.add(userIconLabel, "span, center, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		panelCentar.add(new JLabel("       Ime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.laborant.getIme()), "wrap");
		panelCentar.add(new JLabel("       Prezime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.laborant.getPrezime()), "wrap");
		panelCentar.add(new JLabel("       Korisničko ime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.laborant.getUsername()), "wrap");
		panelCentar.add(new JLabel("       Stručna sprema:"), "split 2,sg a");
		panelCentar.add(new JLabel(this.laborant.getSprema().getOpis()), "wrap");
		panelCentar.add(new JLabel("       Specijalizacije:"), "split 2,sg a");
		panelCentar.add(new JScrollPane(new JList(this.laborant.getGrupeAnaliza().toArray())), "wrap");
		
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}

	private void initZapad() {
		JPanel panelZapad = new JPanel(new MigLayout());
		panelZapad.setBackground(new Color(193, 229, 255));
		
		JLabel obradaNalazaImage = kreirajLabeluSaSlikom("flask.png",50,50);
		JLabel obradaRezultataImage = kreirajLabeluSaSlikom("obradaRezultata.png", 50, 50);
		JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);
		
		panelZapad.add(obradaNalazaImage, "split 2, sg a");
		panelZapad.add(btnObradaNalaza, "wrap");
		panelZapad.add(obradaRezultataImage, "split 2, sg a");
		panelZapad.add(btnObradaRezultata, "wrap");
		panelZapad.add(odjavaImage, "split 2, sg a");
		panelZapad.add(btnOdjava, "wrap");
		
		this.getContentPane().add(panelZapad, BorderLayout.WEST);
	}

	private void setButtons() {
		Dimension d = new Dimension(250,30);
		btnObradaNalaza.setPreferredSize(d);
		btnObradaRezultata.setPreferredSize(d);
		btnOdjava.setPreferredSize(d);
	}
	
	
	private JLabel kreirajLabeluSaSlikom(String imagePath, int width, int height) {
		JLabel labelImage = new JLabel();	
		ImageIcon icon = ResourceLoader.getImageIcon(imagePath);
		ImageIcon iconResized = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		labelImage.setIcon(iconResized);
		return labelImage;
	}
	
	
	
	

	private void initAkcije() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
				MainFrame login = new MainFrame();
				login.setVisible(true);
			}
		});
		
		btnObradaNalaza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaborantPretragaNalaza l = new LaborantPretragaNalaza(LaborantFrame.this, true, laborant);
				l.setVisible(true);
			}
		});
		
		btnOdjava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
					MainFrame login = new MainFrame();
					login.setVisible(true);
			}
		});
		
		btnObradaRezultata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaborantObradaRezultata l = new LaborantObradaRezultata(LaborantFrame.this, true);
				l.setVisible(true);
			}
		});
		
	}
	
}
