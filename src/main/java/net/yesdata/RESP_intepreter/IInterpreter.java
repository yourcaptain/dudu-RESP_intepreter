package net.yesdata.RESP_intepreter;

import java.util.List;

/**
 * @author yuanaiqing
 * 
 * Interface which defines the RESP string parsing scope
 *
 */
public interface IInterpreter {
	
	/**
	 * @param str single command, just like 'INFO' or 'GET akey'
	 * @return RESP style formatted string
	 * @throws Exception parsing exception
	 */
	String FormatCommand(String str) throws Exception;
	
	/**
	 * @param str multiple commands, just like list which includes strings of 'INFO' and 'GET akey'
	 * @return RESP style formatted string
	 * @throws Exception parsing exception
	 */
	String FormatCommand(List<String> str) throws Exception;
	
	/**
	 * @param response string that responsed from redis server
	 * @return RESP data type nodes
	 * @throws Exception parsing exception
	 */
	List<IRespNode> IntepretResponse(String response) throws Exception;
}
