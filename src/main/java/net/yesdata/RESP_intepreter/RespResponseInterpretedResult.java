package net.yesdata.RESP_intepreter;

public class RespResponseInterpretedResult {
	private IRespNode RespNode;
	
	private int lastIndex;
	public IRespNode getRespNode() {
		return RespNode;
	}
	public void setRespNode(IRespNode respNode) {
		RespNode = respNode;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
}
