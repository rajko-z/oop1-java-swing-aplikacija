package gui.admin.addEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.GrupaAnaliza;
import gui.admin.AnalizeIGrupeDijalog;
import net.miginfocom.swing.MigLayout;
import services.AnalizeServis;

public class AddEditGrupaAnalize extends JDialog{

	private static final long serialVersionUID = 492564240420051726L;
	
	private boolean isEditDijalog = false;
	private GrupaAnaliza grupaAnaliza;
	private JDialog roditelj;
	private AnalizeServis analizeServis = new AnalizeServis();
	
	protected JButton btnSacuvaj = new JButton("Sačuvaj");
	protected JButton btnIzlaz = new JButton("Izlaz");
	protected JTextField tfNaziv = new JTextField(20);
	
	public AddEditGrupaAnalize(JDialog roditelj, boolean modal, GrupaAnaliza grupaAnaliza) {
		super(roditelj, modal);
		this.grupaAnaliza = grupaAnaliza;
		this.roditelj = roditelj;

		if (grupaAnaliza != null) {
			this.isEditDijalog = true;
		}
		if (isEditDijalog) {
			setTitle("Promena grupe analize");
		} else {
			setTitle("Dodavanje nove grupe analize");
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}
	
	private void initGUI() {
		JPanel centar = new JPanel(new MigLayout());
		
		centar.add(new JLabel("Naziv:"), "split 2, sg a");
		centar.add(tfNaziv, "pushx, growx, wrap");
		centar.add(new JLabel(" "), "span, wrap");
		centar.add(new JLabel(" "), "sg b, split 3");
		centar.add(btnSacuvaj);
		centar.add(btnIzlaz, "wrap");
		
		this.getContentPane().add(centar);

		if (isEditDijalog) {
			popuniPoljaPodacima();
		}
	}

	private void popuniPoljaPodacima() {
		tfNaziv.setText(grupaAnaliza.getNaziv());
	}

	private void initAkcije() {
		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String naziv = tfNaziv.getText().trim();
				if (proveriUnos(naziv)) {
					if (isEditDijalog) {
						analizeServis.editGrupa(grupaAnaliza, naziv);
					}else {
						analizeServis.kreirajNovuGrupu(naziv);
					}
					JOptionPane.showMessageDialog(null, "Podaci su uspešno sačuvani");
					((AnalizeIGrupeDijalog)roditelj).osveziTabelu();
					AddEditGrupaAnalize.this.dispose();
				}
					
			}
			private boolean proveriUnos(String naziv) {
				if (naziv.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli naziv.");
					return false;
				}
				if (naziv.length() > 30) {
					JOptionPane.showMessageDialog(null, "Naziv grupe mora da sadrži do 30 karaktera.");
				}
				if (analizeServis.grupaAnalizeSaZadatimImenomVecPostoji(naziv)) {
					JOptionPane.showMessageDialog(null, "Grupa analize sa zadatim imenom već postoji.\n"
							+ "Ukoliko ste ranije ovu grupu izbacili iz ponude,\n"
							+ "možete je vratiti odabirom opcije 'Vrati nazad obrisanu stavku'.");
					return false;
				}
				return true;
			}

		});

		btnIzlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditGrupaAnalize.this.dispose();
			}
		});
	}
	
	
}
