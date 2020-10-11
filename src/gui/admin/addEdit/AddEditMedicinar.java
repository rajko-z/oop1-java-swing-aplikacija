package gui.admin.addEdit;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import entity.korisnici.MedicinskiTehničar;
import gui.admin.ZaposleniDijalog;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import services.LaborantServis;
import services.MedicinskiTehničarServis;

public class AddEditMedicinar extends JDialog{
	
	private static final long serialVersionUID = -4412686975785284923L;
	
	private MedicinskiTehničar mTehničar;
	private RepositoryFactory rp = RepositoryFactory.getInstance();
	private MedicinskiTehničarServis mTehničarServis = new MedicinskiTehničarServis();
	private JDialog roditelj;
	
	
	protected JButton btnSacuvaj = new JButton("Sačuvaj");
	protected JButton btnIzlaz = new JButton("Izlaz");
	
	protected JTextField tfIme = new JTextField(30);
	protected JTextField tfPrezime = new JTextField(30);
	protected JComboBox comboBoxStrucnaSprema = new JComboBox(rp.getStručnaSpremaRepo().getEntitetiList().toArray());
	
	public AddEditMedicinar(JDialog roditelj, boolean modal, MedicinskiTehničar mTehničar) {
		super(roditelj, modal);
		this.mTehničar = mTehničar;
		this.roditelj = roditelj;
		
		if (mTehničar == null) {
			setTitle("Dodavanje novog mediciskog tehničara");
		}
		else {
			setTitle("Promena podataka");
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		JPanel centar = new JPanel(new MigLayout());
		
		centar.add(new JLabel("Ime:"), "split 2, sg a");
		centar.add(tfIme, "pushx, growx, wrap");
		centar.add(new JLabel("Prezime:"), "split 2, sg a");
		centar.add(tfPrezime, "pushx, growx, wrap");
		centar.add(new JLabel("Stručna sprema:"), "split 2, sg a");
		centar.add(comboBoxStrucnaSprema, "pushx, growx, wrap");
		centar.add(new JLabel(" "), "sg a, split 3");
		centar.add(btnSacuvaj);
		centar.add(btnIzlaz,"wrap");
		
		this.getContentPane().add(centar);
		
		if (this.mTehničar != null) {
			popuniPoljaPodacima();
		}
		
	}
	
	private void popuniPoljaPodacima() {
		tfIme.setText(mTehničar.getIme());
		tfPrezime.setText(mTehničar.getPrezime());
		comboBoxStrucnaSprema.setSelectedItem(mTehničar.getSprema());
	}

	private void initAkcije() {
		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (unosValidan()) {
					int answer = JOptionPane.showConfirmDialog(null, generisiPoruku(),
							  "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						if (mTehničar == null) {
							mTehničarServis.addMTehničar(tfIme.getText().trim(), tfPrezime.getText().trim(), (StručnaSprema)comboBoxStrucnaSprema.getSelectedItem());
						}
						else {
							mTehničarServis.edit(mTehničar, tfIme.getText().trim(), tfPrezime.getText().trim(), (StručnaSprema)comboBoxStrucnaSprema.getSelectedItem());
						}
						JOptionPane.showMessageDialog(null, "Podaci su uspešno sačuvani", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						AddEditMedicinar.this.dispose();
						((ZaposleniDijalog) roditelj).osveziTabelu();
					}
				}
				
			}

			private String generisiPoruku() {
				StringBuilder sb = new StringBuilder("Da li ste sigurni da želite da sačuvate sledeće podatke:\n");
				sb.append("Ime: " + tfIme.getText().trim() + '\n');
				sb.append("Prezime: " + tfPrezime.getText().trim() + '\n');
				sb.append("Stručna sprema: " + comboBoxStrucnaSprema.getSelectedItem().toString() + '\n');
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
				return true;
			}
			
		});
		
		btnIzlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditMedicinar.this.dispose();
				AddEditMedicinar.this.setVisible(false);
			}
		});
		
	}
	

}
