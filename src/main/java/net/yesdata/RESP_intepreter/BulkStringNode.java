/**
 * 
 */
package net.yesdata.RESP_intepreter;

/**
 * @author yuanaiqing
 *
 */
public class BulkStringNode extends AbstractRespNode {
	
	private String value;
	
	public BulkStringNode(String value){
		this.value = value;
	}
	
	public RESP_DATA_TYPE getItemType(){
		return RESP_DATA_TYPE.BULK_STRING;
	}
	
	public void print(){
		System.out.println(this.toRespFormatString());
	}
	
	public String toRespFormatString(){
		return ConstStrings.DOLLOR_SIMBOL 
				+ (this.value == null ? String.valueOf(-1) : this.value.length())
				+  ConstStrings.CRLF
				+ (this.value == null ? "" : this.value.toString())
				+ (this.value == null ? "" : ConstStrings.CRLF);
	}
	
	public String toConsoleFormatString(){
		return (this.value == null ? "" : this.value.toString())
				+ (this.value == null ? "" : ConstStrings.CRLF);
	}
}
