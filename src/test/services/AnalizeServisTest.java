package test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.AnalizaPopust;
import entity.GrupaAnaliza;
import entity.GrupaPopust;
import entity.PosebnaAnaliza;
import repositories.RepositoryFactory;
import services.AnalizeServis;

public class AnalizeServisTest {
	
	static AnalizeServis analizeServis;
	static GrupaAnaliza grupa1;
	static GrupaAnaliza grupa2;
	static GrupaAnaliza grupa3;
	static GrupaAnaliza grupa4;	
	
	static PosebnaAnaliza posebnaAnaliza1;
	static PosebnaAnaliza posebnaAnaliza2;
	static PosebnaAnaliza posebnaAnaliza3;
	static PosebnaAnaliza posebnaAnaliza4;
	
	static List<GrupaAnaliza>grupe;
	static List<PosebnaAnaliza>analize;
	
	@BeforeClass
	public static void setBeforeClass() {
		grupe = new ArrayList<GrupaAnaliza>();
		analize = new ArrayList<PosebnaAnaliza>();
	}
	
	
	@Before
    public void setBeforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
		analizeServis = new AnalizeServis();
       
		grupa1 = new GrupaAnaliza(1, "grupa1", true);
		grupa2 = new GrupaAnaliza(2, "grupa2", true);
		grupa3 = new GrupaAnaliza(3, "grupa3", false);
		grupa4 = new GrupaAnaliza(4, "grupa4", true);
       
		posebnaAnaliza1 = new PosebnaAnaliza(1, "naziv1", grupa1, 1000, 10.0, 20.0, "g/L", true);
		posebnaAnaliza2 = new PosebnaAnaliza(2, "naziv2", grupa2, 2000, 10.0, 20.0, "g/L", true);
		posebnaAnaliza3 = new PosebnaAnaliza(4, "naziv3", grupa3, 1000, 10.0, 20.0, "g/L", true);
		posebnaAnaliza4 = new PosebnaAnaliza(5, "naziv4", grupa4, 1000, 10.0, 20.0, "g/L", false);
	
		grupe.add(grupa1);
		grupe.add(grupa2);
		grupe.add(grupa3);
		grupe.add(grupa4);
		
