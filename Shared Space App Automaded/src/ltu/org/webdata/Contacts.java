package ltu.org.webdata;

public class Contacts {
	
	
	private String name;
	
	private String fb_status;
	
	private String fb_location;
	
	private String fb_calendar;
	
	private String news_header;
	
	private String blog_title;
	
	private String social_strength;
		
	public Contacts(){
		
	}
	
	
	public Contacts(String name, String fb_status, String fb_location,String fb_calendar,String news_header, String blog_title, String social_strength) 
	{
	    this.name = name;
	    this.fb_status = fb_status;
	    this.fb_location=fb_location;
	    this.fb_calendar  = fb_calendar;
	    this.news_header = news_header;
	    this.blog_title = blog_title;
	    this.social_strength = social_strength;
	
    }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getFbStatus() {
		return fb_status;
	}

	public void setFbStatus(String fb_Status) {
		this.fb_status = fb_Status;
	}


	public String getLocation() {
		return fb_location;
	}

	public void setLocation(String fb_location) {
		this.fb_location = fb_location;
	}
	
	
	public String getCalendar() {
		return fb_calendar;
	}

	public void setCalendar(String fb_calendar) {
		this.fb_calendar = fb_calendar;
	}

	

	public String getBlogs() {
		return blog_title;
	}

	public void setBlogs(String blog_title) {
		this.blog_title = blog_title;
	}

	public String getNews() {
		return news_header;
	}

	public void setNews(String news_header) {
		this.news_header = news_header;
	}
	
	
	public String getStrength() {
		return social_strength;
	}

	public void setStrength(String social_strength) {
		this.social_strength = social_strength;
	}	

	
	
@Override
public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<contact ");
		if (getName()!=null)
		{
			sb.append(" name=\"" + getName()+ "\"");
			
			
		}
		if (getFbStatus()!=null)
		{
			
			sb.append(" status=\"" + getFbStatus()+ "\"");
				
		}
		
		if (getLocation()!=null)
		{
			sb.append(" location=\"" + getLocation()+ "\"");
			
				
		}
		
		if (getCalendar()!=null)
		{
			sb.append(" calendar=\"" + getCalendar()+ "\"");
				
		}
		
		if (getNews()!=null)
		{
			sb.append(" news=\"" + getNews()+ "\"");
							
		}
		
		if (getBlogs()!=null)
		{
			sb.append(" blogs=\"" + getBlogs()+ "\"");		
			
		}
		
		if (getStrength()!=null)
		{
			sb.append(" sstrength=\"" + getStrength()+ "\"");											
		}				
		sb.append(" > </contact>\n");
		return sb.toString();
	}

}
