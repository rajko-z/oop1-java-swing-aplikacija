package repositories;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Entity;
import res.ResourceLoader;

public abstract class GenericRepository<T extends Entity> {
	
	private String filePath;
	private String separator = "|";
	private List<T>entitetiList;
	private Map<Integer, T>entitetiMap;
	
	public GenericRepository(String path) {
		this.filePath = path;
		this.entitetiList = new ArrayList<T>();
		this.entitetiMap = new HashMap<Integer,T>();
	}
	
	public boolean loadData() {
		try {
			InputStream inputStream = ResourceLoader.getInputStream(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader in = new BufferedReader(inputStreamReader);
			String line = null;
			while((line = in.readLine())!= null) {
				String tokens[] = line.split("\\" + this.separator);
				for(int i = 0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim();
				}
				createEntityAndAddToCollection(tokens);
			}
			in.close();
			return true;
			
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean saveData() {
		PrintWriter out;
		try {
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.filePath), "utf-8"));
			
			if(this.entitetiList.size() > 0 | (this.entitetiList.size() > 0 & this.getEntitetiMap().size() > 0)) {
				for(T entity: this.entitetiList) {
					out.println(entity.toFileEntity());
				}
			}
			else if(entitetiMap.size() > 0 & (this.getEntitetiList().size() == 0)) {
				for(T entity: this.entitetiMap.values()) {
					out.println(entity.toFileEntity());
				}
			}
			out.close();
			return true;
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			return false;
		}
	}
	
	public int generateIdList() {
		int retVal = 0;
		for(T entitet: this.entitetiList) {
			if (entitet.getId() > retVal) {
				retVal = entitet.getId();
			}
		}
		return retVal + 1;
	}
	
	public int generateIdMap() {
		int retVal = 0;
		for (Integer key : this.getEntitetiMap().keySet()) {
			if (key > retVal) {
				retVal = key;
			}
		}
		return retVal + 1;
	}
	
	
	public Entity getEntityByIdList(int id) {
		for(T entitet: this.getEntitetiList()) {
			if (entitet.getId() == id) {
				return entitet;
			}
		}
		return null;
	}
	
	public Entity getEntityByIdMap(int id) {
		for (Map.Entry<Integer,T> entry : this.getEntitetiMap().entrySet()) {
			if (entry.getKey() == id) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	
	public String getFilePath() {
		return filePath;
	}

	public List<T> getEntitetiList() {
		return entitetiList;
	}

	public Map<Integer, T> getEntitetiMap() {
		return entitetiMap;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	protected abstract Entity createEntityAndAddToCollection(String tokens[]);

}
