package gui.admin.izvestaji;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.Nalaz;
import net.miginfocom.swing.MigLayout;
import services.DatumiServis;
import services.NalaziServis;
import utils.DTOselektovaniDaniIDatumi;
import utils.DatumLabelaFormater;

public class PrihodiRashodiDijalog extends JDialog{

	private static final long serialVersionUID = -1212967371402812716L;
	private DatumiServis datumiServis = new DatumiServis();
	private NalaziServis nalaziServis = new NalaziServis();
	
	
	protected JDatePickerImpl datePickerDatumPocetak;
	protected JDatePickerImpl datePickerDatumKraj;
	
	protected JRadioButton rbPon = new JRadioButton("PON");
	protected JRadioButton rbUto = new JRadioButton("UTO");
	protected JRadioButton rbSre = new JRadioButton("SRE");
	protected JRadioButton rbCet = new JRadioButton("ČET");
	protected JRadioButton rbPet = new JRadioButton("PET");
	protected JRadioButton rbSub = new JRadioButton("SUB");
	protected JRadioButton rbNed = new JRadioButton("NED");
	protected JRadioButton rbSve = new JRadioButton("SVI DANI");
	
	protected JButton btnPrikaziPrihode = new JButton("Prikaži prihode");
	protected JButton btnPrikaziRashode = new JButton("Prikaži rashode");
	
	public PrihodiRashodiDijalog(JFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("Prihodi i rashodi");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		initZapad();
	}

	private void initZapad() {
		podesiKalendar();
		
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
		zapad.add(new JLabel(" "), "span, wrap");
		zapad.add(new JLabel(" "), "split 3, sg a");
		zapad.add(btnPrikaziPrihode);
		zapad.add(btnPrikaziRashode);
		
		add(zapad, BorderLayout.CENTER);
	}

	private void podesiKalendar() {
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
		
		btnPrikaziPrihode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnos()) {
					LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
					LocalDate kraj = getSelectedDate(datePickerDatumKraj);
					List<DayOfWeek>dani = getSelektovaneDane();
					List<Nalaz>nalaziZaTabelu = nalaziServis.getNalaziUVremenskomPerioduSaDanima(pocetak, kraj, dani);
					if (nalaziZaTabelu.size() == 0) {
						JOptionPane.showMessageDialog(null, "U ovom periodu nije bilo obradjenih nalaza");
					}
					else {
						PrihodiNalaza p = new PrihodiNalaza(PrihodiRashodiDijalog.this, false, nalaziZaTabelu);
						p.setVisible(true);
					}
				}
			}
		});
		
		btnPrikaziRashode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnos()) {
					LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
					LocalDate kraj = getSelectedDate(datePickerDatumKraj);
					List<DayOfWeek>dani = getSelektovaneDane();
					DTOselektovaniDaniIDatumi dto = new DTOselektovaniDaniIDatumi(pocetak, kraj, dani);
					RashodiZaposlenih rashodi = new RashodiZaposlenih(PrihodiRashodiDijalog.this, false, dto);
					rashodi.setVisible(true);
				}
			}
		});
	}
	
	private boolean proveriUnos() {
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
		return true;
	}
	
	private List<DayOfWeek> getSelektovaneDane() {
		ArrayList<DayOfWeek>retList = new ArrayList<DayOfWeek>();
		if (rbSve.isSelected()) {
			return Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
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
	private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
		 Date datum = (Date) datePicker.getModel().getValue();
		 if (datum != null) {
			 return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 }
		 return null;
	}
	
	
	
}
