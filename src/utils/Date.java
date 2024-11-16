package utils;

public class Date {
	
	private int year;
	private int month;
	private int day;
	
	private int hour;
	private int minute;
	private int seconds;
	
    public Date(String date) {
		String[] Div1 = date.replace("'", "").split(" ");
		String[] Date = Div1[0].split(":");
		String[] Hours = Div1[1].split(":");

		year = Integer.parseInt(Date[0]);
		month = Integer.parseInt(Date[1]);
		day = Integer.parseInt(Date[2]);

		hour = Integer.parseInt(Hours[0]);
		minute = Integer.parseInt(Hours[1]);
		seconds = Integer.parseInt(Hours[2]);
    }
    
    public Date(int y, int m, int d, int h, int min, int s) {
    	year = y;
    	month = m;
    	day = d;
    	hour = h;
    	minute = min;
    	seconds = s;
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o instanceof Date) {
    		Date d = (Date) o;
    		return compareTo(d) == 0;
    	}
    	else
    	{
    		return super.equals(o);
    	}
    }
    
    public int compareTo(Date d) {
    	if(year < d.year) return -1;
    	else if(year > d.year) return 1;
    	else
    	{
    		if(month < d.month) return -1;
    		else if(month > d.month) return 1;
    		else
    		{
    			if(day < d.day) return -1;
    			else if(day > d.day) return 1;
    			else
    			{
    				if(hour < d.hour) return -1;
    				else if(hour > d.hour) return 1;
    				else
    				{
    					if(minute < d.minute) return -1;
    					else if(minute > d.minute) return 1;
    					else
    					{
    						if(seconds < d.seconds) return -1;
    						else if(seconds > d.seconds) return 1;
    						else
    						{
    							return 0;
    						}
    					}
    				}
    			}
    		}
    	}
    }
    
    
    public String getDate() {
    	String d = "" + day;
    	if(day < 10) d = "0" + day;
    	
    	String m = "" + month;
    	if(month < 10) m = "0" + month;
    	
    	String y = "" + year;
    	if(year < 10) y = "0" + year;
    	
    	return d + "/" + m + "/" + y;
    }
    
    public String getTime() {
    	
    	
    	String h = "" + hour;
    	if(hour < 10) h = "0" + hour;
    	
    	String m = "" + minute;
    	if(minute < 10) m = "0" + minute;
    	
    	String s = "" + seconds;
    	if(seconds < 10) s = "0" + seconds;
    	
    	
    	return h + ":" + m + ":" + s;
    }

    public String toString() {
    	return "'" + year + ":" + month + ":" + day + " " + hour + ":" + minute + ":" + seconds + "'";
    }


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public int getHour() {
		return hour;
	}


	public void setHour(int hour) {
		this.hour = hour;
	}


	public int getMinute() {
		return minute;
	}


	public void setMinute(int minute) {
		this.minute = minute;
	}


	public int getSeconds() {
		return seconds;
	}


	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
       
}
