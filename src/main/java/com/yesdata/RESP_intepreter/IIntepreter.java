package com.yesdata.RESP_intepreter;

import java.util.List;

public interface IIntepreter {
	String FormatCommand(String str) throws Exception;
	
	String FormatCommand(List<String> str) throws Exception;
	
	List<IRespNode> IntepretResponse(String response) throws Exception;
}
