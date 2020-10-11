package repositories;

import repositories.korisnici.AdminRepository;
import repositories.korisnici.KorisnikRepository;
import repositories.korisnici.LaborantRepository;
import repositories.korisnici.MedicinskiTehničarRepository;
import repositories.korisnici.PacijentRepository;
import utils.FileSettings;

public class RepositoryFactory {
	
	
	private static RepositoryFactory instanca;
	
	
	private FileSettings settings;
	private CenePodesavanjaRepo cenePodesavanjaRepo;
	private DatumiPopustaAnalizaRepository datumiPopustaAnalizaRepo;
	private DatumiPopustaGrupaRepository datumiPopustaGrupaRepo;
	private KorisnikRepository korisnikRepo;
	private AdminRepository adminRepo;
	private PacijentRepository pacijentRepo;
	private MedicinskiTehničarRepository medicinskiTehničarRepo;
	private LaborantRepository laborantRepo;
	private GrupeAnalizaRepository grupeAnalizaRepo;
	private PosebnaAnalizaRepository posebnaAnalizaRepo;
	private StručnaSpremaRepository stručnaSpremaRepo;
	private StanjeZahtevaRepository stanjeZahtevaRepo;
	private DostavaRepository dostavaRepo;
	private ZahtevRepository zahtevRepo;
	private NalazRepository nalazRepo;
	private AnalizaZaObraduRepository analizaZaObraduRepo;
	private PlateRepository plateRepo;
	private AnalizaPopustRepository analizaPopustRepo;
	private GrupaPopustRepository grupaPopustRepo;
	
	
	public static RepositoryFactory getInstance() {
		if (instanca == null) {
			instanca = new RepositoryFactory();
		}
		return instanca;
	}
	
	private RepositoryFactory() {
		FileSettings settings = new FileSettings();
		this.settings = settings;
		this.cenePodesavanjaRepo = new CenePodesavanjaRepo(settings.getCenePodesavanjaFileName());
		this.datumiPopustaAnalizaRepo = new DatumiPopustaAnalizaRepository(settings.getDatumiPopustaAnalizaFileName());
		this.datumiPopustaGrupaRepo = new DatumiPopustaGrupaRepository(settings.getDatumiPopustaGrupaFileName());
		this.stručnaSpremaRepo = new StručnaSpremaRepository(settings.getStručnaSpremaFileName());
		
		this.grupeAnalizaRepo = new GrupeAnalizaRepository(settings.getGrupeAnalizaFileName());
		this.posebnaAnalizaRepo = new PosebnaAnalizaRepository(settings.getPosebneAnalizeFileName(), this.grupeAnalizaRepo);
		this.analizaPopustRepo = new AnalizaPopustRepository(settings.getAnalizaPopustFileName(), this.posebnaAnalizaRepo);
		this.grupaPopustRepo = new GrupaPopustRepository(settings.getGrupaPopustFileName(), this.grupeAnalizaRepo);
		
		this.stanjeZahtevaRepo = new StanjeZahtevaRepository(settings.getStanjaZahtevaFileName());
		this.dostavaRepo = new DostavaRepository(settings.getDostavaFileName());
		
		this.adminRepo = new AdminRepository(settings.getKorisniciFileName());
		this.pacijentRepo = new PacijentRepository(settings.getKorisniciFileName());
		this.medicinskiTehničarRepo = new MedicinskiTehničarRepository(settings.getKorisniciFileName(), this.stručnaSpremaRepo);
		this.laborantRepo = new LaborantRepository(settings.getKorisniciFileName(), this.grupeAnalizaRepo, this.stručnaSpremaRepo);
		this.korisnikRepo = new KorisnikRepository(settings.getKorisniciFileName(), this.adminRepo, this.pacijentRepo, this.laborantRepo, this.medicinskiTehničarRepo);
		
		this.plateRepo = new PlateRepository(settings.getPlateFileName(), this.korisnikRepo);
		this.zahtevRepo = new ZahtevRepository(settings.getZahteviFileName(), posebnaAnalizaRepo, korisnikRepo, stanjeZahtevaRepo, dostavaRepo);
		this.analizaZaObraduRepo = new AnalizaZaObraduRepository(settings.getAnalizaZaObraduFileName(), this.posebnaAnalizaRepo, this.laborantRepo);
		this.nalazRepo = new NalazRepository(settings.getNalaziFileName(), this.zahtevRepo, this.analizaZaObraduRepo);
	}
	
	
	
	public void loadData() {
		loadDatumiPopustaAnaliza();
		loadDatumiPopustaGrupa();
		loadCenePodesavanja();
		loadStrucneSpeme();
		loadStanjeZahteva();
		loadDostava();
		loadGrupeAnaliza();
		loadPosebneAnalize();
		loadAnalizePopust();
		loadGrupePopusta();
		loadKorisnici();
		loadPlate();
		loadZahtevi();
		loadAnalizeZaObradu();
		loadNalazi();
	}
	
	public void saveData() {
		saveDatumiPopustaAnaliza();
		saveDatumiPopustaGrupa();
		saveCenePodesavanja();
		saveStrucneSpeme();
		saveStanjeZahteva();
		saveDostava();
		saveGrupeAnaliza();
		savePosebneAnalize();
		saveAnalizePopust();
		saveGrupePopusta();
		saveKorisnici();
		savePlate();
		saveZahtevi();
		saveAnalizeZaObradu();
		saveNalazi();
	}
	
