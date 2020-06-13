package br.com.fabricio.analise.empresas.core.enuns;

public enum EnumRelatorios {
	INF_CADASTRO("/dados/CIA_ABERTA/CAD/DADOS/cad_cia_aberta.csv"),
	ITR("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_%s.zip"),
	DRE("/dados/CIA_ABERTA/DOC/DFP/DRE/DADOS/dre_cia_aberta_%s.zip"),
	BPA("/dados/CIA_ABERTA/DOC/DFP/BPA/DADOS/bpa_cia_aberta_%s.zip"),
	BPP("/dados/CIA_ABERTA/DOC/DFP/BPP/DADOS/bpp_cia_aberta_%s.zip"),
	BPA_CONSOLIDADO("/dados/CIA_ABERTA/DOC/DFP/BPA/DADOS/bpa_cia_aberta_con_%s.csv"),
	BPA_INDIVIDUAL("/dados/CIA_ABERTA/DOC/DFP/BPA/DADOS/bpa_cia_aberta_ind_%s.csv"),
	BPA_ITR_CONSOLIDADO("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_bpa_con_%s.csv"),
	BPA_ITR_INDIVIDUAL("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_bpa_ind_%s.csv"),
	BPP_CONSOLIDADO("/dados/CIA_ABERTA/DOC/DFP/BPP/DADOS/bpp_cia_aberta_con_%s.csv"),
	BPP_INDIVIDUAL("/dados/CIA_ABERTA/DOC/DFP/BPP/DADOS/bpp_cia_aberta_ind_%s.csv"),
	BPP_ITR_CONSOLIDADO("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_bpp_con_%s.csv"),
	BPP_ITR_INDIVIDUAL("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_bpp_ind_%s.csv"),
	DRE_CONSOLIDADO("/dados/CIA_ABERTA/DOC/DFP/DRE/DADOS/dre_cia_aberta_con_%s.csv"),
	DRE_INDIVIDUAL("/dados/CIA_ABERTA/DOC/DFP/DRE/DADOS/dre_cia_aberta_ind_%s.csv"),
	DRE_ITR_CONSOLIDADO("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_dre_con_%s.csv"),
	DRE_ITR_INDIVIDUAL("/dados/CIA_ABERTA/DOC/ITR/DADOS/itr_cia_aberta_dre_ind_%s.csv");

	private final String path;

	private EnumRelatorios(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
	public String getPath(Integer year) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(path,year));
		return sb.toString();
	}
	
	public String getDirectory() {
		Integer endIndex = path.lastIndexOf('/');
		return path.substring(0, endIndex);
	}


}
