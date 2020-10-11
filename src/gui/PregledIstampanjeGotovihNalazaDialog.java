package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.korisnici.Korisnik;
import entity.korisnici.Pacijent;
import gui.models.GotovNalazTableModel;
import gui.pacijent.PregledNalazaPacijent;
import net.miginfocom.swing.MigLayout;
import res.ResourceLoader;
import services.IzvestajNalazaServis;
import services.NalaziServis;

public class PregledIstampanjeGotovihNalazaDialog extends JDialog {

	private static final long serialVersionUID = -7242582839036479083L;
	private Korisnik korisnik;
	private NalaziServis nalaziServis = new NalaziServis();
	private IzvestajNalazaServis izvestajNalazaServis = new IzvestajNalazaServis();

	protected JButton btnIzlaz = new JButton("Izlaz");
	protected JButton btnPregled = new JButton();
	protected JButton btnStampa = new JButton();
	protected ImageIcon pregledIconResized;

	protected JToolBar mainToolbar = new JToolBar();
	protected JTable tabelaNalazi;

	public PregledIstampanjeGotovihNalazaDialog(JFrame roditelj, boolean modal, Korisnik korisnik) {
		super(roditelj, modal);
		this.korisnik = korisnik;
		setTitle("Pregled gotovih nalaza");
		setSize(1350, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		actions();
	}

	private void initGUI() {
		podesiToolBar();
		podesiTabelu();
		podesiJug();
	}

	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnIzlaz);
		add(panelJug, BorderLayout.SOUTH);
	}

	private void podesiToolBar() {
		ImageIcon pregledIcon = ResourceLoader.getImageIcon("pregledNalaza.png");
		pregledIconResized = new ImageIcon(pregledIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		btnPregled.setIcon(pregledIconResized);
		btnPregled.setToolTipText("Pogledaj nalaz");

		ImageIcon stampaIcon = ResourceLoader.getImageIcon("print.png");
		ImageIcon stampaIconResized = new ImageIcon(
				stampaIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH));
		btnStampa.setIcon(stampaIconResized);
		btnStampa.setToolTipText("Odštampaj nalaz");
		
		MigLayout layout = new MigLayout();
		mainToolbar.setBackground(Color.white);
		mainToolbar.setLayout(layout);
		mainToolbar.add(btnPregled, "span, wrap");
		mainToolbar.add(btnStampa, "span, wrap");
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.EAST);

	}

	private void podesiTabelu() {
		if (korisnik instanceof Pacijent) {
			tabelaNalazi = new JTable(new GotovNalazTableModel(nalaziServis.getNalaziZaPacijenta((Pacijent)korisnik)));
		}
		else {
			tabelaNalazi = new JTable(new GotovNalazTableModel(nalaziServis.getSviZavrseniNalazi()));
		}
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaNalazi.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaNalazi.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaNalazi.setAutoCreateRowSorter(true);
		tabelaNalazi.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaNalazi);
		
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}

	private void actions() {
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PregledIstampanjeGotovihNalazaDialog.this.dispose();
				PregledIstampanjeGotovihNalazaDialog.this.setVisible(false);
				super.windowClosing(e);
			}
		});
		
		
		btnIzlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PregledIstampanjeGotovihNalazaDialog.this.dispose();
				PregledIstampanjeGotovihNalazaDialog.this.setVisible(false);
			}
		});
		
		btnPregled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaNalazi.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati nalaz iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaNalazi.getValueAt(selectedRow, 0).toString());
					Nalaz nalaz = nalaziServis.getNalazById(id);
					if (korisnik instanceof Pacijent) {
						PregledNalazaPacijent pp = new PregledNalazaPacijent(PregledIstampanjeGotovihNalazaDialog.this, true, nalaz);
						pp.setVisible(true);
						
					}else {
						String nalazPodaci = generisiPodatkeOnalazu(nalaz);
						JTextArea tf = new JTextArea(nalazPodaci);
						tf.setEditable(false);
						tf.setFont(new Font("Monospaced", Font.BOLD, 16));
						JOptionPane.showMessageDialog(null, tf, "Pregled nalaza", JOptionPane.INFORMATION_MESSAGE, pregledIconResized);
					}
			}
			}
		});
		
		btnStampa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaNalazi.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati nalaz iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaNalazi.getValueAt(selectedRow, 0).toString());
					Nalaz nalaz = nalaziServis.getNalazById(id);
					String nalazPodaci = generisiPodatkeOnalazu(nalaz);
					izvestajNalazaServis.kreirajIzvestajUFolderuIzvestaji(nalazPodaci, nalaz);
					fileChooserDialog(nalaz);
				}
			}
		});
		
	}
	
	private void fileChooserDialog(Nalaz nalaz) {
		String nalazPodaci = generisiPodatkeOnalazu(nalaz);
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Sačuvaj nalaz");
		fileChooser.setFileFilter(new FileNameExtensionFilter("text file", "*.txt"));
		fileChooser.setSelectedFile(new File(izvestajNalazaServis.generisiImeFajlaZaNalaz(nalaz)));
		
        int option = fileChooser.showSaveDialog(PregledIstampanjeGotovihNalazaDialog.this);
        if(option == JFileChooser.APPROVE_OPTION){
        	File file = fileChooser.getSelectedFile();
        	if(!izvestajNalazaServis.kreirajIzvestajNaKompuKorisnika(file, nalazPodaci)) {
        		JOptionPane.showMessageDialog(null, "Došlo je do greškre prilikom čuvanja fajla");
        	}
        }
	}
	
	
	
	private String generisiPodatkeOnalazu(Nalaz nalaz) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-20s| %-23s| %-26s| %-26s| %-18s| %s\n", "Grupa analize",
				"Stavka analize", "Donja referentna vrednost", "Gornja referenta vrednost",
				"Izmerene vrednost", "Jedinica mere"));
		sb.append(String.format("%-20s| %-23s| %-26s| %-26s| %-18s| %s"," "," ", " "," "," "," ").replace(" ", "-") + "------------\n");
		for (AnalizaZaObradu a: nalaz.getAnalizeZaObradu()) {
			sb.append(String.format("%-20s| %-23s| %-26s| %-26s| %-18s| %s\n", a.getAnaliza().getGrupaAnaliza().getNaziv(),
					a.getAnaliza().getNaziv(), a.getAnaliza().getDonjaRefVrednost(), a.getAnaliza().getGornjaRefVrednost(),
					a.getIzmerenaVrednost(), a.getAnaliza().getJedinicnaVrednost()));
		}
		sb.append(String.format("%-20s| %-23s| %-26s| %-26s| %-18s| %s"," "," ", " "," "," "," ").replace(" ", "-") + "------------\n");
		sb.append(String.format("%-30s %-20s\n", "IME PACIJENTA:", nalaz.getZahtev().getPacijent().getIme().toUpperCase()));
		sb.append(String.format("%-30s %-20s\n", "PREZIME PACIJENTA:", nalaz.getZahtev().getPacijent().getPrezime().toUpperCase()));
		if (nalaz.getZahtev().getDostava().isKućnaDostava()) {
			sb.append(String.format("%-30s %s\n", "DA LI JE BILA KUĆNA DOSTAVA:","JESTE"));
		}
		else {
			sb.append(String.format("%-30s %-15s\n", "DA LI JE BILA KUĆNA DOSTAVA:","NIJE"));
		}
		sb.append(String.format("%-30s %-15s\n", "UKUPNA CENA SA DOSTAVOM:", nalaz.getZahtev().getCena()));
		sb.append("--------------------------------------------------------------\n");
		sb.append(String.format("%-30s %-15s\n", "DATUM OBRADE:", nalaz.getDatumObrade()));
		return sb.toString();
	}
	
	
	
}
