package newapp.global.configuration;

import lombok.Data;

@Data
public class ServerInfo {
	private String mac;
	ServerInfo(String mac) {
		this.mac = mac;
	}
}
