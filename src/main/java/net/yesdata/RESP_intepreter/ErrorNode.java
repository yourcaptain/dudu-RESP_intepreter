/**
 * 
 */
package net.yesdata.RESP_intepreter;

/**
 * @author yuanaiqing
 *
 */
public class ErrorNode extends AbstractRespNode {
	
	private String value;
	
	public ErrorNode(String value){
		this.value = value;
	}
	
	public RESP_DATA_TYPE getItemType(){
		return RESP_DATA_TYPE.ERROR;
	}
	
	public void print(){
		System.out.println(this.toRespFormatString());
	}
	
	public String toRespFormatString(){
		return ConstStrings.SUBTRACT_SIMBOL + this.value + ConstStrings.CRLF;
	}
	
	public String toConsoleFormatString(){
		return this.value + ConstStrings.CRLF;
	}
}
