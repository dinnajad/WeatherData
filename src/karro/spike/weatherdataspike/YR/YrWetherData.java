package karro.spike.weatherdataspike.YR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import karro.spike.weatherdataspike.model.IWeatherData;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.util.Log;


/***
 * DataObject
 * @author Karro
 *
 */
@Root(strict=false, name="time")
public class YrWetherData implements IWeatherData {
	
	
	
	private String mId;
	
	@Attribute(name="from")
	private String  mTime;
	
	@Attribute(name="to")
	private String  mEndTime;	
	
	@Element(name="temperature")// hur tar man ut ett attribut på ett element? nästade objekt
	private YrTemperature mTemperature;
	
	@Element(name="precipitation")
	private YrPrecipitation mPrecipitation;

	@Element(name="windDirection")
	private YrWindDirection mWindDirection;

	@Element(name="windSpeed")
	private YrWindSpeed mWindSpeed;
	
	@Element(name="pressure")
	private YrPressure mPressure;
	
	@Element(name="symbol")
	private YrSymbol mSymbol;// symbol numberEx
	
	
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#toString()
	 */	
	@Override
	public String toString() {
		return "YrWetherData [mTime=" + mTime + ", mEndTime=" + mEndTime
				+ ", mTemperature=" + mTemperature + "]";
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		mId = id;
	}

	
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getTime()
	 */
	@Override
	public Date getTime() {
		return parseDate(mTime);
	}
	
	/**
	 * @param time
	 */
	private static Date parseDate(String time) {
		Date date;
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		try {  
			date = format.parse(time);  
			//System.out.println(date);  

		} catch (ParseException e) {  
			Log.e("YrWetherData","" + e);
			date = new Date();
			//TODO hur hantera parse Exception better
		}

		return date;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setTime(java.lang.String)
	 */
	@Override
	public void setTime(String time) {
		mTime = time;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getTemperature()
	 */
	@Override
	public YrTemperature getTemperature() {
		return mTemperature;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setTemperature(karro.spike.weatherdataspike.YR.YrTemperature)
	 */
	@Override
	public void setTemperature(YrTemperature temperature) {
		mTemperature = temperature;
	}

	
	
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getEndTime()
	 */
	@Override
	public Date getEndTime() {
		return parseDate(mEndTime);
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setEndTime(java.lang.String)
	 */
	@Override
	public void setEndTime(String endTime) {
		mEndTime = endTime;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getPrecipitation()
	 */
	@Override
	public YrPrecipitation getPrecipitation() {
		return mPrecipitation;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setPrecipitation(karro.spike.weatherdataspike.YR.YrPrecipitation)
	 */
	@Override
	public void setPrecipitation(YrPrecipitation precipitation) {
		mPrecipitation = precipitation;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getWindDirection()
	 */
	@Override
	public YrWindDirection getWindDirection() {
		return mWindDirection;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setWindDirection(karro.spike.weatherdataspike.YR.YrWindDirection)
	 */
	@Override
	public void setWindDirection(YrWindDirection windDirection) {
		mWindDirection = windDirection;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getWindSpeed()
	 */
	@Override
	public YrWindSpeed getWindSpeed() {
		return mWindSpeed;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setWindSpeed(karro.spike.weatherdataspike.YR.YrWindSpeed)
	 */
	@Override
	public void setWindSpeed(YrWindSpeed windSpeed) {
		mWindSpeed = windSpeed;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getPressure()
	 */
	@Override
	public YrPressure getPressure() {
		return mPressure;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setPressure(karro.spike.weatherdataspike.YR.YrPressure)
	 */
	@Override
	public void setPressure(YrPressure pressure) {
		mPressure = pressure;
	}

	

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getSymbol()
	 */
	@Override
	public YrSymbol getSymbol() {
		return mSymbol;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setSymbol(karro.spike.weatherdataspike.YR.YrSymbol)
	 */
	@Override
	public void setSymbol(YrSymbol symbol) {
		mSymbol = symbol;
	}

	@Override
	public Float getFloatTemperature() {
	Float temp= null;
		try{
		temp = Float.parseFloat(mTemperature.getTemperature());
	}catch (NumberFormatException e){
		//ignorerar och returnerar null
	}
		return temp;
	}

}
