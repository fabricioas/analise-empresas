package br.com.fabricio.analise.empresas.core;

import java.time.LocalDateTime;

public class AtualizacaoArquivo {

	private String id;
	private LocalDateTime dataAtualizacao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

}
