package br.com.fabricio.analiseempresasservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricio.analise.empresas.cotacoes.CotacoesLoader;
import br.com.fabricio.analise.empresas.download.DownloadCVM;
import br.com.fabricio.analise.empresas.loader.CvmLoader;
import br.com.fabricio.analise.empresas.loader.InformacaoCadastralB3Loader;

@RestController
public class PreparacaoArquivosController {
 

    @GetMapping(path = "cvm/download")
    public String downloadCVM() {
    	DownloadCVM downloadCVM = new DownloadCVM();
    	downloadCVM.downloadArquivos();
        return "OK";
    }

    @GetMapping(path = "cvm/load")
    public String loadCVM() {
    	CvmLoader loader = new CvmLoader("./arquivos");
    	loader.loadAllFiles();
        return "OK";
    }

    @GetMapping(path = "b3/cadastro/load")
    public String loadB3Cadastro() {
    	InformacaoCadastralB3Loader loader = new InformacaoCadastralB3Loader();
    	loader.start();
        return "OK";
    }

    @GetMapping(path = "b3/cotacoes/load")
    public String loadB3Cotacoes() {
    	CotacoesLoader loader = new CotacoesLoader();
    	loader.start();
        return "OK";
    }
}