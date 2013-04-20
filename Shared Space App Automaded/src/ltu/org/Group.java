package ltu.org;


public class Group {

	
	private String groupTitle;
	
	private String GroupMembers;
	

	public String getTitle() {
		return groupTitle;
	}

	public void setTitle(String groupTitle) {
		this.groupTitle = groupTitle.trim();
	}
	// getters and setters omitted for brevity 
	
	public String getDescription() {
		return GroupMembers;
	}

	public void setDescription(String GroupMembers) {
		this.GroupMembers = GroupMembers.trim();
	}
		
	public Group copy(){
		Group copy = new Group();
		copy.groupTitle = groupTitle;		
		copy.GroupMembers = GroupMembers;		
		return copy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Group Title: ");
		sb.append(groupTitle);
		sb.append('\n');
		sb.append("Members: ");
		sb.append(GroupMembers);
		return sb.toString();
	}
}
