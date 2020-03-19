package br.com.fabricio.cvm.entities;

public enum EnumEmpresaField {
	CNPJ_CIA(0),
	DENOM_SOCIAL(1),
	DENOM_COMERC(2),
	DT_REG(3),
	DT_CONST(4),
	DT_CANCEL(5),
	MOTIVO_CANCEL(6),
	SIT(7),
	DT_INI_SIT(8),
	CD_CVM(9),
	SETOR_ATIV(10),
	CATEG_REG(11),
	DT_INI_CATEG(12),
	SIT_EMISSOR(13),
	DT_INI_SIT_EMISSOR(14),
	TP_ENDER(15),
	LOGRADOURO(16),
	COMPL(17),
	BAIRRO(18),
	MUN(19),
	UF(20),
	PAIS(21),
	CEP(22),
	DDD_TEL(23),
	TEL(24),
	DDD_FAX(25),
	FAX(26),
	EMAIL(27),
	TP_RESP(28),
	RESP(29),
	DT_INI_RESP(30),
	LOGRADOURO_RESP(31),
	COMPL_RESP(32),
	BAIRRO_RESP(33),
	MUN_RESP(34),
	UF_RESP(35),
	PAIS_RESP(36),
	CEP_RESP(37),
	DDD_TEL_RESP(38),
	TEL_RESP(39),
	DDD_FAX_RESP(40),
	FAX_RESP(41),
	EMAIL_RESP(42),
	CNPJ_AUDITOR(43),
	AUDITOR(44);
	
	private int index;

	private EnumEmpresaField(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
