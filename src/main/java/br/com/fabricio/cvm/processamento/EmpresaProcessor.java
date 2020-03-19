package br.com.fabricio.cvm.processamento;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.fabricio.cvm.app.ArquivoInfo;
import br.com.fabricio.cvm.entities.Empresa;

public class EmpresaProcessor {
	private ArquivoInfo arquivoEmpresa;

	public EmpresaProcessor(ArquivoInfo arquivoEmpresa) {
		super();
		this.arquivoEmpresa = arquivoEmpresa;
	}

	public Map<String, Empresa> read() {
		System.out.print("Processando arquivo de empresa: ");
		if (arquivoEmpresa != null && arquivoEmpresa.getContent() != null) {
			Map<String, Empresa> empresas = arquivoEmpresa.getContent().stream().map(s -> s.split(";"))
					.filter(p -> !p[0].equals("CNPJ_CIA")).map(Empresa::new)
					.collect(Collectors.groupingBy(Empresa::getCnpj, Collectors.reducing(null, this::maxEmpresa)));
			System.out.println(empresas.size());
			return empresas;
		}else {
			return new HashMap<String, Empresa>();
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
