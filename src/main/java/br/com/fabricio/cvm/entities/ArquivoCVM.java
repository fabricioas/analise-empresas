package br.com.fabricio.cvm.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArquivoCVM implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;
	private String csv;
	private String hash;
	private LocalDateTime dataAtualizacao;
	private LocalDateTime dataUltimaModificacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public LocalDateTime getDataUltimaModificacao() {
		return dataUltimaModificacao;
	}

	public void setDataUltimaModificacao(LocalDateTime dataUltimaModificacao) {
		this.dataUltimaModificacao = dataUltimaModificacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoCVM other = (ArquivoCVM) obj;
		if (this.nome.equals(other.getNome())) {
			return true;
		}
		if (this.hash.equals(other.getHash())) {
			return true;
		}
		return false;
	}

}
