package gui.pacijent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

import entity.Zahtev;
import entity.korisnici.Pacijent;
import gui.models.ZahtevTableModel;
import res.ResourceLoader;
import services.ZahteviServis;

public class PacijentStanjeZahtevaDijalog extends JDialog {

	private static final long serialVersionUID = -8654380577027546792L;
	protected JButton btnPogledajZahtev = new JButton("Pogledaj zahtev");
	protected Pacijent pacijent;
	protected ZahteviServis zahteviServis = new ZahteviServis();
	protected JTable tblNeobradjeniZahtevi;
	protected JButton btnOdustani = new JButton("Odustani");
	protected JToolBar mainToolbar = new JToolBar();
	
	
	public PacijentStanjeZahtevaDijalog(PacijentFrame pacijentFrame, boolean modal, Pacijent pacijent) {
		super(pacijentFrame, modal);
		this.pacijent = pacijent;
		this.setTitle("Trenutno stanje zahteva");
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setSize(1350,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
		akcije();
	}

	private void initGUI() {
		
		// toolbar podesevanje
		ImageIcon odradjeno = ResourceLoader.getImageIcon("information.png");
		ImageIcon odradjenoResizedIcon = new ImageIcon(odradjeno.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPogledajZahtev.setIcon(odradjenoResizedIcon);
		btnPogledajZahtev.setToolTipText("Pogledaj zahtev");
		mainToolbar.add(btnPogledajZahtev);
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
		
		//tabela podesavanja
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		Zahtev zahtev = zahteviServis.getZahtevPacijentaKojiNijeGotov(pacijent);
		List<Zahtev>zahtevi = new ArrayList<Zahtev>();
		zahtevi.add(zahtev);
		
		tblNeobradjeniZahtevi = new JTable(new ZahtevTableModel(zahtevi));
		tblNeobradjeniZahtevi.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblNeobradjeniZahtevi.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblNeobradjeniZahtevi.getTableHeader().setReorderingAllowed(false);
		tblNeobradjeniZahtevi.setDefaultEditor(Object.class, null);
		JTableHeader th = tblNeobradjeniZahtevi.getTableHeader();
		th.setPreferredSize(new Dimension(50, 45));
		
		JScrollPane srcPan = new JScrollPane(tblNeobradjeniZahtevi);
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);

		
		//podesavje bortherlayout.SOUTH
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnOdustani);
		add(panelJug, BorderLayout.SOUTH);
	}
	
	private void akcije() {
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacijentStanjeZahtevaDijalog.this.dispose();
				PacijentStanjeZahtevaDijalog.this.setVisible(false);		
			}
		});
		
		btnPogledajZahtev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblNeobradjeniZahtevi.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zahtev iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tblNeobradjeniZahtevi.getValueAt(selectedRow, 0).toString());
					Zahtev zahtev = zahteviServis.getZahtevById(id);
					String poruka = generisiPoruku(zahtev);
					JOptionPane.showMessageDialog(null, poruka, "Inforamcija", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			private String generisiPoruku(Zahtev zahtev) {
				return String.format("Trenutno stanje zahteva:     %s\n\nKada obrada zahteva bude završena moćete da\n"
						+ " pogledate njegov nalaz u istoriji vaših nalaza", zahtev.getStanjeZahteva().getOpis().toUpperCase());
			}
			
		});
		
	}
	
}
