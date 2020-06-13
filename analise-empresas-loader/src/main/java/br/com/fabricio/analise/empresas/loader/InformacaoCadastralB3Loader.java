package br.com.fabricio.analise.empresas.loader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClients;

import br.com.fabricio.analise.empresas.core.converters.DateConverter;
import br.com.fabricio.analise.empresas.core.converters.LongConverter;
import br.com.fabricio.analise.empresas.core.entities.AtivoBolsa;
import br.com.fabricio.analise.empresas.core.entities.InformacaoCadastral;

public class InformacaoCadastralB3Loader {
	private static final String URL = "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM=%s";
	private static final Pattern pattern = Pattern.compile("[A-Z]{4}[0-9]+", Pattern.CASE_INSENSITIVE);

	private MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://localhost:27017"),
			"analise-empresas");
	private DateConverter dateConverter = new DateConverter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	private LongConverter longConverter = new LongConverter();
	
	private static Integer count = 0;
	
	public void start(){
		deleteAll();
		findInformacoesCadastrais()
		.parallelStream()
		.forEach(ic -> {
			try {
				handler(ic);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void handler(InformacaoCadastral informacaoCadastral) throws Exception {
		String site = String.format(URL, informacaoCadastral.getCodigoCvm());
		synchronized (count) {
			System.out.print(count++);
			System.out.print(" => ");
			System.out.println(site);
		}
		InputStream stream = new URL(site).openStream();
		Document document = Jsoup.parse(stream, StandardCharsets.UTF_8.displayName(), String.format(URL, informacaoCadastral.getCodigoCvm()));

		Element element = document.body();
		DadosB3 dadosB3 = extractCodigos(element);
		if( isNotEmpty(dadosB3) ) {
			CapitalSocial capitalSocial = extractCapitalSocial(element);
			List<AtivoBolsa> ativos = dadosB3.getTickets().stream().map( m ->{
				AtivoBolsa ativoBolsa = new AtivoBolsa();
				ativoBolsa.setCnpj(informacaoCadastral.getId());
				ativoBolsa.setCodigoCvm(informacaoCadastral.getCodigoCvm());
				ativoBolsa.setCodigoPregao(dadosB3.getPregao());
				ativoBolsa.setDataAtualizacaoCapitalSocial( dateConverter.convertToObject(capitalSocial.getDataAtualizacao()));
				ativoBolsa.setQtdAcoesOrdinarias(longConverter.convertToObject(capitalSocial.getQtdAcoesOrdinarias()));
				ativoBolsa.setQtdAcoesPreferenciais(longConverter.convertToObject(capitalSocial.getQtdAcoesPreferenciais()));
				ativoBolsa.setTicket(m);
				return ativoBolsa;
			}).collect(Collectors.toList());
			saveAll(ativos);
		}
	}

	private boolean isNotEmpty(DadosB3 dadosB3) {
		if( dadosB3 == null ) {
			return false;
		}
		if( dadosB3.getTickets() == null ) {
			return false;
		}
		return !dadosB3.getTickets().isEmpty();
	}

	private List<InformacaoCadastral> findInformacoesCadastrais() {
		return mongoOps.findAll(InformacaoCadastral.class);
	}

	private DadosB3 extractCodigos(Element body) {
		final DadosB3 dadosB3 = new DadosB3();
		Element accordionDados = body.getElementById("accordionDados");
		if (accordionDados != null) {
			Elements trs = accordionDados.getElementsByTag("tr");
			trs.forEach(tr -> {
				if (tr.child(0).text().equalsIgnoreCase("Nome de Pregão:")) {
					dadosB3.setPregao(tr.child(1).text());
				}
				if (tr.child(0).text().equalsIgnoreCase("Códigos de Negociação:")) {
					List<String> tickets = tr.select(".LinkCodNeg")
							.stream()
							.map(Element::text)
							.distinct()
							.filter( this::isTicketValido )
							.collect(Collectors.toList());
					dadosB3.setTickets(tickets);
					tr.getElementsByTag("strong").forEach(e -> {
						if (e.text().equalsIgnoreCase("códigos cvm:")) {
							dadosB3.setCvm(e.nextSibling().nextSibling().toString());
						}
					});

				}
			});
		}
		return dadosB3;
	}

	private Boolean isTicketValido(String ticket) {
		return pattern.matcher(ticket).find();
	}
	
	private CapitalSocial extractCapitalSocial(Element body) {
		final CapitalSocial capitalSocial = new CapitalSocial();
		Element divComposicaoCapitalSocial = body.getElementById("divComposicaoCapitalSocial");
		Elements thead = divComposicaoCapitalSocial.getElementsByTag("thead");
		Elements ths = thead.get(0).getElementsByTag("th");
		capitalSocial.setDataAtualizacao(ths.get(0).text().replaceAll("-", "").trim());

		Elements tbody = divComposicaoCapitalSocial.getElementsByTag("tbody");
		Elements trs = tbody.get(0).getElementsByTag("tr");
		trs.forEach(tr -> {
			if (tr.children().get(0).text().equalsIgnoreCase("ordinárias")) {
				capitalSocial.setQtdAcoesOrdinarias(tr.children().get(1).text());
			} else if (tr.children().get(0).text().equalsIgnoreCase("preferenciais")) {
				capitalSocial.setQtdAcoesPreferenciais(tr.children().get(1).text());
			}
		});

		System.out.println(capitalSocial);
		return capitalSocial;
	}

	private void deleteAll() {
		mongoOps.dropCollection(AtivoBolsa.class);
	}

	private void saveAll(List<AtivoBolsa> list) {
		BulkOperations operations = mongoOps.bulkOps(BulkMode.UNORDERED, AtivoBolsa.class);
		operations.insert(list);
		operations.execute();
	}
}

class CapitalSocial {
	private String dataAtualizacao;
	private String qtdAcoesPreferenciais;
	private String qtdAcoesOrdinarias;

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getQtdAcoesPreferenciais() {
		return qtdAcoesPreferenciais;
	}

	public void setQtdAcoesPreferenciais(String qtdAcoesPreferenciais) {
		this.qtdAcoesPreferenciais = qtdAcoesPreferenciais;
	}

	public String getQtdAcoesOrdinarias() {
		return qtdAcoesOrdinarias;
	}

	public void setQtdAcoesOrdinarias(String qtdAcoesOrdinarias) {
		this.qtdAcoesOrdinarias = qtdAcoesOrdinarias;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CapitalSocial [dataAtualizacao=");
		builder.append(dataAtualizacao);
		builder.append(", qtdAcoesPreferenciais=");
		builder.append(qtdAcoesPreferenciais);
		builder.append(", qtdAcoesOrdinarias=");
		builder.append(qtdAcoesOrdinarias);
		builder.append("]");
		return builder.toString();
	}

}

class DadosB3 {
	private String cvm;
	private String pregao;
	private List<String> tickets;

	public String getCvm() {
		return cvm;
	}

	public void setCvm(String cvm) {
		this.cvm = cvm;
	}

	public String getPregao() {
		return pregao;
	}

	public void setPregao(String pregao) {
		this.pregao = pregao;
	}

	public List<String> getTickets() {
		return tickets;
	}

	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("codigos [cvm=");
		builder.append(cvm);
		builder.append(", pregao=");
		builder.append(pregao);
		builder.append(", tickets=");
		builder.append(tickets);
		builder.append("]");
		return builder.toString();
	}

}