package br.com.fabricio.cvm.repositories;

import java.time.LocalDateTime;

import org.apache.commons.lang3.BooleanUtils;

import br.com.fabricio.cvm.config.TransactonManager;
import br.com.fabricio.cvm.entities.DadosImpotacao;

public class DadosImportacaoRepositorio extends AbstractRepositorio<DadosImpotacao> {

	public DadosImportacaoRepositorio(TransactonManager tm) {
		super(tm,"dadosImportacao", 10);
	}

	@Override
	protected Class<DadosImpotacao> getClassRepos() {
		return DadosImpotacao.class;
	}

	@Override
	public DadosImpotacao getById(String id) {
		return getCache().get(id);
	}

	@Override
	public void save(DadosImpotacao dadosImpotacao) {
		dadosImpotacao.setDataAtualizacao(LocalDateTime.now());
		getCache().put(dadosImpotacao.getUrl(), dadosImpotacao);
	}

	public boolean isExists(DadosImpotacao newDadosImpotacao) {
		if(BooleanUtils.toBoolean(System.getProperty("debbug"))) {
			return false;
		}
		DadosImpotacao dadosImpotacao = getCache().get(newDadosImpotacao.getUrl());
		if (dadosImpotacao == null) {
			return false;
		}
		return dadosImpotacao.equals(newDadosImpotacao);
	}
}