package ltu.org;

import java.util.List;



import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxFeedParser extends BaseFeedParser {

	protected SaxFeedParser(String feedUrl){
		super(feedUrl);
	}
	
	
	/*public List<Group> parse() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			GroupHandler handler = new GroupHandler();
			parser.parse(this.getInputStream(), handler);
			return handler.getGroups();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}*/
	@Override
	public List<Meeting> parse() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			MeetingHandler handler = new MeetingHandler();
			parser.parse(this.getInputStream(), handler);
			return handler.getMeetings();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}