	public boolean loadDatumiPopustaAnaliza() { 
		return this.datumiPopustaAnalizaRepo.loadData();
	}
	
	public boolean saveDatumiPopustaAnaliza() { 
		return this.datumiPopustaAnalizaRepo.saveData();
	}
	
	public boolean loadDatumiPopustaGrupa() {
		return this.datumiPopustaGrupaRepo.loadData();
	}
	
	public boolean saveDatumiPopustaGrupa() {
		return this.datumiPopustaGrupaRepo.saveData();
	}
	
	public boolean loadCenePodesavanja() {
		return this.cenePodesavanjaRepo.loadData();
	}
	
	public boolean saveCenePodesavanja() {
		return this.cenePodesavanjaRepo.saveData();
	}
	
	public boolean loadStrucneSpeme() {
		return this.stručnaSpremaRepo.loadData();
	}
	
	public boolean saveStrucneSpeme() {
		return this.stručnaSpremaRepo.saveData();
	}
	
	public boolean loadStanjeZahteva() {
		return this.stanjeZahtevaRepo.loadData();
	}
	
	public boolean saveStanjeZahteva() {
		return this.stanjeZahtevaRepo.saveData();
	}
	
	public boolean loadDostava() {
		return this.getDostavaRepo().loadData();
	}
	
	public boolean saveDostava() {
		return this.getDostavaRepo().saveData();
	}
	
	public boolean loadGrupeAnaliza() {
		return this.grupeAnalizaRepo.loadData();
	}

	public boolean saveGrupeAnaliza() {
		return this.grupeAnalizaRepo.saveData();
	}
	
	public boolean loadPosebneAnalize() {
		return this.posebnaAnalizaRepo.loadData();
	}

	public boolean savePosebneAnalize() {
		return this.posebnaAnalizaRepo.saveData();
	}
	
	public boolean loadAnalizePopust(){
		return this.analizaPopustRepo.loadData();
	}
	
	public boolean saveAnalizePopust(){
		return this.analizaPopustRepo.saveData();
	}
	
	public boolean loadGrupePopusta() {
		return this.grupaPopustRepo.loadData();
	}
	
	public boolean saveGrupePopusta() {
		return this.grupaPopustRepo.saveData();
	}
	
	public boolean loadKorisnici() {
		return this.korisnikRepo.loadData();
	}
	
	public boolean saveKorisnici() {
		return this.korisnikRepo.saveData();
	}
	
	public boolean loadPlate() {
		return this.plateRepo.loadData();
	}
	
	public boolean savePlate() {
		return this.plateRepo.saveData();
	}
	
	public boolean loadZahtevi() {
		return this.zahtevRepo.loadData();
	}
	
	public boolean saveZahtevi() {
		return this.zahtevRepo.saveData();
	}
	
	public boolean loadAnalizeZaObradu() {
		return this.analizaZaObraduRepo.loadData();
	}
	
	public boolean saveAnalizeZaObradu() {
		return this.analizaZaObraduRepo.saveData();
	}
	
	public boolean loadNalazi() {
		return this.nalazRepo.loadData();
	}
	
	public boolean saveNalazi() {
		return this.nalazRepo.saveData();
	}
	
	
	public FileSettings getSettings() {return settings;}
	
	public GrupaPopustRepository getGrupaPopustRepo() {return grupaPopustRepo;}

	public AnalizaPopustRepository getAnalizaPopustRepo() {return analizaPopustRepo;}

	public DatumiPopustaAnalizaRepository getDatumiPopustaAnalizaRepo() {return datumiPopustaAnalizaRepo;}

	public DatumiPopustaGrupaRepository getDatumiPopustaGrupaRepo() {return datumiPopustaGrupaRepo;}

	public PlateRepository getPlateRepo() {return plateRepo;}

	public CenePodesavanjaRepo getCenePodesavanjaRepo() {return cenePodesavanjaRepo;}
	
	public KorisnikRepository getKorisnikRepo() {return korisnikRepo;}

	public AdminRepository getAdminRepo() {return adminRepo;}

	public PacijentRepository getPacijentRepo() {return pacijentRepo;}

	public MedicinskiTehničarRepository getMedicinskiTehničarRepo() {return medicinskiTehničarRepo;}

	public LaborantRepository getLaborantRepo() {return laborantRepo;}
	
	public GrupeAnalizaRepository getGrupeAnalizaRepo() {return grupeAnalizaRepo;}
	
	public PosebnaAnalizaRepository getPosebnaAnalizaRepo() {return posebnaAnalizaRepo;}
	
	public StručnaSpremaRepository getStručnaSpremaRepo() {return stručnaSpremaRepo;}
	
	public StanjeZahtevaRepository getStanjeZahtevaRepo() {return stanjeZahtevaRepo;}
	
	public ZahtevRepository getZahtevRepo() {return zahtevRepo;}
	
	public NalazRepository getNalazRepo() {return nalazRepo;}

	public DostavaRepository getDostavaRepo() {return dostavaRepo;}
	
	public AnalizaZaObraduRepository getAnalizaZaObraduRepo() {return analizaZaObraduRepo;}
	
}
