package gui.admin.addEdit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.StručnaSprema;
import gui.models.StručnaSpremaTableModel;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import services.StručnaSpremaServis;
import services.ValidatorServis;

public class EditKoeficijenti extends JDialog{

	private static final long serialVersionUID = -7576614277116472845L;
	protected JTable tabelaSprema = new JTable(new StručnaSpremaTableModel(RepositoryFactory.getInstance().getStručnaSpremaRepo().getEntitetiList()));
	
	public EditKoeficijenti(JDialog roditelj, boolean modal) {
		super(roditelj, modal);
		setTitle("Promena koeficijenata");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1100,250));
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		initTabela();
	}

	private void initTabela() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		tabelaSprema.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaSprema.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaSprema.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaSprema);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void initAkcije() {
		tabelaSprema.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = tabelaSprema.getSelectedRow();
				if (selectedRow != -1) {
					int id = Integer.parseInt(tabelaSprema.getValueAt(selectedRow, 0).toString());
					StručnaSprema sprema = (StručnaSprema) RepositoryFactory.getInstance().getStručnaSpremaRepo().getEntityByIdList(id);
					KoeficijentRowDijalog k = new KoeficijentRowDijalog(EditKoeficijenti.this, true, sprema);
					k.setVisible(true);
				}
			}
		});
	}
	
	protected void refreshTable() {
		StručnaSpremaTableModel s = (StručnaSpremaTableModel) tabelaSprema.getModel();
		s.fireTableDataChanged();
	}
	
	
	
	class KoeficijentRowDijalog extends JDialog {

		private static final long serialVersionUID = 7882681902422322748L;
		private ValidatorServis validatorServis = new ValidatorServis();
		private StručnaSprema sprema;
		private JDialog roditelj;
		private StručnaSpremaServis spremaServis = new StručnaSpremaServis();
		
		protected JTextField tfSprema = new JTextField(30);
		protected JTextField tfKoeficijent = new JTextField(30);
		protected JButton btnSacuvaj = new JButton("Sačuvaj");
		protected JButton btnOdustani = new JButton("Odustani");
		
		public KoeficijentRowDijalog(JDialog roditelj, boolean modal, StručnaSprema sprema) {
			super(roditelj, modal);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.sprema = sprema;
			this.roditelj = roditelj;
			buildgui();
			this.pack();
			this.setLocationRelativeTo(null);
			akcija();
		}
		private void buildgui() {
			JPanel panelCentar = new JPanel(new MigLayout());
			
			panelCentar.add(new JLabel("Sprema:"), "split 2, sg a");
			panelCentar.add(tfSprema, "pushx, growx, wrap");
			panelCentar.add(new JLabel("Koeficijent:"), "split 2, sg a");
			panelCentar.add(tfKoeficijent, "pushx, growx, wrap");
			
			tfSprema.setText(this.sprema.getOpis());
			tfSprema.setEditable(false);
			tfKoeficijent.setText(String.valueOf(this.sprema.getKoeficijent()));
			
			KoeficijentRowDijalog.this.getContentPane().add(panelCentar, BorderLayout.CENTER);
			
			JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelJug.add(btnSacuvaj);
			panelJug.add(btnOdustani);
			KoeficijentRowDijalog.this.getContentPane().add(panelJug, BorderLayout.SOUTH);
		}
		
		private void akcija() {
			btnOdustani.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					KoeficijentRowDijalog.this.dispose();
				}
			});
			btnSacuvaj.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (proveriUnos()) {
						spremaServis.setKoeficijent(Double.parseDouble(tfKoeficijent.getText().trim()), sprema);
						JOptionPane.showMessageDialog(null, "Koeficijent je uspešno sačuvan");
						((EditKoeficijenti)roditelj).refreshTable();
						KoeficijentRowDijalog.this.dispose();
					}
				}
				
				private boolean proveriUnos() {
					if (tfKoeficijent.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Unesite koeficijent");
						return false;
					}
					if (validatorServis.nijeBrojUOdgovarajucemRasponu(1, 20, tfKoeficijent.getText().trim())) {
						JOptionPane.showMessageDialog(null, "Koeficijent mora biti u rasponu od 1 do 20");
						return false;
					}
					return true;
				}
			});
		}
	}
	
	
	
	
	
	
	
	
	
	
}
