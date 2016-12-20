package com.yesdata.RESP_intepreter;

public interface IRespNode{
	RESP_DATA_TYPE getItemType();
	void print();
	String toRespFormatString();
	//RespResponseInterpretedResult InterpretResponse(String response) throws Exception;
}
