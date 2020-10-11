package gui.admin.izvestaji;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.GrupaAnaliza;
import entity.Nalaz;
import entity.korisnici.Pacijent;
import gui.models.PacijentiPrihodiTableModel;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import res.ResourceLoader;
import services.DatumiServis;
import services.NalaziServis;
import services.PacijentServis;
import services.ZahteviServis;
import utils.CheckBoxListItemAnaliza;
import utils.CheckBoxListRendererAnaliza;
import utils.DatumLabelaFormater;

public class PacijentiIzvestaj extends JDialog{

	private static final long serialVersionUID = -1982304562254160410L;
	
	private DatumiServis datumiServis = new DatumiServis();
	private NalaziServis nalaziServis = new NalaziServis();
	private PacijentServis pacijentServis = new PacijentServis();
	
	protected JDatePickerImpl datePickerDatumPocetak;
	protected JDatePickerImpl datePickerDatumKraj;
	protected JButton btnPretraga = new JButton("Pretraži");
	protected JButton btnPrikaziSve = new JButton("Prikaz svih pacijenata");
	protected JToolBar mainToolbar = new JToolBar();
	
	protected JTable tblNalazaPacijenta = new JTable();
	
	protected JList<CheckBoxListItemAnaliza>listaGrupaAnaliza;
	protected JCheckBox ckSveAnalize = new JCheckBox("Sve analize");
	
	
	
