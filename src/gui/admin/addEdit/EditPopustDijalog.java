package gui.admin.addEdit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import gui.admin.PopustiDijalog;
import net.miginfocom.swing.MigLayout;
import services.AnalizeServis;
import services.ValidatorServis;

public class EditPopustDijalog extends JDialog{

	private static final long serialVersionUID = 5275306526124739494L;
	private ValidatorServis validatorServis = new ValidatorServis();
	private AnalizeServis analizeServis = new AnalizeServis();
	private JDialog roditelj;
	private PosebnaAnaliza analiza;
	private GrupaAnaliza grupa;
	
	protected JTextField tfNaziv = new JTextField(20);
	protected JButton sacuvaj = new JButton("Sačuvaj");
	protected JButton izlaz = new JButton("Izlaz");
	protected JTextField tfPopust = new JTextField();
	
	public EditPopustDijalog(JDialog roditelj, boolean modal, PosebnaAnaliza analiza, GrupaAnaliza grupaAnaliza) {
		super(roditelj, modal);
		this.analiza = analiza;
		this.roditelj = roditelj;
		this.grupa = grupaAnaliza;
		setTitle("Promena popusta");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
		initAkcije();
	}


	private void initGUI() {
		JPanel centar = new JPanel(new MigLayout());
		centar.add(new JLabel("Naziv:"), "split 2, sg a");
		centar.add(tfNaziv, "pushx, growx, wrap");
		centar.add(new JLabel("<html>Popust:<br>(0 ukoliko<br>nema popusta)"), "split 2, sg a");
		centar.add(tfPopust, "pushx, growx, wrap");
		centar.add(new JLabel(" "), "span");
		centar.add(new JLabel(" "), "split 3, sg a");
		centar.add(sacuvaj);
		centar.add(izlaz);
		
		this.getContentPane().add(centar, BorderLayout.CENTER);
		popuniPoljaPodacima();
		tfNaziv.setEditable(false);
	}
	
	private void popuniPoljaPodacima() {
		if (analiza != null) {
			tfNaziv.setText(analiza.getNaziv());
			int popust = analizeServis.getPopustZaAnalizu(analiza);
			tfPopust.setText(String.valueOf(popust));
		}else {
			tfNaziv.setText(grupa.getNaziv());
			int popust = analizeServis.getPopustZaGrupu(grupa);
			tfPopust.setText(String.valueOf(popust));
		}
	}
	
	private void initAkcije() {
		izlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditPopustDijalog.this.dispose();
			}
		});
		
		sacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnos()) {
					double popust = Double.parseDouble(tfPopust.getText().trim());
					if (analiza != null) {
						analizeServis.editPopustAnaliza(analiza, popust);
					}else {
						analizeServis.editPopustGrupa(grupa, popust);
					}
					JOptionPane.showMessageDialog(null, "Podaci upešno sačuvani");
					EditPopustDijalog.this.dispose();
					((PopustiDijalog)roditelj).osveziTabelu();
				}
			}

			private boolean proveriUnos() {
				if (tfPopust.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Unesite popust");
					return false;
				}
				if (validatorServis.nijeBrojUOdgovarajucemRasponu(0, 70, tfPopust.getText().trim())) {
					JOptionPane.showMessageDialog(null, "Popust mora biti broj od 0 (ukoliko želite da skinete stavku sa popusta) i 70.");
					return false;
				}
				return true;
			}
		});
	}

}