		analize.add(posebnaAnaliza1);
		analize.add(posebnaAnaliza2);
		analize.add(posebnaAnaliza3);
		analize.add(posebnaAnaliza4);
    }
	
	@After
	public void setAfterTest() {
		analize.clear();
		grupe.clear();
	}
	
	
	@Test
	public void getValidneGrupeAnalizaNaziviTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().addAll(grupe);
		
		List<String>expected = Arrays.asList(grupa1.getNaziv(), grupa2.getNaziv(), grupa4.getNaziv());
		List<String>actual = analizeServis.getValidneGrupeAnalizaNazivi();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void getPosebnaAnalizaByIdTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiMap().put(1, posebnaAnaliza1);
		rp.getPosebnaAnalizaRepo().getEntitetiMap().put(2, posebnaAnaliza2);
		rp.getPosebnaAnalizaRepo().getEntitetiMap().put(3, posebnaAnaliza3);
		
		PosebnaAnaliza expected = posebnaAnaliza2;
		PosebnaAnaliza actual = analizeServis.getPosebnaAnalizaById(4);
		assertTrue(actual == null);
		
		actual = analizeServis.getPosebnaAnalizaById(2);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getGrupaAnalizeByIdTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiMap().put(1, grupa1);
		rp.getGrupeAnalizaRepo().getEntitetiMap().put(2, grupa2);
		rp.getGrupeAnalizaRepo().getEntitetiMap().put(3, grupa3);
		rp.getGrupeAnalizaRepo().getEntitetiMap().put(4, grupa4);
		
		GrupaAnaliza expected = grupa3;
		GrupaAnaliza actual = analizeServis.getGrupaAnalizeById(10);
		assertTrue(actual == null);
		
		actual = analizeServis.getGrupaAnalizeById(3);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getValidneAnalizeTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiList().addAll(analize);
		
		List<PosebnaAnaliza>expected = Arrays.asList(posebnaAnaliza1, posebnaAnaliza2, posebnaAnaliza3);
		List<PosebnaAnaliza>actual = analizeServis.getValidneAnalize();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void getObrisaneAnalizeTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiList().addAll(analize);
		
		List<PosebnaAnaliza>expected = Arrays.asList(posebnaAnaliza4);
		List<PosebnaAnaliza>actual = analizeServis.getObrisaneAnalize();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void getValidneGrupeAnalizaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().addAll(grupe);
		
		List<GrupaAnaliza>expected = Arrays.asList(grupa1, grupa2, grupa4);
		List<GrupaAnaliza>actual = analizeServis.getValidneGrupeAnaliza();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	
	@Test
	public void getObrisaneGrupeAnalzia() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().addAll(grupe);
		
		List<GrupaAnaliza>expected = Arrays.asList(grupa3);
		List<GrupaAnaliza>actual = analizeServis.getObrisaneGrupeAnaliza();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	
	@Test
	public void obrisiAnalizuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiList().add(posebnaAnaliza1);
		
		assertTrue(rp.getPosebnaAnalizaRepo().getEntitetiList().size() == 1);
		assertTrue(posebnaAnaliza1.isValidna());
		
		analizeServis.obrisiAnalizu(posebnaAnaliza1);
		assertFalse(posebnaAnaliza1.isValidna());
	}
	
	@Test
	public void obrisiGrupuAnalizaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().add(grupa1);
		
		assertTrue(posebnaAnaliza1.isValidna());
		
		analizeServis.obrisiGrupuAnalize(grupa1);
		assertFalse(grupa1.isValidna());
	}
	
	@Test
	public void editAnalizaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiList().add(posebnaAnaliza1);
		
		analizeServis.editAnaliza(posebnaAnaliza1, "nov naziv", grupa1, 2000.0, 1.0 , 2.0, "mere");
		assertTrue("naziv nije promenjen!", posebnaAnaliza1.getNaziv().equals("nov naziv"));
		assertTrue("grupa nije promenjena", posebnaAnaliza1.getGrupaAnaliza().equals(grupa1));
		assertTrue("cena nije promenjen!", posebnaAnaliza1.getCena() == 2000.0);
		assertTrue("donja ref vrednost nije promenjena!", posebnaAnaliza1.getDonjaRefVrednost() == 1.0);
		assertTrue("gornja ref vrednost nije promenjen!", posebnaAnaliza1.getGornjaRefVrednost() == 2.0);
		assertTrue("jedinica mere nije promenjena!", posebnaAnaliza1.getJedinicnaVrednost().equals("mere"));
	}
	
	
	@Test
	public void kreirajNovuAnalizuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiList().add(posebnaAnaliza1);
		
		PosebnaAnaliza expected = new PosebnaAnaliza(2, "nova", grupa4, 1000, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza actual = analizeServis.kreirajNovuAnalizu("nova", grupa4, 1000, 10.0, 20.0, "g/L");
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void grupaAnalizeSaZadatimImenomVecPostojiTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().addAll(grupe);
		
		assertTrue(analizeServis.grupaAnalizeSaZadatimImenomVecPostoji("grupa1"));
		assertFalse(analizeServis.grupaAnalizeSaZadatimImenomVecPostoji("fdsafdsf"));
	}
	
	@Test
	public void analizaSaUnetimImenomVecPostojiTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getPosebnaAnalizaRepo().getEntitetiList().addAll(analize);
		
		assertTrue(analizeServis.analizaSaUnetimImenomVecPostoji("naziv1"));
		assertFalse(analizeServis.analizaSaUnetimImenomVecPostoji("nfafdfs"));
	}
	
	@Test
	public void editGrupaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().add(grupa1);
		analizeServis.editGrupa(grupa1, "nova grupa");
		assertTrue("naziv grupe nije promenjen!", grupa1.getNaziv().equals("nova grupa"));
		assertFalse("naziv grupe nije promenjen!", grupa1.getNaziv().equals("nova grupaaaa"));
	}
	
	@Test
	public void kreirajNovuGrupuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		rp.getGrupeAnalizaRepo().getEntitetiList().add(grupa1);
		GrupaAnaliza expected = new GrupaAnaliza(2, "nova grupa", true);
		GrupaAnaliza actual = analizeServis.kreirajNovuGrupu("nova grupa");
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test 
	public void vratiAnalizuNazadUponudu() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		PosebnaAnaliza posebnaAnaliza = new PosebnaAnaliza(5, "naziv4", grupa4, 1000, 10.0, 20.0, "g/L", false);
		rp.getPosebnaAnalizaRepo().getEntitetiList().add(posebnaAnaliza);
		assertFalse(posebnaAnaliza.isValidna());
		analizeServis.vratiAnalizuNazadUPonudu(posebnaAnaliza);
		assertTrue(posebnaAnaliza.isValidna());
	}
	
	@Test
	public void vratiGrupuNazadUPonudu() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		GrupaAnaliza grupa = new GrupaAnaliza(1, "grupa", false);
		rp.getGrupeAnalizaRepo().getEntitetiList().add(grupa);
		assertFalse(grupa.isValidna());
		analizeServis.vratiGrupuNazadUPonudu(grupa);
		assertTrue(grupa.isValidna());
	}
	
	@Test
	public void getPopustZaAnalizuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		AnalizaPopust analizaPopust1 = new AnalizaPopust(posebnaAnaliza1, 20);
		AnalizaPopust analizaPopust2 = new AnalizaPopust(posebnaAnaliza2, 30);
		
		rp.getAnalizaPopustRepo().getEntitetiMap().put(1, analizaPopust1);
		rp.getAnalizaPopustRepo().getEntitetiMap().put(2, analizaPopust2);
		
		int expected = 20;
		int actual = analizeServis.getPopustZaAnalizu(posebnaAnaliza1);
		assertTrue(expected == actual);
	}
	
	@Test
	public void getCenaSaPopustomTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		AnalizaPopust analizaPopust1 = new AnalizaPopust(posebnaAnaliza1, 20);
		AnalizaPopust analizaPopust2 = new AnalizaPopust(posebnaAnaliza2, 50);
		
		rp.getAnalizaPopustRepo().getEntitetiMap().put(1, analizaPopust1);
		rp.getAnalizaPopustRepo().getEntitetiMap().put(2, analizaPopust2);
		
		double expected = 800.0;
		double actual = analizeServis.getCenaSaPopustom(posebnaAnaliza1);
		assertTrue(Math.abs(expected - actual) < 0.1);
		
		expected = 1000.0;
		actual = analizeServis.getCenaSaPopustom(posebnaAnaliza2);
		assertTrue(Math.abs(expected - actual) < 0.1);
	}
	
	@Test
	public void getPopustZaGrupuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		GrupaPopust grupaPopust1 = new GrupaPopust(grupa1, 15);
		GrupaPopust grupaPopust2 = new GrupaPopust(grupa2, 10);
		
		rp.getGrupaPopustRepo().getEntitetiMap().put(1, grupaPopust1);
		rp.getGrupaPopustRepo().getEntitetiMap().put(2, grupaPopust2);
		
		int expected = 15;
		int actual = analizeServis.getPopustZaGrupu(grupa1);
		assertTrue(expected == actual);
		
		expected = 0;
		actual = analizeServis.getPopustZaGrupu(grupa3);
		assertTrue(expected == actual);
	}
	
	@Test
	public void editPopustAnaliza() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		AnalizaPopust analizaPopust1 = new AnalizaPopust(posebnaAnaliza1, 20);
		AnalizaPopust analizaPopust2 = new AnalizaPopust(posebnaAnaliza2, 50);
		
		rp.getAnalizaPopustRepo().getEntitetiMap().put(1, analizaPopust1);
		rp.getAnalizaPopustRepo().getEntitetiMap().put(2, analizaPopust2);
		
		analizeServis.editPopustAnaliza(posebnaAnaliza1, 40);
		assertTrue(analizeServis.getPopustZaAnalizu(posebnaAnaliza1) == 40);
	}
	
	@Test
	public void editPopustGrupa() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		GrupaPopust analizaPopust1 = new GrupaPopust(grupa1, 10);
		rp.getGrupaPopustRepo().getEntitetiMap().put(1, analizaPopust1);
		
		analizeServis.editPopustGrupa(grupa1, 15);
		assertTrue(analizeServis.getPopustZaGrupu(grupa1) == 15);
	}
	
	@Test
	public void analizeGetIznosPopustaOstvarenNadGrupamaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getGrupeAnalizaRepo().getEntitetiList().addAll(grupe);
		
		GrupaPopust grupaPopust1 = new GrupaPopust(grupa1, 15);
		GrupaPopust grupaPopust2 = new GrupaPopust(grupa2, 10);
		
		rp.getGrupaPopustRepo().getEntitetiMap().put(1, grupaPopust1);
		rp.getGrupaPopustRepo().getEntitetiMap().put(2, grupaPopust2);
		
		PosebnaAnaliza analiza1 = new PosebnaAnaliza(1, "naziv1", grupa1, 1000, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza analiza2 = new PosebnaAnaliza(2, "naziv2", grupa1, 2000, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza analiza3 = new PosebnaAnaliza(3, "naziv3", grupa1, 3000, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza analiza4 = new PosebnaAnaliza(4, "naziv4", grupa2, 1000, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza analiza5 = new PosebnaAnaliza(5, "naziv5", grupa2, 5000, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza analiza6 = new PosebnaAnaliza(6, "naziv6", grupa2, 300, 10.0, 20.0, "g/L", true);
		PosebnaAnaliza analiza7 = new PosebnaAnaliza(7, "naziv7", grupa3, 400, 10.0, 20.0, "g/L", true);
		
		List<PosebnaAnaliza>zahtevaneAnalize = new ArrayList<PosebnaAnaliza>();
		zahtevaneAnalize.add(analiza1);
		zahtevaneAnalize.add(analiza2);
		zahtevaneAnalize.add(analiza3);
		zahtevaneAnalize.add(analiza4);
		zahtevaneAnalize.add(analiza5);
		zahtevaneAnalize.add(analiza6);
		zahtevaneAnalize.add(analiza7);
		
		double expected = (1000 + 2000 + 3000)/100 * 15 + (1000 + 5000 + 300)/100 * 10;
		double actual = analizeServis.analizeGetIznosPopustaOstvarenNadGrupama(zahtevaneAnalize);
		assertTrue(expected == actual);
	}
}
