package br.com.hd.integrationtests.data.vo.knowledge.v1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoftwareWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private SoftwareEmbeddedVO embedded;

	public SoftwareWrapperVO() {}

	public SoftwareEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(SoftwareEmbeddedVO embedded) {
		this.embedded = embedded;
	}

	public SoftwareWrapperVO(SoftwareEmbeddedVO embedded) {
		this.embedded = embedded;
	}
	
}
