
public class ExpensiveReader implements reader{

	private String desc;
	@Override
	public String read() {
		// TODO Auto-generated method stub
		desc = getDesc();
		return desc;
	}
	public String getDesc() {
		return "Expensive Product";
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
