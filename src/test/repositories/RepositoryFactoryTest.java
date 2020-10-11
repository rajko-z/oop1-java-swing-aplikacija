package test.repositories;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.Test;
import repositories.RepositoryFactory;

public class RepositoryFactoryTest {

	RepositoryFactory rp = RepositoryFactory.getInstance();
	
	
	@Test
	public void loadDatumiPopustaAnalizaTest() { 
		assertTrue(rp.getDatumiPopustaAnalizaRepo().loadData());
	}
	
	
	@Test
	public void loadDatumiPopustaGrupaTest() {
		assertTrue(rp.getDatumiPopustaGrupaRepo().loadData());
	}
	
	@Test
	public void loadCenePodesavanjaTest() {
		assertTrue(rp.getCenePodesavanjaRepo().loadData());
	}
	
	
	@Test
	public void loadStrucneSpemeTest() {
		assertTrue(rp.getStručnaSpremaRepo().loadData());
	}
	
	
	@Test
	public void loadStanjeZahtevaTest() {
		assertTrue(rp.getStanjeZahtevaRepo().loadData());
	}
	
	
	@Test
	public void loadDostavaTest() {
		assertTrue(rp.getDostavaRepo().loadData());
	}
	
	
	@Test
	public void loadGrupeAnalizaTest() {
		assertTrue(rp.getGrupeAnalizaRepo().loadData());
	}

	
	@Test
	public void loadPosebneAnalizeTest() {
		assertTrue(rp.getPosebnaAnalizaRepo().loadData());
	}

	
	@Test
	public void loadAnalizePopustTest(){
		assertTrue(rp.getAnalizaPopustRepo().loadData());
	}
	
	
	@Test
	public void loadGrupePopustaTest() {
		assertTrue(rp.getGrupaPopustRepo().loadData());
	}
	
	
	@Test
	public void loadKorisniciTest() {
		assertTrue(rp.getKorisnikRepo().loadData());
	}
	
	
	@Test
	public void loadPlateTest() {
		assertTrue(rp.getPlateRepo().loadData());
	}
	
	
	@Test
	public void loadZahteviTest() {
		assertTrue(rp.getZahtevRepo().loadData());
	}
	
	
	@Test
	public void loadAnalizeZaObraduTest() {
		assertTrue(rp.getAnalizaZaObraduRepo().loadData());
	}
	
	
	@Test
	public void loadNalaziTest() {
		rp.getZahtevRepo().loadData();
		rp.getAnalizaZaObraduRepo().loadData();
		assertTrue(rp.getNalazRepo().loadData());
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
	
	
	
}
