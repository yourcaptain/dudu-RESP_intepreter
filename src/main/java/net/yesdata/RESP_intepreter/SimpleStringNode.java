/**
 * 
 */
package net.yesdata.RESP_intepreter;

/**
 * @author yuanaiqing
 *
 */
public class SimpleStringNode extends AbstractRespNode {
	
	private String value;
	
	public SimpleStringNode(String value){
		this.value = value;
	}
	
	public RESP_DATA_TYPE getItemType(){
		return RESP_DATA_TYPE.SIMPLE_STRING;
	}
	
	public void print(){
		System.out.println(this.toRespFormatString());
	}
	
	public String toRespFormatString(){
		return ConstStrings.PLUS_SIMBOL + this.value + ConstStrings.CRLF;
	}
	
	public String toConsoleFormatString(){
		return this.value + ConstStrings.CRLF;
	}
	
}
