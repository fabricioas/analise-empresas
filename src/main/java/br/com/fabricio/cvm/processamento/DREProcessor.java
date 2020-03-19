package br.com.fabricio.cvm.processamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.fabricio.cvm.app.ArquivoInfo;
import br.com.fabricio.cvm.entities.ChaveDemonstrativo;
import br.com.fabricio.cvm.entities.Conta;
import br.com.fabricio.cvm.entities.Demonstrativo;
import br.com.fabricio.cvm.entities.Empresa;
import br.com.fabricio.cvm.entities.LancamentoDRE;

public class DREProcessor {
	private Map<String, Empresa> empresas;
	private ArquivoInfo arquivoDRE;
	
	public DREProcessor(Map<String, Empresa> empresas, ArquivoInfo arquivoDRE) {
		super();
		this.empresas = empresas;
		this.arquivoDRE = arquivoDRE;
	}

	public void read(){
		if( arquivoDRE != null ) {
			Map<Integer, LancamentoDRE> lancamentos = arquivoDRE.getContent().stream().map(s -> s.split(";"))
					.filter(p -> !p[0].equals("CNPJ_CIA")).map(LancamentoDRE::new)
					.collect(Collectors.groupingBy(LancamentoDRE::getKey, Collectors.reducing(null, this::maxDRE)));
	
			processaLancamentos(lancamentos);
		}
	}
	
	private void processaLancamentos(Map<Integer, LancamentoDRE> lancamentos) {
		lancamentos.values().stream().collect(Collectors.groupingBy(ChaveDemonstrativo::new))
				.forEach(this::processaEmpresa);
	}
	
	private void processaEmpresa(ChaveDemonstrativo k, List<LancamentoDRE> v) {
		Demonstrativo demonstrativo = v.stream().map(Conta::new).reduce(new Demonstrativo(), this::addConta,
				(d, c) -> c);
		v.stream().findFirst().ifPresent(c -> preencheDemosntrativo(demonstrativo, c));
		Optional<Empresa> empresa = Optional.ofNullable(empresas.get(k.getCnpj()));
		empresa.ifPresent( p -> p.addDemostrativo(demonstrativo) );
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
}
