package net.yesdata.RESP_intepreter;

import java.util.ArrayList;
import java.util.List;

public class DefaultRespInterpreter implements IInterpreter{
	public String FormatCommand(String commandString) throws Exception {
		try {
			if (commandString.trim().length() == 0) {
				throw new Exception("Command string should not be blank.");
			}
			
			String[] commands = commandString.split(" ");
			List<IRespNode> respNodes = new ArrayList<IRespNode>();
			for (String command : commands) {
				if (command.trim().length() > 0) {
					IRespNode node = new BulkStringNode(command);
					respNodes.add(node);
				}
			}
			
			IRespNode resultNode = new ArrayNode(respNodes);
			return resultNode.toRespFormatString();
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public String FormatCommand(List<String> commands) throws Exception {
		try {
			String commandString = "";
			for (String command : commands ) {
				commandString += FormatCommand(command);
			}
			
			return commandString;
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public List<IRespNode> IntepretResponse(String response) throws Exception{
		try {
			if (response == null || response.length() == 0){
				throw new Exception("The response could not be blank.");
			}
			
			List<IRespNode> responses = new ArrayList<IRespNode>();
			
			int beginIndex = 0;
			int responseLen = response.length();
			while (beginIndex < responseLen){
				RespResponseInterpretedResult respResponseInterpretedResult = InterpreterDispatcher(response, beginIndex);
				if (respResponseInterpretedResult != null && respResponseInterpretedResult.getRespNode() != null) {
					responses.add(respResponseInterpretedResult.getRespNode());
					beginIndex = respResponseInterpretedResult.getLastIndex();
				}
				else {
					throw new Exception("Error occurred. Interpreting stoped." + FormatErrorTips(beginIndex));
				}
			}
			
			return responses;
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	private RespResponseInterpretedResult InterpreterDispatcher(String response, int beginIndex) throws Exception{
		try {
			String startChar = response.substring(beginIndex, beginIndex + 1);
			if (startChar.equals(ConstStrings.PLUS_SIMBOL)) {
				return InterpretSimpleStringResponse(response, beginIndex);
			}
			else if (startChar.equals(ConstStrings.SUBTRACT_SIMBOL)) {
				return InterpretErrorStringResponse(response, beginIndex);
			}
			else if (startChar.equals(ConstStrings.COLON_SIMBOL)) {
				return InterpretIntegerResponse(response, beginIndex);
			}
			else if (startChar.equals(ConstStrings.DOLLOR_SIMBOL)) {
				return InterpretBulkStringResponse(response, beginIndex);
			}
			else if (startChar.equals(ConstStrings.ASTERISK_SIMBOL)) {
				return InterpretArrayResponse(response, beginIndex);
			}
			else {
				throw new Exception("The first byte '" + startChar + "' is invalid." + FormatErrorTips(startChar, beginIndex));
			}
			
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	private RespResponseInterpretedResult InterpretSimpleStringResponse(String response, int beginIndex) throws Exception {
		try {
			
			//Start to interpret response string
			String startChar = response.substring(beginIndex, beginIndex + 1);
			if (!startChar.equals(ConstStrings.PLUS_SIMBOL)){
				throw new Exception("A Simple String command must be started with char '+'.");
			}
			
			int crlfIndex = response.indexOf(ConstStrings.CRLF, beginIndex);
			String body = response.substring(beginIndex + 1, crlfIndex);
			
			if (body.length() == 0) {
				throw new Exception("No Simple String response founded.");
			}
			
			//Construct return value
			RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
			IRespNode respNode = new SimpleStringNode(body);
			int lastIndex = crlfIndex + ConstStrings.CRLF.length();
			respResponseInterpretedResult.setRespNode(respNode);
			respResponseInterpretedResult.setLastIndex(lastIndex);
			
			return respResponseInterpretedResult;
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	private RespResponseInterpretedResult InterpretErrorStringResponse(String response, int beginIndex) throws Exception {
		try {
			
			//Start to interpret response string
			String startChar = response.substring(beginIndex, beginIndex + 1);
			if (!startChar.equals(ConstStrings.SUBTRACT_SIMBOL)){
				throw new Exception("A Error String type must be started with char '+'.");
			}
			
			int crlfIndex = response.indexOf(ConstStrings.CRLF, beginIndex);
			String body = response.substring(beginIndex + 1, crlfIndex);
			
			if (body.length() == 0) {
				throw new Exception("No Error String response founded.");
			}
			
			//Construct return value
			RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
			IRespNode respNode = new ErrorNode(body);
			int lastIndex = crlfIndex + ConstStrings.CRLF.length();
			respResponseInterpretedResult.setRespNode(respNode);
			respResponseInterpretedResult.setLastIndex(lastIndex);
			
			return respResponseInterpretedResult;
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	private RespResponseInterpretedResult InterpretIntegerResponse(String response, int beginIndex) throws Exception {
		try {
			
			//Start to interpret response string
			String startChar = response.substring(beginIndex, beginIndex + 1);
			if (!startChar.equals(ConstStrings.COLON_SIMBOL)){
				throw new Exception("A Integer type must be started with char '+'.");
			}
			
			int crlfIndex = response.indexOf(ConstStrings.CRLF, beginIndex);
			String body = response.substring(beginIndex + 1, crlfIndex);
			
			if (body.length() == 0) {
				throw new Exception("No Integer response founded.");
			}
			
			int body2Integer = -1;
			try {
				body2Integer = Integer.valueOf(body);
			}
			catch(Exception ex) {
				throw new Exception("The response should be Integer.");
			}
			
			//Construct return value
			RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
			IRespNode respNode = new IntegerNode(body2Integer);
			int lastIndex = crlfIndex + ConstStrings.CRLF.length();
			respResponseInterpretedResult.setRespNode(respNode);
			respResponseInterpretedResult.setLastIndex(lastIndex);
			
			return respResponseInterpretedResult;
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	private RespResponseInterpretedResult InterpretBulkStringResponse(String response, int beginIndex) throws Exception {
		try {
			
			//Start to interpret response string
			String startChar = response.substring(beginIndex, beginIndex + 1);
			if (!startChar.equals(ConstStrings.DOLLOR_SIMBOL)){
				throw new Exception("A Bulk String type must be started with char '+'.");
			}
			
			int crlfIndex = response.indexOf(ConstStrings.CRLF, beginIndex);
			String sizeStr = response.substring(beginIndex + 1, crlfIndex);
			if (sizeStr.length() == 0) {
				throw new Exception("The size part of Bulk String should not be blank.");
			}
			
			//Null
			if (sizeStr.equals("-1")){
				//Construct return value
				RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
				IRespNode respNode = new BulkStringNode(null);
				int lastIndex = crlfIndex + ConstStrings.CRLF.length();
				respResponseInterpretedResult.setRespNode(respNode);
				respResponseInterpretedResult.setLastIndex(lastIndex);
				
				return respResponseInterpretedResult;
			}
			
			//Empty
//			if (sizeStr.equals("0")){
//				//Construct return value
//				RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
//				IRespNode respNode = new BulkStringNode("");
//				int lastIndex = crlfIndex + ConstStrings.CRLF.length();
//				respResponseInterpretedResult.setRespNode(respNode);
//				respResponseInterpretedResult.setLastIndex(lastIndex);
//				
//				return respResponseInterpretedResult;
//			}
			
			//
			int size = -1;
			try {
				size = Integer.valueOf(sizeStr);
			}
			catch(Exception ex) {
				throw new Exception("The size part of Bulk String should not be Integer number.");
			}
			int expectedBodyEndPosition = crlfIndex  + ConstStrings.CRLF.length() + size;
			int expectedBodyEndWithCRLFPosition = crlfIndex  + ConstStrings.CRLF.length() + size + ConstStrings.CRLF.length();
			
			if (response.length() < expectedBodyEndWithCRLFPosition) {
				throw new Exception("The response string length is short than bulk string size.");
			}
			
			int nextCrlfBeginIndex = crlfIndex + ConstStrings.CRLF.length();
			String body = response.substring(nextCrlfBeginIndex, expectedBodyEndPosition);
			
			if (size > 0 && body.length() == 0) {
				throw new Exception("Bulk String body should not be blank while the size is defined as " + sizeStr + ".");
			}
			
			if (!String.valueOf(body.length()).equals(sizeStr)) {
				throw new Exception("The size part (" + sizeStr + ") of Bulk String do not quals to the body's size(" + String.valueOf(body.length()) + ").");
			}
			
			//Construct return value
			RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
			IRespNode respNode = new BulkStringNode(body);
			int lastIndex = expectedBodyEndWithCRLFPosition;
			respResponseInterpretedResult.setRespNode(respNode);
			respResponseInterpretedResult.setLastIndex(lastIndex);
			
			return respResponseInterpretedResult;
		}
		catch (Exception ex) {
			throw ex;
		}
	}
		
	private RespResponseInterpretedResult InterpretArrayResponse(String response, int beginIndex) throws Exception {
		try {
			
			//Start to interpret response string
			String startChar = response.substring(beginIndex, beginIndex + 1);
			if (!startChar.equals(ConstStrings.ASTERISK_SIMBOL)){
				throw new Exception("A Array type must be started with char '+'.");
			}
			
			int crlfIndex = response.indexOf(ConstStrings.CRLF, beginIndex);
			String sizeStr = response.substring(beginIndex + 1, crlfIndex);
			
			//Empty Array
			if (sizeStr.equals("0")) {
				IRespNode respArrayNode = new ArrayNode(new ArrayList<IRespNode>());
				
				//Construct return value
				RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
				respResponseInterpretedResult.setRespNode(respArrayNode);
				int lastIndex = crlfIndex + ConstStrings.CRLF.length();
				respResponseInterpretedResult.setLastIndex(lastIndex);
				
				return respResponseInterpretedResult;
			}
			
			//Nil Array
			if (sizeStr.equals("-1")) {
				IRespNode respArrayNode = new ArrayNode(null);
				
				//Construct return value
				RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
				respResponseInterpretedResult.setRespNode(respArrayNode);
				int lastIndex = crlfIndex + ConstStrings.CRLF.length();
				respResponseInterpretedResult.setLastIndex(lastIndex);
				
				return respResponseInterpretedResult;
			}
			
			//While not empty
			int arrayItemsCount = 0;
			try {
				arrayItemsCount = Integer.valueOf(sizeStr);
			}
			catch (Exception ex) {
				throw new Exception("The size part of Array is invalid.");
			}
			
			int lastIndex = crlfIndex + ConstStrings.CRLF.length();
			List<IRespNode> tempRespNodes = new ArrayList<IRespNode>();
			for (int i=0; i<arrayItemsCount; i++){
				RespResponseInterpretedResult respResponseInterpretedResult = InterpreterDispatcher(response, lastIndex);
				if (respResponseInterpretedResult != null && respResponseInterpretedResult.getRespNode() != null) {
					tempRespNodes.add(respResponseInterpretedResult.getRespNode());
					
					lastIndex = respResponseInterpretedResult.getLastIndex();
				}
			}
			
			IRespNode respArrayNode = new ArrayNode(tempRespNodes);
			
			//Construct return value
			RespResponseInterpretedResult respResponseInterpretedResult = new RespResponseInterpretedResult();
			respResponseInterpretedResult.setRespNode(respArrayNode);
			respResponseInterpretedResult.setLastIndex(lastIndex);
			
			return respResponseInterpretedResult;
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	private String FormatErrorTips(String str, int index){
		return "Error occurs near by '" + str + "' at position of '" + String.valueOf(index) + "'.";
	}
	
	private String FormatErrorTips(int index){
		return "Error occurs at position of '" + String.valueOf(index) + "'.";
	}
}
