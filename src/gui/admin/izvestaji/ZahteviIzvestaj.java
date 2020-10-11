package gui.admin.izvestaji;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.ProtectionDomain;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.GrupaAnaliza;
import entity.StanjeZahteva;
import entity.Zahtev;
import gui.models.ZahtevTableModel;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import res.ResourceLoader;
import services.DatumiServis;
import services.ZahteviServis;
import utils.CheckBoxListItemAnaliza;
import utils.CheckBoxListRendererAnaliza;
import utils.DatumLabelaFormater;

public class ZahteviIzvestaj extends JDialog{

	private static final long serialVersionUID = -3722957964275425967L;
	private DatumiServis datumiServis = new DatumiServis();
	private ZahteviServis zahteviServis = new ZahteviServis();
	
	protected JDatePickerImpl datePickerDatumPocetak;
	protected JDatePickerImpl datePickerDatumKraj;
	protected JButton btnPretraga = new JButton("Pretraži");
	
	protected JTable tabelaZahteva = new JTable();
	
	protected JRadioButton rbPon = new JRadioButton("PON");
	protected JRadioButton rbUto = new JRadioButton("UTO");
	protected JRadioButton rbSre = new JRadioButton("SRE");
	protected JRadioButton rbCet = new JRadioButton("ČET");
	protected JRadioButton rbPet = new JRadioButton("PET");
	protected JRadioButton rbSub = new JRadioButton("SUB");
	protected JRadioButton rbNed = new JRadioButton("NED");
	protected JRadioButton rbSve = new JRadioButton("SVI DANI");
	
	protected JRadioButton rbTriPlus = new JRadioButton("3+");
	protected JRadioButton rbCetiriPlus = new JRadioButton("4+");
	protected JRadioButton rbPetPlus = new JRadioButton("5+");
	protected JRadioButton rbKolicinaNeodredjeno = new JRadioButton("NEODREDJENO");
	protected ButtonGroup groupKolicina = new ButtonGroup();
	
	protected JList<CheckBoxListItemAnaliza>listaGrupaAnaliza;
	protected JCheckBox ckSveAnalize = new JCheckBox("Sve analize");
	
	protected JList<StanjeZahteva> listaStanje;
	protected JCheckBox ckSvaStanja = new JCheckBox("Sva stanja");
	
