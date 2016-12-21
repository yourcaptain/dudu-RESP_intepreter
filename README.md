#RESP_Intepreter
REdis Serialization Protocol (RESP) implemented in java.
You can simply construct string commands to RESP commands, and interpret redis response.

By 
```
import net.yesdata.RESP_intepreter.* 
```
you can play with RESP with happy.

#How to format command string to RESP style?#
```
String command = "INFO";
IInterpreter intepreter = new DefaultRespInterpreter();
String sentCommand = intepreter.FormatCommand(command);
```

#How to read and print RESP style response?#
```
IInterpreter intepreter = new DefaultRespInterpreter();
List<IRespNode> respNodes =  intepreter.IntepretResponse(responseBody);
for(IRespNode n : respNodes) {
    System.out.println(n.toRespFormatString());
}
```