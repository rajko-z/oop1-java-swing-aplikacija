package gui.pacijent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.PosebnaAnaliza;
import entity.korisnici.Pacijent;
import gui.grafici.PrikazGrafikaAnalize;
import gui.models.AnalizeZaObraduTableModel;
import res.ResourceLoader;
import services.NalaziServis;

public class PregledNalazaPacijent extends JDialog {

	private static final long serialVersionUID = -5178340713098285515L;
	
	private Nalaz nalaz;
	private NalaziServis nalaziServis = new NalaziServis();
	
	private JTable tabelaNalaza = new JTable();
	private JToolBar mainToolBar = new JToolBar();
	private JButton btnGrafik = new JButton("Pogledaj istoriju izmerenih vrednosti kroz grafik");
	private JTextField tfUkupnaCena = new JTextField(20);
	private JTextField tfKucnaPoseta = new JTextField(20);
	private JTextField tfDatumObrade = new JTextField(20);
	
	public PregledNalazaPacijent(JDialog roditelj, boolean modal, Nalaz nalaz) {
		super(roditelj, modal);
		this.nalaz = nalaz;
		podesiTitle();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1500,500);
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
		initAkcije();
	}

	private void podesiTitle() {
		StringBuilder sb = new StringBuilder("Pacijent: ");
		Pacijent p = this.nalaz.getZahtev().getPacijent();
		sb.append(p.getIme() + " ");
		sb.append(p.getPrezime() + "  ---");
		sb.append("PREGLED NALAZA---");
		this.setTitle(sb.toString());
	}
	
	private void initGUI() {
		podesiToolBar();
		podesiTabelu();
		podesiJug();
	}
	private void podesiTabelu() {
		tabelaNalaza.setModel(new AnalizeZaObraduTableModel(this.nalaz.getAnalizeZaObradu()));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		tabelaNalaza.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaNalaza.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaNalaza.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane srcPan = new JScrollPane(tabelaNalaza);
		
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
	}
	private void podesiJug() {
		tfUkupnaCena.setText(String.valueOf(nalaz.getZahtev().getCena()));
		if (nalaz.getZahtev().getDostava().isKućnaDostava()) {
			tfKucnaPoseta.setText("DA");
		}else {
			tfKucnaPoseta.setText("NE");
		}
		tfDatumObrade.setText(String.valueOf(nalaz.getZahtev().getDatumObrade()));
		
		tfUkupnaCena.setEditable(false);
		tfDatumObrade.setEditable(false);
		tfKucnaPoseta.setEditable(false);
		
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelJug.add(new JLabel("Ukupna cena nalaza:"));
		panelJug.add(tfUkupnaCena);
		panelJug.add(new JLabel("Da li je bila kućna poseta:"));
		panelJug.add(tfKucnaPoseta);
		panelJug.add(new JLabel("Datum obrade:"));
		panelJug.add(tfDatumObrade);
		
		this.getContentPane().add(panelJug, BorderLayout.SOUTH);
	}
	private void podesiToolBar() {
		ImageIcon grafikIcon = ResourceLoader.getImageIcon("grafikObican.png");
		ImageIcon grafikIconResized = new ImageIcon(grafikIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH));
		btnGrafik.setIcon(grafikIconResized);
		btnGrafik.setToolTipText("<html>Pogledaj istoriju vrednosti<br>analize kroz grafik");
		
		mainToolBar.add(btnGrafik);
		mainToolBar.setFloatable(false);
		mainToolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.getContentPane().add(mainToolBar, BorderLayout.NORTH);
	}
	
	private void initAkcije() {
		btnGrafik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaNalaza.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati analizu iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaNalaza.getValueAt(selectedRow, 0).toString());
					AnalizaZaObradu a =  nalaziServis.getAnalizuZaObraduById(id);
					PosebnaAnaliza analiza = a.getAnaliza();
					PrikazGrafikaAnalize prikaz = new PrikazGrafikaAnalize(PregledNalazaPacijent.this, false, nalaz.getZahtev().getPacijent(), analiza);
					prikaz.setVisible(true);
				}
			}
		});
		
	}
}
