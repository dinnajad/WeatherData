/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import karro.spike.weatherdataspike.YR.YrForecast;
import karro.spike.weatherdataspike.YR.YrWetherData;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class ForecastTransformer {

	private static final String TAG = "ForecastTransformer";

	public static OneDayWeatherData getTodaysWeather(YrForecast fc){
		Date today = new Date();//TODO use calendar to set time correct now its UTC tror jag
		List<YrWetherData> dataList = fc.getList();
		ArrayList<IWeatherData> aDataList = new ArrayList<IWeatherData>();
		aDataList.addAll(dataList);
		ArrayList<OneDayWeatherData> resultList =groupWeatherDataFor24h(aDataList);
		return resultList.get(0);
	}

	/**
	 * @param selectedDay the date to find data 
	 * @param dataList
	 */
	@Deprecated
	private static OneDayWeatherData findWeatherdataForDate(Date selectedDay , List<YrWetherData> dataList) {

		OneDayWeatherData odwd =  new OneDayWeatherData();

		for (int i = 0; i < dataList.size(); i++) {

			//getItem
			IWeatherData item = dataList.get(i);
			//check date			
			Date fromDate = item.getTime();		

			if(datesEqual(fromDate, selectedDay)){					
				odwd.AddIWeatherData(item);
			}
		}

		return odwd;
	}

	public static ArrayList<OneDayWeatherData> groupWeatherDataFor24h( List<IWeatherData> dataList) {
		Date compareDate = new Date();
		return groupWeatherDataFor24h(compareDate,dataList);
	}

	/***
	 * groups the items into OneDayWeatherData items
	 * @param dataList
	 * @return grouped List
	 */
	public static ArrayList<OneDayWeatherData> groupWeatherDataFor24h(Date compareDate, List<IWeatherData> dataList) {

		ArrayList<OneDayWeatherData> resultList = new ArrayList<OneDayWeatherData>();
		OneDayWeatherData grupp =  new OneDayWeatherData();
		resultList.add(grupp);
		
		for (int i = 0; i < dataList.size(); i++) {

			//getItem
			IWeatherData item = dataList.get(i);
			//check date												
			Date fromDate = item.getTime();	

			long timediff = calcTimeDiff(compareDate, fromDate);

			if(timediff>(24 * 60 *60 * 1000)){				
				compareDate = add24hTime(compareDate);
				grupp =  new OneDayWeatherData();	
				resultList.add(grupp);		
			}			
			grupp.AddIWeatherData(item);//L�gg till i gruppen			
		}
		return resultList;
	}
	
	private static Date add24hTime(Date startDate){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		cal.add(Calendar.HOUR, 24);
		return cal.getTime();		
	}

	/**
	 * @param compareDate
	 * @param fromDate
	 */
	private static long calcTimeDiff(Date compareDate, Date fromDate) {
		GregorianCalendar compareCal = new GregorianCalendar();
		compareCal.setTime(compareDate);
		long compareTime = compareCal.getTimeInMillis();

		GregorianCalendar fromTimeCal = new GregorianCalendar();
		fromTimeCal.setTime(fromDate);
		long fromTime = fromTimeCal.getTimeInMillis();

		long timediff= fromTime -compareTime;
		return timediff;
	}

	/***
	 * groups the items into OneDayWeatherData items based on date obs. compares only the date component of the timestamp
	 * @param dataList
	 * @return grouped List
	 */
	public static ArrayList<OneDayWeatherData> groupWeatherDataForDate( List<IWeatherData> dataList) {
		Date compareDate = new Date(1);
		ArrayList<OneDayWeatherData> resultList = new ArrayList<OneDayWeatherData>();
		OneDayWeatherData odwd =  new OneDayWeatherData();

		for (int i = 0; i < dataList.size(); i++) {

			//getItem
			IWeatherData item = dataList.get(i);
			//check date												
			Date fromDate = item.getTime();			

			if(!datesEqual(fromDate, compareDate)){				
				compareDate = fromDate;
				odwd =  new OneDayWeatherData();	
				resultList.add(odwd);		
			}			
			odwd.AddIWeatherData(item);//L�gg till i gruppen			
		}
		return resultList;
	}

	/***
	 * 
	 * @param date
	 * @return true if it is today
	 */
	private static boolean datesEqual(Date date, Date date2 ) {

		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);


		cal.setTime(date2);
		int secondDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		boolean result = (dayOfYear == secondDayOfYear);
		return result;
	}

	/**
	 * @param endTime
	 */
	private static Date parseDate(String endTime) {
		Date date;
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		try {  
			date = format.parse(endTime);  
			//System.out.println(date);  

		} catch (ParseException e) {  
			Log.e(TAG,"" + e);
			date = new Date();
			//TODO hur hantera parse Exception better
		}

		return date;
	}

}
