/**
 * 
 */
package net.yesdata.RESP_intepreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanaiqing
 *
 */
public class ArrayNode extends AbstractRespNode {
	
	private List<IRespNode> value;
	
	public ArrayNode(List<IRespNode> value){
		this.value = value;
	}
	
	public RESP_DATA_TYPE getItemType(){
		return RESP_DATA_TYPE.ARRAY;
	}
	
	public void print(){
		System.out.println(this.toRespFormatString());
	}
	
	public String toRespFormatString(){
		String body = "";
		if (this.value != null) {
			for (IRespNode respNode : this.value) {
				body += respNode.toRespFormatString();
			}
		}
		return ConstStrings.ASTERISK_SIMBOL 
				+ (this.value == null ? String.valueOf(-1) : this.value.size())
				+ ConstStrings.CRLF
				+ body;
	}
	
	public String toConsoleFormatString(){
		String body = "";
		if (this.value != null) {
			for (IRespNode respNode : this.value) {
				body += respNode.toConsoleFormatString();
			}
		}
		return body;
	}
}
