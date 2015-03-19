/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Karro
 *
 */
public class OneDayWeatherData {

	private String place;
	private Date day;

	private float mMaxTemperature = -300;
	private float mMinTemperature = Float.MAX_VALUE;

	private ArrayList<IWeatherData> mIWeatherData ;

	public void AddIWeatherData(IWeatherData IWeatherData)
	{
		if(mIWeatherData==null)mIWeatherData = new ArrayList<IWeatherData>();
		mIWeatherData.add(IWeatherData);
		CalculateData();
	}
	
	/***
	 * Calculated the aggregated data from the items in the List
	 */
	public void CalculateData(){
		day= mIWeatherData.get(0).getTime();
		
		
		for(int i = 0; i < mIWeatherData.size(); i++) {
			IWeatherData item = mIWeatherData.get(i);
			float temp = item.getFloatTemperature();
			//check if temp is min or max this day
			temp = item.getFloatTemperature();
			
			mMaxTemperature = Math.max(mMaxTemperature, temp);
			mMinTemperature = Math.min(mMinTemperature, temp);			
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OneDayWeatherData [place=" + place + ", day=" + day
				+ ", maxTemperature=" + mMaxTemperature + ", mintemperature="
				+ mMinTemperature + "]";
	}
	
	// Use @Override to avoid accidental overloading.
	   @Override public boolean equals(Object o) {
	     // Return true if the objects are identical.
	     // (This is just an optimization, not required for correctness.)
	     if (this == o) {
	       return true;
	     }

	     // Return false if the other object has the wrong type.
	     // This type may be an interface depending on the interface's specification.
	     if (!(o instanceof OneDayWeatherData)) {
	       return false;
	     }

	     // Cast to the appropriate type.
	     // This will succeed because of the instanceof, and lets us access private fields.
	     OneDayWeatherData lhs = (OneDayWeatherData) o;

	     // Check each field. Primitive fields, reference fields, and nullable reference
	     // fields are all treated differently.
	     boolean result = mMaxTemperature == lhs.mMaxTemperature &&
	    		 mMinTemperature == lhs.mMinTemperature &&
				 mIWeatherData==null ? lhs.mIWeatherData==null :  mIWeatherData.equals(lhs.mIWeatherData);
	    		
	     /*
	    		 
	    		 primitiveField == lhs.primitiveField &&
	             referenceField.equals(lhs.referenceField) &&
	             (nullableField == null ? lhs.nullableField == null
	                                    : nullableField.equals(lhs.nullableField));
	     */
	     
	     return result;
	   }
	
	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	/**
	 * @return the maxTemperature
	 */
	public float getMaxTemperature() {
		return mMaxTemperature;
	}

	/***
	 * 
	 * @return the maxTemperature
	 */
	public String getMaxTemperatureString(){
		return Float.toString(mMaxTemperature);
	}
	/**
	 * @param maxTemperature the maxTemperature to set
	 */
	public void setMaxTemperature(float maxTemperature) {
		this.mMaxTemperature = maxTemperature;
	}
	/**
	 * @return the mintemperature
	 */
	public float getMintemperature() {
		return mMinTemperature;
	}

	public String getMinTemperatureString(){
		return Float.toString(mMinTemperature);
	}
	/**
	 * @param mintemperature the mintemperature to set
	 */
	public void setMintemperature(float mintemperature) {
		this.mMinTemperature = mintemperature;
	}

	public String getDayString() {
		if(mIWeatherData!=null){
		int size = mIWeatherData.size();
		
		Date enddate =mIWeatherData.get(size-1).getEndTime();
		
		if(day==null)return " ";
		Calendar cal = new GregorianCalendar();
		cal.setTime(day);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		return (formater.format(day)+" - "+formater.format(enddate));
		}
		return " ";
	}


}
