package gui.admin.addEdit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.Plata;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import gui.admin.PlateDijalog;
import net.miginfocom.swing.MigLayout;
import services.DatumiServis;
import services.LaborantServis;
import services.MedicinskiTehničarServis;
import services.PlataServis;
import services.ValidatorServis;

public class AddEditPlataDijalog extends JDialog {

	private static final long serialVersionUID = -8365356178234387029L;
	private Plata plata;
	private Korisnik korisnik;
	private JDialog roditelj;
	private boolean isEditDijalog = false;
	private LaborantServis laborantServis = new LaborantServis();
	private MedicinskiTehničarServis mTehničarServis = new MedicinskiTehničarServis();
	private ValidatorServis validatorServis = new ValidatorServis();
	private PlataServis plataServis = new PlataServis();
	private DatumiServis datumiServis = new DatumiServis();

	// za add
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JList listMeseci = new JList(datumiServis.getMeseci());

	// za edit
	protected JTextField tfMesec = new JTextField(20);

	// zajednicke
	protected JTextField tfBrojSpecijalizacija = new JTextField(20);
	protected JTextField tfObradjeneStavke = new JTextField(20);
	protected JTextField tfRedovnaPlata = new JTextField(20);
	protected JTextField tfBonus = new JTextField(20);
	protected JTextField tfBrojKucnihPoseta = new JTextField(20);
	protected JButton btnSacuvaj = new JButton("Sačuvaj");
	protected JButton btnOdustani = new JButton("Odustani");

	public AddEditPlataDijalog(JDialog roditelj, boolean modal, Plata plata, Korisnik korisnik) {
		super(roditelj, modal);
		this.roditelj = roditelj;
		this.plata = plata;
		this.korisnik = korisnik;

		if (plata != null) {
			this.isEditDijalog = true;
			this.setTitle("Promena iznosa");
		} else {
			this.setTitle("Unos plate");
		}

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		podesiCentar();
		podesiJug();
	}

	private void podesiCentar() {
		JPanel panelCentar = new JPanel(new MigLayout());

		podesiUZavisnostiDaLiJeAddIliEdit(panelCentar);

		JLabel lblobradjeneStavke = new JLabel();
		if (korisnik instanceof Laborant) {
			panelCentar.add(new JLabel("<html>Broj specijalizacija<br>laboranta:"), "split 2, sg a");
			panelCentar.add(tfBrojSpecijalizacija, "pushx , growx, wrap");
			lblobradjeneStavke.setText("<html>Broj obradjenih nalaza<br>u mesecu");
		} else {
			panelCentar.add(new JLabel("<html>Broj kućnih poseta:"), "split 2, sg a");
			panelCentar.add(tfBrojKucnihPoseta, "pushx , growx, wrap");
			lblobradjeneStavke.setText("<html>Broj obradjenih zahteva<br>u mesecu");
		}

		panelCentar.add(lblobradjeneStavke, "split 2, sg a");
		panelCentar.add(tfObradjeneStavke, "pushx, growx, wrap");
		panelCentar.add(new JLabel("Redovna plata"), "split 2, sg a");
		panelCentar.add(tfRedovnaPlata, "pushx, growx, wrap");
		panelCentar.add(new JLabel("Bonus:"), "split 2, sg a");
		panelCentar.add(tfBonus, "pushx, growx, wrap");

		this.getContentPane().add(panelCentar, BorderLayout.CENTER);

		initPoljaPodacima();

	}

	private void podesiUZavisnostiDaLiJeAddIliEdit(JPanel panelCentar) {
		if (isEditDijalog) {
			panelCentar.add(new JLabel("Mesec:"), "split 2, sg a");
			panelCentar.add(tfMesec, "pushx, growx, wrap");
		} else {
			JScrollPane src = new JScrollPane(listMeseci);
			panelCentar.add(new JLabel("Izaberite mesec:"), "split 2, sg a");
			panelCentar.add(src, "pushx, growx, wrap");
		}
	}

	private void initPoljaPodacima() {
		
		zabraniMenjaneKomponentama();
		
		if (isEditDijalog) {
			podesiObradjeneStavkeUMesecu(this.plata.getGodina(), this.plata.getMesec());
			tfRedovnaPlata.setText(String.valueOf(this.plata.getRedovnaPlata()));
			tfBonus.setText(String.valueOf(this.plata.getBonus()));
			tfMesec.setText(this.plata.getMesec().toString());
			if (korisnik instanceof Laborant) {
				podesiBrojSpecijalizacija();
			} else {
				podesiBrojKucnihPoseta(this.plata.getGodina(), this.plata.getMesec());
			}
		}
		else {
			sakriKomponente();
		}
	}
	
	private void podesiBrojSpecijalizacija() {
		Laborant l = (Laborant) korisnik;
		tfBrojSpecijalizacija.setText(String.valueOf(l.getGrupeAnaliza().size()));
	}
	
