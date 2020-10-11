package gui.admin.addEdit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import gui.admin.AnalizeIGrupeDijalog;
import gui.models.AnalizaTableModel;
import gui.models.GrupeAnalizaTableModel;
import services.AnalizeServis;

public class VratiObrisaneGrupeIAnalize extends JDialog {

	private static final long serialVersionUID = 4726549423755355103L;
	private JDialog roditelj;
	private boolean isAnalizeDijalog = false;
	private AnalizeServis analizeServis = new AnalizeServis();
	private JTable tabelaStavke = new JTable();

	public VratiObrisaneGrupeIAnalize(JDialog roditelj, boolean modal, boolean isAnalizeDijalog) {
		super(roditelj, modal);
		this.roditelj = roditelj;
		this.isAnalizeDijalog = isAnalizeDijalog;
		this.setPreferredSize(new Dimension(1000, 500));

		if (this.isAnalizeDijalog) {
			setTitle("Vrati analizu nazad u ponudu");
		} else {
			setTitle("Vrati grupu nazad u ponudu");
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();

	}

	private void initGUI() {
		podesiTabelu();
	}

	private void podesiTabelu() {
		setTableData();
		JPanel panel = new JPanel(new GridLayout(1, 1));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Selektujte stavku iz tabele", TitledBorder.CENTER, TitledBorder.TOP));
		tabelaStavke.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaStavke.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaStavke.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaStavke);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void setTableData() {
		if (isAnalizeDijalog) {
			tabelaStavke.setModel(new AnalizaTableModel(analizeServis.getObrisaneAnalize()));
		} else {
			tabelaStavke.setModel(new GrupeAnalizaTableModel(analizeServis.getObrisaneGrupeAnaliza()));
		}
	}

	private void initAkcije() {
		tabelaStavke.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = tabelaStavke.getSelectedRow();
				if (selectedRow != -1) {
					int answer = JOptionPane.showConfirmDialog(null, generisiPoruku(),"Pitanje",JOptionPane.YES_NO_CANCEL_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						
						int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
						
						if (isAnalizeDijalog) {
							PosebnaAnaliza analiza = analizeServis.getPosebnaAnalizaById(id);
							analizeServis.vratiAnalizuNazadUPonudu(analiza);
							JOptionPane.showMessageDialog(null, "Analiza je uspešno vraćena u ponudu");
						} else {
							GrupaAnaliza grupa = analizeServis.getGrupaAnalizeById(id);
							analizeServis.vratiGrupuNazadUPonudu(grupa);
							JOptionPane.showMessageDialog(null, "Grupa je uspešno vraćena u ponudu");
						}
						osveziTabelu();
						((AnalizeIGrupeDijalog) roditelj).osveziTabelu();
					}
				}
			}

			private String generisiPoruku() {
				if (isAnalizeDijalog) {
					return new String("Da li ste sigurni da želite da vratite ovu analizu nazad u ponudu?");
				}else {
					return new String("Da li ste sigurni da želite da vratite ovu grupu nazad u ponudu,"
							+ "i sa njom sve analize koje ona poseduje?\n");
				}
			}
		});
	}

	private void osveziTabelu() {
		if (isAnalizeDijalog) {
			AnalizaTableModel model = new AnalizaTableModel(analizeServis.getObrisaneAnalize());
			this.tabelaStavke.setModel(model);
		}else {
			GrupeAnalizaTableModel model = new GrupeAnalizaTableModel(analizeServis.getObrisaneGrupeAnaliza());
			this.tabelaStavke.setModel(model);
		}
	}

}
