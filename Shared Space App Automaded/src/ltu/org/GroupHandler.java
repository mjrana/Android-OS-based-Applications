package ltu.org;
import java.util.ArrayList;



import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static ltu.org.BaseFeedParser.*;

public class GroupHandler extends DefaultHandler{
	private List<Group> groups;
	private Group currentGroups;
	private StringBuilder builder;
	
	public List<Group> getGroups(){
		return this.groups;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentGroups != null){
			if (localName.equalsIgnoreCase(GroupTITLE)){
				currentGroups.setTitle(builder.toString());
			} else if (localName.equalsIgnoreCase(GroupMEMBERS)){
				currentGroups.setDescription(builder.toString());
			
			} else if (localName.equalsIgnoreCase(ITEM)){
				groups.add(currentGroups);
			}
			builder.setLength(0);	
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		groups = new ArrayList<Group>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase(ITEM)){
			this.currentGroups = new Group();
		}
	}
}