package br.com.fabricio.analise.empresas.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fabricio.analise.empresas.core.DataBaseService;
import br.com.fabricio.analise.empresas.core.entities.DemonstrativoFinanceiro;
import br.com.fabricio.analise.empresas.core.entities.InformacaoCadastral;
import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;

public class DemonstrativoFinanceiroChart {

	private DataBaseService dataBaseService = new DataBaseService("localhost:27017");

	public void print(Filtro filtro, String fileOut) {
		try {
			Files.deleteIfExists(Paths.get(fileOut));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
		try (OutputStream outputStream = Files.newOutputStream(Paths.get(fileOut), StandardOpenOption.CREATE);) {
			PrintStream printStream = new PrintStream(outputStream);
			List<Chart> dados = findDemonstrativo(filtro.getCnpj(), filtro.getTipoDemonstrativo()).stream()
					.filter(f -> filterDemonstrativo(f, filtro.getNivel()))
					.sorted((a, b) -> a.getAno().compareTo(b.getAno()))
					.sorted((a, b) -> a.getCodigoConta().compareTo(b.getCodigoConta()))
					.collect(Collectors.groupingBy(k -> Pair.of(k.getCodigoConta(), k.getDescricaoConta()))).entrySet()
					.stream().map(Chart::new).sorted((a, b) -> {
						String s1 = a.getSerie().split("-")[0];
						String s2 = b.getSerie().split("-")[0];
						return s1.compareTo(s2);
					}).collect(Collectors.toList());

			InformacaoCadastral informacaoCadastral = findInformacoesCadastrais(filtro.getCnpj());
			
			DadosEmpresa dadosEmpresa = new DadosEmpresa();
			dadosEmpresa.setCnpj(informacaoCadastral.getId());
			dadosEmpresa.setNome(informacaoCadastral.getNomeComercial());
			dadosEmpresa.setRamoAtividade(informacaoCadastral.getSetorAtividade());
			dadosEmpresa.setDados(dados);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dadosEmpresa);
			printStream.print(json);
			printStream.flush();
			printStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Boolean filterDemonstrativo(DemonstrativoFinanceiro dre, Integer nivel) {
		return NivelReport.fitro(nivel).matcher(dre.getCodigoConta()).find() && dre.getTrimestre().equals(4);
	}

	public List<DemonstrativoFinanceiro> findDemonstrativo(String cnpj, EnumTipoDemonstrativo tipoDemonstrativo) {
		Map<String, Object> filter = new HashMap<>();
		filter.put("cnpj", cnpj);
		filter.put("tipoDemonstrativo", tipoDemonstrativo);
		return dataBaseService.find(filter, DemonstrativoFinanceiro.class);
	}

	public InformacaoCadastral findInformacoesCadastrais(String cnpj) {
		return dataBaseService.findById(cnpj, InformacaoCadastral.class);
	}

}

class DadosEmpresa {
	private String cnpj;
	private String nome;
	private String ramoAtividade;
	private List<Chart> dados;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public List<Chart> getDados() {
		return dados;
	}

	public void setDados(List<Chart> dados) {
		this.dados = dados;
	}

}

class Chart {
	private List<DemonstrativoFinanceiro> data = new LinkedList<>();
	private String serie;

	public Chart(Entry<Pair<String, String>, List<DemonstrativoFinanceiro>> entry) {
		super();
		this.serie = entry.getKey().getFirst() + " - " + entry.getKey().getSecond();
		this.data = entry.getValue();
	}

	public List<String> getLabels() {
		return data.stream().map(DemonstrativoFinanceiro::getAno).map(m -> m.toString()).collect(Collectors.toList());
	}

	public List<String> getData() {
		return data.stream().map(DemonstrativoFinanceiro::getValorConta).map(BigDecimal::toEngineeringString)
				.collect(Collectors.toList());
	}

	public String getSerie() {
		return serie;
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
