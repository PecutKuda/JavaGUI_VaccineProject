
public class CheapReader implements reader{

	private String desc;
	@Override
	public String read() {
		// TODO Auto-generated method stub
		desc = getDesc();
		return desc;
	}
	public String getDesc() {
		return "Cheap Product";
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
