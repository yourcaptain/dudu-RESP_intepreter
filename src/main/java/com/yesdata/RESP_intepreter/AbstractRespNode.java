package com.yesdata.RESP_intepreter;

public abstract class AbstractRespNode implements IRespNode{
	public abstract void print();
	
	public abstract String getItemType();
	
	public abstract void setItemType();
	
}
