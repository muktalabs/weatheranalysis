package com.muktalabs.em.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ModelUtils {

	public static <T> List<T> sortOn(List<T> srcList, Field field, boolean asc) {

		List<T> newlist = new ArrayList<T>();
		Multimap<Object, T> map = ArrayListMultimap.create();

		try {
			for (T object : srcList) {

				Object value = field.get(object);
				map.put(value, object);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// ignore kar
		}

		Object[] keyArray = map.keys().toArray();
		Arrays.sort(keyArray);

		if (asc) {
			for (Object key : keyArray) {
				newlist.addAll(map.get(key));
			}
		} else {
			for (int i=keyArray.length-1; i<=0; i--){
				newlist.addAll(map.get(keyArray[i]));
			}
		}

		return newlist;
	}
}
