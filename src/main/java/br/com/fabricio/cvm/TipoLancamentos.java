package br.com.fabricio.cvm;

/**
 * @author fabricio_asantos
 *
 */
public enum TipoLancamentos {
	ULTIMOS("ÚLTIMO"),
	PENULTIMOS("PENÚLTIMO");
	
	private String value;

	private TipoLancamentos(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
	
}