	private void podesiBrojKucnihPoseta(Year year, Month month) {
		int godina = Integer.parseInt(year.toString());
		LocalDate pocetak = LocalDate.of(godina, month, 1);
		LocalDate kraj = datumiServis.getDatumPoslednjegDanaUMesecuNekeGodine(year, month);
		MedicinskiTehničar m = (MedicinskiTehničar) korisnik;
		int brojKucnihPoseta = mTehničarServis
				.getBrojKucnihPosetaUZadatomPerioduSaDanima(m, pocetak, kraj, datumiServis.getDaniUNedelji());
		tfBrojKucnihPoseta.setText(String.valueOf(brojKucnihPoseta));
	}
	
	private void podesiObradjeneStavkeUMesecu(Year year, Month month) {
		int godina = Integer.parseInt(year.toString());
		LocalDate pocetak = LocalDate.of(godina, month, 1);
		LocalDate kraj = datumiServis.getDatumPoslednjegDanaUMesecuNekeGodine(year,month);

		int broj = 0;
		if (korisnik instanceof Laborant) {
			broj = laborantServis.getBrojObradjenihNalazUZadatomPeriouduSaDanima((Laborant) this.korisnik, pocetak, kraj, datumiServis.getDaniUNedelji());
			tfObradjeneStavke.setText(String.valueOf(broj));
		}else {
			broj = mTehničarServis.getBrojObradjenihZahtevaUZadatomPerioduSaDanima((MedicinskiTehničar) this.korisnik, pocetak,kraj, datumiServis.getDaniUNedelji());
		}
		tfObradjeneStavke.setText(String.valueOf(broj));
	}
	
	
	
	private void sakriKomponente() {
		tfBrojSpecijalizacija.setVisible(false);;
		tfObradjeneStavke.setVisible(false);
		tfRedovnaPlata.setVisible(false);
		tfBonus.setVisible(false);
		tfBrojKucnihPoseta.setVisible(false);
		btnSacuvaj.setVisible(false);
	}
	
	private void prikaziKomponente() {
		tfBrojSpecijalizacija.setVisible(true);;
		tfObradjeneStavke.setVisible(true);
		tfRedovnaPlata.setVisible(true);
		tfBonus.setVisible(true);
		tfBrojKucnihPoseta.setVisible(true);
		btnSacuvaj.setVisible(true);
	}
	private void zabraniMenjaneKomponentama() {
		tfMesec.setEditable(false);
		tfObradjeneStavke.setEditable(false);
		tfRedovnaPlata.setEditable(false);
		tfBrojSpecijalizacija.setEditable(false);
		tfBrojKucnihPoseta.setEditable(false);
	}

	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnSacuvaj);
		panelJug.add(btnOdustani);
		this.getContentPane().add(panelJug, BorderLayout.SOUTH);
	}
	
	private void popuniPoljaAddDijalog() {
		double redovnaPlata = plataServis.getRedovnaPlataZaKorisnika(korisnik);
		Month mesec = (Month)listMeseci.getSelectedValue();
		tfRedovnaPlata.setText(String.valueOf(redovnaPlata));
		podesiObradjeneStavkeUMesecu(Year.now(), mesec);
		if (korisnik instanceof Laborant) {
			podesiBrojSpecijalizacija();
		}else {
			podesiBrojKucnihPoseta(Year.now(), mesec);
		}
	}
	
	
	private void initAkcije() {

		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (validatorServis.nijeBrojUOdgovarajucemRasponu(0.0, 100000.0, tfBonus.getText().trim())) {
					JOptionPane.showMessageDialog(null, "Za bonus morate uneti broj maksimalan do 100000");
				} else {
					if (isEditDijalog) {
						plataServis.promeniBonusPlate(plata, Double.parseDouble(tfBonus.getText().trim()));
						JOptionPane.showMessageDialog(null, "Podaci su uspešno sačuvani");
					}else {
						Month mesec = (Month) listMeseci.getSelectedValue();
						double bonus = Double.parseDouble(tfBonus.getText().trim());
						double redovnaPlata = Double.parseDouble(tfRedovnaPlata.getText().trim());
						plataServis.kreirajNovuPlatu(Year.now(), mesec, bonus, redovnaPlata, korisnik);
						JOptionPane.showMessageDialog(null, "Plata je uspešno uneta");
					}
					((PlateDijalog) roditelj).osveziTabelu();
					AddEditPlataDijalog.this.dispose();
				}
			}
		});

		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditPlataDijalog.this.dispose();
				AddEditPlataDijalog.this.setVisible(false);
			}
		});
		
		listMeseci.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listMeseci.getSelectedIndex() != -1) {
					Month mesec = (Month) listMeseci.getSelectedValue();
					if (mesec.getValue() > LocalDate.now().getMonthValue()) {
						JOptionPane.showMessageDialog(null, "Molim vas izaberite trenunti mesec,\n"
								+ "ili neki od prošlih ukoliko ste zaboravili\nda unesete platu.");
					}
					else if (plataServis.plataZaKorisnikaZaZadatiMesecVecPostoji(Year.now(), mesec, korisnik)) {
						JOptionPane.showMessageDialog(null, "Plata zaposlenog za zadati mesec je već uneta u sistem.\n"
								+ "Ukoliko želite da odradite izmenu plate, vratite se na prošli prozor");
						sakriKomponente();
					}
					else {
						popuniPoljaAddDijalog();
						prikaziKomponente();
					}
				}
			}

		});

	}

}
