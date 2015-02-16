package com.yibairun.api.operations;


public interface YibaiRunApi {
	public abstract void setToken(String token);
	public abstract void setDeviceId(String deviceId);
	public abstract UserOperations userOperations();
	public abstract ProductOperations productOperations();

}
