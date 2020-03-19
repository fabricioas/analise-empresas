package br.com.fabricio.cvm.app;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.fabricio.cvm.Utils;

public class ArquivoInfo {
	private String name;
	private String url;
	private LocalDateTime dataAtualizacao;
	private LocalDateTime dataUltimaModificacao;
	private List<String> content;
	private String hash;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Optional.ofNullable(name).ifPresent( p -> this.name  = p.toLowerCase());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
		hash = Utils.generateHash(content);
	}
	
	public String hash() {
		return hash;
	}

}
