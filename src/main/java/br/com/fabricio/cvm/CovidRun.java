package br.com.fabricio.cvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

public class CovidRun {
	
	public static final String[] FILTER = {"Brazil","Italy","Spain","US"};
	
	public static void main(String...strings) throws Exception {
		PrintWriter pw = new PrintWriter("./app/pages/data/covid_deaths.js");
		URL url = new URL("https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_covid19_deaths_global.csv&filename=time_series_covid19_deaths_global.csv");
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		reader.lines().filter( p -> !p.startsWith("Province/State")).map( CovidSeries::new ).filter(CovidSeries::isPresent).forEach( i -> {
			pw.println("var "+ i.getName() + " = {");
			pw.println("'name':'"+ i.getName()+"',");
			String series = i.getSeries().stream().map( m -> m.toString() ) .collect(Collectors.joining(","));
			pw.println("'series':["+series+"]");
			pw.println("};");
			pw.println("");
		});
		pw.close();
	}
}

class CovidSeries {

	private String name;
	private List<Integer> series = new LinkedList<>();

	
	public CovidSeries(String linha) {
		super();
		String[] csv = linha.split(",");
		this.name = csv[1];
		for( int i = 5; i < csv.length ;i++ ) {
			Integer deaths = Integer.valueOf(csv[i]);
			if( deaths > 0 ) {
				series.add(deaths);
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<Integer> getSeries() {
		return series;
	}
	
	public boolean isPresent() {
		return ArrayUtils.contains(CovidRun.FILTER, name);
	}

}
