package br.com.dickinho.hack.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;

import br.com.dickinho.hack.dto.LineChartDto;
import br.com.dickinho.hack.model.PriceHistory;
import br.com.dickinho.hack.repository.PriceHistoryRepository;

@Service
public class PriceHistoryService {
	@Autowired
	public PriceHistoryRepository priceHistorydRepository;
	
	private static Integer colorGenerator;
	private List<String> processed;
	private List<PriceHistory> items;
	
	public PriceHistoryService() {
		processed = new ArrayList<>();
		items = new ArrayList<>();
		colorGenerator = 0;
	}
	
	public List<String> findAllDates() {
		List<LocalDate> datas = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		priceHistorydRepository.findAllDates().forEach(dt -> datas.add(LocalDate.parse(dt,formatter)));
		Collections.sort(datas);
		return Lists.transform(datas, Functions.toStringFunction());
	}
	
	public List<LineChartDto> getChartData(String tipo){
		List<LineChartDto> response = new ArrayList<>();
		items = priceHistorydRepository.findByName(tipo);
		
		for (PriceHistory item : items) {
			if(!processed.contains(item.getModelo() + " - " + item.getEcommerce()))
				response.add(convertPriceToLineChartDto(item));
			
			processed.add(item.getModelo() + " - " + item.getEcommerce());
		}
		processed.clear();
		colorGenerator = 0;
		Collections.sort(response, new Comparator<LineChartDto>() {

			@Override
			public int compare(LineChartDto o1, LineChartDto o2) {
				return Double.compare(o1.getData().get(0), o2.getData().get(0));
			}});
		return response;
	}
	
	private LineChartDto convertPriceToLineChartDto(PriceHistory price) {
		LineChartDto line = new LineChartDto();
		line.setLabel(price.getModelo() + " - " + price.getEcommerce());
		colorGenerator++;
		line.setBorderColor(getLineColor());
		line.setData(getPrices(price.getModelo(), price.getEcommerce()));
		return line;
	}
	
	private List<Double> getPrices(String modelo, String ecommerce){
		List<Double> values = new ArrayList<>();
		List<PriceHistory> response = items.stream().filter(prd -> prd.getModelo().equals(modelo) && 
				prd.getEcommerce().equals(ecommerce)).collect(Collectors.toList());
		
		Collections.sort(response, new Comparator<PriceHistory>() {

			@Override
			public int compare(PriceHistory o1, PriceHistory o2) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
				LocalDate d1 =  LocalDate.parse(o1.getDataRegistro(),formatter);
				LocalDate d2 =  LocalDate.parse(o2.getDataRegistro(),formatter);
				return d1.compareTo(d2);
			}});
		
		response.forEach(filtro -> {
			if(!"Produto indisponível".equals(filtro.getPreco()))
				values.add(Double.parseDouble(filtro.getPreco().replace(",", ".")));
			else
				values.add(0.0);
		});
		return values;
	}
	
	private String getLineColor() {
		if(colorGenerator.equals(1))
			return "#020B1F";
		if(colorGenerator.equals(2))
			return "#78D107";
		if(colorGenerator.equals(3))
			return "#4707D1";
		if(colorGenerator.equals(4))
			return "#D1072F";
		if(colorGenerator.equals(5))
			return "#D17B07";
		if(colorGenerator.equals(6))
			return "#789D0D";
		if(colorGenerator.equals(7))
			return "#06823E";
		if(colorGenerator.equals(8))
			return "#202559";
		if(colorGenerator.equals(9))
			return "#A3AD05";
		if(colorGenerator.equals(10))
			return "#AD0D05";
		if(colorGenerator.equals(11))
			return "#D1F325";
		if(colorGenerator.equals(12))
			return "#02923D";
		if(colorGenerator.equals(13))
			return "#1BA8A8";
		if(colorGenerator.equals(14))
			return "#BF0785";
		if(colorGenerator.equals(15))
			return "#5DA70F";
		if(colorGenerator.equals(16))
			return "#D14269";
		if(colorGenerator.equals(17))
			return "#B4752A";
		if(colorGenerator.equals(18))
			return "#1D375A";
		if(colorGenerator.equals(19))
			return "#020B1F";
		if(colorGenerator.equals(20))
			return "#020B1F";
		if(colorGenerator.equals(21))
			return "#020B1F";
		if(colorGenerator.equals(22))
			return "#020B1F";
		if(colorGenerator.equals(23))
			return "#020B1F";
		if(colorGenerator.equals(24))
			return "#020B1F";
		if(colorGenerator.equals(25))
			return "#020B1F";
		if(colorGenerator.equals(26))
			return "#020B1F";
		if(colorGenerator.equals(27))
			return "#020B1F";
		if(colorGenerator.equals(28))
			return "#020B1F";
		if(colorGenerator.equals(29))
			return "#020B1F";
		if(colorGenerator.equals(30))
			return "#020B1F";
		if(colorGenerator.equals(31))
			return "#020B1F";
		if(colorGenerator.equals(32))
			return "#020B1F";
		if(colorGenerator.equals(33))
			return "#020B1F";
		if(colorGenerator.equals(34))
			return "#020B1F";
		if(colorGenerator.equals(35))
			return "#020B1F";
		if(colorGenerator.equals(36))
			return "#020B1F";
		if(colorGenerator.equals(37))
			return "#020B1F";
		if(colorGenerator.equals(38))
			return "#020B1F";
		if(colorGenerator.equals(39))
			return "#020B1F";
		if(colorGenerator.equals(40))
			return "#020B1F";
		
		return "blue";
	}
}
