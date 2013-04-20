package ltu.org;



public abstract class FeedParserFactory {
	
	static String feedUrl = "http://46.162.92.139/~mjrana/notes/data/users/test.xml";
	
	public static String TransUname = "test";
	
	public static FeedParser getParser(){
		String Uname="medial.ltu";
		return getParser(ParserType.ANDROID_SAX, Uname);		
	}
	
	public static FeedParser getParser(ParserType type, String Uname){
		switch (type){
			case SAX:
				
				// office network
				//String UfeedUrl = "http://172.16.0.24/~mjrana/notes/data/users/"+TransUname+".xml";
				
				//server computer l-sls0483d.research.ltu.se:8080
				String UfeedUrl = "http://l-sls0483d.research.ltu.se/userdata/notes/data/users/"+TransUname+".xml";
				
				// home network
				//String UfeedUrl = "http://192.168.0.101/~mjrana/notes/data/users/"+TransUname+".xml";
				
				return new SaxFeedParser(UfeedUrl);
				default: return null;
		}
		
	}
}
