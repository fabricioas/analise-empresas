package br.com.fabricio.analise.empresas.loader;

import java.util.Arrays;
import java.util.List;

import br.com.fabricio.analise.empresas.core.ArquivoProcessamento;
import br.com.fabricio.analise.empresas.core.CsvLinhaParser;
import br.com.fabricio.analise.empresas.core.DataBaseService;
import br.com.fabricio.analise.empresas.core.FileLoader;
import br.com.fabricio.analise.empresas.core.entities.InformacaoCadastral;
import br.com.fabricio.analise.empresas.core.enuns.EnumRelatorios;
import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;

public class InformacoesCadastraisLoader extends FileLoader<InformacaoCadastral> {

	public InformacoesCadastraisLoader(String repositorio, DataBaseService baseService) {
		super(repositorio, baseService);
	}

	@Override
	protected List<ArquivoProcessamento> getListOfFiles(String repositorio) {
		return Arrays.asList(new ArquivoProcessamento(repositorio+EnumRelatorios.INF_CADASTRO.getPath(),EnumRelatorios.INF_CADASTRO, EnumTipoDemonstrativo.UNICO));
	}

	@Override
	protected InformacaoCadastral toMap(CsvLinhaParser parser, ArquivoProcessamento arquivoProcessamento) {
		InformacaoCadastral informacaoCadastral = new InformacaoCadastral();
		informacaoCadastral.setId(parser.getValue(InformacaoCadatralField.CNPJ_CIA));
		informacaoCadastral.setNomeComercial(parser.getValue(InformacaoCadatralField.DENOM_COMERC));
		informacaoCadastral.setDataRegistro(parser.getValue(InformacaoCadatralField.DT_REG));
		informacaoCadastral.setSitucao(parser.getValue(InformacaoCadatralField.SIT));
		informacaoCadastral.setTipoRelatorio(arquivoProcessamento.getTipoRelatorio());
		informacaoCadastral.setCodigoCvm(parser.getValue(InformacaoCadatralField.CD_CVM));
		informacaoCadastral.setSetorAtividade(parser.getValue(InformacaoCadatralField.SETOR_ATIV));
		informacaoCadastral.setTipoMercado(parser.getValue(InformacaoCadatralField.TP_MERC));
		informacaoCadastral.setCategoriaRegistro(parser.getValue(InformacaoCadatralField.CATEG_REG));
		informacaoCadastral.setMunicipio(parser.getValue(InformacaoCadatralField.MUN));
		informacaoCadastral.setUf(parser.getValue(InformacaoCadatralField.UF));
		return informacaoCadastral;
	}

	@Override
	protected Boolean isFiltred(InformacaoCadastral item) {
		return isAtivo(item);
	}
	
	private Boolean isAtivo(InformacaoCadastral informacaoCadastral) {
		return !informacaoCadastral.getSitucao().equals("CANCELADA");
	}

	@Override
	protected Class<InformacaoCadastral> getClassDb() {
		return InformacaoCadastral.class;
	}

}
