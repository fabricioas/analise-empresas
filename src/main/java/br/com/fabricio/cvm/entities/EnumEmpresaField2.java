package br.com.fabricio.cvm.entities;

public enum EnumEmpresaField2 {
	CNPJ_CIA(0),
	DT_REFER(1),
	VERSAO(2),
	DENOM_CIA(3),
	CD_CVM(4),
	CATEG_DOC(5),
	ID_DOC(6),
	DT_RECEB(7),
	LINK_DOC(8);

	private int index;

	private EnumEmpresaField2(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
