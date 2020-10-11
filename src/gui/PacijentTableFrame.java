package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entity.korisnici.Pacijent;
import gui.medicinar.AddEditPacijentDijalog;
import gui.medicinar.RegistrujPacijentaDijalog;
import gui.models.PacijentTableModel;
import repositories.RepositoryFactory;
import res.ResourceLoader;
import services.PacijentServis;


public class PacijentTableFrame extends JDialog {
	private static final long serialVersionUID = -1519233609633445024L;
	private JTable tablePacijenti = new JTable(new PacijentTableModel(RepositoryFactory.getInstance().getPacijentRepo().getEntitetiList()));

	protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnRegister = new JButton("Registruj pacijenta");
	protected JButton btnEdit = new JButton("Izmena podataka");
	protected JButton btnAddUser = new JButton("Dodaj pacijenta");
	protected JTextField tfSearch = new JTextField(30);
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	private PacijentServis pacijentServis = new PacijentServis();
	
	
	public PacijentTableFrame(JFrame parentFrame, boolean modal) {
		super(parentFrame, modal);
		setSize(1800,400);
		setTitle("Pacijenti");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
		initAkcije();
	}
	
	
	private void initGUI() {
		podesavanjeToolBara();
		podesavanjeTabele();
		podesavanjePretrage();
	}
	
	private void podesavanjeToolBara() {
		ImageIcon registerPacijent = ResourceLoader.getImageIcon("registerUser.png");
		ImageIcon resizedRegisterPacijent = new ImageIcon(registerPacijent.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnRegister.setIcon(resizedRegisterPacijent);
		btnRegister.setToolTipText("Registruj novog pacijenta");
		mainToolbar.add(btnRegister);
		
		ImageIcon addPacijent = ResourceLoader.getImageIcon("plus.png");
		ImageIcon resizedAddPacijent = new ImageIcon(addPacijent.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnAddUser.setIcon(resizedAddPacijent);
		btnAddUser.setToolTipText("Dodaj u sistem bez registracije");
		mainToolbar.add(btnAddUser);
		
		ImageIcon editPacijent = ResourceLoader.getImageIcon("editUser.png");
		ImageIcon resizedEditPacijent = new ImageIcon(editPacijent.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnEdit.setIcon(resizedEditPacijent);
		btnEdit.setToolTipText("Promeni podatke");
		mainToolbar.add(btnEdit);
		
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
	}
	
	private void podesavanjeTabele() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		tablePacijenti.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablePacijenti.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePacijenti.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) tablePacijenti.getModel());
		tablePacijenti.setRowSorter(tableSorter);
		JScrollPane srcPan = new JScrollPane(tablePacijenti);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void podesavanjePretrage() {
		JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.CENTER));		
		pSearch.setBackground(Color.LIGHT_GRAY);
		pSearch.add(new JLabel("Pretraga:"));
		pSearch.add(tfSearch);
		add(pSearch, BorderLayout.SOUTH);
	}
	
	
	private void initAkcije() {
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PacijentTableFrame.this.dispose();
				PacijentTableFrame.this.setVisible(false);
				super.windowClosing(e);
			}
		});
		
		tfSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (tfSearch.getText().trim().length() == 0) {
				     tableSorter.setRowFilter(null);
				  } else {
					  tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
				  }
			}
		});
		
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistrujPacijentaDijalog registerPacijentDijalog = new RegistrujPacijentaDijalog(PacijentTableFrame.this, true);
				registerPacijentDijalog.setVisible(true);
				
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tablePacijenti.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati pacijenta iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else{
					int id = Integer.parseInt(tablePacijenti.getValueAt(selectedRow, 0).toString());
					Pacijent p = pacijentServis.getById(id);
					if (p != null){
						AddEditPacijentDijalog editPacijentDijalog = new AddEditPacijentDijalog(PacijentTableFrame.this, true, p);
						editPacijentDijalog.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "Pacijent nije pronadjen", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnAddUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditPacijentDijalog editPacijentDijalog = new AddEditPacijentDijalog(PacijentTableFrame.this, true, null);
				editPacijentDijalog.setVisible(true);
			}
		});
		
		
	}
	
	public void osveziTabelu() {
		PacijentTableModel pm = (PacijentTableModel)this.tablePacijenti.getModel();
		pm.fireTableDataChanged();
	}

}
