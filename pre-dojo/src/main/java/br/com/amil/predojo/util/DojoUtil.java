package br.com.amil.predojo.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DojoUtil {

	/**
	 * Formata a data para o padrão dd/MM/yyyy HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	/**
	 * Formata a data para o padrão dd/MM/yyyy HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Date parse(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Ordena um map pelos valores
	 * @param map
	 * @return
	 */
	public static <K,V extends Comparable<? super V>> List<Entry<K, V>> sortMapByValues(Map<K,V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

		Collections.sort(sortedEntries, 
				new Comparator<Entry<K,V>>() {
					@Override
					public int compare(Entry<K,V> e1, Entry<K,V> e2) {
						return e2.getValue().compareTo(e1.getValue());
					}
				});

		return sortedEntries;
	}
}
