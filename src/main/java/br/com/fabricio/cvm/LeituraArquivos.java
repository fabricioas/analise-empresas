package br.com.fabricio.cvm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.fabricio.cvm.entities.ChaveDemonstrativo;
import br.com.fabricio.cvm.entities.Conta;
import br.com.fabricio.cvm.entities.Demonstrativo;
import br.com.fabricio.cvm.entities.Empresa;
import br.com.fabricio.cvm.entities.LancamentoDRE;
import br.com.fabricio.cvm.repositories.EmpresaRepositorio;

public class LeituraArquivos {
	private EmpresaRepositorio repositorio = new EmpresaRepositorio(null);

	public static void main(String[] args) throws Exception {
		LeituraArquivos leituraArquivos = new LeituraArquivos();
		leituraArquivos.arquivoEmpresa();
		leituraArquivos.arquivoDRE();
	}

	private void arquivoEmpresa() throws IOException {
		List<String> linhas = Files.readAllLines(Paths.get("./arquivos/itr_cia_aberta_2011.csv"),
				StandardCharsets.ISO_8859_1);

		System.out.println(linhas.stream().count());

		Map<String, Empresa> empresas = linhas.stream().map(s -> s.split(";")).filter(p -> !p[0].equals("CNPJ_CIA"))
				.map(Empresa::new)
				.collect(Collectors.groupingBy(Empresa::getCnpj, Collectors.reducing(null, this::maxEmpresa)));
		repositorio.setEmpresas(empresas);
		System.out.println(empresas.size());
	}

	public void arquivoDRE() throws IOException {
		List<String> linhas = Files.readAllLines(Paths.get("./arquivos/itr_cia_aberta_dre_con_2011.csv"),
				StandardCharsets.ISO_8859_1);

		System.out.println(linhas.stream().count());

		Map<Integer, LancamentoDRE> lancamentos = linhas.stream().map(s -> s.split(";"))
				.filter(p -> !p[0].equals("CNPJ_CIA")).map(LancamentoDRE::new)
				.collect(Collectors.groupingBy(LancamentoDRE::getKey, Collectors.reducing(null, this::maxDRE)));
		System.out.println("Qtd Lancamento ->" + lancamentos.size());

		processaLancamentos(lancamentos);

//		report();

	}

//	private void report() {
//		PrintStream out;
//		try {
//			out = new PrintStream("./output.txt");
//
//			out.println("========Report=======");
//			repositorio.getEmpresas().forEach((k, v) -> {
//				if (v.getDemonstrativos().isEmpty()) {
//					out.println(k);
//					out.println("===>No report<====");
//				} else {
//					out.print(v.getCnpj());
//					out.print(" - ");
//					out.println(v.getNome());
//					v.getDemonstrativos().forEach(d -> {
//						out.print("===> ");
//						out.print(d.getNumeroTrimestre());
//						out.print("-");
//						out.print(d.getAno());
//						out.println(" <=== ");
//						d.getContas().stream().sorted((c1, c2) -> c1.getCodigoConta().compareTo(c2.getCodigoConta()))
//								.forEach(c -> {
//									String linha = StringUtils
//											.rightPad(c.getCodigoConta() + " - " + c.getDescricaoConta(), 80, '-');
//									out.print(linha + "  ");
//									out.println(new DecimalFormat("#,###,##0.00").format(c.getVlConta()));
//								});
//						out.println();
//					});
//				}
//			});
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private void processaLancamentos(Map<Integer, LancamentoDRE> lancamentos) {
		lancamentos.values().stream().collect(Collectors.groupingBy(ChaveDemonstrativo::new))
				.forEach(this::processaEmpresa);
	}

	private void processaEmpresa(ChaveDemonstrativo k, List<LancamentoDRE> v) {
		Demonstrativo demonstrativo = v.stream().map(Conta::new).reduce(new Demonstrativo(), this::addConta,
				(d, c) -> c);
		v.stream().findFirst().ifPresent(c -> preencheDemosntrativo(demonstrativo, c));
		Empresa empresa = repositorio.findEmpresa(k.getCnpj());
		empresa.getDemonstrativos().add(demonstrativo);
	}

	private Demonstrativo addConta(Demonstrativo d, Conta c) {
		d.getContas().add(c);
		return d;
	}

	private void preencheDemosntrativo(Demonstrativo dre, LancamentoDRE lancamento) {
		dre.setNome(lancamento.getGrupoDfp());
		dre.setCnpj(lancamento.getCnpj());
		dre.setDataInicio(LocalDate.parse(lancamento.getDtInicioExercicio()));
		dre.setDataFim(LocalDate.parse(lancamento.getDtFimExercicio()));
		dre.setDataReferencia(LocalDate.parse(lancamento.getDtReferencia()));
		dre.setNumeroTrimestre(lancamento.getTrimestre());
		dre.setAno(lancamento.getAno());

	}

	private LancamentoDRE maxDRE(LancamentoDRE a, LancamentoDRE b) {
		if (a == null) {
			return b;
		}

		if (b.getVersao() > a.getVersao()) {
			return b;
		} else {

			return a;
		}
	}

	private Empresa maxEmpresa(Empresa a, Empresa b) {
		if (a == null) {
			return b;
		}

		if (b.getVersao() > a.getVersao()) {
			return b;
		} else {
			return a;
		}
	}
}
