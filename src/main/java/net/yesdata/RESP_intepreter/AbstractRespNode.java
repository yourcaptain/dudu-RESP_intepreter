package net.yesdata.RESP_intepreter;

public abstract class AbstractRespNode implements IRespNode{
	public abstract void print();
	
	public abstract RESP_DATA_TYPE getItemType();
	
	public abstract String toRespFormatString();
	
	public abstract String toConsoleFormatString();
	
}
