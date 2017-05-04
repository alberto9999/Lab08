package it.polito.tdp.borders.db;

import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		System.out.println("Lista di tutte le nazioni:");
		Map<Integer,Country> countries = dao.loadAllCountries();
		System.out.println(countries);
		
		System.out.println("Lista dei confini anno 1900:");
		List<Border> borders= dao.getCountryPairs(1900);
		System.out.println(borders);
		
		
		
		
		
	}
}
