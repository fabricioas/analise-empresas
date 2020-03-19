package br.com.fabricio.cvm.entities;

import java.util.Objects;

public class ChaveDemonstrativo {

	private final String cnpj;
	private final Integer trimestre;
	private final Integer ano;

	public ChaveDemonstrativo(LancamentoDRE l) {
		super();
		this.cnpj = l.getCnpj();
		this.trimestre = l.getTrimestre();
		this.ano = l.getAno();
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, trimestre, ano);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChaveDemonstrativo other = (ChaveDemonstrativo) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (trimestre == null) {
			if (other.trimestre != null)
				return false;
		} else if (!trimestre.equals(other.trimestre))
			return false;
		return true;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Integer getTrimestre() {
		return trimestre;
	}

	public Integer getAno() {
		return ano;
	}

}
