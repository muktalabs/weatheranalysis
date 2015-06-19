package com.muktalabs.weatheranalysis.monthly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.muktalabs.weatheranalysis.monthly.MonthlyWeather;

public class MonthlyWeatherHbaseOperations {

	private static Configuration conf = null;
	public static final String TABLE_NAME = "MonthlyTemperature";
	public static final byte[] DAYLIGHT_FAMILY = Bytes.toBytes("daylight");
	public static final byte[] FULLDAY_FAMILY = Bytes.toBytes("fullday");

	public static final byte[] Q_MIN_TEMP = Bytes.toBytes("MIN_TEMP");
	public static final byte[] Q_MAX_TEMP = Bytes.toBytes("MAX_TEMP");
	public static final byte[] Q_AVG_TEMP = Bytes.toBytes("AVG_TEMP");
	public static final byte[] Q_PPT = Bytes.toBytes("PPT");
	public static final byte[] Q_DEWPT = Bytes.toBytes("DEWPT");

	static {
		conf = HBaseConfiguration.create();
	}

	public static void useTable() throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);

		if (admin.isTableAvailable(TABLE_NAME.getBytes())) {
			System.out.println(TABLE_NAME + " Table already exists!");
			System.out.println("Using Existing Table" + TABLE_NAME);
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(
					TABLE_NAME.getBytes());

			HColumnDescriptor dfamily = new HColumnDescriptor(DAYLIGHT_FAMILY);
			tableDesc.addFamily(dfamily);
			HColumnDescriptor ffamily = new HColumnDescriptor(FULLDAY_FAMILY);
			tableDesc.addFamily(ffamily);

			admin.createTable(tableDesc);
			System.out.println("Table Created " + TABLE_NAME + " ok.");
		}
	}

	public static HTable getTable() throws IOException {

		HTable table = new HTable(conf, TABLE_NAME);
		return table;
	}

	public static void addRecord(MonthlyWeather record) throws Exception {
		try {
			HTable htable = getTable();

			Put put = new Put(record.pack());

			put.addColumn(DAYLIGHT_FAMILY, Q_MIN_TEMP,
					Bytes.toBytes(record.getMinTemp()));
			put.addColumn(DAYLIGHT_FAMILY, Q_MAX_TEMP,
					Bytes.toBytes(record.getMaxTemp()));
			put.addColumn(DAYLIGHT_FAMILY, Q_AVG_TEMP,
					Bytes.toBytes(record.getAvgTemp()));
			put.addColumn(DAYLIGHT_FAMILY, Q_PPT,
					Bytes.toBytes(record.getPrecipitation()));
			put.addColumn(DAYLIGHT_FAMILY, Q_DEWPT,
					Bytes.toBytes(record.getDewpoint()));

			htable.put(put);
			System.out.println(" Record Inserted To Table " + htable.getName());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MonthlyWeather get(int stationCode, int year, int month)
			throws IOException {
		MonthlyWeather weather = null;
		HTable table = getTable();
		Scan scan = new Scan();
		scan.addFamily(DAYLIGHT_FAMILY);
	    MonthlyWeather startrow = new MonthlyWeather(stationCode, year, month);
		MonthlyWeather endrow = new MonthlyWeather(stationCode, year, month+1);

		scan.setStartRow(startrow.pack());
		scan.setStopRow(endrow.pack());

		ResultScanner scanner = table.getScanner(scan);

		Iterator<Result> resultIterator = scanner.iterator();
		if (resultIterator.hasNext()) {
			Result result = resultIterator.next();
			weather = parse(result);
		}
		return weather;
	}

	public static List<MonthlyWeather> get(int stationCode, int year)
			throws IOException {
		MonthlyWeather weather = null;
		HTable table = getTable();
		Scan scan = new Scan();
		scan.addFamily(DAYLIGHT_FAMILY);
		MonthlyWeather startrow = new MonthlyWeather(stationCode, year, 0);
		MonthlyWeather endrow = new MonthlyWeather(stationCode, year + 1, 0);

		scan.setStartRow(startrow.pack());
		scan.setStopRow(endrow.pack());

		ResultScanner scanner = table.getScanner(scan);
		
		
		List<MonthlyWeather> li = new ArrayList<MonthlyWeather>();   	
		Iterator<Result> resultIterator = scanner.iterator();
		while(resultIterator.hasNext()) {
			Result result = resultIterator.next();
			weather = parse(result);
		li.add(weather);
		
		}
     	
		return li;
	}

	public static Iterator<MonthlyWeather> getAllRecords() throws IOException {
		Iterator<MonthlyWeather> dataIterator = new Iterator<MonthlyWeather>() {
			HTable table = getTable();

			ResultScanner scanner = table.getScanner(DAYLIGHT_FAMILY);
			Iterator<Result> resultIterator = scanner.iterator();

			public boolean hasNext() {
				return resultIterator.hasNext();
			}

			public void remove() {
				resultIterator.remove();
			}

			public MonthlyWeather next() {
				Result result = resultIterator.next();
				return parse(result);
			}

		};

		return dataIterator;
	}

	private static MonthlyWeather parse(Result result) {

		byte[] key = result.getRow();
		byte[] value = result.getValue(DAYLIGHT_FAMILY, Q_MIN_TEMP);
		MonthlyWeather weather = MonthlyWeather.unpack(key);

		weather.setAvgTemp(Bytes.toFloat(result.getValue(DAYLIGHT_FAMILY,
				Q_AVG_TEMP)));
		weather.setMinTemp(Bytes.toFloat(result.getValue(DAYLIGHT_FAMILY,
				Q_MIN_TEMP)));
		weather.setMaxTemp(Bytes.toFloat(result.getValue(DAYLIGHT_FAMILY,
				Q_MAX_TEMP)));
		weather.setPrecipitation(Bytes.toFloat(result.getValue(DAYLIGHT_FAMILY,
				Q_PPT)));
		weather.setDewpoint(Bytes.toFloat(result.getValue(DAYLIGHT_FAMILY,
				Q_DEWPT)));

		return weather;
	}
}