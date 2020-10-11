package gui.admin.addEdit;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.jar.JarFile;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.GrupaAnaliza;
import entity.StručnaSprema;
import entity.korisnici.Laborant;
import gui.admin.ZaposleniDijalog;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import services.LaborantServis;

public class AddEditLaborant extends JDialog {

	private static final long serialVersionUID = 404184168900072506L;
	private Laborant laborant;
	private RepositoryFactory rp = RepositoryFactory.getInstance();
	private LaborantServis laborantServis = new LaborantServis();
	private JDialog roditelj;
	
	
	protected JButton btnSacuvaj = new JButton("Sačuvaj");
	protected JButton btnIzlaz = new JButton("Izlaz");
	
	protected JTextField tfIme = new JTextField(30);
	protected JTextField tfPrezime = new JTextField(30);
	protected JComboBox comboBoxStrucnaSprema = new JComboBox(rp.getStručnaSpremaRepo().getEntitetiList().toArray());
	protected JList listaGrupaAnaliza = new JList(rp.getGrupeAnalizaRepo().getEntitetiList().toArray());
	protected DefaultListModel specijalizacijeListModel = new DefaultListModel<GrupaAnaliza>();
	protected JList listaSpecijalizacija = new JList(specijalizacijeListModel);
	
	
	
	public AddEditLaborant(JDialog roditelj, boolean modal, Laborant laborant) {
		super(roditelj, modal);
		this.laborant = laborant;
		this.roditelj = roditelj;
		
		if (laborant == null) {
			setTitle("Dodavanje novog laboranta");
		}
		else {
			setTitle("Promena podataka");
			for (GrupaAnaliza gp: laborant.getGrupeAnaliza()) {
				specijalizacijeListModel.addElement(gp);
			}
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}
	
	

	private void initGUI() {
		JPanel centar = new JPanel(new MigLayout());
		
		JScrollPane srcOdabraneSpec = new JScrollPane(listaSpecijalizacija);
		JScrollPane srcPonudjeneSpec = new JScrollPane(listaGrupaAnaliza);
		srcOdabraneSpec.setPreferredSize(new Dimension(180,150));
		srcPonudjeneSpec.setPreferredSize(new Dimension(180,160));
		
		
		centar.add(new JLabel("Ime:"), "split 2, sg a");
		centar.add(tfIme, "pushx, growx, wrap");
		centar.add(new JLabel("Prezime:"), "split 2, sg a");
		centar.add(tfPrezime, "pushx, growx, wrap");
		centar.add(new JLabel("Stručna sprema:"), "split 2, sg a");
		centar.add(comboBoxStrucnaSprema, "pushx, growx, wrap");
		centar.add(new JLabel("<html>Specijalizacije:<br>možete ih brisati<br>klikom na njih"), "top, split 5, sg a");
		centar.add(srcOdabraneSpec);
		centar.add(new JLabel(" "), "sg a");
		centar.add(new JLabel("<html>Izaberite specijalizaciju<br>koju želite da dodate"), "top");
		centar.add(srcPonudjeneSpec, "wrap");
		centar.add(new JLabel(" "), "sg a, split 3");
		centar.add(btnSacuvaj);
		centar.add(btnIzlaz,"wrap");
		
		this.getContentPane().add(centar);
		
		if (this.laborant != null) {
			popuniPoljaPodacima();
		}
		
	}
	
	private void popuniPoljaPodacima() {
		tfIme.setText(laborant.getIme());
		tfPrezime.setText(laborant.getPrezime());
		comboBoxStrucnaSprema.setSelectedItem(laborant.getSprema());
	}

	private void initAkcije() {
		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (unosValidan()) {
					int answer = JOptionPane.showConfirmDialog(null, generisiPoruku(),
							  "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						ArrayList<GrupaAnaliza> specijalizacije = getIzabraneSpecijalizacije();
						
						if (laborant == null) {
							laborantServis.addLaborant(tfIme.getText().trim(), tfPrezime.getText().trim(), (StručnaSprema)comboBoxStrucnaSprema.getSelectedItem(), specijalizacije);
						}
						else {
							laborantServis.edit(laborant, tfIme.getText().trim(), tfPrezime.getText().trim(), (StručnaSprema)comboBoxStrucnaSprema.getSelectedItem(), specijalizacije);
						}
						JOptionPane.showMessageDialog(null, "Podaci su uspešno sačuvani", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						AddEditLaborant.this.dispose();
						((ZaposleniDijalog) roditelj).osveziTabelu();
					}
				}
				
			}

			private String generisiPoruku() {
				ArrayList<GrupaAnaliza> specijalizacije = getIzabraneSpecijalizacije();
				StringBuilder sb = new StringBuilder("Da li ste sigurni da želite da sačuvate sledeće podatke:\n");
				sb.append("Ime: " + tfIme.getText().trim() + '\n');
				sb.append("Prezime: " + tfPrezime.getText().trim() + '\n');
				sb.append("Stručna sprema: " + comboBoxStrucnaSprema.getSelectedItem().toString() + '\n');
				sb.append("Specijalizacije:\n");
				for (GrupaAnaliza ga: specijalizacije) {
					sb.append(ga.getNaziv() + "\n");
				}
				return sb.toString();
			}

			private boolean unosValidan() {
				if (tfIme.getText().trim().equals("") | tfPrezime.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli ime ili prezime", "Greska",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else if(comboBoxStrucnaSprema.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Niste uneli strucnu spremu", "Greska",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else if(specijalizacijeListModel.size() == 0) {
					JOptionPane.showMessageDialog(null, "Niste oznacili specijalizacije", "Greska",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				return true;
			}
			
		});
		
		btnIzlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditLaborant.this.dispose();
				AddEditLaborant.this.setVisible(false);
			}
		});
		
		
		listaGrupaAnaliza.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listaGrupaAnaliza.getSelectedIndex() >= 0) {
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da dodate ovu specijalizaciju","Pitanje",JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						if (specijalizacijeListModel.contains(listaGrupaAnaliza.getSelectedValue())) {
							JOptionPane.showMessageDialog(null, "Laborant vec sadrzi zadatu specijalizaciju.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							specijalizacijeListModel.addElement(listaGrupaAnaliza.getSelectedValue());
						}
					}
				}
			}
		});
		
		
		listaSpecijalizacija.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listaSpecijalizacija.getSelectedIndex() >= 0) {
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da uklonite ovu specijalizaciju?",
							  "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						specijalizacijeListModel.removeElement(listaSpecijalizacija.getSelectedValue());
					}
				}
			}
		});
		
		
		
	}
	
	private ArrayList<GrupaAnaliza> getIzabraneSpecijalizacije(){
		ArrayList<GrupaAnaliza> specijalizacije = new ArrayList<GrupaAnaliza>();
		for(int i = 0; i< specijalizacijeListModel.getSize();i++){
            GrupaAnaliza ga = (GrupaAnaliza) specijalizacijeListModel.getElementAt(i);
            specijalizacije.add(ga);
        }
		return specijalizacije;
	}

	
	
}