	protected JRadioButton rbSaDostavom = new JRadioButton("SA");
	protected JRadioButton rbBezDostave = new JRadioButton("BEZ");
	protected JRadioButton rbDostavaNeodredjeno = new JRadioButton("NEODREDJENO");
	protected ButtonGroup groupDostava = new ButtonGroup();
	
	
	public ZahteviIzvestaj(JFrame roditelj, boolean modal) {
		super(roditelj, modal);
		setTitle("Zahtevi izveštaj");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1600,750));
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		initZapad();
		initTable();
	}
	
	private void initZapad() {
		podesiKalendar();
		dodajKolicineUGrupu();
		dodajDostaveUGrupu();
		podesiListuGrupaAnaliza();
		podesiListuStanje();
		podesiIconuButtona();
		
		JScrollPane srcAnalize = new JScrollPane(listaGrupaAnaliza);
		srcAnalize.setPreferredSize(new Dimension(200,300));
		JScrollPane srcStanja = new JScrollPane(listaStanje);
		srcStanja.setPreferredSize(new Dimension(200,200));
		
		
		JPanel zapad = new JPanel(new MigLayout());
		zapad.add(new JLabel("Izaberite početni datum: "), "split 2, sg a");
		zapad.add(datePickerDatumPocetak,"wrap");
		zapad.add(new JLabel("Izaberite krajnji datum: "), "split 2, sg a");
		zapad.add(datePickerDatumKraj,"wrap");
		
		zapad.add(new JLabel("Izaberite dane:"),"sg a, split 5");
		zapad.add(rbPon);
		zapad.add(rbUto);
		zapad.add(rbSre);
		zapad.add(rbCet, "wrap");
		zapad.add(new JLabel(" "), "sg a, split 5");
		zapad.add(rbPet);
		zapad.add(rbSub);
		zapad.add(rbNed);
		zapad.add(rbSve, "wrap");
		
		zapad.add(new JLabel("<html>Označite broj<br>analiza u zahtevu<br>ili polje NEODREDJENO"), "sg a, split 5");
		zapad.add(rbTriPlus);
		zapad.add(rbCetiriPlus);
		zapad.add(rbPetPlus);
		zapad.add(rbKolicinaNeodredjeno,"wrap");
		
		zapad.add(new JLabel("<html>Izaberite grupe analiza<br>ili označite 'Sve analize':"), "split 3,top, sg a");
		zapad.add(srcAnalize);
		zapad.add(ckSveAnalize, "wrap 5px");
		
		zapad.add(new JLabel("<html>Izaberite stanje zahteva<br>ili označite 'Sva stanja':"), "split 3,top, sg a");
		zapad.add(srcStanja);
		zapad.add(ckSvaStanja, "wrap");
		
		zapad.add(new JLabel("<html>Označite da li zahtev<br>sa kućnom posetom ili bez<br>ili polje NEODREDJENO"), "sg a, split 4");
		zapad.add(rbSaDostavom);
		zapad.add(rbBezDostave);
		zapad.add(rbDostavaNeodredjeno, "wrap");
		
		zapad.add(new JLabel(" "), "split 2, sg a");
		zapad.add(btnPretraga);
		this.getContentPane().add(zapad, BorderLayout.WEST);
	}
	
	private void initTable() {
		JPanel panel = new JPanel(new GridLayout(1,1));
		tabelaZahteva.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaZahteva.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaZahteva.getTableHeader().setReorderingAllowed(false);
		JTableHeader th = tabelaZahteva.getTableHeader();
		th.setPreferredSize(new Dimension(50, 45));
		JScrollPane srcPan = new JScrollPane(tabelaZahteva);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void dodajKolicineUGrupu() {
		groupKolicina.add(rbTriPlus);
		groupKolicina.add(rbCetiriPlus);
		groupKolicina.add(rbPetPlus);
		groupKolicina.add(rbKolicinaNeodredjeno);
	}
	private void dodajDostaveUGrupu() {
		groupDostava.add(rbSaDostavom);
		groupDostava.add(rbBezDostave);
		groupDostava.add(rbDostavaNeodredjeno);
	}
	
	private void podesiListuStanje() {
		List<StanjeZahteva> items = RepositoryFactory.getInstance().getStanjeZahtevaRepo().getEntitetiList();
		StanjeZahteva[] dataForList = new StanjeZahteva[items.size()];
		listaStanje = new JList<StanjeZahteva>(items.toArray(dataForList));
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
	
	private void podesiIconuButtona() {
		ImageIcon pretragaIcon = ResourceLoader.getImageIcon("document.png");
		ImageIcon pretragaIconResized = new ImageIcon(pretragaIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnPretraga.setIcon(pretragaIconResized);
		btnPretraga.setToolTipText("Pretraži");
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
		
		rbSve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (rbSve.isSelected()) {
					rbPon.setSelected(false); rbUto.setSelected(false); rbSre.setSelected(false);
					rbCet.setSelected(false); rbPet.setSelected(false); rbSub.setSelected(false);
					rbNed.setSelected(false);
				}
			}
		});
		
		
		btnPretraga.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnosPodataka()) {
					LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
					LocalDate kraj = getSelectedDate(datePickerDatumKraj);
					List<DayOfWeek>dani = getSelektovaneDane();
					List<Zahtev>zahtevi = zahteviServis.getZahteviUVremenskomPerioduSaDanima(pocetak, kraj, dani);
					
					if (getSelektovanuKolicinu() != 0) {
						zahtevi = zahteviServis.filtrirajPoKolicini(getSelektovanuKolicinu(), zahtevi);
					}
					if (getSelektovaneAnalize().size() != 0) {
						zahtevi = zahteviServis.filtrirajPoAnalizama(getSelektovaneAnalize(), zahtevi);
					}
					if (getSelektovanoStanje() != null) {
						zahtevi = zahteviServis.filtrirajPoStanju(getSelektovanoStanje(), zahtevi);
					}
					
					if (!rbDostavaNeodredjeno.isSelected()) {
						zahtevi = zahteviServis.filtrirajPoKucnojPoseti(oznacenaJekucnaPoseta(), zahtevi);
					}
					
					if (zahtevi.size() == 0) {
						JOptionPane.showMessageDialog(null, "Trenutno nema zahteva koji zadovoljavaju unete kriterijume");
					}
					else {
						tabelaZahteva.setModel(new ZahtevTableModel(zahtevi));
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
				if ( ! (rbPon.isSelected() | rbUto.isSelected() | rbSre.isSelected() | rbCet.isSelected() | rbPet.isSelected() | rbSub.isSelected() | rbNed.isSelected() | rbSve.isSelected())){
					JOptionPane.showMessageDialog(null, "Izaberite dane.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if ( ! (rbTriPlus.isSelected() | rbCetiriPlus.isSelected() | rbPetPlus.isSelected() | rbKolicinaNeodredjeno.isSelected())) {
					JOptionPane.showMessageDialog(null, "Izaberite količinu analiza u zahtevu\nili neodredjeno ako vam nije važno.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (getSelektovaneAnalize().size() == 0 & !ckSveAnalize.isSelected()) {
					JOptionPane.showMessageDialog(null, "Izaberite grupe analiza u zahtevu\nili obeležite polje 'Sve analize'.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (listaStanje.getSelectedIndex() == -1 & !ckSvaStanja.isSelected()) {
					JOptionPane.showMessageDialog(null, "Izaberite stanje zahteva\nili obeležite polje 'Sva stanja'.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (!(rbSaDostavom.isSelected() | rbBezDostave.isSelected() | rbDostavaNeodredjeno.isSelected())) {
					JOptionPane.showMessageDialog(null, "Označite kućnu posetu\nili polje NEODREDJENO.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
			}
			
		});
	}
	
	
	private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
		 Date datum = (Date) datePicker.getModel().getValue();
		 if (datum != null) {
			 return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 }
		 return null;
	}
	
	private List<DayOfWeek> getSelektovaneDane() {
		ArrayList<DayOfWeek>retList = new ArrayList<DayOfWeek>();
		if (rbSve.isSelected()) {
			return datumiServis.getDaniUNedelji();
		}
		if (rbPon.isSelected()) {
			retList.add(DayOfWeek.MONDAY);
		}
		if (rbUto.isSelected()) {
			retList.add(DayOfWeek.TUESDAY);
		}
		if (rbSre.isSelected()) {
			retList.add(DayOfWeek.WEDNESDAY);
		}
		if (rbCet.isSelected()) {
			retList.add(DayOfWeek.THURSDAY);
		}
		if (rbPet.isSelected()) {
			retList.add(DayOfWeek.FRIDAY);
		}
		if (rbSub.isSelected()) {
			retList.add(DayOfWeek.SATURDAY);
		}
		if (rbNed.isSelected()) {
			retList.add(DayOfWeek.SUNDAY);
		}
		return retList;
	}
	
	private int getSelektovanuKolicinu() {
		if (rbTriPlus.isSelected()) {
			return 3;
		}else if(rbCetiriPlus.isSelected()) {
			return 4;
		}else if(rbPetPlus.isSelected()) {
			return 5;
		}return 0;
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
	
	private StanjeZahteva getSelektovanoStanje() {
		if (listaStanje.getSelectedIndex() != -1 & !ckSvaStanja.isSelected()) {
			return listaStanje.getSelectedValue();
		}
		return null;
	}
	private boolean oznacenaJekucnaPoseta() {
		if (rbSaDostavom.isSelected()) {
			return true;
		}
		return false;
	}
	
}
