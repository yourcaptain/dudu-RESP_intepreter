/**
 * 
 */
package net.yesdata.RESP_intepreter;

/**
 * @author yuanaiqing
 *
 */
public class IntegerNode extends AbstractRespNode {
	
	private Integer value;
	
	public IntegerNode(Integer value){
		this.value = value;
	}
	
	public RESP_DATA_TYPE getItemType(){
		return RESP_DATA_TYPE.INTEGER;
	}
	
	public void print(){
		System.out.println(this.toRespFormatString());
	}
	
	public String toRespFormatString(){
		return ConstStrings.COLON_SIMBOL + this.value.toString() + ConstStrings.CRLF;
	}
	
	public String toConsoleFormatString(){
		return this.value.toString() + ConstStrings.CRLF;
	}
}
