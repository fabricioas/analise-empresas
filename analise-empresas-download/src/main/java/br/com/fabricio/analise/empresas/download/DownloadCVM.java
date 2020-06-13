package br.com.fabricio.analise.empresas.download;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.fabricio.analise.empresas.core.AtualizacaoArquivo;
import br.com.fabricio.analise.empresas.core.DataBaseService;
import br.com.fabricio.analise.empresas.core.DownloadFile;
import br.com.fabricio.analise.empresas.core.enuns.EnumRelatorios;



public class DownloadCVM {

	private static final String hostCvm = "http://dados.cvm.gov.br";
	private static final String DIRETORIO_ROOT = "./arquivos";
	private DataBaseService dbService = new DataBaseService("localhost:27017");

	public void downloadArquivos() {
		downloadOne(EnumRelatorios.INF_CADASTRO);
		downloadByYears(EnumRelatorios.ITR);
		downloadByYears(EnumRelatorios.DRE);
		downloadByYears(EnumRelatorios.BPA);
		downloadByYears(EnumRelatorios.BPP);
	}

	private void downloadOne(EnumRelatorios relatorio) {
		download(relatorio.getPath());
	}

	private void download(String path) {
		LocalDate date = findLastUpdateFile(path);
		DownloadFile downloadFile = new DownloadFile(hostCvm, path, DIRETORIO_ROOT + parseRepositorio(path), date);
		downloadFile.download();
		saveDataAtualizacao(path, downloadFile.getUpdateTime());
	}

	private void downloadByYears(final EnumRelatorios relatorio) {
		getYears().parallel().forEach(a -> download(relatorio.getPath(a)));
	}

	private Stream<Integer> getYears() {
		return IntStream.rangeClosed(2011, LocalDate.now().getYear()).boxed();
	}

	private String parseRepositorio(String path) {
		Integer endIndex = path.lastIndexOf('/');
		return path.substring(0, endIndex);
	}

	private LocalDate findLastUpdateFile(String path) {
		AtualizacaoArquivo atualizacaoArquivo = dbService.findById(path, AtualizacaoArquivo.class);
		if (atualizacaoArquivo == null) {
			return null;
		}
		return atualizacaoArquivo.getDataAtualizacao().toLocalDate();
	}

	private void saveDataAtualizacao(String path, LocalDateTime updateTime) {
		AtualizacaoArquivo atualizacaoArquivo = new AtualizacaoArquivo();
		atualizacaoArquivo.setId(path);
		atualizacaoArquivo.setDataAtualizacao(updateTime);
		dbService.save(atualizacaoArquivo);
	}
}
