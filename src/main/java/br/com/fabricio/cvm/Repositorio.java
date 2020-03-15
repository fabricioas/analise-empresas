package br.com.fabricio.cvm;

import java.util.Map;

public class Repositorio {
	private Map<String, Empresa> empresas;

	public Map<String, Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Map<String, Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public Empresa findEmpresa(String cnpj) {
		return empresas.get(cnpj);
	}

}
