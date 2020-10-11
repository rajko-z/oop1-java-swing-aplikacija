package gui.admin.addEdit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.CenePodesavanja;
import net.miginfocom.swing.MigLayout;
import services.ValidatorServis;

public class EditCenePodesavanjaDijalog extends JDialog{

	private static final long serialVersionUID = -7546983399304617500L;
	
	private ValidatorServis validatorServis = new ValidatorServis();
	
	protected CenePodesavanja cenePodesavanja = CenePodesavanja.getInstance();
	protected double cenaBezVremena = cenePodesavanja.getDostavaDatum();
	protected double cenaSaVremenom = cenePodesavanja.getDostavaVreme();
	protected double cenaOsnova = cenePodesavanja.getOsnova();
	
	protected JLabel promeniCenuKucnePosete = new JLabel();
	protected JLabel promeniCenuSaVremonom = new JLabel();
	protected JLabel promeniCenuOsnove =  new JLabel();
	
	protected JTextField tfCenaBezVremena = new JTextField(20);
	protected JTextField tfCenaSaVremonom = new JTextField(20);
	protected JTextField tfCenaOsnove = new JTextField(20);
	
	
	
	protected JButton btnSacuvaj = new JButton("Sačuvaj");
	protected JButton btnOdustani = new JButton("Odustani");
	protected JButton btnKoeficijenti = new JButton("Koeficijenti");
	
	
	public EditCenePodesavanjaDijalog(JFrame roditelj, boolean modal) {
		super(roditelj, modal);
		this.setTitle("Promena cena poseta i koeficijenata");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		podesiCentar();
		podesiJug();
		
	}

	private void podesiCentar() {
		JPanel panelCentar = new JPanel(new MigLayout());
		
		setTextZaLabele();
		panelCentar.add(promeniCenuKucnePosete, "sg a, split 2");
		panelCentar.add(tfCenaBezVremena, "pushx, growx, wrap");
		panelCentar.add(promeniCenuSaVremonom, "sg a, split 2");
		panelCentar.add(tfCenaSaVremonom, "pushx, growx, wrap");
		panelCentar.add(promeniCenuOsnove, "sg a, split 2");
		panelCentar.add(tfCenaOsnove, "pushx, growx, wrap");
		panelCentar.add(new JLabel("<html>Promenite koeficijente<br>stručne spreme"), "sg a, split 2");
		panelCentar.add(btnKoeficijenti, "pushx, growx, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
		
		podesiPoljaVrednostima();
		
		
	}

	private void setTextZaLabele() {
		promeniCenuKucnePosete.setText("<html>Promeni cenu kućne posete<br>bez naznačenog vremena.<br>"
				+ "Sadašnja cena iznosi " + cenaBezVremena);
		promeniCenuSaVremonom.setText("<html>Promeni cenu kućne posete<br>sa naznačenim vremenom.<br>"
				+ "Sadašnja cena iznosi " + cenaSaVremenom);
		promeniCenuOsnove.setText("<html>Promeni cenu osnove.<br>Sadašnja osnova iznosi " + cenaOsnova);
	}
	
	private void podesiPoljaVrednostima() {
		tfCenaBezVremena.setText(String.valueOf(cenaBezVremena));
		tfCenaSaVremonom.setText(String.valueOf(cenaSaVremenom));
		tfCenaOsnove.setText(String.valueOf(cenaOsnova));
	}
	
	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnSacuvaj);
		panelJug.add(btnOdustani);
		this.getContentPane().add(panelJug, BorderLayout.SOUTH);
	}
	
	private void initAkcije() {
		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String osnovaStr = tfCenaOsnove.getText().trim();
				String cenaSaVremenomStr = tfCenaSaVremonom.getText().trim();
				String cenaBezVremenaStr = tfCenaBezVremena.getText().trim();
				if (proveriUnos(osnovaStr, cenaBezVremenaStr, cenaSaVremenomStr)) {
					int answer = JOptionPane.showConfirmDialog(null, generisPoruku(), "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						cenePodesavanja.setData(Double.parseDouble(osnovaStr), Double.parseDouble(cenaBezVremenaStr), Double.parseDouble(cenaSaVremenomStr));
						JOptionPane.showMessageDialog(null, "Podaci su uspešno ažurirani");
						EditCenePodesavanjaDijalog.this.dispose();
					}
				}
				
			}

			private Object generisPoruku() {
				StringBuilder sb = new StringBuilder();
				sb.append("Da li ste sigurni da želite da sačuvate date podatke:\n");
				sb.append("-----------------------------------------------------------------\n");
				sb.append("Cena osnove: " + tfCenaOsnove.getText().trim() + '\n' );
				sb.append("Cena kućne posete bez uračunatog vremena: "  + tfCenaBezVremena.getText().trim() + '\n');
				sb.append("Cena kućne posete sa uračunatim vremonom: " + tfCenaSaVremonom.getText().trim() + '\n');
				sb.append("-----------------------------------------------------------------\n");
				return sb.toString();
			}

			private boolean proveriUnos(String osnovaStr, String cenaBezVremenaStr, String cenaSaVremenomStr) {
				if (osnovaStr.equals("") | cenaBezVremenaStr.equals("") | cenaSaVremenomStr.equals("")) {
					JOptionPane.showMessageDialog(null, "Unesite sve potrebne podatke");
					return false;
				}
				else if (validatorServis.nijeBrojUOdgovarajucemRasponu(1,100000 , osnovaStr)) {
					JOptionPane.showMessageDialog(null, "Za osnovu morate uneti broj do 100000");
					return false;
				}
				else if (validatorServis.nijeBrojUOdgovarajucemRasponu(1, 5000, cenaBezVremenaStr) | 
						validatorServis.nijeBrojUOdgovarajucemRasponu(1, 5000, cenaSaVremenomStr)) {
					JOptionPane.showMessageDialog(null, "Za cene poseta, morate uneti vrednosti do 5000");
					return false;
				}
				return true;
				
			}
			
			
		});		
		
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditCenePodesavanjaDijalog.this.dispose();
			}
		});
		
		btnKoeficijenti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditKoeficijenti edit = new EditKoeficijenti(EditCenePodesavanjaDijalog.this, true);
				edit.setVisible(true);
			}
		});
		
		
		
	}
}
