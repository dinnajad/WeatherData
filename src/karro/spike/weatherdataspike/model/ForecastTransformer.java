/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import karro.spike.weatherdataspike.YR.Forecast;
import karro.spike.weatherdataspike.YR.YrWetherData;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class ForecastTransformer {

	private static final String TAG = "ForecastTransformer";

	public static OneDayWeatherData getTodaysWeather(Forecast fc){
		Date today = new Date();
		List<YrWetherData> dataList = fc.getList();
		
		return findWetherdataForDate(today, dataList);
	}

	/**
	 * @param today the date to find data for
	 * @param dataList
	 */
	private static OneDayWeatherData findWetherdataForDate(Date today , List<YrWetherData> dataList) {
		OneDayWeatherData odwd =  new OneDayWeatherData();
		float maxTemp = 0;
		float minTemp = 0;
		
		for (int i = 0; i < dataList.size(); i++) {

			//getItem
			YrWetherData item = dataList.get(i);
			//check date
			String fromTime = item.getTime();
			String endTime = item.getEndTime();

			Date formDate = parseDate(fromTime);
			Date endDate = parseDate(endTime);
			

			if(datesEqual(endDate, today)){			
			Float temp = Float.parseFloat(item.getTemperature().getTemperature());//TODO refactorera så man slipper ett steg
			if(temp>maxTemp)maxTemp=temp;
			if(temp<minTemp)minTemp= temp;			
			}
		}
		odwd.setDay(today);
		odwd.setMaxTemperature(maxTemp);
		odwd.setMintemperature(minTemp);
		//TODO odwd.setPlace(place);
		return odwd;
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


		cal.setTime(date);
		int secondDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		return dayOfYear == secondDayOfYear;
	}

	public static Date setTimeToMidnight(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime( date );
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * @param endTime
	 */
	private static Date parseDate(String endTime) {
		Date date;
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		try {  
			date = format.parse(endTime);  
			System.out.println(date);  

		} catch (ParseException e) {  
			Log.e(TAG,"" + e);
			date = new Date();
			//TODO hur hantera parse Exception better
		}

		return date;
	}

}
