package br.com.fabricio.analiseempresasservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;
import br.com.fabricio.analise.empresas.report.DemonstrativoFinanceiroChart;
import br.com.fabricio.analise.empresas.report.Filtro;

@RestController
public class AnaliseEmpresasController {
 
    @GetMapping(path = "/listaEmpresas")
    public String listaEmpresas() {
        return "custom";
    }

    @GetMapping(path = "/detalhaEmpresa/{cnpj}")
    public String detalheEmpresa(@PathVariable("cnpj") String cnpj) {
    	Pattern pattern = Pattern.compile("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})");
    	Matcher matcher = pattern.matcher(cnpj);
    	if( matcher.find()) {
    		StringBuilder sb = new StringBuilder();
    		sb.append(matcher.group(1));
    		sb.append(".");
    		sb.append(matcher.group(2));
    		sb.append(".");
    		sb.append(matcher.group(3));
    		sb.append("/");
    		sb.append(matcher.group(4));
    		sb.append("-");
    		sb.append(matcher.group(5));
    	   	DemonstrativoFinanceiroChart chart = new DemonstrativoFinanceiroChart();
        	Filtro filtro = new Filtro();
        	filtro.setCnpj(sb.toString());
        	filtro.setNivel(1);
        	filtro.setTipoDemonstrativo(EnumTipoDemonstrativo.CONSOLIDADO);
        	chart.print(filtro, "C:\\Users\\fabricio_asantos\\git\\fabricio\\analise-empresas\\app\\pages\\data\\dre.json");
     		}
    	
        return "OK";
    }
}