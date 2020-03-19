package br.com.fabricio.cvm;

import br.com.fabricio.cvm.config.TransactonManager;
import br.com.fabricio.cvm.repositories.ArquivoCVMRepositorio;

public class CvmRun {

	public static void main(String[] args) {
		System.setProperty("debbug","true");

//		for (Integer ano = 2010; ano <= LocalDate.now().getYear(); ano++) {
//			DownloadArquivos downloadArquivos = new DownloadArquivos(ano.toString());
//			downloadArquivos.downloadDRE();
//			if( downloadArquivos.isLoad() ) {
//				System.out.println(downloadArquivos.getCsvDRE());
//			}
//		}
		DownloadArquivos downloadArquivos = new DownloadArquivos();
		String path = "http://dados.cvm.gov.br/dados/CIA_ABERTA/CAD/DADOS/";
		String fileName = "inf_cadastral_cia_aberta.csv";
		downloadArquivos.download(path,fileName);

		downloadArquivos = new DownloadArquivos();
		String ano = "2019";
		path = "http://dados.cvm.gov.br/dados/CIA_ABERTA/DOC/DFP/DRE/DADOS/";
		fileName = "dre_cia_aberta_" + ano + ".zip";

		downloadArquivos.download(path,fileName);
		TransactonManager tm = new TransactonManager();
		tm.begin();
		ArquivoCVMRepositorio repositorio = new ArquivoCVMRepositorio(tm);
		repositorio.getCache().forEach((k) -> System.out.println(k.getKey()));
		tm.end();
	}

}
