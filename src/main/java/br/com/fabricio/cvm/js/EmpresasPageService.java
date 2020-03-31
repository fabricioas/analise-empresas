package br.com.fabricio.cvm.js;

import java.util.LinkedList;
import java.util.List;

import br.com.fabricio.cvm.entities.Empresa;
import br.com.fabricio.cvm.repositories.DataSourceJS;

public class EmpresasPageService {

	DataSourceJS dataSource = new DataSourceJS("./app/pages/cvm/data/empresas.db");
	private List<EmpresasPage> itens;
	
	public void begin() {
		itens = new LinkedList<>();
	}
	
	public void save(Empresa empresa) {
		EmpresasPage empresasPage = new EmpresasPage();
		empresasPage.setCnpj(empresa.getCnpj());
		empresasPage.setNome(empresa.getNome());
		empresasPage.setDataUltimaAtualizacao(empresa.getDtRecebimento());
		itens.add(empresasPage);
	}
	
	public void end() {
		dataSource.save("empresas", itens);
	}
}
