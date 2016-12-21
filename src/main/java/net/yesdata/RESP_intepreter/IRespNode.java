package net.yesdata.RESP_intepreter;

/**
 * @author yuanaiqing
 * 
 * Interface which identify RESP data type
 *
 */
public interface IRespNode{
	
	/**
	 * @return this node's RESP type
	 */
	RESP_DATA_TYPE getItemType();
	
	/**
	 * print on console
	 */
	void print();
	
	/**
	 * @return redis serial protocal format
	 */
	String toRespFormatString();
	
	/**
	 * @return human readable format
	 */
	String toConsoleFormatString();
}
