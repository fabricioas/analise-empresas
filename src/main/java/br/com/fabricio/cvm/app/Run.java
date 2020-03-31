package br.com.fabricio.cvm.app;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import br.com.fabricio.cvm.entities.Empresa;
import br.com.fabricio.cvm.js.EmpresasPageService;
import br.com.fabricio.cvm.processamento.DREProcessor;
import br.com.fabricio.cvm.processamento.EmpresaProcessor;

public class Run {
	private static Map<String, ArquivoInfo> arquivos = new HashMap<>();
	
	public static void main(String[] args) throws InterruptedException {
		 DownloadArquivo d = new DownloadArquivo("http://dados.cvm.gov.br/dados/CIA_ABERTA/CAD/DADOS/", "inf_cadastral_cia_aberta.csv");
		 d.filter( p -> p.getName().startsWith("inf_cadastral_cia_aberta.csv"));
		 d.run(a -> {
			 arquivos.put(a.getName(), a);
		 });
		for (Integer ano = 2010; ano <= LocalDate.now().getYear(); ano++) {
			DownloadArquivo da = new DownloadArquivo("http://dados.cvm.gov.br/dados/CIA_ABERTA/DOC/ITR/DADOS/",
					"itr_cia_aberta_" + ano + ".zip");
			da.filter( f -> 
				Pattern.matches(".*dre_con.*|itr_cia_aberta_[0-9]{4}.csv", f.getName())	
				);
			da.run(a -> {
				 arquivos.put(a.getName(), a);
			});
		}

		Map<String, Empresa> empresas = new HashMap<>();
		for (Integer ano = 2010; ano <= LocalDate.now().getYear(); ano++) {
			ArquivoInfo arquivoEmpresa = arquivos.get("itr_cia_aberta_"+ano+".csv");
			EmpresaProcessor empresaProcessor = new EmpresaProcessor(arquivoEmpresa);
			empresas.putAll(empresaProcessor.read());
			System.out.print("Qtd empresas==="+ano+"===>");
			System.out.println(empresas.size());
		}
		
		for (Integer ano = 2010; ano <= LocalDate.now().getYear(); ano++) {
			ArquivoInfo arquivoDRE = arquivos.get("itr_cia_aberta_dre_con_"+ano+".csv");
			DREProcessor dreProcessor = new DREProcessor(empresas,arquivoDRE);
			dreProcessor.read();
			
			System.out.print("Qtd empresas com demonstrativos==="+ano+"===>");
			System.out.println(empresas.values().stream().filter( p -> p.getDemonstrativos().size() > 0 ).count());

		}
		EmpresasPageService empresaService = new EmpresasPageService();
		empresaService.begin();
		empresas.forEach( (k, v) -> {
			empresaService.save(v);
		});
		empresaService.end();
		
		report(empresas);
	}

	private static void report(Map<String, Empresa> empresas) {
		PrintStream out;
		try {
			out = new PrintStream("./output.txt");

			out.println("========Report=======");
			empresas.forEach((k, v) -> {
				if (v.getDemonstrativos().isEmpty()) {
					out.println(k);
					out.println("===>No report<====");
				} else {
					out.print(v.getCnpj());
					out.print(" - ");
					out.println(v.getNome());
					v.getDemonstrativos().forEach(d -> {
						out.print("===> ");
						out.print(d.getNumeroTrimestre());
						out.print("-");
						out.print(d.getAno());
						out.println(" <=== ");
						d.getContas().stream().sorted((c1, c2) -> c1.getCodigoConta().compareTo(c2.getCodigoConta()))
								.forEach(c -> {
									String linha = StringUtils
											.rightPad(c.getCodigoConta() + " - " + c.getDescricaoConta(), 80, '-');
									out.print(linha + "  ");
									out.println(new DecimalFormat("#,###,##0.00").format(c.getVlConta()));
								});
						out.println();
					});
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
