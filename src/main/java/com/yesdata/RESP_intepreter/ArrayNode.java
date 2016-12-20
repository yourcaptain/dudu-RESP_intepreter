/**
 * 
 */
package com.yesdata.RESP_intepreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanaiqing
 *
 */
public class ArrayNode extends AbstractRespNode {
	
	private List<IRespNode> value;
	
	public ArrayNode(){
		this.value = new ArrayList<IRespNode>();
	}
	
	public ArrayNode(List<IRespNode> value){
		this.value = value;
	}
	
	public void AddItem(IRespNode value){
		this.value.add(value);
	}
	
	public List<IRespNode> GetAllItems() {
		return this.value;
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
}
