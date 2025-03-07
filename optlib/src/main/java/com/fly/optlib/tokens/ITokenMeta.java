package com.fly.optlib.tokens;

public interface ITokenMeta {

	public String getName();

	public String getOrganisation();
	
	public int getTokenType();
	
	public String getSecretBase32();
	
	public int getDigits();
	
	public int getTimeStep();
	
	public int getCounter();
}
