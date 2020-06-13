package br.com.fabricio.analise.empresas.loader;

import java.util.stream.Stream;

import br.com.fabricio.analise.empresas.core.DataBaseService;
import br.com.fabricio.analise.empresas.core.FileLoader;

public class CvmLoader {

	private DataBaseService dataBaseService = new DataBaseService("localhost:27017");
	private final FileLoader<?>[] loaders;

	public static void main(String...strings) {
		CvmLoader loader = new CvmLoader("./arquivos");
		loader.loadAllFiles();		
	}
	
	public CvmLoader(String repositorioRoot) {
		super();
		loaders = new FileLoader[]{
			new InformacoesCadastraisLoader(repositorioRoot,dataBaseService),
			new PatrimonioAtivoLoader(repositorioRoot, dataBaseService),
			new PatrimonioPassivoLoader(repositorioRoot, dataBaseService),
			new DemonstrativoResultadosLoader(repositorioRoot, dataBaseService)
		};
	}
	
	
	public void loadAllFiles() {
		Stream.of(loaders).forEach( loader ->{
			loader.load();
		});
	}
	
	
}
