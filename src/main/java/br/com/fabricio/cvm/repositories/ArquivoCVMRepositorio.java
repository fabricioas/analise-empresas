package br.com.fabricio.cvm.repositories;

import java.time.LocalDateTime;

import org.apache.commons.lang3.BooleanUtils;

import br.com.fabricio.cvm.config.TransactonManager;
import br.com.fabricio.cvm.entities.ArquivoCVM;

public class ArquivoCVMRepositorio extends AbstractRepositorio<ArquivoCVM> {

	public ArquivoCVMRepositorio(TransactonManager tm) {
		super(tm,"arquivoCVM", 200);
	}

	@Override
	protected Class<ArquivoCVM> getClassRepos() {
		return ArquivoCVM.class;
	}

	@Override
	public ArquivoCVM getById(String id) {
		return getCache().get(id);
	}

	@Override
	public void save(ArquivoCVM arquivoCVM) {
		arquivoCVM.setDataAtualizacao(LocalDateTime.now());
		getCache().put(arquivoCVM.getNome(), arquivoCVM);
	}

	public boolean isExists(ArquivoCVM newArquivoCVM) {
		if(BooleanUtils.toBoolean(System.getProperty("debbug"))) {
			return false;
		}
		ArquivoCVM arquivoCVM = getCache().get(newArquivoCVM.getNome());
		if (arquivoCVM == null) {
			return false;
		}
		return arquivoCVM.equals(newArquivoCVM);
	}
}