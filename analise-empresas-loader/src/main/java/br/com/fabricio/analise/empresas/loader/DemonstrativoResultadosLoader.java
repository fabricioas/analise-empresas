package br.com.fabricio.analise.empresas.loader;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.fabricio.analise.empresas.core.ArquivoProcessamento;
import br.com.fabricio.analise.empresas.core.CsvLinhaParser;
import br.com.fabricio.analise.empresas.core.DataBaseService;
import br.com.fabricio.analise.empresas.core.FileLoader;
import br.com.fabricio.analise.empresas.core.TrimestreUtils;
import br.com.fabricio.analise.empresas.core.entities.DemonstrativoFinanceiro;
import br.com.fabricio.analise.empresas.core.enuns.EnumRelatorios;
import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;

public class DemonstrativoResultadosLoader extends FileLoader<DemonstrativoFinanceiro> {

	public DemonstrativoResultadosLoader(String repositorio, DataBaseService baseService) {
		super(repositorio, baseService);
	}

	@Override
	protected List<ArquivoProcessamento> getListOfFiles(String repositorio) {
		return getYears().collect(LinkedList::new, this::getArquivos, this::addArquivos);
	}

	private List<ArquivoProcessamento> getArquivos(List<ArquivoProcessamento> list, Integer ano) {
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.DRE_ITR_CONSOLIDADO.getPath(ano),EnumRelatorios.DRE_ITR_CONSOLIDADO, EnumTipoDemonstrativo.CONSOLIDADO));
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.DRE_ITR_INDIVIDUAL.getPath(ano),EnumRelatorios.DRE_ITR_INDIVIDUAL, EnumTipoDemonstrativo.INDIVIDUAL));
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.DRE_CONSOLIDADO.getPath(ano),EnumRelatorios.DRE_CONSOLIDADO, EnumTipoDemonstrativo.CONSOLIDADO));
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.DRE_INDIVIDUAL.getPath(ano),EnumRelatorios.DRE_INDIVIDUAL, EnumTipoDemonstrativo.INDIVIDUAL));
		return list;
	}

	private void addArquivos(List<ArquivoProcessamento> list1, List<ArquivoProcessamento> list2) {
		list1.addAll(list2);
	}

	@Override
	protected DemonstrativoFinanceiro toMap(CsvLinhaParser parser, ArquivoProcessamento arquivoProcessamento) {
		DemonstrativoFinanceiro result = new DemonstrativoFinanceiro();
		result.setCnpj(parser.getValue(DemonstrativoResultadosField.CNPJ_CIA));
		result.setDataReferencia(parser.getValue(DemonstrativoResultadosField.DT_REFER));
		result.setVersao(parser.getValue(DemonstrativoResultadosField.VERSAO));
		result.setTipoRelatorio(arquivoProcessamento.getTipoRelatorio());
		result.setTipoDemonstrativo(arquivoProcessamento.getTipoDemonstrativo());
		result.setGrupoDFP(parser.getValue(DemonstrativoResultadosField.GRUPO_DFP));
		result.setEscalaMoeda(parser.getValue(DemonstrativoResultadosField.ESCALA_MOEDA));
		result.setOrdemExercicio(parser.getValue(DemonstrativoResultadosField.ORDEM_EXERC));
		result.setDataInicioExercicio(parser.getValue(DemonstrativoResultadosField.DT_INI_EXERC));
		result.setDataFimExercicio(parser.getValue(DemonstrativoResultadosField.DT_FIM_EXERC));
		result.setCodigoConta(parser.getValue(DemonstrativoResultadosField.CD_CONTA));
		result.setDescricaoConta(parser.getValue(DemonstrativoResultadosField.DS_CONTA));
		result.setValorConta(result.getEscalaMoeda().convert(parser.getValue(DemonstrativoResultadosField.VL_CONTA)));
		result.setAno(result.getDataFimExercicio().getYear());
		result.setTrimestre(TrimestreUtils.getTrimestre(result.getDataFimExercicio()));
		result.generateId();
		return result;
	}

	@Override
	protected Boolean isFiltred(DemonstrativoFinanceiro item) {
		return item.getOrdemExercicio().equalsIgnoreCase("ÚLTIMO");
//				&& item.getCnpj().equals("60.894.730/0001-05");
	}

	@Override
	protected Class<DemonstrativoFinanceiro> getClassDb() {
		return DemonstrativoFinanceiro.class;
	}

	private Stream<Integer> getYears() {
		return IntStream.rangeClosed(2011, LocalDate.now().getYear()).boxed();
	}

}
