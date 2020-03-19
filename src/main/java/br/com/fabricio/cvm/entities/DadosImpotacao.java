package br.com.fabricio.cvm.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DadosImpotacao implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url;
	private LocalDateTime dataAtualizacao;
	private LocalDateTime dataUltimaModificacao;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getDataUltimaModificacao() {
		return dataUltimaModificacao;
	}

	public void setDataUltimaModificacao(LocalDateTime dataUltimaModificacao) {
		this.dataUltimaModificacao = dataUltimaModificacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosImpotacao other = (DadosImpotacao) obj;
		if (this.url.equals(other.getUrl())) {
			return true;
		}
		if (this.dataUltimaModificacao.isEqual(other.getDataUltimaModificacao())) {
			return true;
		}
		return false;
	}

}
