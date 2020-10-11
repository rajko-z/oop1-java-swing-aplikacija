package gui.medicinar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import org.assertj.core.internal.bytebuddy.description.modifier.FieldPersistence;

import entity.Zahtev;
import entity.korisnici.Korisnik;
import entity.korisnici.MedicinskiTehničar;
import gui.models.ZahtevTableModel;
import res.ResourceLoader;
import services.ZahteviServis;

public class ObradaKucnihPosetaDialog extends JDialog{

	private static final long serialVersionUID = -6796315041675553122L;
	protected MedicinskiTehničar mTehničar;
	protected ZahteviServis zahteviServis = new ZahteviServis();
	
	protected boolean dodajKucnuPosetuDijalog;
	protected JTable tabelaKucnaPoseta;
	protected JToolBar mainToolbar = new JToolBar();
	
	protected JButton btnOdradjeno = new JButton("Označi da je uzorak preuzet");
	protected JButton btnOdustani = new JButton("Odustani");
	protected JButton btnKucnaPoseta = new JButton();
	
	
	
	
	public ObradaKucnihPosetaDialog(MedicinskiTehničarFrame medicinskiTehničarFrame, boolean modal, Korisnik korisnik, boolean dodajKucnuPosetuDijalog) {
		super(medicinskiTehničarFrame, modal);
		this.dodajKucnuPosetuDijalog = dodajKucnuPosetuDijalog;
		this.mTehničar = (MedicinskiTehničar) korisnik;
		
		setSize(1350,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (dodajKucnuPosetuDijalog) {
			setTitle("Preuzimanje kućne posete");
		}
		else {
			setTitle("Moje preuzete posete");
		}
		
		initGUI();
		akcije();
	}

	private void initGUI() {
		podesiToolBar();
		podesiTabelu();
		podesiJug();
	}
	
	
	private void podesiToolBar() {
		if (dodajKucnuPosetuDijalog) {
			podesiPrvuIkonuUZavisnostiOdDijaloga("kuca.png", "Odaberite kućnu posetu");
			btnOdradjeno.setVisible(false);
		}
		else {
			podesiPrvuIkonuUZavisnostiOdDijaloga("cancel.png", "Odustanite od kućne posete");
		}
		ImageIcon odradjeno = ResourceLoader.getImageIcon("tick.png");
		ImageIcon odradjenoResizedIcon = new ImageIcon(odradjeno.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnOdradjeno.setIcon(odradjenoResizedIcon);
		
		mainToolbar.add(btnKucnaPoseta);
		mainToolbar.add(btnOdradjeno);
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
	}
	
	private void podesiPrvuIkonuUZavisnostiOdDijaloga(String filePath, String btnText) {
		ImageIcon kucnaPoseta = ResourceLoader.getImageIcon(filePath);
		ImageIcon resizedKucnaPoseta = new ImageIcon(kucnaPoseta.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnKucnaPoseta.setText(btnText);
		btnKucnaPoseta.setIcon(resizedKucnaPoseta);
	}

	private void podesiTabelu() {
		
		initTabeluPodacimaUZavisnostiOdDijaloga();
		
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaKucnaPoseta.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaKucnaPoseta.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaKucnaPoseta.getTableHeader().setReorderingAllowed(false);
		JTableHeader th = tabelaKucnaPoseta.getTableHeader();
		th.setPreferredSize(new Dimension(50, 45));
		JScrollPane srcPan = new JScrollPane(tabelaKucnaPoseta);
		
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
		
	}
	
	private void initTabeluPodacimaUZavisnostiOdDijaloga() {
		List<Zahtev>zahtevi = null;
		if(dodajKucnuPosetuDijalog) {
			zahtevi = zahteviServis.getZahteviSaKucnomDostavom();
		}else {
			zahtevi = zahteviServis.getZahteviTrenutnihPosetaMediciskogTehnicara(mTehničar);
		}
		tabelaKucnaPoseta = new JTable(new ZahtevTableModel(zahtevi));
	}
	
	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnOdustani);
		add(panelJug, BorderLayout.SOUTH);
	}

	private void akcije() {
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ObradaKucnihPosetaDialog.this.dispose();
				ObradaKucnihPosetaDialog.this.setVisible(false);				
			}
		});
		
		btnKucnaPoseta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaKucnaPoseta.getSelectedRow();
				if (selectedRow == -1) {
					
					JOptionPane.showMessageDialog(null, "Morate selektovati zahtev iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaKucnaPoseta.getValueAt(selectedRow, 0).toString());
					Zahtev zahtev = zahteviServis.getZahtevById(id);
					String poruka = generisiPoruku(zahtev, dodajKucnuPosetuDijalog);
					int answer = JOptionPane.showConfirmDialog(null, poruka, "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						if (dodajKucnuPosetuDijalog) {
							zahteviServis.oznaciZahtevNaStanjePreuzimanja(zahtev, mTehničar);
							JOptionPane.showMessageDialog(null, "Kućna dostava je uspešno preuzeta.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							zahteviServis.oznaciZahtevNaPocetnoStanje(zahtev);
							JOptionPane.showMessageDialog(null, "Kućna dostava je uspešno poništena.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						}
						ObradaKucnihPosetaDialog.this.dispose();
						ObradaKucnihPosetaDialog.this.setVisible(false);
					}
				}
				
			}

			private String generisiPoruku(Zahtev zahtev, boolean dodajKucnuPosetu) {
				String retVal = "";
				if (dodajKucnuPosetu) {
					retVal += "Da li ste sigurni da želite da preuzmete kućnu posetu ovog zahteva?\n\n";
				}
				else {
					retVal += "Da li ste sigurni da želite da odustane od kućne posete ovog zahteva?\n\n";
				}
				retVal += "datum:   " + zahtev.getDostava().getDatumDostave() + "\n";
				if (zahtev.getDostava().isKućnaDostavaSaVremenom()) {
					retVal += "vreme:   " +  zahtev.getDostava().getVremeDostave() + "\n";
				}
				retVal += "adresa:   " + zahtev.getPacijent().getAdresa();
				return retVal;
			}
		});
		
		btnOdradjeno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tabelaKucnaPoseta.getSelectedRow();
				if (selectedRow == -1) {
					
					JOptionPane.showMessageDialog(null, "Morate selektovati zahtev iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaKucnaPoseta.getValueAt(selectedRow, 0).toString());
					Zahtev zahtev = zahteviServis.getZahtevById(id);
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da označite ovaj zahtev kao preuzet\n"
							+ "tako da laboranti mogu da krenu na obradu.", "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						zahteviServis.oznaciZahtevNaStanjeObrade(zahtev, mTehničar);
						JOptionPane.showMessageDialog(null, "Zahtev je uspešno stavljen na obradu.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						ObradaKucnihPosetaDialog.this.dispose();
						ObradaKucnihPosetaDialog.this.setVisible(false);
					}
				}
				
			}
		});
		
		
	}
}
