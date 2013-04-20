package ltu.org;

public class Meeting {
	
	private String meetingTitle;
	
	private String meetingOwner;
	
	private String meetingId;
	

	public String getTitle() {
		return meetingTitle;
	}

	public void setTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle.trim();
	}
	// getters and setters omitted for brevity 
	
	public String getOwner() {
		return meetingOwner;
	}

	public void setOwner(String meetingOwner) {
		this.meetingOwner = meetingOwner.trim();
	}
	
	public String getMeetingID() {
		return meetingId;
	}

	public void setMeetingID(String meetingId) {
		this.meetingId = meetingId.trim();
	}
		
	public Meeting copy(){
		Meeting copy = new Meeting();
		copy.meetingTitle = meetingTitle;		
		copy.meetingOwner = meetingOwner;
		copy.meetingId = meetingId;
		return copy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Meeting Title: ");
		sb.append(meetingTitle);
		sb.append('\n');
		sb.append("Owner: ");
		sb.append(meetingOwner);
		sb.append('\n');
		sb.append("Meeitn id: ");
		sb.append(meetingId);
		
		return sb.toString();
	}
}
