package karro.spike.weatherdataspike.YR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.util.Log;



@Root(strict=false, name="time")
public class YrWetherData implements IWeatherData {
	
	
	
	private String mId;
	
	@Attribute(name="from")
	private String  mTime;
	
	@Attribute(name="to")
	private String  mEndTime;	
	
	@Element(name="temperature")// hur tar man ut ett attribut på ett element? nästade objekt
	private Temperature mTemperature;
	
	@Element(name="precipitation")
	private Precipitation mPrecipitation;

	@Element(name="windDirection")
	private WindDirection mWindDirection;

	@Element(name="windSpeed")
	private WindSpeed mWindSpeed;
	
	@Element(name="pressure")
	private Pressure mPressure;
	
	@Element(name="symbol")
	private Symbol mSymbol;// symbol numberEx
	
	
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
	public Temperature getTemperature() {
		return mTemperature;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setTemperature(karro.spike.weatherdataspike.YR.Temperature)
	 */
	@Override
	public void setTemperature(Temperature temperature) {
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
	public Precipitation getPrecipitation() {
		return mPrecipitation;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setPrecipitation(karro.spike.weatherdataspike.YR.Precipitation)
	 */
	@Override
	public void setPrecipitation(Precipitation precipitation) {
		mPrecipitation = precipitation;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getWindDirection()
	 */
	@Override
	public WindDirection getWindDirection() {
		return mWindDirection;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setWindDirection(karro.spike.weatherdataspike.YR.WindDirection)
	 */
	@Override
	public void setWindDirection(WindDirection windDirection) {
		mWindDirection = windDirection;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getWindSpeed()
	 */
	@Override
	public WindSpeed getWindSpeed() {
		return mWindSpeed;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setWindSpeed(karro.spike.weatherdataspike.YR.WindSpeed)
	 */
	@Override
	public void setWindSpeed(WindSpeed windSpeed) {
		mWindSpeed = windSpeed;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getPressure()
	 */
	@Override
	public Pressure getPressure() {
		return mPressure;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setPressure(karro.spike.weatherdataspike.YR.Pressure)
	 */
	@Override
	public void setPressure(Pressure pressure) {
		mPressure = pressure;
	}

	

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#getSymbol()
	 */
	@Override
	public Symbol getSymbol() {
		return mSymbol;
	}

	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.YR.IWeatherData#setSymbol(karro.spike.weatherdataspike.YR.Symbol)
	 */
	@Override
	public void setSymbol(Symbol symbol) {
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
