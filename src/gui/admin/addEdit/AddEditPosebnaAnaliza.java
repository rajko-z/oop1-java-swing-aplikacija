package gui.admin.addEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import gui.admin.AnalizeIGrupeDijalog;
import net.miginfocom.swing.MigLayout;
import services.AnalizeServis;
import services.ValidatorServis;

public class AddEditPosebnaAnaliza extends JDialog {

	private static final long serialVersionUID = 6435384509456816612L;
	private JDialog roditelj;
	private AnalizeServis analizeServis = new AnalizeServis();
	private ValidatorServis validatorServis = new ValidatorServis();
	
	protected JButton btnSacuvaj = new JButton("Sačuvaj");
	protected JButton btnIzlaz = new JButton("Izlaz");

	protected PosebnaAnaliza posebnaAnaliza;
	protected boolean isEditDijalog = false;

	protected JComboBox comboBoxGrupe = new JComboBox(analizeServis.getValidneGrupeAnaliza().toArray());
	protected JTextField tfNaziv = new JTextField(20);
	protected JTextField tfCena = new JTextField(20);
	protected JTextField tfDonjaRef = new JTextField(20);
	protected JTextField tfGornjaRef = new JTextField(20);
	protected JTextField tfJedinicaMere = new JTextField(20);

	public AddEditPosebnaAnaliza(JDialog roditelj, boolean modal, PosebnaAnaliza posebnaAnaliza) {
		super(roditelj, modal);
		this.posebnaAnaliza = posebnaAnaliza;
		this.roditelj = roditelj;

		if (posebnaAnaliza != null) {
			this.isEditDijalog = true;
		}
		if (isEditDijalog) {
			setTitle("Promena podataka analize");
		} else {
			setTitle("Dodavanje nove analize");
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		JPanel centar = new JPanel(new MigLayout());

		centar.add(new JLabel("Naziv:"), "split 2, sg a");
		centar.add(tfNaziv, "pushx, growx, wrap");
		centar.add(new JLabel("Grupa:"), "split 2, sg a");
		centar.add(comboBoxGrupe, "pushx, growx, wrap");
		centar.add(new JLabel("Cena:"), "split 2, sg a");
		centar.add(tfCena, "pushx, growx, wrap");
		centar.add(new JLabel("Donja ref. vrednost:"), "split 2, sg b");
		centar.add(tfDonjaRef, "pushx, growx, wrap");
		centar.add(new JLabel("Gornja ref. vrednost:"), "split 2, sg b");
		centar.add(tfGornjaRef, "pushx, growx, wrap");
		centar.add(new JLabel("Jedinica mere:"), "split 2, sg b");
		centar.add(tfJedinicaMere, "pushx, growx, wrap");
		centar.add(new JLabel(" "), "span, wrap");
		centar.add(new JLabel(" "), "sg b, split 3");
		centar.add(btnSacuvaj);
		centar.add(btnIzlaz, "wrap");

		this.getContentPane().add(centar);

		if (isEditDijalog) {
			popuniPoljaPodacima();
		}
	}

	private void popuniPoljaPodacima() {
		tfNaziv.setText(posebnaAnaliza.getNaziv());
		tfCena.setText(String.valueOf(posebnaAnaliza.getCena()));
		tfDonjaRef.setText(String.valueOf(posebnaAnaliza.getDonjaRefVrednost()));
		tfGornjaRef.setText(String.valueOf(posebnaAnaliza.getGornjaRefVrednost()));
		tfJedinicaMere.setText(posebnaAnaliza.getJedinicnaVrednost());
		comboBoxGrupe.setSelectedItem(posebnaAnaliza.getGrupaAnaliza());
	}

	private void initAkcije() {
		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String naziv = tfNaziv.getText().trim();
				String cena = tfCena.getText().trim();
				String donjaRef = tfDonjaRef.getText().trim();
				String gornjaRef = tfGornjaRef.getText().trim();
				String jedinicaMere = tfJedinicaMere.getText().trim();
				
				if (proveriUnos(naziv, cena, donjaRef, gornjaRef, jedinicaMere)) {
					int answer = JOptionPane.showConfirmDialog(null, generiPoruku(), "Pitanje", JOptionPane.YES_NO_OPTION);
					
					if (answer == JOptionPane.YES_OPTION) {
						
						if (isEditDijalog) {
							analizeServis.editAnaliza(posebnaAnaliza, naziv, getSelektovanaGrupa(), getCena(), getDonjaRef(), getGornjaRef(), jedinicaMere);
						}else {
							analizeServis.kreirajNovuAnalizu(naziv, getSelektovanaGrupa(), getCena(), getDonjaRef(), getGornjaRef(), jedinicaMere);
						}
						JOptionPane.showMessageDialog(null, "Podaci su uspešno sačuvani");
						((AnalizeIGrupeDijalog)roditelj).osveziTabelu();
						AddEditPosebnaAnaliza.this.dispose();
					}
					
				}
			}

			private String generiPoruku() {
				StringBuilder sb = new StringBuilder("Da li ste sigurni da želite da sačuvate ove padatke:\n");
				sb.append("Naziv analize: " + tfNaziv.getText().trim() + "\n");
				sb.append("Grupa analize: " + getSelektovanaGrupa().getNaziv() + "\n");
				sb.append("Cena : " + tfCena.getText().trim() + "\n");
				sb.append("Donja ref. vrednost: " + getDonjaRef() + "\n");
				sb.append("Gornja ref. vrednost: " + getGornjaRef() + "\n");
				sb.append("Jedinica mere: " + tfJedinicaMere.getText().trim() + "\n");
				
				return sb.toString();
			}

			private boolean proveriUnos(String naziv, String cena, String donjaRef, String gornjaRef,
					String jedinicaMere) {
				if (naziv.equals("") | cena.equals("") | donjaRef.equals("") | gornjaRef.equals("")
						| jedinicaMere.equals("") | comboBoxGrupe.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve potrebne podatke.");
					return false;
				}
				if (naziv.length() > 30 ) {
					JOptionPane.showMessageDialog(null, "Naziv ne može imati više od 30 slova.");
					return false;
				}
				if (analizeServis.analizaSaUnetimImenomVecPostoji(naziv)) {
					JOptionPane.showMessageDialog(null, "Analiza sa unetim imenom već postoji.\n"
							+ "Ukoliko ste ranije uklonili ovu analizu iz ponude,\n"
							+ "možete je vratiti odabirom opcije 'Vrati nazad obrisanu stavku'.");
					return false;
				}
				if (jedinicaMere.length() > 15) {
					JOptionPane.showMessageDialog(null, "Jedinica mere ne može imati više od 15 slova.");
					return false;
				}
				if (validatorServis.nijeBrojUOdgovarajucemRasponu(50.0, 20000, cena)) {
					JOptionPane.showMessageDialog(null, "Cena mora biti u rasponu izmedju 50 i 20000 dinara.");
					return false;
				}
				if (validatorServis.nijeBrojUOdgovarajucemRasponu(0.0, 2000.0, donjaRef) |
						validatorServis.nijeBrojUOdgovarajucemRasponu(0.0, 2000.0, gornjaRef)) {
					JOptionPane.showMessageDialog(null, "Donja i gornja vrednost moraju biti u rasponu izmedju 0 i 2000");
					return false;
				}
				if (getDonjaRef() >= getGornjaRef()) {
					JOptionPane.showMessageDialog(null, "Gornja referentna vrednost mora biti veća od donje");
					return false;
				}
				return true;
			}

		});

		btnIzlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditPosebnaAnaliza.this.dispose();
				AddEditPosebnaAnaliza.this.setVisible(false);
			}
		});
	}
	
	private double getDonjaRef() {
		return Double.parseDouble(tfDonjaRef.getText().trim());
	}
	private double getGornjaRef() {
		return Double.parseDouble(tfGornjaRef.getText().trim());
	}
	private GrupaAnaliza getSelektovanaGrupa() {
		return (GrupaAnaliza) comboBoxGrupe.getSelectedItem();
	}
	private double getCena() {
		return Double.parseDouble(tfCena.getText().trim());
	}

}
