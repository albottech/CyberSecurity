package io.kryptoblocks.msa.network.stream.service.udp;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter @Setter
public class SIEMRecievedJSONModel{
		
		 @JsonProperty("source")
	   	 private Source source;
		 
		 @JsonProperty("fields")
		 private Fields fields;
		 
		 @JsonProperty("data")
		 private Data data;
		 
		 String type; 
		 
		 
		 public SIEMRecievedJSONModel(Source source, Fields fields, Data data, String type) {
			super();
			this.source = source;
			this.fields = fields;
			this.data = data;
			this.type = type;
		}

		public SIEMRecievedJSONModel() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "SIEMRecievedJSONModel [source=" + source + ", fields=" + fields + ", data=" + data + ", type="
					+ type + "]";
		}
		@Getter @Setter
		public static class Source {
			
			 @JsonProperty("id")
			 String sourceId;
			
			 @JsonProperty("name")
			 String sourceName;
			 
			 @JsonProperty("subnet")
			 String sourceSubnet;
			 
			 @JsonProperty("host")
			 String sourceHost;
			
			 public Source() {
				 
			 }
			 
			public Source(String sourceId, String sourceName, String sourceSubnet, String sourceHost) {
				super();
				this.sourceId = sourceId;
				this.sourceName = sourceName;
				this.sourceSubnet = sourceSubnet;
				this.sourceHost = sourceHost;
			}
			@Override
			public String toString() {
				return "Source [sourceId=" + sourceId + ", sourceName=" + sourceName + ", sourceSubnet=" + sourceSubnet
						+ ", sourceHost=" + sourceHost + "]";
			}
			 
			 
		}
		
		static class Fields {
			
			@JsonAnySetter
			Map <String, String> fieldsProperties = new LinkedHashMap<>();

			public Map<String, String> getFieldsProperties() {
				return fieldsProperties;
			}

			public void setFieldsProperties(Map<String, String> fieldsProperties) {
				this.fieldsProperties = fieldsProperties;
			}

			public Fields(Map<String, String> fieldsProperties) {
				super();
				this.fieldsProperties = fieldsProperties;
			}
			
			public Fields() {
				
			}
			
			@Override
			public String toString() {
				return "Fields [fieldsProperties=" + fieldsProperties + "]";
			} 
			
			 
		 }
		
		public static class Data{
				
				@JsonProperty("sig")
			 	private Sig sig;
				
				@JsonProperty("norm_sig")
				private NormSig normSig;
				
				@JsonAnySetter
				Map <String, String> dataProperties = new LinkedHashMap<>(); 
					
				
				public Sig getSig() {
					return sig;
				}


				public void setSig(Sig sig) {
					this.sig = sig;
				}


				public NormSig getNormSig() {
					return normSig;
				}


				public void setNormSig(NormSig normSig) {
					this.normSig = normSig;
				}


				public Map<String, String> getDataProperties() {
					return dataProperties;
				}

				
				public void setDataProperties(Map<String, String> dataProperties) {
					this.dataProperties = dataProperties;
				}


				public Data() {
					
				}
				
				
				@Override
				public String toString() {
					return "Data [sig=" + sig + ", normSig=" + normSig + ", dataProperties=" + dataProperties + "]";
				}


				public Data(Sig sig, NormSig normSig, Map<String, String> dataProperties) {
					super();
					this.sig = sig;
					this.normSig = normSig;
					this.dataProperties = dataProperties;
				}

				@Getter @Setter
				public static class Sig {
					
					@JsonProperty("id")
					private String dataSigId;
					
					@JsonProperty("name")
					private String dataSigName;
					
					public Sig() {
						
					}
					
					public Sig(String dataSigId, String dataSigName) {
						super();
						this.dataSigId = dataSigId;
						this.dataSigName = dataSigName;
					}


					@Override
					public String toString() {
						return "\n dataSigId=" + dataSigId + ",\n dataSigName=" + dataSigName;
					}
				}
				@Getter @Setter
				public static class NormSig {
					
					@JsonProperty("id")
					private String dataNormSigId;
					
					@JsonProperty("name")
					private String dataNormSigName;
					
					public NormSig() {
						
					}
					
					@Override
					public String toString() {
						return "\n dataNormSigId=" + dataNormSigId + ",\n dataNormSigName=" + dataNormSigName;
					}

					public NormSig(String dataNormSigId, String dataNormSigName) {
						super();
						this.dataNormSigId = dataNormSigId;
						this.dataNormSigName = dataNormSigName;
					}
				}
		 }
}

	


