package example.ChatRoom;

import java.util.List;

import tcpEngine.ConnectInfo;
import network.NetworkEngine;

public class UpdateConnectionInfo implements Runnable
{
	private MainGUI gui;
	private NetworkEngine engine;
	public UpdateConnectionInfo(MainGUI gui, NetworkEngine e){
		this.gui = gui;
		this.engine = e;
	}

	@Override
	public void run()
	{
		while(!engine.isClosed()){
			try {
				Thread.sleep(3000);
				List<ConnectInfo> list = engine.getConnectionInfo();
				gui.memberShow.setText("");
				for(ConnectInfo info: list){
					gui.memberShow.append(info.getName()+"\n");
				}
			} catch (Exception e) {
				break;
			}

			
		}

	}

}
