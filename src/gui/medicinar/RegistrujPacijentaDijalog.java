package gui.medicinar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.korisnici.Pacijent;
import gui.PacijentTableFrame;
import net.miginfocom.swing.MigLayout;
import services.PacijentServis;

public class RegistrujPacijentaDijalog extends JDialog {

	private static final long serialVersionUID = -1643711015982729307L;

	private JDialog roditelj;
	
	private PacijentServis pacijentServis = new PacijentServis();
	
	private JTextField tfLBO = new JTextField(35);
	private JButton btnPretrazi = new JButton("Pretraži");
	private JTextArea taPodaci = new JTextArea(15,5);
	private JButton btnRegistruj = new JButton("Registruj");
	private JButton btnOdustani = new JButton("Odustani");
	
	public RegistrujPacijentaDijalog(PacijentTableFrame pacijentTableFrame, boolean modal) {
		
		super(pacijentTableFrame, modal);
		this.roditelj = pacijentTableFrame;
		setTitle("Registracija pacijenta");
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
		akcije();
	}

	private void initGUI(){
		MigLayout mgLayout = new MigLayout();
		this.setLayout(mgLayout);
		
		add(new JLabel("<html>Unesite LBO pacijenta kojeg želite da registrujete."
				+ "<br>Ukoliko pacijent nije ranije zahtevao analize,"
				+ "<br>onda je potrebno da odete na glavni meni i da dodate pacijenta u sistem"
				+ " gde ćete navesti sve potrebne podatke.</html>"), "wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel("LBO"), "split 3, sg a");
		add(tfLBO, "pushx, growx");
		add(btnPretrazi, "wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel("Podaci:"), "wrap" );
		add(taPodaci,"span, push, grow, wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel(""));
		add(new JLabel(""));
		add(btnRegistruj);
		add(btnOdustani);
		
		taPodaci.setVisible(false);
		btnRegistruj.setEnabled(false);
	}
	
	private void akcije() {
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RegistrujPacijentaDijalog.this.dispose();
				RegistrujPacijentaDijalog.this.setVisible(false);
				roditelj.setVisible(true);
			}
		});
		
		
		btnPretrazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriValidnostUnosaIIspisiGreske()) {
					Pacijent p = pacijentServis.getPacijentSaZadatimLBObrojem(tfLBO.getText().trim());
					btnPretrazi.setEnabled(false);
					popuniTextArea(p);
					btnRegistruj.setEnabled(true);
					tfLBO.setEditable(false);
				}
				
			}
			private void popuniTextArea(Pacijent p) {
				String podaci = String.format("Ime: %s\nPrezime: %s\nPol: %s\nLBO:"
						+ " %s\nTelefon: %s\nDatum rodjenja: %s\nAdresa %s", p.getIme(), p.getPrezime(),
						p.getPol(), p.getLBO(), p.getTelefon(), p.getDatumRodjena(), p.getAdresa()); 
						
				taPodaci.setText(podaci);
				taPodaci.setVisible(true);
				taPodaci.setEditable(false);
			}


			private boolean proveriValidnostUnosaIIspisiGreske() {
				String LBO = tfLBO.getText().trim();
				if (LBO.equals("")) {
					JOptionPane.showMessageDialog(null, "Molim vas da unesite LBO pacijenta."
		                     ,"Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				else if (pacijentServis.getPacijentSaZadatimLBObrojem(LBO) == null) {
					JOptionPane.showMessageDialog(null, "Pacijent sa zadatim LBO brojem ne postoji."
							+ "Ukoliko pacijent ranije nije zahtevao nalaze potrebno je prvo da ga dodate"
							+ "u sistem, pa onda mozete da ga registrujete","Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				else if (pacijentServis.getPacijentSaZadatimLBObrojemKojiNijeRegistrovan(LBO) == null) {
					JOptionPane.showMessageDialog(null, "Pacijent sa zadatim LBO brojem je vec registrovan",
												"Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
			}
		});
		
		btnRegistruj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pacijent pacijent = pacijentServis.getPacijentSaZadatimLBObrojem(tfLBO.getText().trim());
				pacijentServis.registrujPacijenta(pacijent);
				String poruka = String.format("Pacijent %s %s je uspešno registrovan.\n\n"
						+ "%-20s %s\n%-20s %s", pacijent.getIme(), pacijent.getPrezime(), "korisničko ime:",
						pacijent.getUsername(), "lozinka:", pacijent.getPassword());
				JOptionPane.showMessageDialog(null, poruka, "Uspešna registracija", JOptionPane.INFORMATION_MESSAGE);
				RegistrujPacijentaDijalog.this.dispose();
				RegistrujPacijentaDijalog.this.setVisible(false);
			}
		});
		
		
	}

	
}
