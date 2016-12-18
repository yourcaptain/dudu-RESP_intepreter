package com.yesdata.RESP_intepreter;

import java.util.Iterator;
import java.util.List;

public class RespNodeIterator implements Iterator<IRespNode> {
	
	boolean hasNextFlag;
	
	public RespNodeIterator(boolean val, List<IRespNode> lists){
		hasNextFlag = val;
	}
	
	public boolean hasNext(){
		return false;
	}
	
	public IRespNode next(){
		return null;
	}
}
