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

public class PatrimonioAtivoLoader extends FileLoader<DemonstrativoFinanceiro> {

	public PatrimonioAtivoLoader(String repositorio, DataBaseService baseService) {
		super(repositorio, baseService);
	}

	@Override
	protected List<ArquivoProcessamento> getListOfFiles(String repositorio) {
		return getYears().collect(LinkedList::new, this::getArquivos, this::addArquivos);
	}

	private List<ArquivoProcessamento> getArquivos(List<ArquivoProcessamento> list, Integer ano) {
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.BPA_ITR_CONSOLIDADO.getPath(ano),EnumRelatorios.BPA_ITR_CONSOLIDADO, EnumTipoDemonstrativo.CONSOLIDADO));
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.BPA_ITR_INDIVIDUAL.getPath(ano),EnumRelatorios.BPA_ITR_INDIVIDUAL, EnumTipoDemonstrativo.INDIVIDUAL));
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.BPA_INDIVIDUAL.getPath(ano),EnumRelatorios.BPA_INDIVIDUAL, EnumTipoDemonstrativo.INDIVIDUAL));
		list.add(new ArquivoProcessamento(getRepositorio() + EnumRelatorios.BPA_CONSOLIDADO.getPath(ano),EnumRelatorios.BPA_CONSOLIDADO, EnumTipoDemonstrativo.CONSOLIDADO));
		return list;
	}

	private void addArquivos(List<ArquivoProcessamento> list1, List<ArquivoProcessamento> list2) {
		list1.addAll(list2);
	}
	
	@Override
	protected DemonstrativoFinanceiro toMap(CsvLinhaParser parser, ArquivoProcessamento arquivoProcessamento) {
		DemonstrativoFinanceiro result = new DemonstrativoFinanceiro();
		result.setCnpj(parser.getValue(DemonstrativoPatrimonialField.CNPJ_CIA));
		result.setDataReferencia(parser.getValue(DemonstrativoPatrimonialField.DT_REFER));
		result.setVersao(parser.getValue(DemonstrativoPatrimonialField.VERSAO));
		result.setGrupoDFP(parser.getValue(DemonstrativoPatrimonialField.GRUPO_DFP));
		result.setTipoRelatorio(arquivoProcessamento.getTipoRelatorio());
		result.setTipoDemonstrativo(arquivoProcessamento.getTipoDemonstrativo());
		result.setEscalaMoeda(parser.getValue(DemonstrativoPatrimonialField.ESCALA_MOEDA));
		result.setOrdemExercicio(parser.getValue(DemonstrativoPatrimonialField.ORDEM_EXERC));
		result.setDataFimExercicio(parser.getValue(DemonstrativoPatrimonialField.DT_FIM_EXERC));
		result.setCodigoConta(parser.getValue(DemonstrativoPatrimonialField.CD_CONTA));
		result.setDescricaoConta(parser.getValue(DemonstrativoPatrimonialField.DS_CONTA));
		result.setValorConta(result.getEscalaMoeda().convert(parser.getValue(DemonstrativoPatrimonialField.VL_CONTA)));
		result.setAno(result.getDataFimExercicio().getYear());
		result.setTrimestre(TrimestreUtils.getTrimestre(result.getDataFimExercicio()));
		result.generateId();
		return result;
	}

	@Override
	protected Boolean isFiltred(DemonstrativoFinanceiro item) {
		return item.getOrdemExercicio().equalsIgnoreCase("ÚLTIMO");//&& item.getCnpj().equals("04.128.563/0001-10");
	}

	@Override
	protected Class<DemonstrativoFinanceiro> getClassDb() {
		return DemonstrativoFinanceiro.class;
	}

	private Stream<Integer> getYears() {
		return IntStream.rangeClosed(2011, LocalDate.now().getYear()).boxed();
	}

}
