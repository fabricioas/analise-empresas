package br.com.fabricio.analiseempresasservice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricio.analise.empresas.indicadores.IndicadorPVpaLoader;
import br.com.fabricio.analise.empresas.indicadores.IndicadorVpaLoader;
import br.com.fabricio.analise.empresas.indicadores.PrintIndicadorPVpa;

@RestController
public class IndicadoresController {
 

    @GetMapping(path = "indicadores/vpa")
    public String indicadoresVPA() {
		IndicadorVpaLoader indicador = new IndicadorVpaLoader();
		indicador.deleteAll();
		indicador.handler();
    	return "OK";
    }

    @GetMapping(path = "indicadores/pvpa")
    public String indicadoresPVpa() {
		IndicadorPVpaLoader indicador = new IndicadorPVpaLoader();
		indicador.deleteAll();
		indicador.handler();
        return "OK";
    }

    @GetMapping(path = "indicadores/pvpa/print")
    public String indicadoresPVpaPrint() {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	PrintIndicadorPVpa pvpa = new PrintIndicadorPVpa();
    	pvpa.print(new PrintStream(out));
        return out.toString();
    }

}