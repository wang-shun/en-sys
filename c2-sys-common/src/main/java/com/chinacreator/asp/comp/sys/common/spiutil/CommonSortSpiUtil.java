package com.chinacreator.asp.comp.sys.common.spiutil;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 通用排序spi类
 * 
 * @author 彭盛
 * 
 */
public class CommonSortSpiUtil {

	/**
	 * spi排序，数值大的排序在前面
	 * 
	 * @param maps
	 *            需要排序的集合
	 * @return 排序完成的集合
	 */
	public static <T extends CommonSortSpi> Map sortSpi(Map<String, T> maps) {
		return sortSpi(maps, true);
	}

	/**
	 * spi排序
	 * 
	 * @param maps
	 *            需要排序的集合
	 * @param reverse
	 *            排序方向<br>
	 *            true:数值大的排序在前面<br>
	 *            false:数值小的排序在前面
	 * @return 排序完成的集合
	 */
	public static <T extends CommonSortSpi> Map sortSpi(Map<String, T> maps,
			final boolean reverse) {
		List list = new LinkedList(maps.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (reverse) {
					return ((Comparable) ((CommonSortSpi) ((Map.Entry) o2)
							.getValue()).getPriority())
							.compareTo((((CommonSortSpi) ((Map.Entry) o1)
									.getValue()).getPriority()));
				} else {
					return ((Comparable) ((CommonSortSpi) ((Map.Entry) o1)
							.getValue()).getPriority())
							.compareTo((((CommonSortSpi) ((Map.Entry) o2)
									.getValue()).getPriority()));
				}
			}
		});
		Map result = new LinkedHashMap();

		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}
