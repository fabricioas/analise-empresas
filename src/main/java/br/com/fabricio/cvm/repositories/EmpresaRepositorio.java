package br.com.fabricio.cvm.repositories;

import java.util.Map;

import org.ehcache.Cache;

import br.com.fabricio.cvm.config.TransactonManager;
import br.com.fabricio.cvm.entities.Empresa;

public class EmpresaRepositorio  extends AbstractRepositorio<Empresa>{
	public EmpresaRepositorio(TransactonManager tm) {
		super(tm, "emrpesas", 200);
	}

	public Cache<String, Empresa> getEmpresas() {
		return getCache();
	}

	public void setEmpresas(Map<String, Empresa> empresas) {
		getCache().putAll(empresas);
	}
	
	public Empresa findEmpresa(String cnpj) {
		return getCache().get(cnpj);
	}

	@Override
	protected Class<Empresa> getClassRepos() {
		return Empresa.class;
	}

	@Override
	public void save(Empresa value) {
		getCache().put(value.getCnpj(), value);
	}

}
