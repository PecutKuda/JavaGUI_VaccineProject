
public class readerFactory {
	
	public static reader getreader(Integer vaccinePrice) {
		 reader reader = null;
		 if (vaccinePrice < 50000) 
		 {
			 reader = new CheapReader(); 
		 }
		 else 
		 {
			 reader = new ExpensiveReader();
		 }
		 
		 return reader;
	}
}
