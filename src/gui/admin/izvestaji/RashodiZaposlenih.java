package gui.admin.izvestaji;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;

import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import gui.grafici.GenericChart;
import gui.grafici.GrafikRashodi;
import gui.models.RashodiTableModel;
import services.KorisniciServis;
import services.PlataServis;
import utils.DTOselektovaniDaniIDatumi;

public class RashodiZaposlenih extends JDialog {

	private static final long serialVersionUID = -7923800995666099360L;

	protected JTextField tfUkupanIznos = new JTextField(20);
	protected JTextField tfIznosLaboranti = new JTextField(20);
	protected JTextField tfIznosMedicinari = new JTextField(20);

	DTOselektovaniDaniIDatumi dto;
	JTable tabelaRashoda = new JTable();
	KorisniciServis korisniciServis = new KorisniciServis();
	PlataServis plataServis = new PlataServis();

	public RashodiZaposlenih(JDialog roditelj, boolean modal, DTOselektovaniDaniIDatumi dto) {
		super(roditelj, modal);
		this.dto = dto;
		this.setTitle("Rashodi");
		setSize(1800, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		initTabela();
		postaviJug();
		postaviGrafik();
	}

	private void initTabela() {
		RashodiTableModel rashodiTableModel = new RashodiTableModel(korisniciServis.getSviZaposleni(), this.dto);
		this.tabelaRashoda = new JTable(rashodiTableModel);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		for (int i = 0; i < 5; i++) {
			tabelaRashoda.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		tabelaRashoda.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaRashoda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaRashoda.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaRashoda);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void postaviJug() {
		initPoljaVrednostima();
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.white);
		panel.add(new JLabel("Ukupni rashodi:"));
		panel.add(tfUkupanIznos);
		panel.add(new JLabel("Rashodi laboranti:"));
		panel.add(tfIznosLaboranti);
		panel.add(new JLabel("Rashodi tehničari:"));
		panel.add(tfIznosMedicinari);

		this.getContentPane().add(panel, BorderLayout.SOUTH);
	}

	private void initPoljaVrednostima() {
		tfUkupanIznos.setText(String.valueOf(getUkupneCene()));
		tfIznosLaboranti.setText(String.valueOf(getCeneZaZaposlenog(0)));
		tfIznosMedicinari.setText(String.valueOf(getCeneZaZaposlenog(1)));
		
		tfUkupanIznos.setEditable(false);
		tfIznosLaboranti.setEditable(false);
		tfIznosMedicinari.setEditable(false);
	}
	
	
	private double getUkupneCene() {
		int retVal = 0;
		for (int row = 0; row < tabelaRashoda.getRowCount(); row++) {
			RashodiTableModel model = (RashodiTableModel) tabelaRashoda.getModel();
			double plata = (double) model.getValueAt(row, 4);
			retVal += plata;
		}
		return retVal;
	}
	
	private double getCeneZaZaposlenog(int instanca) {
		int retVal = 0;
		for (int row = 0; row < tabelaRashoda.getRowCount(); row++) {
			RashodiTableModel model = (RashodiTableModel) tabelaRashoda.getModel();
			int id = (int) model.getValueAt(row, 0);
			Korisnik k = korisniciServis.getById(id);
			if (instanca == 0) {
				if (k instanceof Laborant) {
					double plata = (double) model.getValueAt(row, 4);
					retVal += plata;
				}
			}else {
				if (k instanceof MedicinskiTehničar) {
					double plata = (double) model.getValueAt(row, 4);
					retVal += plata;
				}
			}
		}
		return retVal;
	}
	

	private void postaviGrafik() {
		double procenatLaboranta = plataServis.getProcenatIznosaOdUkupnog(getUkupneCene(), getCeneZaZaposlenog(0));
		double procenatTehninar = plataServis.getProcenatIznosaOdUkupnog(getUkupneCene(), getCeneZaZaposlenog(1));
		GenericChart<PieChart> genericChart = new GrafikRashodi(procenatTehninar, procenatLaboranta);
		PieChart chart = genericChart.getChart();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JPanel chartPanel = new XChartPanel(chart);
		this.getContentPane().add(chartPanel, BorderLayout.WEST);
	}

}