	public PacijentiIzvestaj(JFrame roditelj, boolean modal) {
		super(roditelj, modal);
		setTitle("Pacijenti prihodi");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1400,500));
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		initZapad();
		podesiIconuButtonaIToolBar();
		initTable();
	}
	
	private void initZapad() {
		podesiKalendar();
		podesiListuGrupaAnaliza();
		
		JScrollPane srcAnalize = new JScrollPane(listaGrupaAnaliza);
		srcAnalize.setPreferredSize(new Dimension(200,300));
		
		JPanel zapad = new JPanel(new MigLayout());
		zapad.add(new JLabel("Izaberite početni datum: "), "split 2, sg a");
		zapad.add(datePickerDatumPocetak,"wrap");
		zapad.add(new JLabel("Izaberite krajnji datum: "), "split 2, sg a");
		zapad.add(datePickerDatumKraj,"wrap");
		
		zapad.add(new JLabel("<html>Izaberite grupe analiza<br>ili označite 'Sve analize':"), "split 3,top, sg a");
		zapad.add(srcAnalize);
		zapad.add(ckSveAnalize, "wrap");
		
		zapad.add(new JLabel(" "), "split 2, sg a");
		zapad.add(btnPretraga);
		this.getContentPane().add(zapad, BorderLayout.WEST);
	}
	
	private void initTable() {
		setTableData();
		JPanel panel = new JPanel(new GridLayout(1,1));
		tblNalazaPacijenta.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblNalazaPacijenta.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblNalazaPacijenta.getTableHeader().setReorderingAllowed(false);
		tblNalazaPacijenta.setAutoCreateRowSorter(true);
		JTableHeader th = tblNalazaPacijenta.getTableHeader();
		th.setPreferredSize(new Dimension(50, 45));
		JScrollPane srcPan = new JScrollPane(tblNalazaPacijenta);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void setTableData() {
		List<Pacijent>pacijenti = pacijentServis.getPacijenti();
		List<Nalaz>gotoviNalazi = nalaziServis.getSviZavrseniNalazi();
		tblNalazaPacijenta.setModel(new PacijentiPrihodiTableModel(pacijenti, gotoviNalazi));
	}
	
	private void podesiListuGrupaAnaliza() {
		ArrayList<CheckBoxListItemAnaliza> items = new ArrayList<CheckBoxListItemAnaliza>();
		for (GrupaAnaliza ga: RepositoryFactory.getInstance().getGrupeAnalizaRepo().getEntitetiList()) {
			items.add(new CheckBoxListItemAnaliza(ga));
		}
		
		CheckBoxListItemAnaliza[] dataForlist = new CheckBoxListItemAnaliza[items.size()];
		listaGrupaAnaliza = new JList<CheckBoxListItemAnaliza>(items.toArray(dataForlist));
		listaGrupaAnaliza.setCellRenderer(new CheckBoxListRendererAnaliza());
		listaGrupaAnaliza.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void podesiIconuButtonaIToolBar() {
		ImageIcon pretragaIcon = ResourceLoader.getImageIcon("document.png");
		ImageIcon pretragaIconResized = new ImageIcon(pretragaIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnPretraga.setIcon(pretragaIconResized);
		btnPretraga.setToolTipText("Pretraži");
		
		ImageIcon prikaziSveIcon = ResourceLoader.getImageIcon("pacijenti.png");
		ImageIcon prikaziSveIconResized = new ImageIcon(prikaziSveIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
		btnPrikaziSve.setIcon(prikaziSveIconResized);
		btnPrikaziSve.setToolTipText("Prikaži sve pacijente");
		mainToolbar.add(btnPrikaziSve);
		
		mainToolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainToolbar.add(btnPrikaziSve);
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
	}

	private void  podesiKalendar() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePickerDatumPocetak = new JDatePickerImpl(datePanel, new DatumLabelaFormater());
		
		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		UtilDateModel model2 = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		datePickerDatumKraj = new JDatePickerImpl(datePanel2, new DatumLabelaFormater());
	}
	
	
	private void initAkcije() {
		
		listaGrupaAnaliza.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent event) {
	            @SuppressWarnings("unchecked")
				JList<CheckBoxListItemAnaliza> list = (JList<CheckBoxListItemAnaliza>) event.getSource();
	 
	            int index = list.locationToIndex(event.getPoint());
	            CheckBoxListItemAnaliza item = (CheckBoxListItemAnaliza) list.getModel().getElementAt(index);
	            item.setSelected(!item.isSelected());
	            list.repaint(list.getCellBounds(index, index));
	         }
	      });	
		
		
		btnPrikaziSve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTableData();
			}
		});
		
		btnPretraga.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnosPodataka()) {
					LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
					LocalDate kraj = getSelectedDate(datePickerDatumKraj);
					List<Nalaz>nalazi = nalaziServis.getNalaziUVremenskomPeriodu(pocetak, kraj);
				
					if (getSelektovaneAnalize().size() != 0) {
						nalazi = nalaziServis.filtrirajPoAnalizama(getSelektovaneAnalize(), nalazi);
					}
					
					if (nalazi.size() == 0) {
						JOptionPane.showMessageDialog(null, "Trenutno nema obradjenih nalaza koji zadovoljavaju unete kriterijume");
					}
					else {
						List<Pacijent>pacijentiZaModel = getPacijentiOdFiltriranihNalaza(nalazi);
						tblNalazaPacijenta.setModel(new PacijentiPrihodiTableModel(pacijentiZaModel, nalazi));
					}
				}
			}

			private boolean proveriUnosPodataka() {
				if (getSelectedDate(datePickerDatumPocetak) == null | getSelectedDate(datePickerDatumKraj) == null) {
					JOptionPane.showMessageDialog(null, "Niste selektovali datume.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
				LocalDate kraj = getSelectedDate(datePickerDatumKraj);
				
				if (!datumiServis.datumJeProslost(pocetak) | !datumiServis.datumJeProslost(kraj)) {
					JOptionPane.showMessageDialog(null, "Za početni i kranji datum morate izabrati neki od prošlih datuma.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(pocetak.compareTo(kraj) >= 0) {
					JOptionPane.showMessageDialog(null, "Početni datum mora biti starije od krajnjeg.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (getSelektovaneAnalize().size() == 0 & !ckSveAnalize.isSelected()) {
					JOptionPane.showMessageDialog(null, "Izaberite grupe analiza u zahtevu\nili obeležite polje 'Sve analize'.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
			}
			
		});
	}	
	
	private List<Pacijent> getPacijentiOdFiltriranihNalaza(List<Nalaz> nalazi) {
		List<Pacijent> retList = new ArrayList<Pacijent>();
		for (Nalaz n: nalazi) {
			Pacijent p = n.getZahtev().getPacijent();
			if ( ! retList.contains(p)) {
				retList.add(p);
			}
		}
		return retList;
	}

	private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
		 Date datum = (Date) datePicker.getModel().getValue();
		 if (datum != null) {
			 return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 }
		 return null;
	}
	
	private List<GrupaAnaliza>getSelektovaneAnalize(){
		ArrayList<GrupaAnaliza> retList = new ArrayList<>();
		ListModel model = listaGrupaAnaliza.getModel();
		for(int i=0; i < model.getSize(); i++){
			Object a = model.getElementAt(i);
			CheckBoxListItemAnaliza item = (CheckBoxListItemAnaliza)a;
			if (item.isSelected()) {
				retList.add(item.getGrupaAnaliza());
			}
		}
		return retList;
	}

}
