package br.com.fabricio.analise.empresas.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class FileLoader<T extends EntityBasic> {

	private static final Logger log = LoggerFactory.getLogger(FileLoader.class);

	private String repositorio;
	private DataBaseService baseService;

	public FileLoader(String repositorio, DataBaseService baseService) {
		super();
		this.repositorio = repositorio;
		this.baseService = baseService;
	}

	public void load(){
		getListOfFiles(repositorio).forEach(f -> {
			try {
				loadFile(f);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	protected String getRepositorio() {
		return repositorio;
	}
	
	private void loadFile(ArquivoProcessamento f) throws IOException {
		log.info("Carregando o Arquivo '{}'", f);
		Path path = Paths.get(f.getFileName());
		if (isModifield(path.toFile())) {
			List<T> list = Files.lines(path)
					.skip(1)
					.map(CsvLinhaParser::new)
					.map(l -> toMap(l,f))
					.filter(this::isFiltred)
					.distinct()
					.collect(Collectors.toList());
			saveAll(list,path.toFile());
			log.info("Carrega o Arquivo '{}' com {} registros", f, list.size());
		}
	}

	private boolean isModifield(File file) {
		LocalDate lastUpdate = findLastUpdateFile(file.getName());
		LocalDateTime updateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()),
				ZoneOffset.UTC);
		return lastUpdate.isBefore(updateTime.toLocalDate());
	}

	protected abstract List<ArquivoProcessamento> getListOfFiles(String repositorio);

	protected abstract T toMap(CsvLinhaParser parser, ArquivoProcessamento arquivoProcessamento);

	protected abstract Boolean isFiltred(T item);

	protected abstract Class<T> getClassDb();

	private void saveAll(List<T> list, File file) {
		baseService.saveAll(list, getClassDb());
		LocalDateTime updateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()),
				ZoneOffset.UTC);
		AtualizacaoArquivo atualizacaoArquivo = new AtualizacaoArquivo();
		atualizacaoArquivo.setId(file.getName());
		atualizacaoArquivo.setDataAtualizacao(updateTime);
		baseService.save(atualizacaoArquivo);
	}
	
	private LocalDate findLastUpdateFile(String path) {
		AtualizacaoArquivo atualizacaoArquivo = baseService.findById(path, AtualizacaoArquivo.class);
		if (atualizacaoArquivo == null) {
			return LocalDate.MIN;
		}
		return atualizacaoArquivo.getDataAtualizacao().toLocalDate();
	}

}
