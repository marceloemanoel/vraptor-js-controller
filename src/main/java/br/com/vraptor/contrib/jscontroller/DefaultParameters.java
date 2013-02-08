package br.com.vraptor.contrib.jscontroller;

public enum DefaultParameters {
	
	LOGGER("logger","org.apache.velocity.runtime.log.NullLogSystem"),
	RESOURCE_LOADER("resourceLoader","classpath");
	
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private DefaultParameters(String name, String value){
		this.name = name;
		this.value = value;
	}

}
